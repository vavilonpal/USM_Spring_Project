package org.moodle.springlaboratorywork.dtos;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.moodle.springlaboratorywork.entity.Match;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MatchDTO {

    private String leagueName;
    private LocalDateTime matchDateTime;

    private String homeTeamName;
    private String homeTeamCoachName;

    private String awayTeamName;
    private String awayTeamCoachName;

    public static MatchDTO mapToDTO(Match match) {
        return MatchDTO.builder()
                .leagueName(match.getLeague().getName())
                .matchDateTime(match.getMatchDateTime())
                .homeTeamName(match
                        .getHomeTeam()
                        .getName())
                .homeTeamCoachName(match
                        .getHomeTeam()
                        .getCoach()
                        .getName())
                .awayTeamName(match
                        .getAwayTeam()
                        .getName())
                .awayTeamCoachName(match
                        .getAwayTeam()
                        .getCoach()
                        .getName())
                .build();
    }

}
