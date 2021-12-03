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
                paragraphsEntity.getCharacterId(),
                paragraphsEntity.getChapterId()
        );
    }

    private static ParagraphsEntity convertParagraphssModel2Entity(Paragraphs paragraph) {
        return ParagraphsEntity.builder()
                .id(paragraph.getId())
                .paragraphNum(paragraph.getParagraphNum())
                .plainText(paragraph.getPlainText())
                .characterId(paragraph.getCharacterId())
                .chapterId(paragraph.getChapterId())
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
                        .characterId(paragraph.getCharacterId())
                        .chapterId(paragraph.getChapterId())
                        .build()
        );
        return convertParagraphsEntity2Model(paragraphEntity);
    }

    @Override
    public Paragraphs readById(int id) throws ParagraphNotFoundException {
        Optional<ParagraphsEntity> entity = paragraphsRepository.findById(id);
        if (entity.isEmpty()) {
            throw new ParagraphNotFoundException(
                    String.format("Cannot find paragraph with ID %s", id)
            );
        }
        return convertParagraphsEntity2Model(entity.get());
    }

    @Override
    public Collection<Paragraphs> readAll() {
        return paragraphsRepository.findAll().stream().map(ParagraphManagerImpl::convertParagraphsEntity2Model)
                .collect(Collectors.toList());
    }

    @Override
    public Paragraphs modify(Paragraphs paragraph) throws ParagraphNotFoundException {
        ParagraphsEntity entity = convertParagraphssModel2Entity(paragraph);
        if (paragraphsRepository.findById(entity.getId()).isEmpty()) {
            throw new ParagraphNotFoundException(String.format("Modify error: Paragraph ID %s not found", paragraph.getId()));
        }
        return convertParagraphsEntity2Model(paragraphsRepository.save(entity));
    }

    @Override
    public void delete(Paragraphs paragraph) throws ParagraphNotFoundException {
        if (paragraphsRepository.findById(paragraph.getId()).isEmpty()) {
            throw new ParagraphNotFoundException(String.format("Delete error: Paragraph ID %s not found", paragraph.getId()));
        }
        paragraphsRepository.delete(convertParagraphssModel2Entity(paragraph));
    }
}
