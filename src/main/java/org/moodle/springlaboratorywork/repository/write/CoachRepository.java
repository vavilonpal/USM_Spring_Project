package org.moodle.springlaboratorywork.repository.write;


import org.moodle.springlaboratorywork.entity.Coach;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CoachRepository extends JpaRepository<Coach, Long> {
    Optional<Coach> findByName(String name);

    Boolean existsByNameAndTeamName(String coachName, String teamName);
}
