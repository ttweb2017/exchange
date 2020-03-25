package com.voicetm.karaoke.repo;

import com.voicetm.karaoke.domain.Singer;
import com.voicetm.karaoke.domain.Video;
import com.voicetm.karaoke.dto.VideoDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoRepo extends JpaRepository<Video, Long> {
    Long countBySinger(Singer singer);

    void deleteAllBySinger(Singer singer);

    @Query("select new com.voicetm.karaoke.dto.VideoDto(v, s) " +
            "from Video v left join v.singer s")
    List<VideoDto> getAll();

    @Query("select new com.voicetm.karaoke.dto.VideoDto(v, s) " +
            "from Video v left join v.singer s where v.id = :id")
    VideoDto getOneById(@Param("id") Long id);

    @Query("select new com.voicetm.karaoke.dto.VideoDto(v, s) " +
            "from Video v " +
            "left join v.singer s " +
            "where v.active = :active and v.video <> null")
    List<VideoDto> getAllByActiveAndVideoIsNotNull(@Param("active") boolean active);

    @Query("select new com.voicetm.karaoke.dto.VideoDto(v, s) " +
            "from Video v " +
            "left join v.singer s " +
            "where v.singer = :singer and v.active = true and v.video <> null")
    List<VideoDto> getAllBySingerAndActiveAndVideoIsNotNull(@Param("singer") Singer singer);

    @Query("select new com.voicetm.karaoke.dto.VideoDto(v, s) " +
            "from Video v " +
            "left join v.singer s " +
            "where v.active = :active and v.video <> null order by v.watchedCounter desc")
    List<VideoDto> getTopByActiveAndVideoIsNotNullOrderByWatchedCounterDesc(@Param("active") boolean active);
}
