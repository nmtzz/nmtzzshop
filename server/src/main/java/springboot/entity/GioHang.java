package springboot.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "GIO_HANG")
public class GioHang {
    @Id
    @Column(name = "MA_GIO_HANG", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "TEN_NGUOI_DUNG")
    private Users tenUsers;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "MA_CHI_TIET")
    private ChiTietSanPham chiTietSanPham;

    @Column(name = "SO_LUONG")
    private Long soLuong;

}