package org.moodle.springlaboratorywork.service;


import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.moodle.springlaboratorywork.dtos.PlayerDTO;
import org.moodle.springlaboratorywork.entity.Player;
import org.moodle.springlaboratorywork.entity.Team;
import org.moodle.springlaboratorywork.entity.embedded.PlayerStatistic;
import org.moodle.springlaboratorywork.repository.write.PlayerRepository;
import org.moodle.springlaboratorywork.repository.write.TeamRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlayerService {
    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;

    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }


    public List<Player> getPlayersByTeamId(Long teamId){
        return  playerRepository.findAllPlayersByTeamId(teamId);
    }
    public Player getPlayerById(Long id) {
        return playerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Player by id: " + id + " not found"));
    }

    public Player createPlayer(PlayerDTO playerDTO) {
        Player player = Player.builder()
                .name(playerDTO.getName())
                .surname(playerDTO.getSurname())
                .build();
        return playerRepository.save(player);
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


        return playerRepository.save(player);
    }

    public void deletePlayer(Long id) {
        Player player = getPlayerById(id);
        playerRepository.delete(player);
    }
}
