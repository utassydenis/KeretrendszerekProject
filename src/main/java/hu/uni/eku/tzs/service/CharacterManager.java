package hu.uni.eku.tzs.service;

import hu.uni.eku.tzs.model.Characters;
import hu.uni.eku.tzs.service.exceptions.CharactersAlreadyExistsException;
import hu.uni.eku.tzs.service.exceptions.CharactersNotFoundException;

import java.util.Collection;

public interface CharacterManager {

    Characters record(Characters character) throws CharactersAlreadyExistsException;

    Characters readById(int id) throws CharactersNotFoundException;

    Collection<Characters> readAll();

    Characters modify(Characters character) throws CharactersNotFoundException;

    void delete(Characters character) throws CharactersNotFoundException;

}
