package springboot.repository.forview;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springboot.entity.view.SanPhamBanDuocTrongNgay;
@Repository
public interface SanPhamBanDuocTrongNgayRepo extends JpaRepository<SanPhamBanDuocTrongNgay, Long> {
}
