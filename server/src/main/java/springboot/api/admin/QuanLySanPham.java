package springboot.api.admin;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springboot.aws.S3Service;
import springboot.entity.DanhMuc;
import springboot.entity.SanPham;
import springboot.entity.dto.SanPhamDto;
import springboot.mapper.SanPhamMapper;
import springboot.repository.SanPhamRepo;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin/san-pham")
public class QuanLySanPham {
    @Autowired
    private SanPhamRepo sanPhamRepo;

    @Autowired
    private SanPhamMapper sanPhamMapper;

    @Autowired
    private S3Service s3Service;

    @RequestMapping("/")
    public List<SanPhamDto> getAllSp() {
        return sanPhamRepo.findAll().stream().map(sanPhamMapper::toSanPhamDto).toList();
    }
    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestParam Optional<Long> id, @RequestParam String moTa, @RequestParam Long danhMuc, @RequestParam String tenSanPham, @RequestParam Optional<MultipartFile> file, @RequestParam Optional<String> hinhAnh) {
        if (file.isEmpty() && hinhAnh.isEmpty()) {
            return ResponseEntity.status(400).body("Không có hình ảnh");
        }
        DanhMuc danhMuc1 = new DanhMuc();
        danhMuc1.setId(danhMuc);
        SanPham sanPham = new SanPham();
        id.ifPresent(sanPham::setId);
        if (file.isPresent()) {
            System.out.println(file);
            String url = s3Service.uploadAndGetUrl(file.get());
            sanPham.setHinhAnh(url);
        } else {
            System.out.println("No file");
            hinhAnh.ifPresent(sanPham::setHinhAnh);
        }
        sanPham.setDanhMuc(danhMuc1);
        sanPham.setMoTa(moTa);
        sanPham.setTenSanPham(tenSanPham);
        sanPhamRepo.save(sanPham);
        return ResponseEntity.status(201).body(sanPham.getHinhAnh());
    }
    @GetMapping("/sp")
    public SanPhamDto getSanPhamDto( @RequestParam Long id) {
        return sanPhamMapper.toSanPhamDto(sanPhamRepo.findById(id).get());
    }

    @Transactional
    @PostMapping("/delete")
    public ResponseEntity<?> delete(@RequestBody List<SanPham> sanPhamList) {
        sanPhamRepo.deleteAll(sanPhamList);
        return ResponseEntity.status(200).body("Deleted");
    }

    @Transactional
    @DeleteMapping("/delete-one")
    public ResponseEntity<?> delete(@RequestParam  Long id) {
        sanPhamRepo.deleteById(id);
        return ResponseEntity.status(200).body("Deleted");
    }
}
