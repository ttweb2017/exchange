package com.voicetm.karaoke.repo;

import com.voicetm.karaoke.domain.Category;
import com.voicetm.karaoke.domain.Song;
import com.voicetm.karaoke.dto.SongDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SongRepo extends JpaRepository<Song, Long> {
    Long countByCategory(Category category);

    void deleteAllByCategory(Category category);

    @Query("select new com.voicetm.karaoke.dto.SongDto(s, c) " +
            "from Song s left join s.category c")
    List<SongDto> getAll();

    @Query("select new com.voicetm.karaoke.dto.SongDto(s, c) " +
            "from Song s left join s.category c where s.id = :id")
    SongDto getOneById(@Param("id") Long id);

    @Query("select new com.voicetm.karaoke.dto.SongDto(s, c) " +
            "from Song s " +
            "left join s.category c " +
            "where s.active = :active and s.video <> null")
    List<SongDto> getAllByActiveAndVideoIsNotNull(@Param("active") boolean active);

    @Query("select new com.voicetm.karaoke.dto.SongDto(s) " +
            "from Song s " +
            "where s.category = :category and s.active = :active and s.video <> null")
    List<SongDto> getAllByCategoryAndActiveAndVideoIsNotNull(@Param("category") Category category, @Param("active") boolean active);

    @Query("select new com.voicetm.karaoke.dto.SongDto(s, c) " +
            "from Song s " +
            "left join s.category c " +
            "where s.active = :active and s.video <> null order by s.watchedCounter desc")
    List<SongDto> getTopByActiveAndVideoIsNotNullOrderByWatchedCounterDesc(@Param("active") boolean active);
}
