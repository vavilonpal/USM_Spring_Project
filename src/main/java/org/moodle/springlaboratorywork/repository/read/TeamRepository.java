package org.moodle.springlaboratorywork.repository.read;


import org.moodle.springlaboratorywork.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {

    List<Team> findAllByLeagueId(Long leagueId);
    Boolean existsByName(String name);
    Optional<Team> findByName(String name);
}
