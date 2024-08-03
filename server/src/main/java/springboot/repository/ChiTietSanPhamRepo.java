package springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import springboot.entity.ChiTietSanPham;

import java.util.List;

@Repository
public interface ChiTietSanPhamRepo extends JpaRepository<ChiTietSanPham, Long> {
    //    SELECT * FROM CHI_TIET_SAN_PHAM WHERE MA_SAN_PHAM = ? AND ROWNUM = 1;
    @Query(nativeQuery = true, value = "SELECT * FROM CHI_TIET_SAN_PHAM WHERE MA_SAN_PHAM = :maSanPham ORDER BY GIA_TIEN FETCH FIRST 1 ROWS ONLY")
    ChiTietSanPham findByMaSanPham(@Param("maSanPham") Integer maSanPham);

    @Query(nativeQuery = true, value = "SELECT DISTINCT MAU_SAC FROM CHI_TIET_SAN_PHAM WHERE MA_SAN_PHAM = :maSanPham")
    List<String> getMauSac(@Param("maSanPham") Integer maSanPham);

    @Query(nativeQuery = true, value = "SELECT DISTINCT KICH_THUOC FROM CHI_TIET_SAN_PHAM WHERE MA_SAN_PHAM = :maSanPham")
    List<String> getKichThuoc(@Param("maSanPham") Integer maSanPham);

    @Query(nativeQuery = true, value = "SELECT GIA_BAN FROM CHI_TIET_SAN_PHAM WHERE MA_SAN_PHAM = :maSanPham AND MAU_SAC = :mauSac AND KICH_THUOC = :kichThuoc")
    Long getGiaBan(@Param("maSanPham") Integer maSanPham, @Param("mauSac") String mauSac, @Param("kichThuoc") String kichThuoc);

    @Query(nativeQuery = true, value = "SELECT SO_LUONG_TON_KHO FROM CHI_TIET_SAN_PHAM WHERE MA_SAN_PHAM = :maSanPham AND MAU_SAC = :mauSac AND KICH_THUOC = :kichThuoc")
    Long getTonKho(@Param("maSanPham") Integer maSanPham, @Param("mauSac") String mauSac, @Param("kichThuoc") String kichThuoc);

    //    SELECT * FROM CHI_TIET_SAN_PHAM WHERE MA_SAN_PHAM = 4 ORDER BY GIA_TIEN FETCH FIRST 1 ROWS ONLY;
    @Query(nativeQuery = true, value = "SELECT * FROM CHI_TIET_SAN_PHAM WHERE MA_SAN_PHAM = :maSanPham ORDER BY GIA_TIEN FETCH FIRST 1 ROWS ONLY")
    ChiTietSanPham getGiaMin(@Param("maSanPham") Integer maSanPham);

    //    SELECT * FROM CHI_TIET_SAN_PHAM WHERE MA_SAN_PHAM = 4 AND MAU_SAC = 'Trắng' AND KICH_THUOC = 'L' FETCH FIRST 1 ROWS ONLY;
    @Query(nativeQuery = true, value = "SELECT * FROM CHI_TIET_SAN_PHAM WHERE MA_SAN_PHAM = :maSanPham AND MAU_SAC = :mauSac AND KICH_THUOC = :kichThuoc FETCH FIRST 1 ROWS ONLY")
    ChiTietSanPham getChiTietSanPham(@Param("maSanPham") Integer maSanPham, @Param("mauSac") String mauSac, @Param("kichThuoc") String kichThuoc);

    ChiTietSanPham findChiTietSanPhamById(Long id);

    List<ChiTietSanPham> findChiTietSanPhamsByMaSanPham_Id(Long id);
}
