package hu.uni.eku.tzs.controller.dto;

import hu.uni.eku.tzs.model.Paragraphs;
import org.mapstruct.Mapper;
@Mapper(componentModel = "spring")
public interface ParagraphsMapper {
    ParagraphsDto paragraphs2paragraphDto(Paragraphs paragraph);

    Paragraphs paragraphDto2paragraphs(ParagraphsDto dto);
}
