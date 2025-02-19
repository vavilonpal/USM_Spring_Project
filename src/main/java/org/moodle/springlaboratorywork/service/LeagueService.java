package org.moodle.springlaboratorywork.service;


import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.moodle.springlaboratorywork.dtos.LeagueDTO;
import org.moodle.springlaboratorywork.entity.League;
import org.moodle.springlaboratorywork.entity.Team;
import org.moodle.springlaboratorywork.repository.LeagueRepository;
import org.moodle.springlaboratorywork.repository.TeamRepository;
import org.springframework.stereotype.Service;

import java.util.List;


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
        if (leagueRepository.existsByName(leagueDTO.getName())) {
            throw new EntityExistsException("League by name:" + leagueDTO.getName() + "already exists");
        }
        League league = League.builder()
                .name(leagueDTO.getName())
                .build();
        return leagueRepository.save(league);
    }

    public League updateLeague(Long id, LeagueDTO leagueDTO) {
        League league = getLeagueById(id);

        if (!(leagueDTO.getTeamNames().isEmpty())) {

            leagueDTO.getTeamNames().stream()
                    .map(teamRepository::findByName)
                    .map(teamOptional -> teamOptional
                            .orElseThrow(() -> new EntityNotFoundException("Team not found")))
                    .map(team -> league.getTeams().add(team));

        }
        league.setName(leagueDTO.getName());

        return leagueRepository.save(league);
    }

    public void deleteLeague(Long id) {
        League league = getLeagueById(id);
        leagueRepository.delete(league);
    }
}
