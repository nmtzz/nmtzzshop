package springboot.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Service;
import springboot.entity.ChiTietSanPham;
import springboot.entity.dto.ChiTietSanPhamDto;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
@Service
public interface ChiTietSanPhamMapper {
    @Mapping(source = "maSanPham.tenSanPham", target = "tenSanPham")
    @Mapping(source = "maSanPham.hinhAnh", target = "hinhAnh")
    @Mapping(source = "maSanPham.danhMuc.id", target = "idDanhMuc")
    @Mapping(source = "maSanPham.danhMuc.tenDanhMuc", target = "tenDanhMuc")
    @Mapping(source = "maSanPham.moTa", target = "moTa")
    ChiTietSanPhamDto toChiTietSanPhamDto(ChiTietSanPham chiTietSanPham);
}
