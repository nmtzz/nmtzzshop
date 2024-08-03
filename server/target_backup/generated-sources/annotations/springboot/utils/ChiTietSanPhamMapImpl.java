package springboot.mapper;

import javax.annotation.processing.Generated;

import springboot.entity.ChiTietSanPham;
import springboot.entity.DanhMuc;
import springboot.entity.SanPham;
import springboot.entity.dto.ChiTietSanPhamDto;

@Generated(
        value = "org.mapstruct.ap.MappingProcessor",
        date = "2024-07-20T21:31:22+0700",
        comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.3 (BellSoft)"
)
public class ChiTietSanPhamMapImpl implements ChiTietSanPhamMap {

    @Override
    public ChiTietSanPhamDto toChiTietSanPhamDto(ChiTietSanPham chiTietSanPham) {
        if (chiTietSanPham == null) {
            return null;
        }

        ChiTietSanPhamDto chiTietSanPhamDto = new ChiTietSanPhamDto();

        chiTietSanPhamDto.setTenSanPham(chiTietSanPhamMaSanPhamTenSanPham(chiTietSanPham));
        chiTietSanPhamDto.setHinhAnh(chiTietSanPhamMaSanPhamHinhAnh(chiTietSanPham));
        chiTietSanPhamDto.setIdDanhMuc(chiTietSanPhamMaSanPhamDanhMucId(chiTietSanPham));
        chiTietSanPhamDto.setTenDanhMuc(chiTietSanPhamMaSanPhamDanhMucTenDanhMuc(chiTietSanPham));
        chiTietSanPhamDto.setMoTa(chiTietSanPhamMaSanPhamMoTa(chiTietSanPham));
        chiTietSanPhamDto.setId(chiTietSanPham.getId());
        chiTietSanPhamDto.setKichThuoc(chiTietSanPham.getKichThuoc());
        chiTietSanPhamDto.setMauSac(chiTietSanPham.getMauSac());
        chiTietSanPhamDto.setGiaTien(chiTietSanPham.getGiaTien());
        chiTietSanPhamDto.setSoLuongTonKho(chiTietSanPham.getSoLuongTonKho());

        return chiTietSanPhamDto;
    }

    private String chiTietSanPhamMaSanPhamTenSanPham(ChiTietSanPham chiTietSanPham) {
        if (chiTietSanPham == null) {
            return null;
        }
        SanPham maSanPham = chiTietSanPham.getMaSanPham();
        if (maSanPham == null) {
            return null;
        }
        String tenSanPham = maSanPham.getTenSanPham();
        if (tenSanPham == null) {
            return null;
        }
        return tenSanPham;
    }

    private String chiTietSanPhamMaSanPhamHinhAnh(ChiTietSanPham chiTietSanPham) {
        if (chiTietSanPham == null) {
            return null;
        }
        SanPham maSanPham = chiTietSanPham.getMaSanPham();
        if (maSanPham == null) {
            return null;
        }
        String hinhAnh = maSanPham.getHinhAnh();
        if (hinhAnh == null) {
            return null;
        }
        return hinhAnh;
    }

    private Long chiTietSanPhamMaSanPhamDanhMucId(ChiTietSanPham chiTietSanPham) {
        if (chiTietSanPham == null) {
            return null;
        }
        SanPham maSanPham = chiTietSanPham.getMaSanPham();
        if (maSanPham == null) {
            return null;
        }
        DanhMuc danhMuc = maSanPham.getDanhMuc();
        if (danhMuc == null) {
            return null;
        }
        Long id = danhMuc.getId();
        if (id == null) {
            return null;
        }
        return id;
    }

    private String chiTietSanPhamMaSanPhamDanhMucTenDanhMuc(ChiTietSanPham chiTietSanPham) {
        if (chiTietSanPham == null) {
            return null;
        }
        SanPham maSanPham = chiTietSanPham.getMaSanPham();
        if (maSanPham == null) {
            return null;
        }
        DanhMuc danhMuc = maSanPham.getDanhMuc();
        if (danhMuc == null) {
            return null;
        }
        String tenDanhMuc = danhMuc.getTenDanhMuc();
        if (tenDanhMuc == null) {
            return null;
        }
        return tenDanhMuc;
    }

    private String chiTietSanPhamMaSanPhamMoTa(ChiTietSanPham chiTietSanPham) {
        if (chiTietSanPham == null) {
            return null;
        }
        SanPham maSanPham = chiTietSanPham.getMaSanPham();
        if (maSanPham == null) {
            return null;
        }
        String moTa = maSanPham.getMoTa();
        if (moTa == null) {
            return null;
        }
        return moTa;
    }
}
