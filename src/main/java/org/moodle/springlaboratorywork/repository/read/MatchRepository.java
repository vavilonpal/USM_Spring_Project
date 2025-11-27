package org.moodle.springlaboratorywork.repository.read;


import org.moodle.springlaboratorywork.entity.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {

   /* @Query("SELECT COUNT(m) FROM Match m " +
            "WHERE m.homeTeam.name = :homeTeamName " +
            "AND m.awayTeam.name = :awayTeamName")
    Long countByHomeTeamAndAwayTeam(@Param("homeTeamName") String homeTeamName,
                                    @Param("awayTeamName") String awayTeamName);
    */
    Boolean existsByHomeTeamNameAndAwayTeamName(String homeTeamName, String awayTeamName);

    Set<Match> findAllByHomeTeamId(Long teamId);
    Set<Match> findAllByAwayTeamId(Long teamId);
}