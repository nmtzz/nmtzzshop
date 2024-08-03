package springboot.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springboot.entity.ChiTietSanPham;
import springboot.entity.dto.ChiTietSanPhamDto;
import springboot.mapper.ChiTietSanPhamMapper;
import springboot.repository.ChiTietSanPhamRepo;

import java.util.List;

@RestController
@RequestMapping("/api/ctsp")
public class ChiTietSanPhamApi {
    @Autowired
    private ChiTietSanPhamRepo chiTietSanPhamRepo;
    @Autowired
    private ChiTietSanPhamMapper chiTietSanPhamMapper;

    @GetMapping("/")
    public ChiTietSanPhamDto getChiTietSanPham(@RequestParam Integer maSanPham) {
        ChiTietSanPham chiTietSanPham = chiTietSanPhamRepo.findByMaSanPham(maSanPham);
        return chiTietSanPhamMapper.toChiTietSanPhamDto(chiTietSanPham);
    }

    @GetMapping("/ct")
    public ChiTietSanPhamDto ctsp(@RequestParam Long id) {
        ChiTietSanPham chiTietSanPham = chiTietSanPhamRepo.findChiTietSanPhamById(id);
        return chiTietSanPhamMapper.toChiTietSanPhamDto(chiTietSanPham);
    }

    @GetMapping("/mau-sac")
    List<String> getMauSac(@RequestParam Integer maSanPham) {
        return chiTietSanPhamRepo.getMauSac(maSanPham);
    }

    @GetMapping("/kich-thuoc")
    List<String> getKichThuoc(@RequestParam Integer maSanPham) {
        return chiTietSanPhamRepo.getKichThuoc(maSanPham);
    }

    @GetMapping("/chi-tiet")
    public ChiTietSanPhamDto getChiTietSanPham(@RequestParam Integer maSanPham, @RequestParam String mauSac, @RequestParam String kichThuoc) {
        ChiTietSanPham chiTietSanPham = chiTietSanPhamRepo.getChiTietSanPham(maSanPham, mauSac, kichThuoc);
        return chiTietSanPhamMapper.toChiTietSanPhamDto(chiTietSanPham);
    }
}
