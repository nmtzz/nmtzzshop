package springboot.api.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springboot.gcloud.GoogleCloudStorage;
import springboot.entity.DanhMuc;
import springboot.repository.DanhMucRepo;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RequestMapping("/admin/danh-muc")
@RestController
public class QuanLyDanhMuc {

    @Autowired
    private DanhMucRepo danhMucRepo;

    @Autowired
    private GoogleCloudStorage googleCloudStorage;

    @GetMapping("/")
    public List<DanhMuc> getAllDanhMuc() {
        return danhMucRepo.findAll();
    }

    @PostMapping("/them-danh-muc")
    public ResponseEntity<?> themDanhMuc(@RequestParam Optional<Long> id, @RequestParam String tenDanhMuc, String anhDanhMuc, @RequestPart Optional<MultipartFile> file) throws IOException {
        DanhMuc danhMuc = new DanhMuc();
        id.ifPresent(danhMuc::setId);
        danhMuc.setTenDanhMuc(tenDanhMuc);
        danhMuc.setAnhDanhMuc(anhDanhMuc);
        if (file.isPresent() && !file.get().isEmpty()) {
            String url = googleCloudStorage.uploadAndGetUrl(file.get());
            danhMuc.setAnhDanhMuc(url);
        }
        danhMucRepo.save(danhMuc);
        return ResponseEntity.status(201).body(danhMuc.getAnhDanhMuc());
    }

    @GetMapping("/tim")
    public ResponseEntity<?> getDanhMucById(@RequestParam Long id) {
        DanhMuc danhMuc = danhMucRepo.findById(id).get();
        return ResponseEntity.ok(danhMuc);
    }

    @DeleteMapping("/xoa")
    public ResponseEntity<?> deleteDanhMucById(@RequestParam Long id) {
        danhMucRepo.deleteById(id);
        return ResponseEntity.status(200).body("Deleted");
    }
}
