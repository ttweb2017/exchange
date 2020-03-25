package com.voicetm.karaoke.service;

import com.voicetm.karaoke.domain.Category;
import com.voicetm.karaoke.dto.CategoryDto;
import com.voicetm.karaoke.dto.SongDto;
import com.voicetm.karaoke.repo.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepo categoryRepo;

    @Autowired
    public CategoryService(CategoryRepo categoryRepo){
        this.categoryRepo = categoryRepo;
    }

    public List<CategoryDto> getAll() {
        return categoryRepo.getAll();
    }

    public void save(Category category) {
        categoryRepo.save(category);
    }

    public CategoryDto getOneById(Long id) {
        return categoryRepo.getOneById(id);
    }

    public CategoryDto getOneByIdAndActive(Long id, boolean active) {
        List<SongDto> sortedSongList = new ArrayList<>();
        CategoryDto category = categoryRepo.getOneByIdAndActive(id, active);

        category.getSongs().forEach(songDto -> {
            if(songDto.isActive()){
                sortedSongList.add(songDto);
            }
        });

        category.setSongs(sortedSongList);

        return category;
    }

    public void delete(Category category) {
        categoryRepo.delete(category);
    }

    public List<CategoryDto> getAllByActiveOrderByNameAsc(boolean active) {
        List<CategoryDto> sortedCategoryList = new ArrayList<>();
        List<CategoryDto> categoryDtoList = categoryRepo.getAllByActiveOrderByNameAsc(active);

        categoryDtoList.forEach(categoryDto -> {
            if (categoryDto.getSongs().size() > 0) {
                sortedCategoryList.add(categoryDto);
            }
        });

        return sortedCategoryList;
    }
}
