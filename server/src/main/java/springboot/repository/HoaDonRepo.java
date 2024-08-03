package springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import springboot.entity.HoaDon;

import java.util.List;

@Repository
public interface HoaDonRepo extends JpaRepository<HoaDon, Long> {
    List<HoaDon> getHoaDonsByTenUsers_TenNguoiDung(String tenNguoiDung);
    @Modifying
    @Query(nativeQuery = true, value = "update HOA_DON set  TRANG_THAI = 'Chờ hủy' where MA_HOA_DON = :id")
    void huyDonHang(@Param("id") Long id);

    @Query(nativeQuery = true, value = "SELECT * FROM HOA_DON WHERE TRANG_THAI = 'Chờ hủy'")
    List<HoaDon> timHoaDonChoHuy();

//    update HOA_DON set  TRANG_THAI = 'Đã hủy' where MA_HOA_DON = :id

    @Modifying
    @Query(nativeQuery = true, value = "update HOA_DON set  TRANG_THAI = 'Đã hủy' where MA_HOA_DON = :id")
    void updateTrangThai(@Param("id") Long id);
}
