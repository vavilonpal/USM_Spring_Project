package org.moodle.springlaboratorywork.repository;


import org.moodle.springlaboratorywork.entity.Coach;
import org.moodle.springlaboratorywork.entity.League;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LeagueRepository extends JpaRepository<League, Long> {
    Boolean existsByName(String name);
    Optional<League> findByName(String name);
}
