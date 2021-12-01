package hu.uni.eku.tzs.service;

import hu.uni.eku.tzs.dao.ChaptersRepository;
import hu.uni.eku.tzs.dao.entity.ChaptersEntity;
import hu.uni.eku.tzs.model.Chapters;
import hu.uni.eku.tzs.service.exceptions.ChapterAlreadyExistsException;
import hu.uni.eku.tzs.service.exceptions.ChapterNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChapterManagerImpl implements ChapterManager{

    private final ChaptersRepository chapterRepository;

    private static Chapters convertChaptersEntity2Model(ChaptersEntity chapterEntity) {
        return new Chapters(
                chapterEntity.getId(),
                chapterEntity.getAct(),
                chapterEntity.getScene(),
                chapterEntity.getDescription(),
                chapterEntity.getWork_ID()
                );
    }

    private static ChaptersEntity convertChaptersModel2Entity(Chapters chapter) {
        return ChaptersEntity.builder()
                .id(chapter.getId())
                .act(chapter.getAct())
                .scene(chapter.getScene())
                .description(chapter.getDescription())
                .work_ID(chapter.getWork_ID())
                .build();
    }


    @Override
    public Chapters record(Chapters chapter) throws ChapterAlreadyExistsException {
        if (chapterRepository.findById(chapter.getId()).isPresent()) {
            throw new ChapterAlreadyExistsException();
        }
        ChaptersEntity chapterEntity = chapterRepository.save(
                ChaptersEntity.builder()
                        .id(chapter.getId())
                        .act(chapter.getAct())
                        .scene(chapter.getScene())
                        .description(chapter.getDescription())
                        .work_ID(chapter.getWork_ID())
                        .build()
        );
        return convertChaptersEntity2Model(chapterEntity);
    }

    @Override
    public Chapters readById(int id) throws ChapterNotFoundException {
        Optional<ChaptersEntity> entity = chapterRepository.findById(id);
        if (entity.isEmpty()) {
            throw new ChapterNotFoundException(String.format("Cannot find chapter with ID %s", id));
        }
        return convertChaptersEntity2Model(entity.get());
    }

    @Override
    public Collection<Chapters> readAll() {
        return chapterRepository.findAll().stream().map(ChapterManagerImpl::convertChaptersEntity2Model)
                .collect(Collectors.toList());
    }

    @Override
    public Chapters modify(Chapters chapter) {
        ChaptersEntity entity = convertChaptersModel2Entity(chapter);
        return convertChaptersEntity2Model(chapterRepository.save(entity));
    }

    @Override
    public void delete(Chapters chapter) {
        chapterRepository.delete(convertChaptersModel2Entity(chapter));
    }
}
