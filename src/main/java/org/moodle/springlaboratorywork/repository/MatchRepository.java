package org.moodle.springlaboratorywork.repository;


import org.moodle.springlaboratorywork.entity.Match;
import org.moodle.springlaboratorywork.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {

    @Query("SELECT COUNT(m) FROM Match m " +
            "WHERE m.homeTeam.name = :homeTeamName " +
            "AND m.awayTeam.name = :awayTeamName")
    Long countByHomeTeamAndAwayTeam(@Param("homeTeamName") String homeTeamName,
                                    @Param("awayTeamName") String awayTeamName);
}