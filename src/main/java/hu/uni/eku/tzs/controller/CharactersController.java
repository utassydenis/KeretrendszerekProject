package hu.uni.eku.tzs.controller;

import hu.uni.eku.tzs.controller.dto.CharactersDto;
import hu.uni.eku.tzs.controller.dto.CharactersMapper;
import hu.uni.eku.tzs.model.Characters;
import hu.uni.eku.tzs.service.CharacterManager;
import hu.uni.eku.tzs.service.exceptions.CharacterAlreadyExistsException;
import hu.uni.eku.tzs.service.exceptions.CharacterNotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.stream.Collectors;

@Api(tags = "characters")
@RestController
@RequiredArgsConstructor
@RequestMapping("/characters")
public class CharactersController {
    private final CharacterManager characterManager;

    private final CharactersMapper characterMapper;

    @ApiOperation("Record")
    @PostMapping(value = {""})
    public CharactersDto create(@RequestBody CharactersDto charactersDto) {
        Characters character = characterMapper.charactersDto2characters(charactersDto);
        try {
            Characters recordedCharacters = characterManager.record(character);
            return characterMapper.characters2charactersDto(recordedCharacters);
        } catch (CharacterAlreadyExistsException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
    @ApiOperation("readById")
    @GetMapping("/{id}")
    public CharactersDto readById(@PathVariable int id){
        try {
            return characterMapper.characters2charactersDto(characterManager.readById(id));
        }catch (CharacterNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @ApiOperation("Read All")
    @GetMapping(value = {""})
    public Collection<CharactersDto> readAllCharacters() {
        return characterManager.readAll()
                .stream()
                .map(characterMapper::characters2charactersDto)
                .collect(Collectors.toList());
    }


    @ApiOperation("Modify")
    @PutMapping(value = {""})
    public CharactersDto modify(@RequestBody CharactersDto dto) {
        Characters character = characterMapper.charactersDto2characters(dto);
        try{
            Characters modifyCharacter = characterManager.modify(character);
            return characterMapper.characters2charactersDto(modifyCharacter);
        } catch (CharacterNotFoundException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @ApiOperation("Delete")
    @DeleteMapping(value = {""})
    public void delete(@RequestParam int id) {
        try{
            characterManager.delete(characterManager.readById(id));
        } catch (CharacterNotFoundException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
