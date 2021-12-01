package hu.uni.eku.tzs.controller.dto;

import hu.uni.eku.tzs.model.Characters;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CharactersMapper {

    CharactersDto characters2characterDto(Characters character);

    Characters characterDto2characters(CharactersDto dto);
}
