package springboot.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Nationalized;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "CHI_TIET_SAN_PHAM")
public class ChiTietSanPham {
    @Id
    @Column(name = "MA_CHI_TIET", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "MA_SAN_PHAM")
    private SanPham maSanPham;

    @Nationalized
    @Column(name = "KICH_THUOC")
    private String kichThuoc;

    @Nationalized
    @Column(name = "MAU_SAC")
    private String mauSac;

    @Column(name = "GIA_TIEN")
    private Long giaTien;

    @Column(name = "SO_LUONG_TON_KHO")
    private Long soLuongTonKho;

}