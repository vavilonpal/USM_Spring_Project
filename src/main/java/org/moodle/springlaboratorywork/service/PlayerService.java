package org.moodle.springlaboratorywork.service;


import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.moodle.springlaboratorywork.dtos.PlayerDTO;
import org.moodle.springlaboratorywork.entity.Player;
import org.moodle.springlaboratorywork.entity.Team;
import org.moodle.springlaboratorywork.entity.embedded.PlayerStatistic;
import org.moodle.springlaboratorywork.repository.read.ReadTeamRepository;
import org.moodle.springlaboratorywork.repository.write.WritePlayerRepository;
import org.moodle.springlaboratorywork.repository.write.WriteTeamRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlayerService {
    private final WritePlayerRepository writePlayerRepository;
    private final ReadTeamRepository teamRepository;

    public List<Player> getAllPlayers() {
        return writePlayerRepository.findAll();
    }


    public List<Player> getPlayersByTeamId(Long teamId){
        return  writePlayerRepository.findAllPlayersByTeamId(teamId);
    }
    public Player getPlayerById(Long id) {
        return writePlayerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Player by id: " + id + " not found"));
    }

    public Player createPlayer(PlayerDTO playerDTO) {
        Player player = Player.builder()
                .name(playerDTO.getName())
                .surname(playerDTO.getSurname())
                .build();
        return writePlayerRepository.save(player);
    }

    public Player updatePlayer(Long id, PlayerDTO playerDTO) {
        Player player = getPlayerById(id);
        Team team;

        if(playerDTO.getTeamName() != null){
             team = teamRepository.findByName(playerDTO.getTeamName())
                    .orElseThrow(() -> new EntityNotFoundException("Team not found"));
            player.setTeam(team);
        }

        PlayerStatistic playerStatistic = new PlayerStatistic(playerDTO.getGoals(), playerDTO.getAssists());
        player.setName(playerDTO.getName());
        player.setSurname(playerDTO.getSurname());
        player.setPlayerStatistic(playerStatistic);


        return writePlayerRepository.save(player);
    }

    public void deletePlayer(Long id) {
        Player player = getPlayerById(id);
        writePlayerRepository.delete(player);
    }
}
