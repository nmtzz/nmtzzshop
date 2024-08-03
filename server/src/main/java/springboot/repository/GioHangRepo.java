package springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import springboot.entity.ChiTietSanPham;
import springboot.entity.GioHang;
import springboot.entity.Users;

import java.util.List;
import java.util.Optional;

@Repository
public interface GioHangRepo extends JpaRepository<GioHang, Long> {
    List<GioHang> findGioHangsByTenUsers_TenNguoiDung(String tenNguoiDung);

    Optional<GioHang> findGioHangByChiTietSanPhamAndTenUsers(ChiTietSanPham chiTietSanPham, Users tenUsers);

    @Modifying
    @Query(nativeQuery = true, value = "UPDATE GIO_HANG SET SO_LUONG = :soLuong WHERE MA_GIO_HANG = :maGioHang")
    void updateSoLuong(@Param("soLuong") Long soLuong, @Param("maGioHang") Long maGioHang);

    void deleteGioHangById(Long id);

}
