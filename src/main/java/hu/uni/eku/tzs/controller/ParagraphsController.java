package hu.uni.eku.tzs.controller;

import hu.uni.eku.tzs.controller.dto.ParagraphsDto;
import hu.uni.eku.tzs.controller.dto.ParagraphsMapper;
import hu.uni.eku.tzs.model.Paragraphs;
import hu.uni.eku.tzs.service.ParagraphsManager;
import hu.uni.eku.tzs.service.exceptions.ParagraphsAlreadyExistsException;
import hu.uni.eku.tzs.service.exceptions.ParagraphsNotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.Collection;
import java.util.stream.Collectors;

@Api(tags = "paragraphs")
@RestController
@RequiredArgsConstructor
@RequestMapping("/paragraphs")
public class ParagraphsController {
    private final ParagraphsManager paragraphManager;

    private final ParagraphsMapper paragraphMapper;

    @ApiOperation("Record")
    @PostMapping(value = {""})
    public ParagraphsDto create(@RequestBody ParagraphsDto paragraphDto) {
        Paragraphs paragrah = paragraphMapper.paragraphDto2paragraphs(paragraphDto);
        try {
            Paragraphs recordedParagraph = paragraphManager.record(paragrah);
            return paragraphMapper.paragraphs2paragraphDto(recordedParagraph);
        } catch (ParagraphsAlreadyExistsException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @ApiOperation("readById")
    @GetMapping("/{id}")
    public ParagraphsDto readById(@PathVariable int id) throws ParagraphsNotFoundException {
        try {
            return paragraphMapper.paragraphs2paragraphDto(paragraphManager.readById(id));
        }catch (ParagraphsNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @ApiOperation("Read All")
    @GetMapping(value = {""})
    public Collection<ParagraphsDto> readAllCharacters() {
        return paragraphManager.readAll()
                .stream()
                .map(paragraphMapper::paragraphs2paragraphDto)
                .collect(Collectors.toList());
    }


    @ApiOperation("Modify")
    @PutMapping(value = {""})
    public ParagraphsDto modify(@RequestBody ParagraphsDto dto) {
        Paragraphs paragraph = paragraphMapper.paragraphDto2paragraphs(dto);
        try{
            Paragraphs modifyParagraph = paragraphManager.modify(paragraph);
            return paragraphMapper.paragraphs2paragraphDto(modifyParagraph);
        } catch (ParagraphsNotFoundException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @ApiOperation("Delete")
    @DeleteMapping(value = {""})
    public void delete(@RequestParam int id) {
        try{
            paragraphManager.delete(paragraphManager.readById(id));
        } catch (ParagraphsNotFoundException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
