package springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springboot.entity.DanhMuc;
@Repository
public interface DanhMucRepo extends JpaRepository<DanhMuc, Long> {
}
