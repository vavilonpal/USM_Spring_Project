package org.moodle.springlaboratorywork.controller;


import lombok.RequiredArgsConstructor;
import org.moodle.springlaboratorywork.dtos.PlayerDTO;
import org.moodle.springlaboratorywork.entity.Player;
import org.moodle.springlaboratorywork.service.PlayerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/players")
@RequiredArgsConstructor
public class PlayerController {
    private final PlayerService playerService;

    @GetMapping
    public ResponseEntity<List<PlayerDTO>> getAllPlayers() {

        List<PlayerDTO> playerDTOList = playerService.getAllPlayers().stream().map(PlayerDTO::mapToDTO).toList();

        return ResponseEntity.ok(playerDTOList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlayerDTO> getPlayer(@PathVariable Long id) {

        PlayerDTO player = PlayerDTO.mapToDTO(playerService.getPlayerById(id));

        return ResponseEntity.ok(player);
    }

    @PostMapping
    public ResponseEntity<PlayerDTO> createPlayer(@RequestBody PlayerDTO playerDTO) {

        Player player = playerService.createPlayer(playerDTO);

        return ResponseEntity.ok(playerDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlayerDTO> updatePlayer(@PathVariable Long id,
                                                  @RequestBody PlayerDTO playerDTO) {
        Player player = playerService.updatePlayer(id, playerDTO);

        return ResponseEntity.ok(playerDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePlayer(@PathVariable Long id) {
        playerService.deletePlayer(id);

        return ResponseEntity.ok("Successful delete");
    }

}
