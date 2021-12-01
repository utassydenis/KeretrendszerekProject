package hu.uni.eku.tzs.controller;

import hu.uni.eku.tzs.controller.dto.WorksDto;
import hu.uni.eku.tzs.controller.dto.WorksMapper;
import hu.uni.eku.tzs.model.Works;
import hu.uni.eku.tzs.service.WorkManager;
import hu.uni.eku.tzs.service.exceptions.WorksAlreadyExistsException;
import hu.uni.eku.tzs.service.exceptions.WorksNotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.stream.Collectors;

@Api(tags = "works")
@RestController
@RequiredArgsConstructor
@RequestMapping("/works")
public class WorksController {
    private final WorkManager worksManager;

    private final WorksMapper worksMapper;

    @ApiOperation("Record")
    @PostMapping(value = {""})
    public WorksDto create(@RequestBody WorksDto worksDto) {
        Works work = worksMapper.worksDto2works(worksDto);
        try {
            Works recordedWork = worksManager.record(work);
            return worksMapper.works2worksDto(recordedWork);
        } catch (WorksAlreadyExistsException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @ApiOperation("readById")
    @GetMapping("/{id}")
    public WorksDto readById(@PathVariable int id) {
        try {
            return worksMapper.works2worksDto(worksManager.readById(id));
        } catch (WorksNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @ApiOperation("Read All")
    @GetMapping(value = {""})
    public Collection<WorksDto> readAllWorks() {
        return worksManager.readAll()
                .stream()
                .map(worksMapper::works2worksDto)
                .collect(Collectors.toList());
    }

    @ApiOperation("Modify")
    @PutMapping(value = {""})
    public WorksDto modify(@RequestBody WorksDto dto) {
        Works work = worksMapper.worksDto2works(dto);
        try {
            Works modifyWork = worksManager.modify(work);
            return worksMapper.works2worksDto(modifyWork);
        } catch (WorksNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @ApiOperation("Delete")
    @DeleteMapping(value = {""})
    public void delete(@RequestParam int id) {
        try {
            worksManager.delete(worksManager.readById(id));
        } catch (WorksNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

}
