package org.moodle.springlaboratorywork.service;


import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.moodle.springlaboratorywork.dtos.LeagueDTO;
import org.moodle.springlaboratorywork.entity.League;
import org.moodle.springlaboratorywork.entity.Team;
import org.moodle.springlaboratorywork.repository.LeagueRepository;
import org.moodle.springlaboratorywork.repository.TeamRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class LeagueService {
    private final LeagueRepository leagueRepository;
    private final TeamRepository teamRepository;

    public List<League> getAllLeagues() {
        return leagueRepository.findAll();
    }

    public League getLeagueById(Long id) {
        return leagueRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("League by id:" + id + " not found"));
    }

    public League createLeague(LeagueDTO leagueDTO) {
        League league = League.builder()
                .name(leagueDTO.getName())
                .build();
        return leagueRepository.save(league);
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

        return leagueRepository.save(league);
    }

    public void deleteLeague(Long id) {
        //League league = getLeagueById(id);
        leagueRepository.deleteById(id);
    }
}
