package springboot.entity.dto;

import java.io.Serializable;

/**
 * DTO for {@link springboot.entity.GioHang}
 */
public record GioHangDto(Long id, String tenSanPham, String hinhAnh,
                         String kichThuoc, String mauSac, Long giaTien,
                         Long soLuong, Long idChiTiet) implements Serializable {
}