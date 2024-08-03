package springboot.entity.view;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.Immutable;

/**
 * Mapping for DB view
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Immutable
@Table(name = "DOANH_THU_THANG_HIEN_TAI")
public class DoanhThuThangHienTai {
    @Id
    @Column(name = "DOANH_THU")
    private Long doanhThu;

    //TODO [Reverse Engineering] generate columns from DB
}