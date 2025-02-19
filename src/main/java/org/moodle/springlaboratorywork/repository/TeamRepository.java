package org.moodle.springlaboratorywork.repository;


import org.moodle.springlaboratorywork.entity.Coach;
import org.moodle.springlaboratorywork.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {


    Boolean existsByName(String name);
    Optional<Team> findByName(String name);
}
