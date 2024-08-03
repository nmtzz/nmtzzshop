package springboot.repository.forview;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springboot.entity.view.DoanhThuThangHienTai;
@Repository
public interface DoanhThuThangHienTaiRepo extends JpaRepository<DoanhThuThangHienTai, Long> {
}
