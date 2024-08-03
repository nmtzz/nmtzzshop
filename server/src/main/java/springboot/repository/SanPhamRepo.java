package springboot.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import springboot.entity.SanPham;

import java.util.List;

@Repository
public interface SanPhamRepo extends JpaRepository<SanPham, Long> {
    @Query(nativeQuery = true, value = "select * from SAN_PHAM where MA_DANH_MUC = :maDanhMuc and ROWNUM <= 8")
    List<SanPham> findByDanhMuc(@Param("maDanhMuc") Long maDanhMuc);

    @Query(nativeQuery = true, value = "select MIN(GIA_TIEN) from CHI_TIET_SAN_PHAM where MA_SAN_PHAM = :id")
    Long timGiaChung(@Param("id") Long id);

    @Query(nativeQuery = true, value = "select * from SAN_PHAM where ROWNUM <= :rownum")
    List<SanPham> findTop(@Param("rownum") int rownum);

    Page<SanPham> findSanPhamsByDanhMuc_Id(Long idDanhMuc, Pageable pageable);

    Page<SanPham> findSanPhamsByTenSanPhamContainingIgnoreCaseAndDanhMuc_Id(String tenSanPham, Long idDanhMuc, Pageable pageable);

    @Query(nativeQuery = true, value = "SELECT MAX(GIA_TIEN) from CHI_TIET_SAN_PHAM JOIN SAN_PHAM ON CHI_TIET_SAN_PHAM.MA_SAN_PHAM = SAN_PHAM.MA_SAN_PHAM where SAN_PHAM.MA_DANH_MUC = :id")
    Long timGiaCaoNhat(@Param("id") Long id);

    @Query(nativeQuery = true, value = "select * from SAN_PHAM where MA_DANH_MUC = :maDanhMuc and ROWNUM <= 5")
    List<SanPham> findByDanhMucLimit5(@Param("maDanhMuc") Long maDanhMuc);
}
