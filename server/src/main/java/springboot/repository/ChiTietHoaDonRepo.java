package springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import springboot.entity.ChiTietHoaDon;

import java.util.List;

@Repository
public interface ChiTietHoaDonRepo extends JpaRepository<ChiTietHoaDon, Long> {
    @Query(nativeQuery = true, value = "SELECT SAN_PHAM.HINH_ANH FROM CHI_TIET_HOA_DON JOIN CHI_TIET_SAN_PHAM ON CHI_TIET_HOA_DON.MA_CHI_TIET = CHI_TIET_SAN_PHAM.MA_CHI_TIET\n" +
            "JOIN SAN_PHAM ON CHI_TIET_SAN_PHAM.MA_SAN_PHAM = SAN_PHAM.MA_SAN_PHAM WHERE CHI_TIET_HOA_DON.MA_HOA_DON = :maHoaDon")
    List<String> getHinhAnhsByMaHoaDon(@Param("maHoaDon") Long maHoaDon);

    List<ChiTietHoaDon> findChiTietHoaDonsByMaHoaDon_Id(Long id);
}
