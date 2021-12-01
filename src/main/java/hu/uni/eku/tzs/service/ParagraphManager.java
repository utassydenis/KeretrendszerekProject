package hu.uni.eku.tzs.service;

import hu.uni.eku.tzs.model.Paragraphs;
import hu.uni.eku.tzs.service.exceptions.ParagraphAlreadyExistsException;
import hu.uni.eku.tzs.service.exceptions.ParagraphNotFoundException;

import java.util.Collection;

public interface ParagraphManager {
    Paragraphs record(Paragraphs paragraph) throws ParagraphAlreadyExistsException;

    Paragraphs readById(int id) throws ParagraphNotFoundException;

    Collection<Paragraphs> readAll();

    Paragraphs modify(Paragraphs paragraph) throws ParagraphNotFoundException;

    void delete(Paragraphs paragraph) throws ParagraphNotFoundException;
}
