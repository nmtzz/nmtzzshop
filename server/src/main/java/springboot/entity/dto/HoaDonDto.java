package springboot.entity.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.Value;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.List;

/**
 * DTO for {@link springboot.entity.HoaDon}
 */
@Getter
@Setter
public class HoaDonDto implements Serializable {
    Long id;
    ZonedDateTime ngayLapHoaDon;
    Long tongTien;
    String tenNguoiNhan;
    String diaChiGiaoHang;
    String soDienThoai;
    String trangThai;
    String ghiChu;
    List<String> hinhAnhSanPhamList;
}