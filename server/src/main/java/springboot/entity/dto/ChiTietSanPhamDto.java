package springboot.entity.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * DTO for {@link springboot.entity.ChiTietSanPham}
 */
@Getter
@Setter
public class ChiTietSanPhamDto implements Serializable {
    Long id;
    String tenSanPham;
    String hinhAnh;
    Long idDanhMuc;
    String tenDanhMuc;
    String moTa;
    String kichThuoc;
    String mauSac;
    Long giaTien;
    Long soLuongTonKho;

}