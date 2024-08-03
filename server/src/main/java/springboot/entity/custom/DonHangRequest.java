package springboot.entity.custom;

import lombok.Getter;
import lombok.Setter;
import springboot.entity.ChiTietHoaDon;
import springboot.entity.HoaDon;

import java.util.List;

@Getter
@Setter
public class DonHangRequest {
    private HoaDon hoaDon;
    private List<ChiTietHoaDon> chiTietHoaDonList;
}
