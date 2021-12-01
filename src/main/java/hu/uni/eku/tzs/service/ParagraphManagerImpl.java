package hu.uni.eku.tzs.service;

import hu.uni.eku.tzs.dao.ParagraphsRepository;
import hu.uni.eku.tzs.dao.entity.ParagraphsEntity;
import hu.uni.eku.tzs.model.Paragraphs;
import hu.uni.eku.tzs.service.exceptions.ParagraphsAlreadyExistsException;
import hu.uni.eku.tzs.service.exceptions.ParagraphsNotFoundException;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class ParagraphManagerImpl implements ParagraphsManager{

    private final ParagraphsRepository paragraphsRepository;

    private static Paragraphs convertParagraphsEntity2Model(ParagraphsEntity paragraphEntity) {
        return new Paragraphs(
                paragraphEntity.getId(),
                paragraphEntity.getParagraphNum(),
                paragraphEntity.getPlainText(),
                paragraphEntity.getCharacterID(),
                paragraphEntity.getChapterID()
        );
    }

    private static ParagraphsEntity convertParagraphsModel2Entity(Paragraphs paragraph) {
        return ParagraphsEntity.builder()
                .id(paragraph.getId())
                .paragraphNum(paragraph.getParagraphNum())
                .plainText(paragraph.getPlainText())
                .characterID(paragraph.getCharacter_ID())
                .chapterID(paragraph.getChapter_ID())
                .build();
    }

    @Override
    public Paragraphs record(Paragraphs paragraph) throws ParagraphsAlreadyExistsException {
        if (paragraphsRepository.findById(paragraph.getId()).isPresent()) {
            throw new ParagraphsAlreadyExistsException();
        }
        ParagraphsEntity paragraphEntity = paragraphsRepository.save(
                ParagraphsEntity.builder()
                        .id(paragraph.getId())
                        .paragraphNum(paragraph.getParagraphNum())
                        .plainText(paragraph.getPlainText())
                        .characterID(paragraph.getCharacter_ID())
                        .chapterID(paragraph.getChapter_ID())
                        .build()
        );
        return convertParagraphsEntity2Model(paragraphEntity);
    }


    @Override
    public Paragraphs readById(int id) throws ParagraphsNotFoundException {
        Optional<ParagraphsEntity> entity = paragraphsRepository.findById(id);
        if (entity.isEmpty()) {
            throw new ParagraphsNotFoundException(String.format("Cannot find paragraph with ID: %s", id));
        }
        return convertParagraphsEntity2Model(entity.get());
    }

    @Override
    public Collection<Paragraphs> readAll() {
        return paragraphsRepository.findAll().stream().map(ParagraphManagerImpl::convertParagraphsEntity2Model)
                .collect(Collectors.toList());
    }

    @Override
    public Paragraphs modify(Paragraphs paragraph) {
        ParagraphsEntity entity = convertParagraphsModel2Entity(paragraph);
        return convertParagraphsEntity2Model(paragraphsRepository.save(entity));
    }

    @Override
    public void delete(Paragraphs paragraph) {
        paragraphsRepository.delete(convertParagraphsModel2Entity(paragraph));
    }
}
