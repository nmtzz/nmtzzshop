package springboot.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springboot.entity.NhanXetSanPham;
@Repository
public interface NhanXetSanPhamRepo extends JpaRepository<NhanXetSanPham, Long> {
    Page<NhanXetSanPham> findNhanXetSanPhamsByMaSanPham_Id(Long id,
                                                           Pageable pageable);
}
