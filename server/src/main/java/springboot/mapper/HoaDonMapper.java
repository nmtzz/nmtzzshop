package springboot.mapper;

import org.mapstruct.*;
import springboot.entity.HoaDon;
import springboot.entity.dto.HoaDonDto;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)public interface HoaDonMapper {
    HoaDon toEntity(HoaDonDto hoaDonDto);

    HoaDonDto toDto(HoaDon hoaDon);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    HoaDon partialUpdate(HoaDonDto hoaDonDto, @MappingTarget HoaDon hoaDon);
}