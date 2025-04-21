package org.moodle.springlaboratorywork.controller;


import lombok.RequiredArgsConstructor;
import org.moodle.springlaboratorywork.dtos.MatchDTO;
import org.moodle.springlaboratorywork.entity.Match;
import org.moodle.springlaboratorywork.service.MatchService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/matches")
@RequiredArgsConstructor
public class MatchController {
    private final MatchService matchService;

    @GetMapping
    public ResponseEntity<List<MatchDTO>> getAllMatches() {
        List<MatchDTO> matchDTOList = matchService.getAllMatches().stream()
                .map(MatchDTO::mapToDTO)
                .toList();

        return ResponseEntity.ok(matchDTOList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MatchDTO> getMatchById(@PathVariable Long id) {
        MatchDTO match = MatchDTO.mapToDTO(matchService.getMatchById(id));

        return ResponseEntity.ok(match);
    }

    @PostMapping
    public ResponseEntity<MatchDTO> createMatch(@RequestBody @Validated MatchDTO matchRequest) {

        Match match = matchService.createMatch(matchRequest);

        return ResponseEntity.ok(matchRequest);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MatchDTO> updateMatch(@PathVariable Long id,
                                                @RequestBody @Validated MatchDTO matchRequest) {
        Match match = matchService.updateMatch(id, matchRequest);

        return ResponseEntity.ok(matchRequest);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMatch(@PathVariable Long id) {
        matchService.deleteMatch(id);

        return ResponseEntity.ok("Successful delete");
    }

}
