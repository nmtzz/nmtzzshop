package springboot.mapper;

import org.mapstruct.*;
import org.springframework.stereotype.Service;
import springboot.entity.NhanXetSanPham;
import springboot.entity.dto.NhanXetSanPhamDto;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
@Service
public interface NhanXetSanPhamMapper {
    @Mapping(source = "nguoiNhanXet", target = "tenUsers.tenNguoiDung")
    NhanXetSanPham toEntity(NhanXetSanPhamDto nhanXetSanPhamDto);

    @Mapping(source = "tenUsers.tenNguoiDung", target = "nguoiNhanXet")
    NhanXetSanPhamDto toDto(NhanXetSanPham nhanXetSanPham);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "nguoiNhanXet", target = "tenUsers.tenNguoiDung")
    NhanXetSanPham partialUpdate(NhanXetSanPhamDto nhanXetSanPhamDto, @MappingTarget NhanXetSanPham nhanXetSanPham);
}