package hu.uni.eku.tzs.service;

import hu.uni.eku.tzs.model.Paragraphs;
import hu.uni.eku.tzs.service.exceptions.ParagraphsAlreadyExistsException;
import hu.uni.eku.tzs.service.exceptions.ParagraphsNotFoundException;

import java.util.Collection;

public interface ParagraphsManager {
    Paragraphs record(Paragraphs paragraph) throws ParagraphsAlreadyExistsException;

    Paragraphs readById(int id) throws ParagraphsNotFoundException;

    Collection<Paragraphs> readAll();

    Paragraphs modify(Paragraphs paragraph) throws ParagraphsNotFoundException;

    void delete(Paragraphs paragraph) throws ParagraphsNotFoundException;
}
