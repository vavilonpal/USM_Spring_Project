package org.moodle.springlaboratorywork.repository.write;


import org.moodle.springlaboratorywork.entity.Coach;
import org.moodle.springlaboratorywork.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
    List<Player> findAllPlayersByTeamId(Long teamId);
}
