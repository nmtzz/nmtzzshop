package springboot.api.user;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import springboot.entity.Users;
import springboot.entity.dto.FormDangNhap;
import springboot.repository.UsersRepo;

@RestController
@RequestMapping("/user/info")
public class UserInfoApi {
    @Autowired
    private UsersRepo usersRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @GetMapping("/")
    public Users getUserByUsername(@RequestParam String username) {
        return usersRepo.findNguoiDungByTenNguoiDung(username).orElse(null);
    }

//    @GetMapping("/auth-password")
//    public ResponseEntity<?> authPassword(@RequestBody FormDangNhap formDangNhap) {
//        if (passwordEncoder.matches(formDangNhap.matKhau(), usersRepo.findNguoiDungByTenNguoiDung(formDangNhap.tenNguoiDung()).get().getMatKhau())) {
//            return ResponseEntity.ok("Success");
//        }
//        return ResponseEntity.status(403).body("Error");
//    }

    @PatchMapping("/doi-mat-khau")
    @Transactional
    public ResponseEntity<?> doiMatKhau(@RequestParam String username, @RequestParam String matKhauCu, @RequestParam String matKhauMoi) {
        if (!passwordEncoder.matches(matKhauCu, usersRepo.findNguoiDungByTenNguoiDung(username).get().getMatKhau())) {
            return ResponseEntity.status(403).body("Error");
        }
        usersRepo.updateMatKhau(passwordEncoder.encode(matKhauMoi), username);
        return ResponseEntity.status(200).body("Updated");
//        users.setMatKhau(passwordEncoder.encode(users.getMatKhau()));
//        usersRepo.save(users);
//        return ResponseEntity.status(200).body("Updated");
    }
}
