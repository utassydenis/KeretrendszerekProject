package hu.uni.eku.tzs.controller;

import hu.uni.eku.tzs.controller.dto.WorksDto;
import hu.uni.eku.tzs.controller.dto.WorksMapper;
import hu.uni.eku.tzs.model.Works;
import hu.uni.eku.tzs.service.WorkManager;
import hu.uni.eku.tzs.service.exceptions.WorksAlreadyExistsException;
import hu.uni.eku.tzs.service.exceptions.WorksNotFoundException;
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
        when(workManager.readAll()).thenReturn(List.of(TestDataProvider.getTestWork()));
        when(workMapper.works2worksDto(any())).thenReturn(TestDataProvider.getTestDto());
        Collection<WorksDto> expected = List.of(TestDataProvider.getTestDto());
        // when
        Collection<WorksDto> actual = controller.readAllWorks();
        //then
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);


    }

    @Test
    void createWorkssHappyPath() throws WorksAlreadyExistsException {
        // given
        Works test = TestDataProvider.getTestWork();
        WorksDto testDto = TestDataProvider.getTestDto();
        when(workMapper.worksDto2works(testDto)).thenReturn(test);
        when(workManager.record(test)).thenReturn(test);
        when(workMapper.works2worksDto(test)).thenReturn(testDto);
        // when
        WorksDto actual = controller.create(testDto);
        // then
        assertThat(actual).usingRecursiveComparison().isEqualTo(testDto);
    }

    @Test
    void createWorkWorksAlreadyExistsException() throws WorksAlreadyExistsException {
        // given
        Works test = TestDataProvider.getTestWork();
        WorksDto testDto = TestDataProvider.getTestDto();
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
        WorksDto requestDto = TestDataProvider.getTestDto();
        Works test = TestDataProvider.getTestWork();
        when(workMapper.worksDto2works(requestDto)).thenReturn(test);
        when(workManager.modify(test)).thenReturn(test);
        when(workMapper.works2worksDto(test)).thenReturn(requestDto);
        WorksDto expected = TestDataProvider.getTestDto();
        // when
        WorksDto response = controller.modify(requestDto);
        // then
        assertThat(response).usingRecursiveComparison()
                .isEqualTo(expected);
    }


    @Test
    void deleteFromQueryParamHappyPath() throws WorksNotFoundException {
        // given
        Works test = TestDataProvider.getTestWork();
        when(workManager.readById(TestDataProvider.ID)).thenReturn(test);
        doNothing().when(workManager).delete(test);
        // when
        controller.delete(TestDataProvider.ID);
        // then is not necessary, mock are checked by default
    }

    @Test
    void deleteFromQueryParamWhenWorkNotFound() throws WorksNotFoundException {
        // given
        final int notFoundWorkID = TestDataProvider.ID;
        doThrow(new WorksNotFoundException()).when(workManager).readById(notFoundWorkID);
//        These two lines mean the same.
//        doThrow(new BookNotFoundException()).when(bookManager).readByIsbn(notFoundBookIsbn);
//        when(bookManager.readByIsbn(notFoundBookIsbn)).thenThrow(new BookNotFoundException());
        // when then
        assertThatThrownBy(() -> controller.delete(notFoundWorkID))
                .isInstanceOf(ResponseStatusException.class);
    }

    private static class TestDataProvider {

        public static final int ID = 1;

        public static Works getTestWork() {
            return new Works(ID, "Test Work", "Test Work Long", 1996, "Comedy");
        }

        public static WorksDto getTestDto() {
            return WorksDto.builder()
                    .id(ID)
                    .title("Test Work")
                    .longTitle("Test Work Long")
                    .date(1996)
                    .genreType("Comedy")
                    .build();
        }
    }
}
