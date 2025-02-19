package org.moodle.springlaboratorywork.dtos;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.moodle.springlaboratorywork.entity.Coach;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CoachDTO {
    private String name;
    private String surName;
    private String teamName;

    public static CoachDTO mapToDTO(Coach coach){
        return CoachDTO.builder()
                .name(coach.getName())
                .surName(coach.getSurname())
                .teamName(coach
                        .getTeam()
                        .getName())
                .build();
    }
}
