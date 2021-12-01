package hu.uni.eku.tzs.controller;

import hu.uni.eku.tzs.controller.dto.CharactersDto;
import hu.uni.eku.tzs.controller.dto.CharactersMapper;
import hu.uni.eku.tzs.model.Characters;
import hu.uni.eku.tzs.service.CharacterManager;
import hu.uni.eku.tzs.service.exceptions.CharacterAlreadyExistsException;
import hu.uni.eku.tzs.service.exceptions.CharacterNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doThrow;


@ExtendWith(MockitoExtension.class)
public class CharactersControllerTest {
    @Mock
    private CharacterManager characterManager;

    @Mock
    private CharactersMapper characterMapper;

    @InjectMocks
    private CharactersController controller;

    @Test
    void readAllHappyPath() {
        // given
        when(characterManager.readAll()).thenReturn(List.of(CharactersControllerTest.TestDataProvider.getTestCharacter()));
        when(characterMapper.characters2charactersDto(any())).thenReturn(CharactersControllerTest.TestDataProvider.getTestDto());
        Collection<CharactersDto> expected = List.of(CharactersControllerTest.TestDataProvider.getTestDto());
        // when
        Collection<CharactersDto> actual = controller.readAllCharacters();
        //then
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);


    }

    @Test
    void createCharactersHappyPath() throws CharacterAlreadyExistsException {
        // given
        Characters test = CharactersControllerTest.TestDataProvider.getTestCharacter();
        CharactersDto testDto = CharactersControllerTest.TestDataProvider.getTestDto();
        when(characterMapper.charactersDto2characters(testDto)).thenReturn(test);
        when(characterManager.record(test)).thenReturn(test);
        when(characterMapper.characters2charactersDto(test)).thenReturn(testDto);
        // when
        CharactersDto actual = controller.create(testDto);
        // then
        assertThat(actual).usingRecursiveComparison().isEqualTo(testDto);
    }

    @Test
    void createCharacterAlreadyExistsException() throws CharacterAlreadyExistsException {
        // given
        Characters test = CharactersControllerTest.TestDataProvider.getTestCharacter();
        CharactersDto testDto = CharactersControllerTest.TestDataProvider.getTestDto();
        when(characterMapper.charactersDto2characters(testDto)).thenReturn(test);
        when(characterManager.record(test)).thenThrow(new CharacterAlreadyExistsException());
        // when then
        assertThatThrownBy(() -> {
            controller.create(testDto);
        }).isInstanceOf(ResponseStatusException.class);
    }

    @Test
    void updateHappyPath() throws CharacterNotFoundException {
        // given
        CharactersDto requestDto = CharactersControllerTest.TestDataProvider.getTestDto();
        Characters test = CharactersControllerTest.TestDataProvider.getTestCharacter();
        when(characterMapper.charactersDto2characters(requestDto)).thenReturn(test);
        when(characterManager.modify(test)).thenReturn(test);
        when(characterMapper.characters2charactersDto(test)).thenReturn(requestDto);
        CharactersDto expected = CharactersControllerTest.TestDataProvider.getTestDto();
        // when
        CharactersDto response = controller.modify(requestDto);
        // then
        assertThat(response).usingRecursiveComparison()
                .isEqualTo(expected);
    }


    @Test
    void deleteFromQueryParamHappyPath() throws CharacterNotFoundException {
        // given
        Characters test = CharactersControllerTest.TestDataProvider.getTestCharacter();
        when(characterManager.readById(CharactersControllerTest.TestDataProvider.ID)).thenReturn(test);
        doNothing().when(characterManager).delete(test);
        // when
        controller.delete(CharactersControllerTest.TestDataProvider.ID);
        // then is not necessary, mock are checked by default
    }

    @Test
    void deleteFromQueryParamWhenCharacterNotFound() throws CharacterNotFoundException {
        // given
        final int notFoundCharacterID = CharactersControllerTest.TestDataProvider.ID;
        doThrow(new CharacterNotFoundException()).when(characterManager).readById(notFoundCharacterID);
//        These two lines mean the same.
//        doThrow(new BookNotFoundException()).when(bookManager).readByIsbn(notFoundBookIsbn);
//        when(bookManager.readByIsbn(notFoundBookIsbn)).thenThrow(new BookNotFoundException());
        // when then
        assertThatThrownBy(() -> controller.delete(notFoundCharacterID))
                .isInstanceOf(ResponseStatusException.class);
    }

    private static class TestDataProvider {

        public static final int ID = 1;

        public static Characters getTestCharacter() {
            return new Characters(ID, "Johnny Test", "Johnny", "A good test subject is hard to find");
        }

        public static CharactersDto getTestDto() {
            return CharactersDto.builder()
                    .id(ID)
                    .charName("Johnny Test")
                    .abbreviation("Johnny")
                    .description("A good test subject is hard to find")
                    .build();
        }
    }
}
