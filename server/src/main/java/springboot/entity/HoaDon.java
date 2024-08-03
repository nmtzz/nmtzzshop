package springboot.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Nationalized;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "HOA_DON")
public class HoaDon {
    @Id
    @Column(name = "MA_HOA_DON", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "TEN_NGUOI_DUNG")
    private Users tenUsers;

    @Column(name = "NGAY_LAP_HOA_DON")
    private ZonedDateTime ngayLapHoaDon;

    @Column(name = "TONG_TIEN")
    private Long tongTien;

    @Nationalized
    @Column(name = "TEN_NGUOI_NHAN")
    private String tenNguoiNhan;

    @Nationalized
    @Column(name = "DIA_CHI_GIAO_HANG")
    private String diaChiGiaoHang;

    @Nationalized
    @Column(name = "SO_DIEN_THOAI")
    private String soDienThoai;

    @Nationalized
    @Column(name = "TRANG_THAI")
    private String trangThai;

    @Nationalized
    @Column(name = "GHI_CHU")
    private String ghiChu;

    @Transient
    private List<String> hinhAnhSanPhamList;

    @PrePersist
    public void onCreate() {
        this.ngayLapHoaDon = ZonedDateTime.now();
    }
}