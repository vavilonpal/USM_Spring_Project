package org.moodle.springlaboratorywork.service;


import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.moodle.springlaboratorywork.dtos.LeagueDTO;
import org.moodle.springlaboratorywork.entity.League;
import org.moodle.springlaboratorywork.entity.Team;
import org.moodle.springlaboratorywork.repository.write.WriteLeagueRepository;
import org.moodle.springlaboratorywork.repository.write.WriteTeamRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class LeagueService {
    private final WriteLeagueRepository writeLeagueRepository;
    private final WriteTeamRepository teamRepository;

    public List<League> getAllLeagues() {
        return writeLeagueRepository.findAll();
    }

    public League getLeagueById(Long id) {
        return writeLeagueRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("League by id:" + id + " not found"));
    }

    public League createLeague(LeagueDTO leagueDTO) {
        League league = League.builder()
                .name(leagueDTO.getName())
                .build();
        return writeLeagueRepository.save(league);
    }

    public League updateLeague(Long id, LeagueDTO leagueDTO) {
        League league = getLeagueById(id);
        league.setName(leagueDTO.getName());

        if (!(leagueDTO.getTeamNames().isEmpty())) {

            Set<Team> leagueTeams =  leagueDTO.getTeamNames().stream()
                    .map(name -> {
                        Team team = teamRepository.findByName(name)
                                .orElseThrow(()-> new EntityNotFoundException("Team not found"));
                        team.setLeague(league);
                        return team;
                    }).collect(Collectors.toSet());

            league.setTeams(leagueTeams);
        }

        return writeLeagueRepository.save(league);
    }

    public void deleteLeague(Long id) {
        //League league = getLeagueById(id);
        writeLeagueRepository.deleteById(id);
    }
}
