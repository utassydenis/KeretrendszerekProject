package hu.uni.eku.tzs.controller.dto;

import hu.uni.eku.tzs.model.Paragraphs;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ParagraphsMapper {

    ParagraphsDto paragraphs2paragraphsDto(Paragraphs paragraph);

    Paragraphs paragraphsDto2paragraphs(ParagraphsDto dto);
}
