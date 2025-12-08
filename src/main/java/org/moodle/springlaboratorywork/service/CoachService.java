package org.moodle.springlaboratorywork.service;


import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.moodle.springlaboratorywork.dtos.CoachDTO;
import org.moodle.springlaboratorywork.entity.Coach;
import org.moodle.springlaboratorywork.entity.Team;
import org.moodle.springlaboratorywork.repository.write.WriteCoachRepository;
import org.moodle.springlaboratorywork.repository.write.WriteTeamRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CoachService {
    private final WriteCoachRepository writeCoachRepository;
    private final WriteTeamRepository teamRepository;

    public List<Coach> getAllCoaches() {
        return writeCoachRepository.findAll();
    }

    public Coach getCoachById(Long id) {
        return writeCoachRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Coach by id: " + id + " not found"));
    }

    public Coach createCoach(CoachDTO coachDTO) {

        Coach coach = Coach.builder()
                .name(coachDTO.getName())
                .surname(coachDTO.getSurName())

                .build();

        return writeCoachRepository.save(coach);
    }

    public Coach updateCoach(Long id, CoachDTO coachDTO) {
        Coach coach = getCoachById(id);


        coach.setName(coachDTO.getName());
        coach.setSurname(coachDTO.getSurName());

        if (coachDTO.getTeamName() != null){
            Team team = teamRepository.findByName(coachDTO.getTeamName())
                    .orElseThrow(() -> new EntityNotFoundException("Team not found or coach already set"));
            coach.setTeam(team);
        }

        return writeCoachRepository.save(coach);
    }

    public void deleteCoach(Long id) {
        Coach coach = getCoachById(id);
        writeCoachRepository.delete(coach);
    }
}
