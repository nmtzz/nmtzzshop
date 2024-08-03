package springboot.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Service;
import springboot.entity.SanPham;
import springboot.entity.dto.SanPhamDto;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
@Service
public interface SanPhamMapper {
    @Mapping(source = "danhMuc.tenDanhMuc", target = "tenDanhMuc")
    @Mapping(source = "danhMuc.id", target = "idDanhMuc")
    SanPhamDto toSanPhamDto(SanPham sanPham);
}
