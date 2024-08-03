package springboot.repository.forview;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springboot.entity.view.TongKhachHang;
@Repository
public interface TongKhachHangRepo extends JpaRepository<TongKhachHang, Long> {
}
