package springboot.repository.forview;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springboot.entity.view.DoanhThuHangThang;
@Repository
public interface DoanhThuHangThangRepo extends JpaRepository<DoanhThuHangThang, Long> {
}
