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
@Table(name = "SAN_PHAM")
public class SanPham {
    @Id
    @Column(name = "MA_SAN_PHAM", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Nationalized
    @Column(name = "TEN_SAN_PHAM")
    private String tenSanPham;

    @Nationalized
    @Column(name = "HINH_ANH")
    private String hinhAnh;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "MA_DANH_MUC")
    private DanhMuc danhMuc;

    @Nationalized
    @Column(name = "MO_TA")
    private String moTa;

    @Transient
    private Long giaChung;
}