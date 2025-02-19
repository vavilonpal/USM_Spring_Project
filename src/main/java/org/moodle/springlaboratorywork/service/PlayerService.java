package org.moodle.springlaboratorywork.service;


import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.moodle.springlaboratorywork.dtos.PlayerDTO;
import org.moodle.springlaboratorywork.entity.Player;
import org.moodle.springlaboratorywork.entity.Team;
import org.moodle.springlaboratorywork.repository.CoachRepository;
import org.moodle.springlaboratorywork.repository.PlayerRepository;
import org.moodle.springlaboratorywork.repository.TeamRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlayerService {
    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;

    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
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
        Team team = teamRepository.findByName(playerDTO.getTeamName())
                .orElseThrow(() -> new EntityNotFoundException("Team not found"));

        player.setName(playerDTO.getName());
        player.setSurname(playerDTO.getSurname());
        player.setTeam(team);

        return playerRepository.save(player);
    }

    public void deletePlayer(Long id) {
        Player player = getPlayerById(id);
        playerRepository.delete(player);
    }
}
