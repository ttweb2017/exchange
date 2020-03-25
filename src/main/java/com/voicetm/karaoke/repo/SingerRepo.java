package com.voicetm.karaoke.repo;

import com.voicetm.karaoke.domain.Singer;
import com.voicetm.karaoke.dto.SingerDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SingerRepo extends JpaRepository<Singer, Long> {
    List<Singer> findAllByActive(boolean active);

    List<Singer> findAllByActiveOrderByFirstNameAsc(boolean active);

    @Query("select new com.voicetm.karaoke.dto.SingerDto(s) " +
            "from Singer s")
    List<SingerDto> getAll();

    @Query("select new com.voicetm.karaoke.dto.SingerDto(s) " +
            "from Singer s where s.id = :id")
    SingerDto getOneById(@Param("id") Long id);

    @Query("select new com.voicetm.karaoke.dto.SingerDto(s) " +
            "from Singer s where s.active = :active order by s.firstName asc")
    List<SingerDto> getAllByActiveOrderByFirstNameAsc(@Param("active") boolean active);
}
