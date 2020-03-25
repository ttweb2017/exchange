package com.voicetm.karaoke.controller;

import com.voicetm.karaoke.dto.CategoryDto;
import com.voicetm.karaoke.dto.SongDto;
import com.voicetm.karaoke.service.CategoryService;
import com.voicetm.karaoke.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class FrontCategoryRestController {
    private final CategoryService categoryService;

    @Autowired
    public FrontCategoryRestController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<CategoryDto> categories(){
        return categoryService.getAllByActiveOrderByNameAsc(true);
    }

    @GetMapping("{id}")
    public CategoryDto songsOfCurrentCategory(@PathVariable Long id){
        return categoryService.getOneByIdAndActive(id, true);
    }
}
