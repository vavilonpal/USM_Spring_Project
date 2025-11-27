package org.moodle.springlaboratorywork.service;


import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.moodle.springlaboratorywork.dtos.TeamDTO;
import org.moodle.springlaboratorywork.entity.Coach;
import org.moodle.springlaboratorywork.entity.League;
import org.moodle.springlaboratorywork.entity.Team;
import org.moodle.springlaboratorywork.repository.write.CoachRepository;
import org.moodle.springlaboratorywork.repository.write.LeagueRepository;
import org.moodle.springlaboratorywork.repository.write.TeamRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamService {
    private final TeamRepository teamRepository;
    private final CoachRepository coachRepository;
    private final LeagueRepository leagueRepository;

    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    public Team getTeamById(Long id) {
        return teamRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Team by id: " + id + " not found"));
    }


    public Team createTeam(TeamDTO teamDTO) {
        if (teamRepository.existsByName(teamDTO.getName())) {
            throw new EntityExistsException("Team by name: " + teamDTO.getName() + " already exists");
        }
        Team team = Team.builder()
                .name(teamDTO.getName())
                .build();
        return teamRepository.save(team);
    }

    public Team updateTeam(Long id, TeamDTO teamDTO) {
        Team team = getTeamById(id);
        Coach coach;
        League league;
        if(teamDTO.getCoachName() != null){
            coach = coachRepository.findByName(teamDTO.getCoachName())
                    .orElseThrow(() -> new EntityNotFoundException("Coach by name: " + teamDTO.getCoachName() + " doesnt exist"));
            team.setCoach(coach);
        }
         if(teamDTO.getLeagueName() != null){
             league = leagueRepository.findByName(teamDTO.getLeagueName())
                     .orElseThrow(() -> new EntityNotFoundException("League: " + teamDTO.getLeagueName() + " not found"));

             team.setLeague(league);
         }

         team.setName(teamDTO.getName());


        return teamRepository.save(team);
    }

    public void deleteTeamById(Long id) {
        //Team team = getTeamById(id);
        teamRepository.deleteById(id);
    }
}
