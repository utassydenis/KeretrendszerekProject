package hu.uni.eku.tzs.service;

import hu.uni.eku.tzs.dao.CharactersRepository;
import hu.uni.eku.tzs.dao.entity.CharactersEntity;
import hu.uni.eku.tzs.model.Characters;
import hu.uni.eku.tzs.service.exceptions.CharacterAlreadyExistsException;
import hu.uni.eku.tzs.service.exceptions.CharacterNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
public class CharactersManagerImplTest {
    @Mock
    CharactersRepository charactersRepository;


    @InjectMocks
    CharacterManagerImpl service;

    @Test
    void recordCharacterHappyPath() throws CharacterAlreadyExistsException { //Record character
        // given
        Characters testChar = TestDataProvider.getTestChar1();
        CharactersEntity testEntity = TestDataProvider.getTest1Entity();
        when(charactersRepository.findById(any())).thenReturn(Optional.empty());
        when(charactersRepository.save(any())).thenReturn(testEntity);
        // when
        Characters actual = service.record(testChar);
        // then
        assertThat(actual).usingRecursiveComparison().isEqualTo(testChar);
//        assertThat(actual).isEqualToComparingFieldByFieldRecursively(hg2g);
    }

    @Test
    void recordCharacterCharacterAlreadyExistsException() { //Record Character exception
        // given
        Characters testChar = TestDataProvider.getTestChar1();
        CharactersEntity testEntity = TestDataProvider.getTest1Entity();
        when(charactersRepository.findById(TestDataProvider.TESTID1)).thenReturn(Optional.ofNullable(testEntity));
        // when
        // then
        assertThatThrownBy(() -> {
            service.record(testChar);
        }).isInstanceOf(CharacterAlreadyExistsException.class);
        //CharacterAlreadyExistsException
    }


    @Test
    void readByIdHappyPath() throws CharacterNotFoundException { //ReadById
        // given
        when(charactersRepository.findById(TestDataProvider.TESTID1))
                .thenReturn(Optional.of(TestDataProvider.getTest1Entity()));
        Characters expected = TestDataProvider.getTestChar1();
        // when
        Characters actual = service.readById(TestDataProvider.TESTID1);
        // then
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void readByIdCharacterNotFoundException() { //ReadById exception
        // given
        when(charactersRepository.findById(TestDataProvider.WRONGID)).thenReturn(Optional.empty());
        // when then
        assertThatThrownBy(() -> {
            service.readById(TestDataProvider.WRONGID);
        }).isInstanceOf(CharacterNotFoundException.class)
                .hasMessageContaining(String.valueOf(TestDataProvider.WRONGID));
    }

    @Test
    void readAllHappyPath() { //ReadAll
        // given
        List<CharactersEntity> characterEntities = List.of(
                TestDataProvider.getTest1Entity(),
                TestDataProvider.getTest2Entity()
        );
        Collection<Characters> expectedCharacters = List.of(
                TestDataProvider.getTestChar1(),
                TestDataProvider.getTestChar2()
        );
        // when
        when(charactersRepository.findAll()).thenReturn(characterEntities);
        // then
        Collection<Characters> actualCharacters = service.readAll();
        assertThat(actualCharacters)
                .usingRecursiveComparison()
                .isEqualTo(expectedCharacters);
    }

    @Test
    void modifyCharacterHappyPath() throws CharacterNotFoundException { //Modify character
        // given
        Characters testChar = TestDataProvider.getTestChar1();
        CharactersEntity testEntity = TestDataProvider.getTest1Entity();
        when(charactersRepository.findById(testChar.getId())).thenReturn(Optional.ofNullable(testEntity));
        when(charactersRepository.save(testEntity)).thenReturn(testEntity);
        // when
        Characters actual = service.modify(testChar);
        // then
        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(testChar);

    }

    @Test
    void modifyCharacterCharacterNotFound() {
        //given
        Characters testChar = TestDataProvider.getTestChar1();
        //when
        when(charactersRepository.findById(testChar.getId())).thenReturn(Optional.empty());
        //then
        assertThatThrownBy(() -> {
            service.modify(testChar);
        }).isInstanceOf(CharacterNotFoundException.class);
    }

    @Test
    void deleteCharacterHappyPath() throws CharacterNotFoundException {
        // given
        Characters testChar = TestDataProvider.getTestChar1();
        CharactersEntity testEntity = TestDataProvider.getTest1Entity();
        // when
        when(charactersRepository.findById(testChar.getId())).thenReturn(Optional.ofNullable(testEntity));
        // then
        service.delete(testChar);
    }

    @Test
    void DeleteCharacterCharacterNotFound() {
        //given
        Characters testChar = TestDataProvider.getTestChar1();
        //when
        when(charactersRepository.findById(testChar.getId())).thenReturn(Optional.empty());
        //then
        assertThatThrownBy(() -> {
            service.delete(testChar);
        }).isInstanceOf(CharacterNotFoundException.class);
    }

    private static class TestDataProvider {

        public static final int TESTID1 = 1;

        public static final int TESTID2 = 2;

        public static final int WRONGID = -1;

        public static Characters getTestChar1() {
            return new Characters(TESTID1, "Douglas Adams", "DG", "English");
        }

        public static Characters getTestChar2() {
            return new Characters(TESTID2, "Frank Herbert", "FH", "American");
        }

        public static CharactersEntity getTest1Entity() {
            return CharactersEntity.builder()
                    .id(TESTID1)
                    .charName("Douglas Adams")
                    .abbreviation("DG")
                    .description("English")
                    .build();
        }

        public static CharactersEntity getTest2Entity() {
            return CharactersEntity.builder()
                    .id(TESTID2)
                    .charName("Frank Herbert")
                    .abbreviation("FH")
                    .description("American")
                    .build();
        }
    }
}
