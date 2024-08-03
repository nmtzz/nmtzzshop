package springboot.mapper;

import org.mapstruct.*;
import org.springframework.stereotype.Service;
import springboot.entity.GioHang;
import springboot.entity.dto.GioHangDto;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
@Service
public interface GioHangMapper {
    @Mapping(source = "giaTien", target = "chiTietSanPham.giaTien")
    @Mapping(source = "mauSac", target = "chiTietSanPham.mauSac")
    @Mapping(source = "kichThuoc", target = "chiTietSanPham.kichThuoc")
    @Mapping(source = "hinhAnh", target = "chiTietSanPham.maSanPham.hinhAnh")
    @Mapping(source = "tenSanPham", target = "chiTietSanPham.maSanPham.tenSanPham")
    @Mapping(source = "idChiTiet", target = "chiTietSanPham.id")
    GioHang toEntity(GioHangDto gioHangDto);

    @InheritInverseConfiguration(name = "toEntity")
    GioHangDto toDto(GioHang gioHang);

    @InheritConfiguration(name = "toEntity")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    GioHang partialUpdate(GioHangDto gioHangDto, @MappingTarget GioHang gioHang);
}