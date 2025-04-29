package org.moodle.springlaboratorywork.dtos;


import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
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
    @NotBlank(message = "League name is empty")
    private String leagueName;

    @FutureOrPresent(message = "The match should not start in the past")
    private LocalDateTime matchDateTime;

    @NotBlank(message = "Home team name is empty")
    private String homeTeamName;

    @NotBlank(message = "away team name is empty")
    private String awayTeamName;


    public static MatchDTO mapToDTO(Match match) {
        return MatchDTO.builder()
                .leagueName(match.getLeague().getName())
                .matchDateTime(match.getMatchDateTime())
                .homeTeamName(match
                        .getHomeTeam()
                        .getName())
                .awayTeamName(match
                        .getAwayTeam()
                        .getName())
                .build();
    }

}
