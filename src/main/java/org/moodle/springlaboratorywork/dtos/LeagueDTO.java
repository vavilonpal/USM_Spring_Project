package org.moodle.springlaboratorywork.dtos;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
    @NotBlank(message = "League name is empty")
    @Size(max = 120, message = "League name too long")
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
