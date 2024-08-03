package springboot.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springboot.entity.NhanXetSanPham;
import springboot.entity.dto.NhanXetSanPhamDto;
import springboot.mapper.NhanXetSanPhamMapper;
import springboot.repository.NhanXetSanPhamRepo;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/nhan-xet")
public class NhanXetSanPhamApi {
    @Autowired
    private NhanXetSanPhamMapper nhanXetSanPhamMapper;

    @Autowired
    private NhanXetSanPhamRepo nhanXetSanPhamRepo;

    @GetMapping("/")
    public List<NhanXetSanPhamDto> getNhanXet(@RequestParam Long maSanPham, @RequestParam(defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, 5);
        return nhanXetSanPhamRepo.findNhanXetSanPhamsByMaSanPham_Id(maSanPham, pageable).stream().map(nhanXetSanPhamMapper::toDto).toList();
    }

    @PostMapping("/")
    public ResponseEntity<?> addNhanXet(@RequestBody NhanXetSanPham nhanXetSanPham) {
        nhanXetSanPham.setThoiGianNhanXet(LocalDate.now());
        nhanXetSanPhamRepo.save(nhanXetSanPham);
        return ResponseEntity.status(201).build();
    }
}
