package org.moodle.springlaboratorywork.dtos;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.moodle.springlaboratorywork.entity.League;
import org.moodle.springlaboratorywork.entity.Team;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LeagueDTO {
    private String name;
    private List<String> teamNames = new ArrayList<>();


    public static LeagueDTO mapToDTO(League league){
        return LeagueDTO.builder()
                .name(league.getName())
                .teamNames(league
                        .getTeams().stream()
                        .map(Team::getName)
                        .toList())
                .build();
    }
}
