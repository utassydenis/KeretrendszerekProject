package hu.uni.eku.tzs.service;

import hu.uni.eku.tzs.model.Characters;
import hu.uni.eku.tzs.service.exceptions.CharacterAlreadyExistsException;
import hu.uni.eku.tzs.service.exceptions.CharacterNotFoundException;

import java.util.Collection;

public interface CharacterManager {

    Characters record(Characters character) throws CharacterAlreadyExistsException;

    Characters readById(int id) throws CharacterNotFoundException;

    Collection<Characters> readAll();

    Characters modify(Characters character) throws CharacterNotFoundException;

    void delete(Characters character) throws CharacterNotFoundException;
}
