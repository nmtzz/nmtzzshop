package springboot.api.admin;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springboot.entity.dto.HoaDonDto;
import springboot.mapper.HoaDonMapper;
import springboot.repository.HoaDonRepo;
import springboot.repository.forview.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class QuanLyChung {
    @Autowired
    private DoanhThuHangThangRepo doanhThuHangThangRepo;
    @Autowired
    private DoanhThuThangHienTaiRepo doanhThuThangHienTaiRepo;
    @Autowired
    private DonHangTheoThangRepo donHangTheoThangRepo;
    @Autowired
    private DonHangTrongNgayRepo donHangTrongNgayRepo;
    @Autowired
    private SanPhamBanDuocTheoThangRepo sanPhamBanDuocTheoThangRepo;
    @Autowired
    private SanPhamBanDuocTrongNgayRepo sanPhamBanDuocTrongNgayRepo;
    @Autowired
    private TongKhachHangRepo tongKhachHangRepo;
    @Autowired
    private HoaDonMapper hoaDonMapper;
    @Autowired
    private HoaDonRepo hoaDonRepo;
    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("Hello Admin");
    }

    @GetMapping("/thong-ke")
    public Map<String, Object> thongKe() {
        Map<String, Object> payload = new HashMap<>();
        payload.put("doanhThuHangThang", doanhThuHangThangRepo.findAll());
        payload.put("doanhThuThangHienTai", doanhThuThangHienTaiRepo.findAll().getFirst());
        payload.put("donHangTheoThang", donHangTheoThangRepo.findAll());
        payload.put("donHangTrongNgay", donHangTrongNgayRepo.findAll().getFirst());
        payload.put("sanPhamBanDuocTheoThang", sanPhamBanDuocTheoThangRepo.findAll());
        payload.put("sanPhamBanDuocTrongNgay", sanPhamBanDuocTrongNgayRepo.findAll().getFirst());
        payload.put("tongKhachHang", tongKhachHangRepo.findAll().getFirst());
        return payload;
    }

    @GetMapping("/cho-huy")
    public List<HoaDonDto> getHoaDonChoHuy() {
        return hoaDonRepo.timHoaDonChoHuy().stream().map(hoaDonMapper::toDto).toList();
    }

    @GetMapping("/hoa-don")
    public List<HoaDonDto> getAllHoaDon() {
        return hoaDonRepo.findAll().stream().map(hoaDonMapper::toDto).toList();
    }

    @Transactional
    @PatchMapping("/update-trang-thai-hoa-don")
    public ResponseEntity<?> updateTrangThaiHoaDon(@RequestParam Long id) {
        hoaDonRepo.updateTrangThai(id);
        return ResponseEntity.status(200).body("Updated");
    }
}
