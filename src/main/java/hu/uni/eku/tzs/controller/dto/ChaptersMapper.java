package hu.uni.eku.tzs.controller.dto;

import hu.uni.eku.tzs.model.Chapters;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ChaptersMapper {

    ChaptersDto chapter2chapterDto(Chapters chapter);

    Chapters chapterDto2chapter(ChaptersDto dto);
}
