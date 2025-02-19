package org.moodle.springlaboratorywork.dtos;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.moodle.springlaboratorywork.entity.Player;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlayerDTO {
    private String name;
    private String surname;
    private String teamName;

    public static PlayerDTO mapToDTO(Player player){
        return PlayerDTO.builder()
                .name(player.getName())
                .surname(player.getSurname())
                .teamName(player
                        .getTeam()
                        .getName())
                .build();
    }
}
