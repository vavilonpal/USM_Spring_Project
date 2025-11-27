package org.moodle.springlaboratorywork.repository.read;


import org.moodle.springlaboratorywork.entity.League;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface ReadLeagueRepository extends JpaRepository<League, Long> {
    Boolean existsByName(String name);
    Optional<League> findByName(String name);
}
