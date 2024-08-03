package springboot.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springboot.entity.DanhMuc;
import springboot.repository.DanhMucRepo;

import java.util.List;

@RestController
@RequestMapping("/api/danh-muc")
public class DanhMucApi {
    @Autowired
    private DanhMucRepo danhMucRepo;

    @RequestMapping("/")
    public List<DanhMuc> getAll() {
        return danhMucRepo.findAll();
    }

    @GetMapping("/get")
    public DanhMuc getHinhAnhDanhMuc(@RequestParam Long id) {
        return danhMucRepo.findById(id).get();
    }
}
