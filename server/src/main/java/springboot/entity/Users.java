package springboot.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.Nationalized;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "USERS")
public class Users {
    @Id
    @Nationalized
    @Column(name = "TEN_NGUOI_DUNG")
    private String tenNguoiDung;

    @Nationalized
    @Column(name = "EMAIL")
    private String email;

    @Nationalized
    @Column(name = "MAT_KHAU")
    private String matKhau;

    @Nationalized
    @Column(name = "VAI_TRO")
    private String vaiTro;


}