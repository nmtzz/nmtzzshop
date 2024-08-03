package springboot.mapper;

import javax.annotation.processing.Generated;

import springboot.entity.DanhMuc;
import springboot.entity.SanPham;
import springboot.entity.dto.SanPhamDto;

@Generated(
        value = "org.mapstruct.ap.MappingProcessor",
        date = "2024-07-20T21:31:22+0700",
        comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.3 (BellSoft)"
)
public class SanPhamMapImpl implements SanPhamMap {

    @Override
    public SanPhamDto toSanPhamDto(SanPham sanPham) {
        if (sanPham == null) {
            return null;
        }

        SanPhamDto sanPhamDto = new SanPhamDto();

        sanPhamDto.setTenDanhMuc(sanPhamDanhMucTenDanhMuc(sanPham));
        sanPhamDto.setId(sanPham.getId());
        sanPhamDto.setTenSanPham(sanPham.getTenSanPham());
        sanPhamDto.setHinhAnh(sanPham.getHinhAnh());
        sanPhamDto.setMoTa(sanPham.getMoTa());
        sanPhamDto.setGiaChung(sanPham.getGiaChung());

        return sanPhamDto;
    }

    private String sanPhamDanhMucTenDanhMuc(SanPham sanPham) {
        if (sanPham == null) {
            return null;
        }
        DanhMuc danhMuc = sanPham.getDanhMuc();
        if (danhMuc == null) {
            return null;
        }
        String tenDanhMuc = danhMuc.getTenDanhMuc();
        if (tenDanhMuc == null) {
            return null;
        }
        return tenDanhMuc;
    }
}
