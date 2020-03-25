package com.voicetm.karaoke.controller;

import com.voicetm.karaoke.domain.Category;
import com.voicetm.karaoke.dto.CategoryDto;
import com.voicetm.karaoke.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/category")
@PreAuthorize("hasAnyAuthority('ADMIN', 'MODERATOR')")
public class AdminCategoryRestController {
    private final CategoryService categoryService;

    @Autowired
    public AdminCategoryRestController(CategoryService categoryService){
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<CategoryDto> singerList(){
        return categoryService.getAll();
    }

    @PutMapping("/set-status/{category}/{status}")
    public ResponseEntity<?> updateSingerStatus(@PathVariable Category category, @PathVariable boolean status){
        if(category == null){
            return ResponseEntity.ok(HttpStatus.BAD_REQUEST);
        }

        category.setActive(status);
        categoryService.save(category);

        return ResponseEntity.ok(true);
    }

    @PostMapping
    public ResponseEntity<?> addCategory(
            @ModelAttribute Category category) {

        categoryService.save(category);

        return ResponseEntity.ok(categoryService.getOneById(category.getId()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable("id") Category categoryFromDb,
            @ModelAttribute Category category
    ) {
        if(categoryFromDb == null){
            return ResponseEntity.ok(HttpStatus.BAD_REQUEST);
        }

        if(category.getName().isEmpty()){
            category.setName(categoryFromDb.getName());
        }

        category.setActive(categoryFromDb.isActive());

        categoryService.save(category);

        return ResponseEntity.ok(categoryService.getOneById(categoryFromDb.getId()));
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Category category) {
        if(category != null){
            categoryService.delete(category);
        }
    }
}
