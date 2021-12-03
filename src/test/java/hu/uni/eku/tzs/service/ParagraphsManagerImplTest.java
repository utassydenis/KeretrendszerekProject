package hu.uni.eku.tzs.service;

import hu.uni.eku.tzs.dao.ParagraphsRepository;
import hu.uni.eku.tzs.dao.entity.ParagraphsEntity;
import hu.uni.eku.tzs.model.Paragraphs;
import hu.uni.eku.tzs.service.exceptions.ParagraphAlreadyExistsException;
import hu.uni.eku.tzs.service.exceptions.ParagraphNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ParagraphsManagerImplTest {
    @Mock
    ParagraphsRepository paragraphsRepository;


    @InjectMocks
    ParagraphManagerImpl service;

    @Test
    void recordParagraphHappyPath() throws ParagraphAlreadyExistsException { //Record character
        // given
        Paragraphs testPara = TestDataProvider.getTestPara1();
        ParagraphsEntity testEntity = TestDataProvider.getTest1Entity();
        when(paragraphsRepository.findById(any())).thenReturn(Optional.empty());
        when(paragraphsRepository.save(any())).thenReturn(testEntity);
        // when
        Paragraphs actual = service.record(testPara);
        // then
        assertThat(actual).usingRecursiveComparison().isEqualTo(testPara);
//        assertThat(actual).isEqualToComparingFieldByFieldRecursively(hg2g);
    }

    @Test
    void recordParagraphParagraphAlreadyExistsException() { //Record Character exception
        // given
        Paragraphs testPara = TestDataProvider.getTestPara1();
        ParagraphsEntity testEntity = TestDataProvider.getTest1Entity();
        when(paragraphsRepository.findById(TestDataProvider.TESTID1)).thenReturn(Optional.ofNullable(testEntity));
        // when
        // then
        assertThatThrownBy(() -> {
            service.record(testPara);
        }).isInstanceOf(ParagraphAlreadyExistsException.class);
    }


    @Test
    void readByIdHappyPath() throws ParagraphNotFoundException { //ReadById
        // given
        when(paragraphsRepository.findById(TestDataProvider.TESTID1))
                .thenReturn(Optional.of(TestDataProvider.getTest1Entity()));
        Paragraphs expected = TestDataProvider.getTestPara1();
        // when
        Paragraphs actual = service.readById(TestDataProvider.TESTID1);
        // then
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void readByIdParagraphNotFoundException() { //ReadById exception
        // given
        when(paragraphsRepository.findById(TestDataProvider.WRONGID)).thenReturn(Optional.empty());
        // when then
        assertThatThrownBy(() -> {
            service.readById(TestDataProvider.WRONGID);
        }).isInstanceOf(ParagraphNotFoundException.class)
                .hasMessageContaining(String.valueOf(TestDataProvider.WRONGID));
    }

    @Test
    void readAllHappyPath() { //ReadAll
        // given
        List<ParagraphsEntity> paragraphsEntities = List.of(
                TestDataProvider.getTest1Entity(),
                TestDataProvider.getTest2Entity()
        );
        Collection<Paragraphs> expectedParagraphs = List.of(
                TestDataProvider.getTestPara1(),
                TestDataProvider.getTestPara2()
        );
        // when
        when(paragraphsRepository.findAll()).thenReturn(paragraphsEntities);
        // then
        Collection<Paragraphs> actualParagraphs = service.readAll();
        assertThat(actualParagraphs)
                .usingRecursiveComparison()
                .isEqualTo(expectedParagraphs);
    }

    @Test
    void modifyParagraphHappyPath() throws ParagraphNotFoundException { //Modify character
        // given
        Paragraphs testPara = TestDataProvider.getTestPara1();
        ParagraphsEntity testEntity = TestDataProvider.getTest1Entity();
        when(paragraphsRepository.findById(testPara.getId())).thenReturn(Optional.ofNullable(testEntity));
        when(paragraphsRepository.save(testEntity)).thenReturn(testEntity);
        // when
        Paragraphs actual = service.modify(testPara);
        // then
        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(testPara);

    }

    @Test
    void modifyParagraphParagraphNotFound() {
        //given
        Paragraphs testPara = TestDataProvider.getTestPara1();
        //when
        when(paragraphsRepository.findById(testPara.getId())).thenReturn(Optional.empty());
        //then
        assertThatThrownBy(() -> {
            service.modify(testPara);
        }).isInstanceOf(ParagraphNotFoundException.class);
    }

    @Test
    void deleteParagraphHappyPath() throws ParagraphNotFoundException {
        // given
        Paragraphs testPara = TestDataProvider.getTestPara1();
        ParagraphsEntity testEntity = TestDataProvider.getTest1Entity();
        // when
        when(paragraphsRepository.findById(testPara.getId())).thenReturn(Optional.ofNullable(testEntity));
        // then
        service.delete(testPara);
    }

    @Test
    void DeleteParagraphParagraphNotFound() {
        //given
        Paragraphs testPara = TestDataProvider.getTestPara1();
        //when
        when(paragraphsRepository.findById(testPara.getId())).thenReturn(Optional.empty());
        //then
        assertThatThrownBy(() -> {
            service.delete(testPara);
        }).isInstanceOf(ParagraphNotFoundException.class);
    }

    private static class TestDataProvider {

        public static final int TESTID1 = 1;

        public static final int TESTID2 = 2;

        public static final int WRONGID = -1;

        public static Paragraphs getTestPara1() {
            return new Paragraphs(TESTID1, 1, "Plain Text One", 1, 1);
        }

        public static Paragraphs getTestPara2() {
            return new Paragraphs(TESTID2, 2, "Plain Text Two", 2, 2);
        }

        public static ParagraphsEntity getTest1Entity() {
            return ParagraphsEntity.builder()
                    .id(TESTID1)
                    .paragraphNum(1)
                    .plainText("Plain Text One")
                    .characterId(1)
                    .chapterId(1)
                    .build();
        }

        public static ParagraphsEntity getTest2Entity() {
            return ParagraphsEntity.builder()
                    .id(TESTID2)
                    .paragraphNum(2)
                    .plainText("Plain Text Two")
                    .characterId(2)
                    .chapterId(2)
                    .build();
        }
    }
}
