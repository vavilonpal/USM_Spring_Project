package org.moodle.springlaboratorywork.repository.read;


import org.moodle.springlaboratorywork.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface ReadPlayerRepository extends JpaRepository<Player, Long> {
    List<Player> findAllPlayersByTeamId(Long teamId);
}
