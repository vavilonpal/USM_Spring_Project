package org.moodle.springlaboratorywork.repository.read;


import org.moodle.springlaboratorywork.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
    List<Player> findAllPlayersByTeamId(Long teamId);
}
