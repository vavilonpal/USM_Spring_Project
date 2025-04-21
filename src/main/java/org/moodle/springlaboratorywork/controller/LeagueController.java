package org.moodle.springlaboratorywork.controller;


import lombok.RequiredArgsConstructor;
import org.moodle.springlaboratorywork.dtos.LeagueDTO;
import org.moodle.springlaboratorywork.entity.League;
import org.moodle.springlaboratorywork.service.LeagueService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/leagues")
@RequiredArgsConstructor
public class LeagueController {
    private final LeagueService leagueService;

    @GetMapping
    public ResponseEntity<List<LeagueDTO>> getAllLeagues(){
        List<LeagueDTO> leagueDTOList = leagueService.getAllLeagues().stream()
                .map(LeagueDTO::mapToDTO)
                .toList();

        return ResponseEntity.ok(leagueDTOList);
    }
    @GetMapping("/{id}")
    public ResponseEntity<LeagueDTO> getLeague(@PathVariable Long id){

        LeagueDTO league = LeagueDTO.mapToDTO(leagueService.getLeagueById(id));

        return ResponseEntity.ok(league);
    }
    @PostMapping
    public ResponseEntity<LeagueDTO> createLeague(@RequestBody @Validated LeagueDTO  leagueRequest){
        League league = leagueService.createLeague(leagueRequest);

        return ResponseEntity.ok(leagueRequest);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LeagueDTO> updateLeague(@PathVariable Long id,
                                                  @RequestBody @Validated LeagueDTO leagueRequest){
        League league = leagueService.updateLeague(id, leagueRequest);

        return ResponseEntity.ok(leagueRequest);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteLeague(@PathVariable Long id){
        leagueService.deleteLeague(id);
        return ResponseEntity.ok("Successful delete");
    }


}
