package hu.uni.eku.tzs.service;

import hu.uni.eku.tzs.dao.CharactersRepository;
import hu.uni.eku.tzs.dao.entity.CharactersEntity;
import hu.uni.eku.tzs.model.Characters;
import hu.uni.eku.tzs.service.exceptions.CharacterAlreadyExistsException;
import hu.uni.eku.tzs.service.exceptions.CharacterNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CharacterManagerImpl implements CharacterManager {

    private final CharactersRepository characterRepository;

    private static Characters convertCharactersEntity2Model(CharactersEntity characterEntity) {
        return new Characters(
                characterEntity.getId(),
                characterEntity.getCharName(),
                characterEntity.getAbbreviation(),
                characterEntity.getDescription());
    }

    private static CharactersEntity convertCharactersModel2Entity(Characters character) {
        return CharactersEntity.builder()
                .id(character.getId())
                .charName(character.getCharName())
                .abbreviation(character.getAbbreviation())
                .description(character.getDescription())
                .build();
    }


    @Override
    public Characters record(Characters character) throws CharacterAlreadyExistsException {
        if (characterRepository.findById(character.getId()).isPresent()) {
            throw new CharacterAlreadyExistsException();
        }
        CharactersEntity characterEntity = characterRepository.save(
                CharactersEntity.builder()
                        .id(character.getId())
                        .charName(character.getCharName())
                        .abbreviation(character.getAbbreviation())
                        .description(character.getDescription())
                        .build()
        );
        return convertCharactersEntity2Model(characterEntity);
    }

    @Override
    public Characters readById(int id) throws CharacterNotFoundException {
        Optional<CharactersEntity> entity = characterRepository.findById(id);
        if (entity.isEmpty()) {
            throw new CharacterNotFoundException(String.format("Cannot find character with ID %s", id));
        }
        return convertCharactersEntity2Model(entity.get());
    }

    @Override
    public Collection<Characters> readAll() {
        return characterRepository.findAll().stream().map(CharacterManagerImpl::convertCharactersEntity2Model)
                .collect(Collectors.toList());
    }

    @Override
    public Characters modify(Characters character) {
        CharactersEntity entity = convertCharactersModel2Entity(character);
        return convertCharactersEntity2Model(characterRepository.save(entity));
    }

    @Override
    public void delete(Characters character) {
        characterRepository.delete(convertCharactersModel2Entity(character));
    }
}
