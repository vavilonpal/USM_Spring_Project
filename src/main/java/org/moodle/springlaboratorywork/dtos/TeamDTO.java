package org.moodle.springlaboratorywork.dtos;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.moodle.springlaboratorywork.entity.Player;
import org.moodle.springlaboratorywork.entity.Team;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeamDTO {
    @NotBlank(message = "Name is empty")
    private String name;
    @NotBlank(message = "Name is empty")
    private String coachName;
    @NotBlank(message = "Name is empty")
    private String leagueName;
    @Builder.Default
    private List<String> playerNames = new ArrayList<>();
    private List<MatchDTO> homeMatches = new ArrayList<>();
    private List<MatchDTO> awayMatches = new ArrayList<>();


    public static TeamDTO mapToDTO(Team team){
        return TeamDTO
                .builder()
                .name(team.getName())
                .coachName(team.getCoach() != null ? team.getCoach().getName() : null)
                .leagueName(team.getLeague() != null ? team.getLeague().getName(): null)
                .playerNames(team.getPlayers()
                        .stream()
                        .map(Player::getName)
                        .toList())
                .homeMatches(team.getHomeMatches()
                        .stream()
                        .map(MatchDTO::mapToDTO)
                        .toList()
                )
                .awayMatches(team.getHomeMatches()
                        .stream()
                        .map(MatchDTO::mapToDTO)
                        .toList()
                )
                .build();
    }


}
