package springboot.entity.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * DTO for {@link springboot.entity.SanPham}
 */
@Setter
@Getter
public class SanPhamDto implements Serializable {
    Long id;
    String tenSanPham;
    String hinhAnh;
    Long idDanhMuc;
    String tenDanhMuc;
    String moTa;
    Long giaChung;
}