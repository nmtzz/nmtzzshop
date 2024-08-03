package springboot.mapper;

import org.mapstruct.*;
import org.springframework.stereotype.Service;
import springboot.entity.ChiTietHoaDon;
import springboot.entity.dto.ChiTietHoaDonDto;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
@Service
public interface ChiTietHoaDonMapper {
    @Mapping(source = "giaTien", target = "maChiTiet.giaTien")
    @Mapping(source = "mauSac", target = "maChiTiet.mauSac")
    @Mapping(source = "kichThuoc", target = "maChiTiet.kichThuoc")
    @Mapping(source = "hinhAnh", target = "maChiTiet.maSanPham.hinhAnh")
    @Mapping(source = "tenSanPham", target = "maChiTiet.maSanPham.tenSanPham")
    ChiTietHoaDon toEntity(ChiTietHoaDonDto chiTietHoaDonDto);

    @InheritInverseConfiguration(name = "toEntity")
    ChiTietHoaDonDto toDto(ChiTietHoaDon chiTietHoaDon);

    @InheritConfiguration(name = "toEntity")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ChiTietHoaDon partialUpdate(ChiTietHoaDonDto chiTietHoaDonDto, @MappingTarget ChiTietHoaDon chiTietHoaDon);
}