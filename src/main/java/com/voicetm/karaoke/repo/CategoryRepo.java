package com.voicetm.karaoke.repo;

import com.voicetm.karaoke.domain.Category;
import com.voicetm.karaoke.dto.CategoryDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Long> {
    List<Category> findAllByActive(boolean active);

    List<Category> findAllByActiveOrderByNameAsc(boolean active);

    @Query("select new com.voicetm.karaoke.dto.CategoryDto(c) " +
            "from Category c")
    List<CategoryDto> getAll();

    @Query("select new com.voicetm.karaoke.dto.CategoryDto(c) " +
            "from Category c where c.id = :id")
    CategoryDto getOneById(@Param("id") Long id);

    @Query("select new com.voicetm.karaoke.dto.CategoryDto(c) " +
            "from Category c where c.active = :active order by c.name asc")
    List<CategoryDto> getAllByActiveOrderByNameAsc(@Param("active") boolean active);

    @Query("select new com.voicetm.karaoke.dto.CategoryDto(c) " +
            "from Category c " +
            "where c.id = :id and c.active = :active")
    CategoryDto getOneByIdAndActive(@Param("id") Long id, @Param("active") boolean active);
}
