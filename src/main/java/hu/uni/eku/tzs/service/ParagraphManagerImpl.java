package hu.uni.eku.tzs.service;

import hu.uni.eku.tzs.dao.ParagraphsRepository;
import hu.uni.eku.tzs.dao.entity.ParagraphsEntity;
import hu.uni.eku.tzs.model.Paragraphs;
import hu.uni.eku.tzs.service.exceptions.ParagraphAlreadyExistsException;
import hu.uni.eku.tzs.service.exceptions.ParagraphNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ParagraphManagerImpl implements ParagraphManager {

    private final ParagraphsRepository paragraphsRepository;

    private static Paragraphs convertParagraphsEntity2Model(ParagraphsEntity paragraphsEntity) {
        return new Paragraphs(
                paragraphsEntity.getId(),
                paragraphsEntity.getParagraphNum(),
                paragraphsEntity.getPlainText(),
                paragraphsEntity.getCharacter_ID(),
                paragraphsEntity.getChapter_ID()
                );
    }

    private static ParagraphsEntity convertParagraphssModel2Entity(Paragraphs paragraph) {
        return ParagraphsEntity.builder()
                .id(paragraph.getId())
                .paragraphNum(paragraph.getParagraphNum())
                .plainText(paragraph.getPlainText())
                .character_ID(paragraph.getCharacter_ID())
                .chapter_ID(paragraph.getChapter_ID())
                .build();
    }

    @Override
    public Paragraphs record(Paragraphs paragraph) throws ParagraphAlreadyExistsException {
        if (paragraphsRepository.findById(paragraph.getId()).isPresent()) {
            throw new ParagraphAlreadyExistsException();
        }
        ParagraphsEntity paragraphEntity = paragraphsRepository.save(
                ParagraphsEntity.builder()
                        .id(paragraph.getId())
                        .paragraphNum(paragraph.getParagraphNum())
                        .plainText(paragraph.getPlainText())
                        .character_ID(paragraph.getCharacter_ID())
                        .chapter_ID(paragraph.getChapter_ID())
                        .build()
        );
        return convertParagraphsEntity2Model(paragraphEntity);
    }

    @Override
    public Paragraphs readById(int id) throws ParagraphNotFoundException {
        Optional<ParagraphsEntity> entity = paragraphsRepository.findById(id);
        if (entity.isEmpty()) {
            throw new ParagraphNotFoundException(String.format("Cannot find paragraph with ID %s", id));
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
        ParagraphsEntity entity = convertParagraphssModel2Entity(paragraph);
        return convertParagraphsEntity2Model(paragraphsRepository.save(entity));
    }

    @Override
    public void delete(Paragraphs paragraph) {
        paragraphsRepository.delete(convertParagraphssModel2Entity(paragraph));
    }
}
