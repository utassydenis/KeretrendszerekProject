package hu.uni.eku.tzs.controller.dto;

import hu.uni.eku.tzs.controller.dto.WorksDto.WorksDtoBuilder;
import hu.uni.eku.tzs.model.Works;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-11-30T17:47:10+0100",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17.0.1 (Oracle Corporation)"
)
@Component
public class WorksMapperImpl implements WorksMapper {

    @Override
    public WorksDto works2workDto(Works work) {
        if ( work == null ) {
            return null;
        }

        WorksDtoBuilder worksDto = WorksDto.builder();

        worksDto.id( work.getId() );
        worksDto.title( work.getTitle() );
        worksDto.longTitle( work.getLongTitle() );
        worksDto.date( work.getDate() );
        worksDto.genreType( work.getGenreType() );

        return worksDto.build();
    }

    @Override
    public Works workDto2workss(WorksDto dto) {
        if ( dto == null ) {
            return null;
        }

        Works works = new Works();

        works.setId( dto.getId() );
        works.setTitle( dto.getTitle() );
        works.setLongTitle( dto.getLongTitle() );
        works.setDate( dto.getDate() );
        works.setGenreType( dto.getGenreType() );

        return works;
    }
}
