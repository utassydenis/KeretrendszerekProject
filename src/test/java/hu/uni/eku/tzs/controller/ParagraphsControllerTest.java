package hu.uni.eku.tzs.controller;

import hu.uni.eku.tzs.controller.dto.ParagraphsDto;
import hu.uni.eku.tzs.controller.dto.ParagraphsMapper;
import hu.uni.eku.tzs.model.Paragraphs;
import hu.uni.eku.tzs.service.ParagraphManager;
import hu.uni.eku.tzs.service.exceptions.*;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doThrow;


public class ParagraphsControllerTest {
    @Mock
    private ParagraphManager paragraphManager;

    @Mock
    private ParagraphsMapper paragraphsMapper;

    @InjectMocks
    private ParagraphsController controller;

    @Test
    void readAllHappyPath() {
        // given
        when(paragraphManager.readAll()).thenReturn(List.of(ParagraphsControllerTest.TestDataProvider.getTestParagraph()));
        when(paragraphsMapper.paragraphs2paragraphsDto(any())).thenReturn(ParagraphsControllerTest.TestDataProvider.getTestDto());
        Collection<ParagraphsDto> expected = List.of(ParagraphsControllerTest.TestDataProvider.getTestDto());
        // when
        Collection<ParagraphsDto> actual = controller.readAllParagraphs();
        //then
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);


    }

    @Test
    void createParagraphsHappyPath() throws ParagraphAlreadyExistsException {
        // given
        Paragraphs test = ParagraphsControllerTest.TestDataProvider.getTestParagraph();
        ParagraphsDto testDto = ParagraphsControllerTest.TestDataProvider.getTestDto();
        when(paragraphsMapper.paragraphsDto2paragraphs(testDto)).thenReturn(test);
        when(paragraphManager.record(test)).thenReturn(test);
        when(paragraphsMapper.paragraphs2paragraphsDto(test)).thenReturn(testDto);
        // when
        ParagraphsDto actual = controller.create(testDto);
        // then
        assertThat(actual).usingRecursiveComparison().isEqualTo(testDto);
    }

    @Test
    void createParagraphAlreadyExistsException() throws ParagraphAlreadyExistsException {
        // given
        Paragraphs test = ParagraphsControllerTest.TestDataProvider.getTestParagraph();
        ParagraphsDto testDto = ParagraphsControllerTest.TestDataProvider.getTestDto();
        when(paragraphsMapper.paragraphsDto2paragraphs(testDto)).thenReturn(test);
        when(paragraphManager.record(test)).thenThrow(new ParagraphAlreadyExistsException());
        // when then
        assertThatThrownBy(() -> {
            controller.create(testDto);
        }).isInstanceOf(ResponseStatusException.class);
    }

    @Test
    void updateHappyPath() throws ParagraphNotFoundException {
        // given
        ParagraphsDto requestDto = ParagraphsControllerTest.TestDataProvider.getTestDto();
        Paragraphs test = ParagraphsControllerTest.TestDataProvider.getTestParagraph();
        when(paragraphsMapper.paragraphsDto2paragraphs(requestDto)).thenReturn(test);
        when(paragraphManager.modify(test)).thenReturn(test);
        when(paragraphsMapper.paragraphs2paragraphsDto(test)).thenReturn(requestDto);
        ParagraphsDto expected = ParagraphsControllerTest.TestDataProvider.getTestDto();
        // when
        ParagraphsDto response = controller.modify(requestDto);
        // then
        assertThat(response).usingRecursiveComparison()
                .isEqualTo(expected);
    }


    @Test
    void deleteFromQueryParamHappyPath() throws ParagraphNotFoundException {
        // given
        Paragraphs test = ParagraphsControllerTest.TestDataProvider.getTestParagraph();
        when(paragraphManager.readById(ParagraphsControllerTest.TestDataProvider.ID)).thenReturn(test);
        doNothing().when(paragraphManager).delete(test);
        // when
        controller.delete(ParagraphsControllerTest.TestDataProvider.ID);
        // then is not necessary, mock are checked by default
    }

    @Test
    void deleteFromQueryParamWhenParagraphNotFound() throws ParagraphNotFoundException {
        // given
        final int notFoundParagraphID = ParagraphsControllerTest.TestDataProvider.ID;
        doThrow(new ParagraphNotFoundException()).when(paragraphManager).readById(notFoundParagraphID);
//        These two lines mean the same.
//        doThrow(new BookNotFoundException()).when(bookManager).readByIsbn(notFoundBookIsbn);
//        when(bookManager.readByIsbn(notFoundBookIsbn)).thenThrow(new BookNotFoundException());
        // when then
        assertThatThrownBy(() -> controller.delete(notFoundParagraphID))
                .isInstanceOf(ResponseStatusException.class);
    }

    private static class TestDataProvider {

        public static final int ID = 1;

        public static Paragraphs getTestParagraph() {
            return new Paragraphs(ID, 1, "Test text", 1, 1);
        }

        public static ParagraphsDto getTestDto() {
            return ParagraphsDto.builder()
                    .id(ID)
                    .paragraphNum(1)
                    .plainText("Test text")
                    .characterId(1)
                    .chapterId(1)
                    .build();
        }
    }
}
