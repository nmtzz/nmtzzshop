package springboot.api;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springboot.entity.GioHang;
import springboot.entity.dto.GioHangDto;
import springboot.mapper.GioHangMapper;
import springboot.repository.GioHangRepo;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/gio-hang")
public class GioHangApi {

    @Autowired
    private GioHangRepo gioHangRepo;

    @Autowired
    private GioHangMapper gioHangMapper;

    @GetMapping("/")
    public List<GioHangDto> getGioHang(@RequestParam String tenNguoiDung) {
        return gioHangRepo.findGioHangsByTenUsers_TenNguoiDung(tenNguoiDung).stream()
                .map(gioHangMapper::toDto)
                .toList();
    }

    @PostMapping("/")
    public ResponseEntity<?> add(@RequestBody GioHang gioHang) {
        Optional<GioHang> checkGioHang = gioHangRepo.findGioHangByChiTietSanPhamAndTenUsers(
                gioHang.getChiTietSanPham(), gioHang.getTenUsers());
        checkGioHang.ifPresent(hang -> gioHangRepo.delete(hang));
        gioHangRepo.save(gioHang);
        return ResponseEntity.status(201).body("Created");
    }

    @DeleteMapping("/")
    @Transactional
    public ResponseEntity<?> delete(@RequestParam Long id) {
        gioHangRepo.deleteGioHangById(id);
        return ResponseEntity.status(200).body("Deleted");
    }

    @PatchMapping("/")
    @Transactional
    public ResponseEntity<?> updateSoLuong(@RequestParam Long soLuong, @RequestParam Long maGioHang) {
        gioHangRepo.updateSoLuong(soLuong, maGioHang);
        return ResponseEntity.status(200).body("Updated");
    }
}
