package springboot.api.user;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springboot.entity.HoaDon;
import springboot.entity.dto.ChiTietHoaDonDto;
import springboot.entity.dto.HoaDonDto;
import springboot.mapper.ChiTietHoaDonMapper;
import springboot.mapper.HoaDonMapper;
import springboot.repository.ChiTietHoaDonRepo;
import springboot.repository.HoaDonRepo;

import java.util.List;

@RestController
@RequestMapping("/user/don-hang")
public class DonHangApi {
    @Autowired
    private HoaDonRepo hoaDonRepo;

    @Autowired
    private HoaDonMapper hoaDonMapper;

    @Autowired
    private ChiTietHoaDonRepo chiTietHoaDonRepo;

    @Autowired
    private ChiTietHoaDonMapper chiTietHoaDonMapper;

    @GetMapping("/")
    public List<HoaDonDto> getHoaDonsByUser(@RequestParam String user) {
        List<HoaDon> hoaDonList = hoaDonRepo.getHoaDonsByTenUsers_TenNguoiDung(user);
        hoaDonList.forEach(hoaDon -> hoaDon.setHinhAnhSanPhamList(chiTietHoaDonRepo.getHinhAnhsByMaHoaDon(hoaDon.getId())));
        return hoaDonList.stream().map(hoaDonMapper::toDto).toList();
    }

    @GetMapping("/hd")
    public HoaDonDto getHoaDonById(@RequestParam Long id) {
        return hoaDonMapper.toDto(hoaDonRepo.findById(id).orElse(null));
    }

    @GetMapping("/cthd")
    public List<ChiTietHoaDonDto> getCthdByHd(@RequestParam Long id) {
        return chiTietHoaDonRepo.findChiTietHoaDonsByMaHoaDon_Id(id).stream().map(chiTietHoaDonMapper::toDto).toList();
    }

    @Transactional
    @PatchMapping("/huy")
    public ResponseEntity<?> huyDonHang(@RequestParam Long id) {
        hoaDonRepo.huyDonHang(id);
        return ResponseEntity.status(200).body("Updated");
    }
}
