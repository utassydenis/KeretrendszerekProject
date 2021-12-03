package hu.uni.eku.tzs.service;

import hu.uni.eku.tzs.dao.WorksRepository;
import hu.uni.eku.tzs.dao.entity.WorksEntity;
import hu.uni.eku.tzs.model.Works;
import hu.uni.eku.tzs.service.exceptions.WorksAlreadyExistsException;
import hu.uni.eku.tzs.service.exceptions.WorksNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WorkManagerImpl implements WorkManager {
    private final WorksRepository workRepository;

    private static Works convertWorksEntity2Model(WorksEntity workEntity) {
        return new Works(
                workEntity.getId(),
                workEntity.getTitle(),
                workEntity.getLongTitle(),
                workEntity.getDate(),
                workEntity.getGenreType()
        );
    }

    private static WorksEntity convertWorkssModel2Entity(Works work) {
        return WorksEntity.builder()
                .id(work.getId())
                .title(work.getTitle())
                .longTitle(work.getLongTitle())
                .date(work.getDate())
                .genreType(work.getGenreType())
                .build();
    }

    @Override
    public Works record(Works work) throws WorksAlreadyExistsException {
        if (workRepository.findById(work.getId()).isPresent()) {
            throw new WorksAlreadyExistsException();
        }
        WorksEntity workEntity = workRepository.save(
                WorksEntity.builder()
                        .id(work.getId())
                        .title(work.getTitle())
                        .longTitle(work.getLongTitle())
                        .date(work.getDate())
                        .genreType(work.getGenreType())
                        .build()
        );
        return convertWorksEntity2Model(workEntity);
    }

    @Override
    public Works readById(int id) throws WorksNotFoundException {
        Optional<WorksEntity> entity = workRepository.findById(id);
        if (entity.isEmpty()) {
            throw new WorksNotFoundException(String.format("Cannot find work with ID %s", id));
        }
        return convertWorksEntity2Model(entity.get());
    }

    @Override
    public Collection<Works> readAll() {
        return workRepository.findAll().stream().map(WorkManagerImpl::convertWorksEntity2Model)
                .collect(Collectors.toList());
    }

    @Override
    public Works modify(Works work) throws WorksNotFoundException {
        WorksEntity entity = convertWorkssModel2Entity(work);
        if (workRepository.findById(entity.getId()).isEmpty()) {
            throw new WorksNotFoundException(String.format("Cannot modify work with ID %s, not found", work.getId()));
        }
        return convertWorksEntity2Model(workRepository.save(entity));
    }

    @Override
    public void delete(Works work) throws WorksNotFoundException {
        if (workRepository.findById(work.getId()).isEmpty()) {
            throw new WorksNotFoundException(String.format("Cannot delete work with ID %s, not found", work.getId()));
        }
        workRepository.delete(convertWorkssModel2Entity(work));
    }
}
