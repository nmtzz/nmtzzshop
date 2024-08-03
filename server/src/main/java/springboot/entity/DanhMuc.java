package springboot.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Nationalized;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "DANH_MUC")
public class DanhMuc {
    @Id
    @Column(name = "MA_DANH_MUC", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Nationalized
    @Column(name = "TEN_DANH_MUC")
    private String tenDanhMuc;

    @Nationalized
    @Column(name = "ANH_DANH_MUC")
    private String anhDanhMuc;
}