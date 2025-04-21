package org.moodle.springlaboratorywork.dtos;


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
    @NotBlank(message = "Name is empty")
    private String leagueName;

    @NotBlank(message = "Name is empty")
    private LocalDateTime matchDateTime;

    @NotBlank(message = "Name is empty")
    private String homeTeamName;

    @NotBlank(message = "Name is empty")
    private String homeTeamCoachName;

    @NotBlank(message = "Name is empty")
    private String awayTeamName;
    @NotBlank(message = "Name is empty")
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
