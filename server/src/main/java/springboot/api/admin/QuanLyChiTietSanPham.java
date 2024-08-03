package springboot.api.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springboot.entity.ChiTietSanPham;
import springboot.entity.dto.ChiTietSanPhamDto;
import springboot.mapper.ChiTietSanPhamMapper;
import springboot.repository.ChiTietSanPhamRepo;

import java.util.List;

@RestController
@RequestMapping("/admin/chi-tiet-san-pham")
public class QuanLyChiTietSanPham {
    @Autowired
    private ChiTietSanPhamMapper chiTietSanPhamMapper;

    @Autowired
    private ChiTietSanPhamRepo chiTietSanPhamRepo;

    @GetMapping("/ma-sp")
    public List<ChiTietSanPhamDto> getByMaSp(@RequestParam Long maSanPham) {
        return chiTietSanPhamRepo.findChiTietSanPhamsByMaSanPham_Id(maSanPham).stream().map(chiTietSanPhamMapper::toChiTietSanPhamDto).toList();
    }

    @PostMapping("/them")
    public ResponseEntity<?> themChiTietSp(@RequestBody ChiTietSanPham chiTietSanPham) {
        chiTietSanPhamRepo.save(chiTietSanPham);
        return ResponseEntity.status(201).build();
    }

    @DeleteMapping("/xoa")
    public ResponseEntity<?> xoaChiTietSp(@RequestParam Long id) {
        chiTietSanPhamRepo.deleteById(id);
        return ResponseEntity.status(200).build();
    }
}
