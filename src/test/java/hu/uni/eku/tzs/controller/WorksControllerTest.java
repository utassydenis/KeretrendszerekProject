package hu.uni.eku.tzs.controller;

import hu.uni.eku.tzs.controller.dto.WorksDto;
import hu.uni.eku.tzs.controller.dto.WorksMapper;
import hu.uni.eku.tzs.model.Works;
import hu.uni.eku.tzs.service.WorkManager;
import hu.uni.eku.tzs.service.exceptions.WorksAlreadyExistsException;
import hu.uni.eku.tzs.service.exceptions.WorksNotFoundException;
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


public class WorksControllerTest {
    @Mock
    private WorkManager workManager;

    @Mock
    private WorksMapper workMapper;

    @InjectMocks
    private WorksController controller;

    @Test
    void readAllHappyPath() {
        // given
        when(workManager.readAll()).thenReturn(List.of(WorksControllerTest.TestDataProvider.getTestWork()));
        when(workMapper.works2worksDto(any())).thenReturn(WorksControllerTest.TestDataProvider.getTestDto());
        Collection<WorksDto> expected = List.of(WorksControllerTest.TestDataProvider.getTestDto());
        // when
        Collection<WorksDto> actual = controller.readAllWorks();
        //then
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);


    }

    @Test
    void createWorksHappyPath() throws WorksAlreadyExistsException {
        // given
        Works test = WorksControllerTest.TestDataProvider.getTestWork();
        WorksDto testDto = WorksControllerTest.TestDataProvider.getTestDto();
        when(workMapper.worksDto2works(testDto)).thenReturn(test);
        when(workManager.record(test)).thenReturn(test);
        when(workMapper.works2worksDto(test)).thenReturn(testDto);
        // when
        WorksDto actual = controller.create(testDto);
        // then
        assertThat(actual).usingRecursiveComparison().isEqualTo(testDto);
    }

    @Test
    void createWorkAlreadyExistsException() throws WorksAlreadyExistsException {
        // given
        Works test = WorksControllerTest.TestDataProvider.getTestWork();
        WorksDto testDto = WorksControllerTest.TestDataProvider.getTestDto();
        when(workMapper.worksDto2works(testDto)).thenReturn(test);
        when(workManager.record(test)).thenThrow(new WorksAlreadyExistsException());
        // when then
        assertThatThrownBy(() -> {
            controller.create(testDto);
        }).isInstanceOf(ResponseStatusException.class);
    }

    @Test
    void updateHappyPath() throws WorksNotFoundException {
        // given
        WorksDto requestDto = WorksControllerTest.TestDataProvider.getTestDto();
        Works test = WorksControllerTest.TestDataProvider.getTestWork();
        when(workMapper.worksDto2works(requestDto)).thenReturn(test);
        when(workManager.modify(test)).thenReturn(test);
        when(workMapper.works2worksDto(test)).thenReturn(requestDto);
        WorksDto expected = WorksControllerTest.TestDataProvider.getTestDto();
        // when
        WorksDto response = controller.modify(requestDto);
        // then
        assertThat(response).usingRecursiveComparison().isEqualTo(expected);
    }


    @Test
    void deleteFromQueryParamHappyPath() throws WorksNotFoundException {
        // given
        Works test = WorksControllerTest.TestDataProvider.getTestWork();
        when(workManager.readById(WorksControllerTest.TestDataProvider.ID)).thenReturn(test);
        doNothing().when(workManager).delete(test);
        // when
        controller.delete(WorksControllerTest.TestDataProvider.ID);
        // then is not necessary, mock are checked by default
    }

    @Test
    void deleteFromQueryWorksWhenParagraphNotFound() throws WorksNotFoundException {
        // given
        final int notFoundWorkID = WorksControllerTest.TestDataProvider.ID;
        doThrow(new WorksNotFoundException()).when(workManager).readById(notFoundWorkID);
//        These two lines mean the same.
//        doThrow(new BookNotFoundException()).when(bookManager).readByIsbn(notFoundBookIsbn);
//        when(bookManager.readByIsbn(notFoundBookIsbn)).thenThrow(new BookNotFoundException());
        // when then
        assertThatThrownBy(() -> controller.delete(notFoundWorkID)).isInstanceOf(ResponseStatusException.class);
    }

    private static class TestDataProvider {

        public static final int ID = 1;

        public static Works getTestWork() {
            return new Works(ID, "Test Work", "Long Test Work", 12, "Comedy");
        }

        public static WorksDto getTestDto() {
            return WorksDto.builder().id(ID).title("Test Work").longTitle("Long Test Work").date(12).genreType("Comedy").build();
        }
    }
}
