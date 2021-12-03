package hu.uni.eku.tzs.service;

import hu.uni.eku.tzs.dao.ChaptersRepository;
import hu.uni.eku.tzs.dao.entity.ChaptersEntity;
import hu.uni.eku.tzs.model.Chapters;
import hu.uni.eku.tzs.service.exceptions.ChapterAlreadyExistsException;
import hu.uni.eku.tzs.service.exceptions.ChapterNotFoundException;
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
public class ChaptersManagerImplTest {
    @Mock
    ChaptersRepository chaptersRepository;


    @InjectMocks
    ChapterManagerImpl service;

    @Test
    void recordChapterrHappyPath() throws ChapterAlreadyExistsException {
        // given
        Chapters testChap = TestDataProvider.getTestChap1();
        ChaptersEntity testEntity = TestDataProvider.getTest1Entity();
        when(chaptersRepository.findById(any())).thenReturn(Optional.empty());
        when(chaptersRepository.save(any())).thenReturn(testEntity);
        // when
        Chapters actual = service.record(testChap);
        // then
        assertThat(actual).usingRecursiveComparison().isEqualTo(testChap);
    }

    @Test
    void recordChapterChapterAlreadyExistsException() {
        // given
        Chapters testChap = TestDataProvider.getTestChap1();
        ChaptersEntity testEntity = TestDataProvider.getTest1Entity();
        // when
        when(chaptersRepository.findById(TestDataProvider.TESTID1)).thenReturn(Optional.ofNullable(testEntity));
        // then
        assertThatThrownBy(() -> {
            service.record(testChap);
        }).isInstanceOf(ChapterAlreadyExistsException.class);
    }


    @Test
    void readByIdHappyPath() throws ChapterNotFoundException { //ReadById
        // given
        when(chaptersRepository.findById(TestDataProvider.TESTID1))
                .thenReturn(Optional.of(TestDataProvider.getTest1Entity()));
        Chapters expected = TestDataProvider.getTestChap1();
        // when
        Chapters actual = service.readById(TestDataProvider.TESTID1);
        // then
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void readByIdChapterNotFoundException() { //ReadById exception
        // given
        when(chaptersRepository.findById(TestDataProvider.WRONGID)).thenReturn(Optional.empty());
        // when then
        assertThatThrownBy(() -> {
            service.readById(TestDataProvider.WRONGID);
        }).isInstanceOf(ChapterNotFoundException.class)
                .hasMessageContaining(String.valueOf(TestDataProvider.WRONGID));
    }

    @Test
    void readAllHappyPath() { //ReadAll
        // given
        List<ChaptersEntity> chapterEntities = List.of(
                TestDataProvider.getTest1Entity(),
                TestDataProvider.getTest2Entity()
        );
        Collection<Chapters> expectedChapters = List.of(
                TestDataProvider.getTestChap1(),
                TestDataProvider.getTestChap2()
        );
        // when
        when(chaptersRepository.findAll()).thenReturn(chapterEntities);
        // then
        Collection<Chapters> actualChapters = service.readAll();
        assertThat(actualChapters)
                .usingRecursiveComparison()
                .isEqualTo(expectedChapters);
    }

    @Test
    void modifyChapterHappyPath() throws ChapterNotFoundException {
        // given
        Chapters testChap = TestDataProvider.getTestChap1();
        ChaptersEntity testEntity = TestDataProvider.getTest1Entity();
        when(chaptersRepository.findById(testChap.getId())).thenReturn(Optional.ofNullable(testEntity));
        when(chaptersRepository.save(testEntity)).thenReturn(testEntity);
        // when
        Chapters actual = service.modify(testChap);
        // then
        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(testChap);
    }

    @Test
    void modifyChapterChapterNotFound() {
        //given
        Chapters testChap = TestDataProvider.getTestChap1();
        //when
        when(chaptersRepository.findById(testChap.getId())).thenReturn(Optional.empty());
        //then
        assertThatThrownBy(() -> {
            service.modify(testChap);
        }).isInstanceOf(ChapterNotFoundException.class);
    }

    @Test
    void deleteChapterHappyPath() throws ChapterNotFoundException {
        // given
        Chapters testChap = TestDataProvider.getTestChap1();
        ChaptersEntity testEntity = TestDataProvider.getTest1Entity();
        // when
        when(chaptersRepository.findById(testChap.getId())).thenReturn(Optional.ofNullable(testEntity));
        // then
        service.delete(testChap);
    }

    @Test
    void DeleteChapterChapterNotFound() {
        //given
        Chapters testChap = TestDataProvider.getTestChap1();
        //when
        when(chaptersRepository.findById(testChap.getId())).thenReturn(Optional.empty());
        //then
        assertThatThrownBy(() -> {
            service.delete(testChap);
        }).isInstanceOf(ChapterNotFoundException.class);
    }

    private static class TestDataProvider {

        public static final int TESTID1 = 1;

        public static final int TESTID2 = 2;

        public static final int WRONGID = -1;

        public static Chapters getTestChap1() {
            return new Chapters(TESTID1, 1, 1, "Chapter Description 1", 1);
        }

        public static Chapters getTestChap2() {
            return new Chapters(TESTID2, 2, 2, "Chapter Description 2", 2);
        }

        public static ChaptersEntity getTest1Entity() {
            return ChaptersEntity.builder()
                    .id(TESTID1)
                    .act(1)
                    .scene(1)
                    .description("Chapter Description 1")
                    .workId(1)
                    .build();
        }

        public static ChaptersEntity getTest2Entity() {
            return ChaptersEntity.builder()
                    .id(TESTID2)
                    .act(2)
                    .scene(2)
                    .description("Chapter Description 2")
                    .workId(2)
                    .build();
        }
    }
}
