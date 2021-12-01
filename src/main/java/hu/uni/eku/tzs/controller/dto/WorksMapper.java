package hu.uni.eku.tzs.controller.dto;

import hu.uni.eku.tzs.model.Works;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WorksMapper {

    WorksDto works2worksDto(Works work);

    Works worksDto2works(WorksDto dto);
}
