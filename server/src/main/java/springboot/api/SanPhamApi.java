package springboot.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springboot.entity.dto.SanPhamDto;
import springboot.mapper.SanPhamMapper;
import springboot.repository.SanPhamRepo;

import java.util.List;
import java.util.Optional;

@RequestMapping("/api/san-pham")
@RestController
public class SanPhamApi {
    @Autowired
    private SanPhamRepo sanPhamRepo;
    @Autowired
    private SanPhamMapper sanPhamMapper;

    @GetMapping("/")
    public List<SanPhamDto> getSanPhamDto(@RequestParam Optional<Long> maDanhMuc, @RequestParam Optional<Integer> rownum) {
        if (maDanhMuc.isPresent()) {
            List<SanPhamDto> sanPhamDtoList = sanPhamRepo.findByDanhMuc(maDanhMuc.get()).stream().map(sanPhamMapper::toSanPhamDto).toList();
            sanPhamDtoList.forEach(sanPhamDto -> sanPhamDto.setGiaChung(sanPhamRepo.timGiaChung(sanPhamDto.getId())));
            return sanPhamDtoList;
        }
        List<SanPhamDto> sanPhamDtoList = sanPhamRepo.findTop(rownum.orElse(8)).stream().map(sanPhamMapper::toSanPhamDto).toList();
        sanPhamDtoList.forEach(sanPhamDto -> sanPhamDto.setGiaChung(sanPhamRepo.timGiaChung(sanPhamDto.getId())));
        return sanPhamDtoList;
    }

    @GetMapping("/page")
    public Page<SanPhamDto> getSanPhamDtoPage(@RequestParam Long maDanhMuc, @RequestParam(defaultValue = "0") Integer page) {
        Pageable pageable = PageRequest.of(page, 6);
        Page<SanPhamDto> sanPhamDtoPage = sanPhamRepo.findSanPhamsByDanhMuc_Id(maDanhMuc, pageable).map(sanPhamMapper::toSanPhamDto);
        sanPhamDtoPage.forEach(sanPhamDto -> sanPhamDto.setGiaChung(sanPhamRepo.timGiaChung(sanPhamDto.getId())));
        return sanPhamDtoPage;
    }

    @GetMapping("/gia-cao-nhat")
    public Long getGiaCaoNhat(@RequestParam Long maDanhMuc) {
        return sanPhamRepo.timGiaCaoNhat(maDanhMuc);
    }

    @GetMapping("/tim-kiem")
    public Page<SanPhamDto> timKiem(@RequestParam String keyword, @RequestParam Long maDanhMuc, @RequestParam(defaultValue = "0") Integer page) {
        Pageable pageable = PageRequest.of(page, 6);
        Page<SanPhamDto> sanPhamDtoPage = sanPhamRepo.findSanPhamsByTenSanPhamContainingIgnoreCaseAndDanhMuc_Id(keyword, maDanhMuc, pageable).map(sanPhamMapper::toSanPhamDto);
        sanPhamDtoPage.forEach(sanPhamDto -> sanPhamDto.setGiaChung(sanPhamRepo.timGiaChung(sanPhamDto.getId())));
        return sanPhamDtoPage;
    }
    @GetMapping("/related-sp")
    public List<SanPhamDto> getRelatedSp(@RequestParam Long maDanhMuc) {
        return sanPhamRepo.findByDanhMucLimit5(maDanhMuc).stream().map(sanPhamMapper::toSanPhamDto).toList();
    }
}
