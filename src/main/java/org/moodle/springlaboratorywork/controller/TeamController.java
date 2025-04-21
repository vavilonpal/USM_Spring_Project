package org.moodle.springlaboratorywork.controller;


import lombok.RequiredArgsConstructor;
import org.moodle.springlaboratorywork.dtos.TeamDTO;
import org.moodle.springlaboratorywork.entity.Team;
import org.moodle.springlaboratorywork.service.TeamService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/teams")
@RequiredArgsConstructor
public class TeamController {
    private final TeamService teamService;


    @GetMapping
    public ResponseEntity<List<TeamDTO>> getAllTeams() {
        List<TeamDTO> teamDTOList = teamService.getAllTeams().stream()
                .map(TeamDTO::mapToDTO)
                .toList();
        return ResponseEntity.ok(teamDTOList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeamDTO> getTeam(@PathVariable Long id) {

        TeamDTO teamDTO = TeamDTO.mapToDTO(teamService.getTeamById(id));

        return ResponseEntity.ok(teamDTO);
    }

    @PostMapping
    public ResponseEntity<TeamDTO> createTeam(@RequestBody @Validated TeamDTO teamDTO) {
        Team team = teamService.createTeam(teamDTO);

        return ResponseEntity.ok(teamDTO);

    }

    @PutMapping("/{id}")
    public ResponseEntity<TeamDTO> updateTeam(@PathVariable Long id,
                                              @RequestBody @Validated TeamDTO teamDTO) {
        Team team = teamService.updateTeam(id, teamDTO);

        return ResponseEntity.ok(teamDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTeam(@PathVariable Long id) {
        teamService.deleteTeamById(id);

        return ResponseEntity.ok("Successful delete");
    }

}
