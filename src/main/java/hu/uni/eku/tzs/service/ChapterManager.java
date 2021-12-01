package hu.uni.eku.tzs.service;

import hu.uni.eku.tzs.model.Chapters;
import hu.uni.eku.tzs.service.exceptions.ChapterAlreadyExistsException;
import hu.uni.eku.tzs.service.exceptions.ChapterNotFoundException;

import java.util.Collection;

public interface ChapterManager {

    Chapters record(Chapters chapter) throws ChapterAlreadyExistsException;

    Chapters readById(int id) throws ChapterNotFoundException;

    Collection<Chapters> readAll();

    Chapters modify(Chapters chapter) throws ChapterNotFoundException;

    void delete(Chapters chapter) throws ChapterNotFoundException;
}
