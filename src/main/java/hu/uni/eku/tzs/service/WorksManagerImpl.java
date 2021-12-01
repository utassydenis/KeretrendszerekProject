package hu.uni.eku.tzs.service;

import hu.uni.eku.tzs.dao.WorksRepository;
import hu.uni.eku.tzs.dao.entity.WorksEntity;
import hu.uni.eku.tzs.model.Works;
import hu.uni.eku.tzs.service.exceptions.WorksAlreadyExistsException;
import hu.uni.eku.tzs.service.exceptions.WorksNotFoundException;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor

public class WorksManagerImpl implements WorksManager {

    private final WorksRepository workRepository;

    private static Works convertWorkssEntity2Model(WorksEntity workEntity) {
        return new Works(
                workEntity.getId(),
                workEntity.getTitle(),
                workEntity.getLongTitle(),
                workEntity.getDate(),
                workEntity.getGenreType()
        );
    }

    private static WorksEntity convertWorksModel2Entity(Works work) {
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
        return convertWorkssEntity2Model(workEntity);
    }


    @Override
    public Works readById(int id) throws WorksNotFoundException {
        Optional<WorksEntity> entity = workRepository.findById(id);
        if (entity.isEmpty()) {
            throw new WorksNotFoundException(String.format("Cannot find work with ID: %s", id));
        }
        return convertWorkssEntity2Model(entity.get());
    }

    @Override
    public Collection<Works> readAll() {
        return workRepository.findAll().stream().map(WorksManagerImpl::convertWorkssEntity2Model)
                .collect(Collectors.toList());
    }

    @Override
    public Works modify(Works work) {
        WorksEntity entity = convertWorksModel2Entity(work);
        return convertWorkssEntity2Model(workRepository.save(entity));
    }

    @Override
    public void delete(Works work) {
        workRepository.delete(convertWorksModel2Entity(work));
    }
}
