package org.moodle.springlaboratorywork.service;


import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.moodle.springlaboratorywork.dtos.LeagueDTO;
import org.moodle.springlaboratorywork.entity.League;
import org.moodle.springlaboratorywork.entity.Team;
import org.moodle.springlaboratorywork.repository.LeagueRepository;
import org.moodle.springlaboratorywork.repository.TeamRepository;
import org.moodle.springlaboratorywork.repository.hibernateRepository.LeagueDao;
import org.moodle.springlaboratorywork.repository.jdbcRepository.LeagueJDBCRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class LeagueService {
    private final LeagueRepository leagueRepository;
    private final TeamRepository teamRepository;
    private final LeagueJDBCRepository leagueJDBCRepository;
    private final LeagueDao leagueDao;

    public List<League> getAllLeagues() {
        return leagueJDBCRepository.findAll();
    }

    public League getLeagueById(Long id) {
        return leagueJDBCRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("League by id:" + id + " not found"));
    }

    public League createLeague(LeagueDTO leagueDTO) {
        if (leagueDao.existsByName(leagueDTO.getName())) {
            throw new EntityExistsException("League by name:" + leagueDTO.getName() + "already exists");
        }
        League league = League.builder()
                .name(leagueDTO.getName())
                .build();
        return leagueJDBCRepository.save(league);
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

        return leagueJDBCRepository.save(league);
    }

    public void deleteLeague(Long id) {
        //League league = getLeagueById(id);
        leagueJDBCRepository.delete(id);
    }
}
