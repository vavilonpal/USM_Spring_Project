package org.moodle.springlaboratorywork.service;


import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.moodle.springlaboratorywork.dtos.MatchDTO;
import org.moodle.springlaboratorywork.entity.League;
import org.moodle.springlaboratorywork.entity.Match;
import org.moodle.springlaboratorywork.entity.Team;
import org.moodle.springlaboratorywork.repository.CoachRepository;
import org.moodle.springlaboratorywork.repository.LeagueRepository;
import org.moodle.springlaboratorywork.repository.MatchRepository;
import org.moodle.springlaboratorywork.repository.TeamRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MatchService {
    private final MatchRepository matchRepository;
    private final TeamRepository teamRepository;
    private final LeagueRepository leagueRepository;


    public List<Match> getAllMatches() {
        return matchRepository.findAll();
    }

    public Match getMatchById(Long id) {
        return matchRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Match by id: " + id + "not found"));
    }

    public Match createMatch(MatchDTO matchDto) {
        if (matchRepository.countByHomeTeamAndAwayTeam(matchDto.getHomeTeamName(), matchDto.getAwayTeamName()) != 0) {
            throw new EntityExistsException("Match already exists");
        }
        Team homeTeam = teamRepository.findByName(matchDto.getHomeTeamName())
                .orElseThrow(() -> new EntityNotFoundException("Home team not exists"));

        Team awayTeam = teamRepository.findByName(matchDto.getAwayTeamName())
                .orElseThrow(() -> new EntityNotFoundException("Away team not exists"));

        League league = leagueRepository.findByName(matchDto.getLeagueName())
                .orElseThrow(() -> new EntityNotFoundException("League not exists"));

        Match match = Match.builder()
                .league(league)
                .matchDateTime(matchDto.getMatchDateTime())
                .homeTeam(homeTeam)
                .awayTeam(awayTeam)
                .build();

        return matchRepository.save(match);
    }

    public Match updateMatch(Long id, MatchDTO matchDto) {
        Match match = getMatchById(id);

        Team homeTeam = teamRepository.findByName(matchDto.getHomeTeamName())
                .orElseThrow(() -> new EntityNotFoundException("Home team not exists"));

        Team awayTeam = teamRepository.findByName(matchDto.getAwayTeamName())
                .orElseThrow(() -> new EntityNotFoundException("Away team not exists"));

        League league = leagueRepository.findByName(matchDto.getLeagueName())
                .orElseThrow(() -> new EntityNotFoundException("League not exists"));

        match.setLeague(league);
        match.setHomeTeam(homeTeam);
        match.setAwayTeam(awayTeam);
        match.setMatchDateTime(match.getMatchDateTime());

        return matchRepository.save(match);
    }

    public void deleteMatch(Long id) {
        Match match = getMatchById(id);
        matchRepository.delete(match);
    }
}
