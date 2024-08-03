package springboot.entity.dto;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link springboot.entity.ChiTietHoaDon}
 */
public record ChiTietHoaDonDto(Long id, String tenSanPham, String hinhAnh, String kichThuoc, String mauSac,
                               Long giaTien, Long soLuong) implements Serializable {
}