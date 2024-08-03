package springboot.mapper;

import java.time.LocalDate;
import javax.annotation.processing.Generated;

import org.springframework.stereotype.Component;
import springboot.entity.NhanXetSanPham;
import springboot.entity.Users;
import springboot.entity.dto.NhanXetSanPhamDto;

@Generated(
        value = "org.mapstruct.ap.MappingProcessor",
        date = "2024-07-20T21:31:22+0700",
        comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.3 (BellSoft)"
)
@Component
public class NhanXetSanPhamMapperImpl implements NhanXetSanPhamMapper {

    @Override
    public NhanXetSanPham toEntity(NhanXetSanPhamDto nhanXetSanPhamDto) {
        if (nhanXetSanPhamDto == null) {
            return null;
        }

        NhanXetSanPham.NhanXetSanPhamBuilder nhanXetSanPham = NhanXetSanPham.builder();

        nhanXetSanPham.tenUsers(nhanXetSanPhamDtoToUsers(nhanXetSanPhamDto));
        nhanXetSanPham.noiDungNhanXet(nhanXetSanPhamDto.noiDungNhanXet());
        nhanXetSanPham.thoiGianNhanXet(nhanXetSanPhamDto.thoiGianNhanXet());

        return nhanXetSanPham.build();
    }

    @Override
    public NhanXetSanPhamDto toDto(NhanXetSanPham nhanXetSanPham) {
        if (nhanXetSanPham == null) {
            return null;
        }

        String nguoiNhanXet = null;
        String noiDungNhanXet = null;
        LocalDate thoiGianNhanXet = null;

        nguoiNhanXet = nhanXetSanPhamTenUsersTenNguoiDung(nhanXetSanPham);
        noiDungNhanXet = nhanXetSanPham.getNoiDungNhanXet();
        thoiGianNhanXet = nhanXetSanPham.getThoiGianNhanXet();

        NhanXetSanPhamDto nhanXetSanPhamDto = new NhanXetSanPhamDto(nguoiNhanXet, noiDungNhanXet, thoiGianNhanXet);

        return nhanXetSanPhamDto;
    }

    @Override
    public NhanXetSanPham partialUpdate(NhanXetSanPhamDto nhanXetSanPhamDto, NhanXetSanPham nhanXetSanPham) {
        if (nhanXetSanPhamDto == null) {
            return nhanXetSanPham;
        }

        if (nhanXetSanPham.getTenUsers() == null) {
            nhanXetSanPham.setTenUsers(Users.builder().build());
        }
        nhanXetSanPhamDtoToUsers1(nhanXetSanPhamDto, nhanXetSanPham.getTenUsers());
        if (nhanXetSanPhamDto.noiDungNhanXet() != null) {
            nhanXetSanPham.setNoiDungNhanXet(nhanXetSanPhamDto.noiDungNhanXet());
        }
        if (nhanXetSanPhamDto.thoiGianNhanXet() != null) {
            nhanXetSanPham.setThoiGianNhanXet(nhanXetSanPhamDto.thoiGianNhanXet());
        }

        return nhanXetSanPham;
    }

    protected Users nhanXetSanPhamDtoToUsers(NhanXetSanPhamDto nhanXetSanPhamDto) {
        if (nhanXetSanPhamDto == null) {
            return null;
        }

        Users.UsersBuilder users = Users.builder();

        users.tenNguoiDung(nhanXetSanPhamDto.nguoiNhanXet());

        return users.build();
    }

    private String nhanXetSanPhamTenUsersTenNguoiDung(NhanXetSanPham nhanXetSanPham) {
        if (nhanXetSanPham == null) {
            return null;
        }
        Users tenUsers = nhanXetSanPham.getTenUsers();
        if (tenUsers == null) {
            return null;
        }
        String tenNguoiDung = tenUsers.getTenNguoiDung();
        if (tenNguoiDung == null) {
            return null;
        }
        return tenNguoiDung;
    }

    protected void nhanXetSanPhamDtoToUsers1(NhanXetSanPhamDto nhanXetSanPhamDto, Users mappingTarget) {
        if (nhanXetSanPhamDto == null) {
            return;
        }

        if (nhanXetSanPhamDto.nguoiNhanXet() != null) {
            mappingTarget.setTenNguoiDung(nhanXetSanPhamDto.nguoiNhanXet());
        }
    }
}
