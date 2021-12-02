package hu.uni.eku.tzs.controller;

import hu.uni.eku.tzs.controller.dto.ChaptersDto;
import hu.uni.eku.tzs.controller.dto.ChaptersMapper;
import hu.uni.eku.tzs.model.Chapters;
import hu.uni.eku.tzs.service.ChapterManager;
import hu.uni.eku.tzs.service.exceptions.ChapterAlreadyExistsException;
import hu.uni.eku.tzs.service.exceptions.ChapterNotFoundException;
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
public class ChaptersControllerTest {

    @Mock
    private ChapterManager chapterManager;

    @Mock
    private ChaptersMapper chaptersMapper;

    @InjectMocks
    private ChaptersController controller;

    @Test
    void readAllHappyPath() { //ReadAll
        // given
        when(chapterManager.readAll()).thenReturn(List.of(TestDataProvider.getTestChapter()));
        when(chaptersMapper.chapters2ChaptersrDto(any())).thenReturn(TestDataProvider.getTestDto());
        Collection<ChaptersDto> expected = List.of(TestDataProvider.getTestDto());
        // when
        Collection<ChaptersDto> actual = controller.readAllChapters();
        //then
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void createChaptersHappyPath() throws ChapterAlreadyExistsException { //Create Chapter
        // given
        Chapters test = TestDataProvider.getTestChapter();
        ChaptersDto testDto = TestDataProvider.getTestDto();
        when(chaptersMapper.chaptersDto2Chapters(testDto)).thenReturn(test);
        when(chapterManager.record(test)).thenReturn(test);
        when(chaptersMapper.chapters2ChaptersrDto(test)).thenReturn(testDto);
        // when
        ChaptersDto actual = controller.create(testDto);
        // then
        assertThat(actual).usingRecursiveComparison().isEqualTo(testDto);
    }

    @Test
    void createChapterChapterAlreadyExistsException() throws ChapterAlreadyExistsException { //Create chapter Exception
        // given
        Chapters test = TestDataProvider.getTestChapter();
        ChaptersDto testDto = TestDataProvider.getTestDto();
        when(chaptersMapper.chaptersDto2Chapters(testDto)).thenReturn(test);
        when(chapterManager.record(test)).thenThrow(new ChapterAlreadyExistsException());
        // when then
        assertThatThrownBy(() -> {
            controller.create(testDto);
        }).isInstanceOf(ResponseStatusException.class);
    }

    @Test
    void updateHappyPath() throws ChapterNotFoundException { //Update Chapter
        // given
        ChaptersDto requestDto = TestDataProvider.getTestDto();
        Chapters test = TestDataProvider.getTestChapter();
        when(chaptersMapper.chaptersDto2Chapters(requestDto)).thenReturn(test);
        when(chapterManager.modify(test)).thenReturn(test);
        when(chaptersMapper.chapters2ChaptersrDto(test)).thenReturn(requestDto);
        ChaptersDto expected = TestDataProvider.getTestDto();
        // when
        ChaptersDto response = controller.modify(requestDto);
        // then
        assertThat(response).usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    void deleteFromQueryParamHappyPath() throws ChapterNotFoundException { //Delete chapter
        // given
        Chapters test = TestDataProvider.getTestChapter();
        when(chapterManager.readById(TestDataProvider.ID)).thenReturn(test);
        doNothing().when(chapterManager).delete(test);
        // when
        controller.delete(TestDataProvider.ID);
        // then is not necessary, mock are checked by default
    }

    @Test
    void deleteFromQueryParamWhenChapterNotFound() throws ChapterNotFoundException { //Delete Chapter Chapter not found
        // given
        final int notFoundChapterID = TestDataProvider.ID;
        doThrow(new ChapterNotFoundException()).when(chapterManager).readById(notFoundChapterID);
//        These two lines mean the same.
//        doThrow(new BookNotFoundException()).when(bookManager).readByIsbn(notFoundBookIsbn);
//        when(bookManager.readByIsbn(notFoundBookIsbn)).thenThrow(new BookNotFoundException());
        // when then
        assertThatThrownBy(() -> controller.delete(notFoundChapterID))
                .isInstanceOf(ResponseStatusException.class);
    }

    @Test
    void readByIdHappyPath() throws ChapterNotFoundException {
        when(chapterManager.readById(TestDataProvider.getTestChapter().getId())).thenReturn(TestDataProvider.getTestChapter());
        ChaptersDto expected = TestDataProvider.getTestDto();
        when(chaptersMapper.chapters2ChaptersrDto(any())).thenReturn(TestDataProvider.getTestDto());
        ChaptersDto actual = controller.readById(TestDataProvider.getTestChapter().getId());
        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    void readByIdChapterNotFoundException() throws ChapterNotFoundException {
        final int notFoundChapterId = TestDataProvider.ID;
        doThrow(new ChapterNotFoundException()).when(chapterManager).readById(notFoundChapterId);
        assertThatThrownBy(() -> controller.readById(notFoundChapterId))
                .isInstanceOf(ResponseStatusException.class);
    }

    private static class TestDataProvider {

        public static final int ID = 1;

        public static Chapters getTestChapter() {
            return new Chapters(ID, 1, 1, "Test", 1);
        }

        public static ChaptersDto getTestDto() {
            return ChaptersDto.builder()
                    .id(ID)
                    .act(1)
                    .scene(1)
                    .description("Test")
                    .workId(1)
                    .build();
        }
    }
}
