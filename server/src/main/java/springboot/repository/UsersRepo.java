package springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import springboot.entity.Users;

import java.util.Optional;

@Repository
public interface UsersRepo extends JpaRepository<Users, Long> {
    Optional<Users> findNguoiDungByTenNguoiDung(String tenNguoiDung);

    boolean existsByTenNguoiDung(String tenNguoiDung);
//    update  USERS set  MAT_KHAU = ? where TEN_NGUOI_DUNG = ?
    @Modifying
    @Query("update Users set matKhau = :matKhau where tenNguoiDung = :tenNguoiDung")
    void updateMatKhau(@Param("matKhau") String matKhau, @Param("tenNguoiDung") String tenNguoiDung);
}
