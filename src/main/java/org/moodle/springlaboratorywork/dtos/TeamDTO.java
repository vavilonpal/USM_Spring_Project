package org.moodle.springlaboratorywork.dtos;


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
    private String name;
    private String coachName;
    private String leagueName;
    @Builder.Default
    private List<String> playerNames = new ArrayList<>();


    public static TeamDTO mapToDTO(Team team){
        return TeamDTO
                .builder()
                .name(team.getName())
                .coachName(team.getCoach() != null ? team.getCoach().getName() : " " )
                .leagueName(team.getLeague().getName())
                .playerNames(team.getPlayers()
                        .stream()
                        .map(Player::getName)
                        .toList())
                .build();
    }


}
