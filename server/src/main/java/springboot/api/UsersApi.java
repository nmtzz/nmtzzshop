package springboot.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import springboot.auth.NguoiDungDetailService;
import springboot.entity.Users;
import springboot.entity.dto.FormDangNhap;
import springboot.jwt.JwtUtil;
import springboot.repository.UsersRepo;

import java.util.Optional;

@RequestMapping("/api/nguoi-dung")
@RestController
public class UsersApi {
    @Autowired
    private UsersRepo usersRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private NguoiDungDetailService nguoiDungDetailService;

    @PostMapping("/dang-ky")
    public ResponseEntity<String> dangKy(@RequestBody Users users) {
        if (usersRepo.existsByTenNguoiDung(users.getTenNguoiDung())) {
            return ResponseEntity.status(409).body("Error");
        }
        users.setVaiTro("USER");
        users.setMatKhau(passwordEncoder.encode(users.getMatKhau()));
        usersRepo.save(users);
        return ResponseEntity.status(201).body("Created");
    }

    @PostMapping("/dang-nhap")
    public ResponseEntity<?> dangNhap(@RequestBody FormDangNhap formDangNhap) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken
                (formDangNhap.tenNguoiDung(), formDangNhap.matKhau()));
        if (authentication.isAuthenticated()) {
            final String token = jwtUtil.generateToken(nguoiDungDetailService.loadUserByUsername(formDangNhap.tenNguoiDung()));
            ResponseCookie cookie = ResponseCookie.from("token", token)
                    .httpOnly(true)
                    .maxAge(168 * 60 * 60)
                    .path("/")
                    .build();
            return ResponseEntity.ok().header("Set-Cookie", cookie.toString()).body(token);
        } else {
            return ResponseEntity.status(403).body("Error");
        }
    }

    @GetMapping("/info")
    public ResponseEntity<?> getTenNguoiDung(Authentication authentication) {
        if (authentication == null) {
            return ResponseEntity.notFound().build();
        }
        Optional<Users> users = usersRepo.findNguoiDungByTenNguoiDung(authentication.getName());
        if (users.isPresent()) {
            users.get().setMatKhau("");
            users.get().setEmail("");
            return ResponseEntity.ok().body(users.get());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/dang-xuat")
    public ResponseEntity<String> dangXuat() {
        ResponseCookie cookie = ResponseCookie.from("token", "")
                .httpOnly(true)
                .maxAge(0)
                .path("/")
                .build();
        return ResponseEntity.ok().header("Set-Cookie", cookie.toString()).body("OK");
    }
}
