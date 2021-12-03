package hu.uni.eku.tzs.service;

import hu.uni.eku.tzs.controller.dto.WorksMapperImpl;
import hu.uni.eku.tzs.dao.ParagraphsRepository;
import hu.uni.eku.tzs.dao.WorksRepository;
import hu.uni.eku.tzs.dao.entity.ParagraphsEntity;
import hu.uni.eku.tzs.dao.entity.WorksEntity;
import hu.uni.eku.tzs.model.Paragraphs;
import hu.uni.eku.tzs.model.Works;
import hu.uni.eku.tzs.service.exceptions.ParagraphAlreadyExistsException;
import hu.uni.eku.tzs.service.exceptions.ParagraphNotFoundException;
import hu.uni.eku.tzs.service.exceptions.WorksAlreadyExistsException;
import hu.uni.eku.tzs.service.exceptions.WorksNotFoundException;
import org.hibernate.jdbc.Work;
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
public class WorksManagerImplTest {
    @Mock
    WorksRepository worksRepository;


    @InjectMocks
    WorkManagerImpl service;

    @Test
    void recordWorkHappyPath() throws WorksAlreadyExistsException { //Record character
        // given
        Works testWork = TestDataProvider.getTestWork1();
        WorksEntity testEntity = TestDataProvider.getTest1Entity();
        when(worksRepository.findById(any())).thenReturn(Optional.empty());
        when(worksRepository.save(any())).thenReturn(testEntity);
        // when
        Works actual = service.record(testWork);
        // then
        assertThat(actual).usingRecursiveComparison().isEqualTo(testWork);
//        assertThat(actual).isEqualToComparingFieldByFieldRecursively(hg2g);
    }

    @Test
    void recordWorkWorkAlreadyExistsException() { //Record Character exception
        // given
        Works testWork = TestDataProvider.getTestWork1();
        WorksEntity testEntity = TestDataProvider.getTest1Entity();
        when(worksRepository.findById(TestDataProvider.TESTID1)).thenReturn(Optional.ofNullable(testEntity));
        // when
        // then
        assertThatThrownBy(() -> {
            service.record(testWork);
        }).isInstanceOf(WorksAlreadyExistsException.class);
    }


    @Test
    void readByIdHappyPath() throws WorksNotFoundException { //ReadById
        // given
        when(worksRepository.findById(TestDataProvider.TESTID1))
                .thenReturn(Optional.of(TestDataProvider.getTest1Entity()));
        Works expected = TestDataProvider.getTestWork1();
        // when
        Works actual = service.readById(TestDataProvider.TESTID1);
        // then
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void readByIdWorkNotFoundException() { //ReadById exception
        // given
        when(worksRepository.findById(TestDataProvider.WRONGID)).thenReturn(Optional.empty());
        // when then
        assertThatThrownBy(() -> {
            service.readById(TestDataProvider.WRONGID);
        }).isInstanceOf(WorksNotFoundException.class)
                .hasMessageContaining(String.valueOf(TestDataProvider.WRONGID));
    }

    @Test
    void readAllHappyPath() { //ReadAll
        // given
        List<WorksEntity> WorkEntities = List.of(
                TestDataProvider.getTest1Entity(),
                TestDataProvider.getTest2Entity()
        );
        Collection<Works> expectedWorks = List.of(
                TestDataProvider.getTestWork1(),
                TestDataProvider.getTestWork2()
        );
        // when
        when(worksRepository.findAll()).thenReturn(WorkEntities);
        // then
        Collection<Works> actualWorks = service.readAll();
        assertThat(actualWorks)
                .usingRecursiveComparison()
                .isEqualTo(expectedWorks);
    }

    @Test
    void modifyWorkHappyPath() throws WorksNotFoundException { //Modify character
        // given
        Works testWork = TestDataProvider.getTestWork1();
        WorksEntity testEntity = TestDataProvider.getTest1Entity();
        when(worksRepository.findById(testWork.getId())).thenReturn(Optional.ofNullable(testEntity));
        when(worksRepository.save(testEntity)).thenReturn(testEntity);
        // when
        Works actual = service.modify(testWork);
        // then
        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(testWork);

    }

    @Test
    void modifyWorkWorkNotFound() {
        //given
        Works testWork = TestDataProvider.getTestWork1();
        //when
        when(worksRepository.findById(testWork.getId())).thenReturn(Optional.empty());
        //then
        assertThatThrownBy(() -> {
            service.modify(testWork);
        }).isInstanceOf(WorksNotFoundException.class);
    }

    @Test
    void deleteWorkHappyPath() throws WorksNotFoundException {
        // given
        Works testWork = TestDataProvider.getTestWork1();
        WorksEntity testEntity = TestDataProvider.getTest1Entity();
        // when
        when(worksRepository.findById(testWork.getId())).thenReturn(Optional.ofNullable(testEntity));
        // then
        service.delete(testWork);
    }

    @Test
    void DeleteWorkWorkNotFound() {
        //given
        Works testWork = TestDataProvider.getTestWork1();
        //when
        when(worksRepository.findById(testWork.getId())).thenReturn(Optional.empty());
        //then
        assertThatThrownBy(() -> {
            service.delete(testWork);
        }).isInstanceOf(WorksNotFoundException.class);
    }

    private static class TestDataProvider {

        public static final int TESTID1 = 1;

        public static final int TESTID2 = 2;

        public static final int WRONGID = -1;

        public static Works getTestWork1() {
            return new Works(TESTID1, "Work One","Work One Long",1996,"Comedy");
        }

        public static Works getTestWork2() {
            return new Works(TESTID2, "Work Two","Work Two Long",1997,"Thriller");
        }

        public static WorksEntity getTest1Entity() {
            return WorksEntity.builder()
                    .id(TESTID1)
                    .title("Work One")
                    .longTitle("Work One Long")
                    .date(1996)
                    .genreType("Comedy")
                    .build();
        }

        public static WorksEntity getTest2Entity() {
            return WorksEntity.builder()
                    .id(TESTID2)
                    .title("Work Two")
                    .longTitle("Work Two Long")
                    .date(1997)
                    .genreType("Thriller")
                    .build();
        }
    }
}
