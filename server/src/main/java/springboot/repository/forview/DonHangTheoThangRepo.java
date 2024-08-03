package springboot.repository.forview;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springboot.entity.view.DonHangTheoThang;
@Repository
public interface DonHangTheoThangRepo extends JpaRepository<DonHangTheoThang, Long> {
}
