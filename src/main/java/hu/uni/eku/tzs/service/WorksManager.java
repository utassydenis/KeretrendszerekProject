package hu.uni.eku.tzs.service;

import hu.uni.eku.tzs.model.Works;
import hu.uni.eku.tzs.service.exceptions.WorksAlreadyExistsException;
import hu.uni.eku.tzs.service.exceptions.WorksNotFoundException;

import java.util.Collection;

public interface WorksManager {

    Works record(Works work) throws WorksAlreadyExistsException;

    Works readById(int id) throws WorksNotFoundException;

    Collection<Works> readAll();

    Works modify(Works work) throws WorksNotFoundException;

    void delete(Works work) throws WorksNotFoundException;
}
