package springboot.entity.dto;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link springboot.entity.NhanXetSanPham}
 */
public record NhanXetSanPhamDto(String nguoiNhanXet, String noiDungNhanXet,
                                LocalDate thoiGianNhanXet) implements Serializable {
}