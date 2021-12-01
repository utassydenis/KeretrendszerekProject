package hu.uni.eku.tzs.controller;

import hu.uni.eku.tzs.controller.dto.ChaptersDto;
import hu.uni.eku.tzs.controller.dto.ChaptersMapper;
import hu.uni.eku.tzs.model.Chapters;
import hu.uni.eku.tzs.service.ChapterManager;
import hu.uni.eku.tzs.service.exceptions.ChapterAlreadyExistsException;
import hu.uni.eku.tzs.service.exceptions.ChapterNotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.Collection;
import java.util.stream.Collectors;

@Api(tags = "chapters")
@RestController
@RequiredArgsConstructor
@RequestMapping("/chapters")
public class ChaptersController {
    private final ChapterManager chapterManager;

    private final ChaptersMapper chapterMapper;

    @ApiOperation("Record")
    @PostMapping(value = {""})
    public ChaptersDto create(@RequestBody ChaptersDto chaptersDto) {
        Chapters chapter = chapterMapper.chapterDto2chapter(chaptersDto);
        try {
            Chapters recordedChapters = chapterManager.record(chapter);
            return chapterMapper.chapter2chapterDto(recordedChapters);
        } catch (ChapterAlreadyExistsException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
    @ApiOperation("readById")
    @GetMapping("/{id}")
    public ChaptersDto readById(@PathVariable int id) throws ChapterNotFoundException {
        try {
            return chapterMapper.chapter2chapterDto(chapterManager.readById(id));
        }catch (ChapterNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @ApiOperation("Read All")
    @GetMapping(value = {""})
    public Collection<ChaptersDto> readAllChapters() {
        return chapterManager.readAll()
                .stream()
                .map(chapterMapper::chapter2chapterDto)
                .collect(Collectors.toList());
    }


    @ApiOperation("Modify")
    @PutMapping(value = {""})
    public ChaptersDto modify(@RequestBody ChaptersDto dto) {
        Chapters chapter = chapterMapper.chapterDto2chapter(dto);
        try{
            Chapters modifyChapter = chapterManager.modify(chapter);
            return chapterMapper.chapter2chapterDto(modifyChapter);
        } catch (ChapterNotFoundException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @ApiOperation("Delete")
    @DeleteMapping(value = {""})
    public void delete(@RequestParam int id) {
        try{
            chapterManager.delete(chapterManager.readById(id));
        } catch (ChapterNotFoundException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

}
