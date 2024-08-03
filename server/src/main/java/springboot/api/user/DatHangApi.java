package springboot.api.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springboot.entity.ChiTietHoaDon;
import springboot.entity.HoaDon;
import springboot.entity.custom.DonHangRequest;
import springboot.repository.ChiTietHoaDonRepo;
import springboot.repository.HoaDonRepo;

import java.util.List;

@RestController
@RequestMapping("/user/dat-hang")
public class DatHangApi {

    @Autowired
    private HoaDonRepo hoaDonRepo;

    @Autowired
    private ChiTietHoaDonRepo chiTietHoaDonRepo;

    @GetMapping("/test")
    public String testApi() {
        return "API is working";
    }

    @PostMapping("/tao-don-hang")
    public ResponseEntity<?> taoDonHang(@RequestBody DonHangRequest donHangRequest) {
        HoaDon newHoaDon = hoaDonRepo.save(donHangRequest.getHoaDon());
        List<ChiTietHoaDon> chiTietHoaDonList = donHangRequest.getChiTietHoaDonList();
        chiTietHoaDonList.forEach(chiTietHoaDon -> chiTietHoaDon.setMaHoaDon(newHoaDon));
        chiTietHoaDonRepo.saveAll(chiTietHoaDonList);
        return ResponseEntity.status(201).body(newHoaDon.getId());
    }
}
