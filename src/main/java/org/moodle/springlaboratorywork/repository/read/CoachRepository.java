package org.moodle.springlaboratorywork.repository.read;


import org.moodle.springlaboratorywork.entity.Coach;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface CoachRepository extends JpaRepository<Coach, Long> {
    Optional<Coach> findByName(String name);

    Boolean existsByNameAndTeamName(String coachName, String teamName);
}
