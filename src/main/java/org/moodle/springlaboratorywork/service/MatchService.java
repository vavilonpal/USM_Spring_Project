package org.moodle.springlaboratorywork.service;


import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.moodle.springlaboratorywork.dtos.MatchDTO;
import org.moodle.springlaboratorywork.entity.League;
import org.moodle.springlaboratorywork.entity.Match;
import org.moodle.springlaboratorywork.entity.Team;
import org.moodle.springlaboratorywork.exception.HomeAndAwayTeamAreTheSameException;
import org.moodle.springlaboratorywork.repository.write.WriteLeagueRepository;
import org.moodle.springlaboratorywork.repository.write.WriteMatchRepository;
import org.moodle.springlaboratorywork.repository.write.WriteTeamRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class MatchService {
    private final WriteMatchRepository writeMatchRepository;
    private final WriteMatchRepository readMatchRepository;
    private final WriteTeamRepository teamRepository;
    private final WriteLeagueRepository leagueRepository;

    public List<Match> getAllMatches() {
        return readMatchRepository.findAll();
    }
    public Set<Match> getHomeMatchesByTeamId(Long teamId){
        return writeMatchRepository.findAllByHomeTeamId(teamId);
    }
    public Set<Match> getAwayMatchesByTeamId(Long teamId){
        return writeMatchRepository.findAllByAwayTeamId(teamId);
    }

    public Match getMatchById(Long id) {
        return writeMatchRepository.findById(id)
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

        return writeMatchRepository.save(match);
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

        League league = leagueRepository.findByName(matchDto.getLeagueName())
                .orElseThrow(() -> new EntityNotFoundException("League not exists"));

        match.setLeague(league);
        match.setMatchDateTime(match.getMatchDateTime());

        return writeMatchRepository.save(match);
    }

    public void deleteMatch(Long id) {
        writeMatchRepository.deleteById(id);
    }

    private void checkTheSameOfTeamNames(String homeTeamName, String awayTeamName){
        if (homeTeamName.equals(awayTeamName)){
            throw new HomeAndAwayTeamAreTheSameException("Teams has same names");
        }
    }
    private void checkExistsByHomeTeamNameAndAwayTeamName(String homeTeamName, String awayTeamName){
        if (writeMatchRepository.existsByHomeTeamNameAndAwayTeamName(homeTeamName, awayTeamName)) {
            throw new EntityExistsException("Match already exists");
        }
    }

}
