package hu.uni.eku.tzs.controller.dto;

import hu.uni.eku.tzs.controller.dto.CharactersDto.CharactersDtoBuilder;
import hu.uni.eku.tzs.model.Characters;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-11-30T17:47:10+0100",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17.0.1 (Oracle Corporation)"
)
@Component
public class CharactersMapperImpl implements CharactersMapper {

    @Override
    public CharactersDto characters2characterDto(Characters character) {
        if ( character == null ) {
            return null;
        }

        CharactersDtoBuilder charactersDto = CharactersDto.builder();

        charactersDto.id( character.getId() );
        charactersDto.charName( character.getCharName() );
        charactersDto.description( character.getDescription() );

        return charactersDto.build();
    }

    @Override
    public Characters characterDto2characters(CharactersDto dto) {
        if ( dto == null ) {
            return null;
        }

        Characters characters = new Characters();

        characters.setId( dto.getId() );
        characters.setCharName( dto.getCharName() );
        characters.setDescription( dto.getDescription() );

        return characters;
    }
}
