package springboot.repository.forview;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springboot.entity.view.SanPhamBanDuocTheoThang;
@Repository
public interface SanPhamBanDuocTheoThangRepo extends JpaRepository<SanPhamBanDuocTheoThang, Long> {
}
