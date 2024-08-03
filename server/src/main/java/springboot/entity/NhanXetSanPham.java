package springboot.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Nationalized;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "NHAN_XET_SAN_PHAM")
public class NhanXetSanPham {
    @Id
    @Column(name = "MA_NHAN_XET", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "TEN_NGUOI_DUNG")
    private Users tenUsers;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "MA_SAN_PHAM")
    private SanPham maSanPham;

    @Nationalized
    @Column(name = "NOI_DUNG_NHAN_XET")
    private String noiDungNhanXet;

    @Column(name = "THOI_GIAN_NHAN_XET")
    private LocalDate thoiGianNhanXet;

}