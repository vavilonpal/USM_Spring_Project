package org.moodle.springlaboratorywork.service;


import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.moodle.springlaboratorywork.dtos.MatchDTO;
import org.moodle.springlaboratorywork.entity.League;
import org.moodle.springlaboratorywork.entity.Match;
import org.moodle.springlaboratorywork.entity.Team;
import org.moodle.springlaboratorywork.exception.HomeAndAwayTeamAreTheSameException;
import org.moodle.springlaboratorywork.repository.LeagueRepository;
import org.moodle.springlaboratorywork.repository.MatchRepository;
import org.moodle.springlaboratorywork.repository.TeamRepository;
import org.moodle.springlaboratorywork.repository.hibernateRepository.MatchDao;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MatchService {
    private final MatchRepository matchRepo;
    private final MatchDao matchRepository;
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
        checkTheSameOfTeamNames(matchDto.getHomeTeamName(), matchDto.getAwayTeamName());
        checkExistsByHomeTeamNameAndAwayTeamName(matchDto.getHomeTeamName(), matchDto.getAwayTeamName());

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
        checkTheSameOfTeamNames(matchDto.getHomeTeamName(), matchDto.getAwayTeamName());

        Match match = getMatchById(id);

        if (!(matchDto.getAwayTeamName().equals(match.getAwayTeam().getName())) ||
                !(matchDto.getHomeTeamName().equals(match.getHomeTeam().getName()))){
            checkExistsByHomeTeamNameAndAwayTeamName(matchDto.getHomeTeamName(), matchDto.getAwayTeamName());

            Team homeTeam = teamRepository.findByName(matchDto.getHomeTeamName())
                    .orElseThrow(() -> new EntityNotFoundException("Home team not exists"));

            Team awayTeam = teamRepository.findByName(matchDto.getAwayTeamName())
                    .orElseThrow(() -> new EntityNotFoundException("Away team not exists"));

            match.setHomeTeam(homeTeam);
            match.setAwayTeam(awayTeam);
        }


        //todo Do ask teams must be from the same league?
        League league = leagueRepository.findByName(matchDto.getLeagueName())
                .orElseThrow(() -> new EntityNotFoundException("League not exists"));

        match.setLeague(league);
        match.setMatchDateTime(match.getMatchDateTime());

        return matchRepository.save(match);
    }

    public void deleteMatch(Long id) {
        Match match = getMatchById(id);
        matchRepository.delete(match);
    }

    private void checkTheSameOfTeamNames(String homeTeamName, String awayTeamName){
        if (homeTeamName.equals(awayTeamName)){
            throw new HomeAndAwayTeamAreTheSameException("Teams has same names");
        }
    }
    private void checkExistsByHomeTeamNameAndAwayTeamName(String homeTeamName, String awayTeamName){
        if (matchRepo.existsByHomeTeamNameAndAwayTeamName(homeTeamName, awayTeamName)) {
            throw new EntityExistsException("Match already exists");
        }
    }

}
