package hu.uni.eku.tzs.controller.dto;

import hu.uni.eku.tzs.controller.dto.ChaptersDto.ChaptersDtoBuilder;
import hu.uni.eku.tzs.model.Chapters;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-11-30T17:47:10+0100",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17.0.1 (Oracle Corporation)"
)
@Component
public class ChaptersMapperImpl implements ChaptersMapper {

    @Override
    public ChaptersDto chapter2chapterDto(Chapters chapter) {
        if ( chapter == null ) {
            return null;
        }

        ChaptersDtoBuilder chaptersDto = ChaptersDto.builder();

        chaptersDto.id( chapter.getId() );
        chaptersDto.act( chapter.getAct() );
        chaptersDto.scene( chapter.getScene() );
        chaptersDto.description( chapter.getDescription() );

        return chaptersDto.build();
    }

    @Override
    public Chapters chapterDto2chapter(ChaptersDto dto) {
        if ( dto == null ) {
            return null;
        }

        Chapters chapters = new Chapters();

        chapters.setId( dto.getId() );
        chapters.setAct( dto.getAct() );
        chapters.setScene( dto.getScene() );
        chapters.setDescription( dto.getDescription() );

        return chapters;
    }
}
