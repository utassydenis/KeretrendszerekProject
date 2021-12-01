package hu.uni.eku.tzs.controller.dto;

import hu.uni.eku.tzs.controller.dto.ParagraphsDto.ParagraphsDtoBuilder;
import hu.uni.eku.tzs.model.Paragraphs;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-11-30T17:47:10+0100",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17.0.1 (Oracle Corporation)"
)
@Component
public class ParagraphsMapperImpl implements ParagraphsMapper {

    @Override
    public ParagraphsDto paragraphs2paragraphDto(Paragraphs paragraph) {
        if ( paragraph == null ) {
            return null;
        }

        ParagraphsDtoBuilder paragraphsDto = ParagraphsDto.builder();

        paragraphsDto.id( paragraph.getId() );
        paragraphsDto.paragraphNum( paragraph.getParagraphNum() );
        paragraphsDto.plainText( paragraph.getPlainText() );

        return paragraphsDto.build();
    }

    @Override
    public Paragraphs paragraphDto2paragraphs(ParagraphsDto dto) {
        if ( dto == null ) {
            return null;
        }

        Paragraphs paragraphs = new Paragraphs();

        paragraphs.setId( dto.getId() );
        paragraphs.setParagraphNum( dto.getParagraphNum() );
        paragraphs.setPlainText( dto.getPlainText() );

        return paragraphs;
    }
}
