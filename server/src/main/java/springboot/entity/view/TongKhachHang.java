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
@Table(name = "TONG_KHACH_HANG")
public class TongKhachHang {
    @Id
    @Column(name = "SO_LUONG")
    private Long soLuong;

    //TODO [Reverse Engineering] generate columns from DB
}