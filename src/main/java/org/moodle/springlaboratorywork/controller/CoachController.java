package org.moodle.springlaboratorywork.controller;


import lombok.RequiredArgsConstructor;
import org.moodle.springlaboratorywork.dtos.CoachDTO;
import org.moodle.springlaboratorywork.entity.Coach;
import org.moodle.springlaboratorywork.service.CoachService;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/coaches")
@RequiredArgsConstructor
public class CoachController {
    private final CoachService coachService;

    @GetMapping
    public ResponseEntity<List<CoachDTO>> getAllCoaches(){
        List<CoachDTO> coachDTOS = coachService.getAllCoaches().stream()
                .map(CoachDTO::mapToDTO)
                .toList();
        return ResponseEntity.ok(coachDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CoachDTO> getCoachById(@PathVariable Long id){
        CoachDTO coachDTO = CoachDTO.mapToDTO(coachService.getCoachById(id));
        return ResponseEntity.ok(coachDTO);
    }

    @PostMapping
    public ResponseEntity<CoachDTO> createCoach(@RequestBody CoachDTO coachRequest){
        Coach coach = coachService.createCoach(coachRequest);
        return ResponseEntity.ok(coachRequest);
    }
    @PutMapping("/{id}")
    public ResponseEntity<CoachDTO> updateCoach(@PathVariable Long id,
                                                @RequestBody CoachDTO coachRequest){

        Coach coach = coachService.updateCoach(id,coachRequest);

        return ResponseEntity.ok(coachRequest);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCoach(@PathVariable Long id){
        coachService.deleteCoach(id);
        return ResponseEntity.ok("Successful delete");
    }
}
