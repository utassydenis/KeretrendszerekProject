package hu.uni.eku.tzs.controller.dto;

import hu.uni.eku.tzs.model.Chapters;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ChaptersMapper {

    ChaptersDto chapters2ChaptersrDto(Chapters chapter);

    Chapters chaptersDto2Chapters(ChaptersDto dto);
}
