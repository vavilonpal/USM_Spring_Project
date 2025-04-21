package org.moodle.springlaboratorywork.repository.jdbcRepository;

import lombok.RequiredArgsConstructor;
import org.moodle.springlaboratorywork.entity.Coach;
import org.moodle.springlaboratorywork.entity.League;
import org.moodle.springlaboratorywork.entity.Team;
import org.moodle.springlaboratorywork.repository.CoachRepository;
import org.moodle.springlaboratorywork.repository.LeagueRepository;
import org.moodle.springlaboratorywork.repository.MatchRepository;
import org.moodle.springlaboratorywork.service.CoachService;
import org.moodle.springlaboratorywork.service.LeagueService;
import org.moodle.springlaboratorywork.service.MatchService;
import org.moodle.springlaboratorywork.service.PlayerService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TeamJDBCRepository {
    private final JDBCUtil jdbcUtil;
    private final CoachRepository coachRepository;
    private final LeagueRepository leagueRepository;
    private final PlayerService playerService;

    private final MatchRepository matchService;

    public Optional<Team> findById(Long id) {
        String FIND_TEAM_BY_ID = "select * from teams where id = ?;";
        Optional<Team> team = Optional.empty();

        try (Connection connection = jdbcUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_TEAM_BY_ID)) {

            preparedStatement.setLong(1, id);

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Long entityId = rs.getLong("id");
                String name = rs.getString("name");

                Long coachId =  rs.getLong("coach_id");
                Coach coach = coachRepository.findById(coachId)
                        .orElse(new Coach());

                Long leagueId = rs.getLong("league_id");
                League league = leagueRepository.findById(leagueId)
                        .orElse(new League());

                LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();
                LocalDateTime updatedAt = rs.getTimestamp("updated_at") != null
                        ? rs.getTimestamp("updated_at").toLocalDateTime()
                        : null ;

                team = Optional.ofNullable(Team.builder()
                       .id(entityId)
                       .name(name)
                       .coach(coach)
                       .league(league)
                       .players(playerService.getPlayersByTeamId(entityId))
                       .awayMatches(matchService.findAllByAwayTeamId(entityId))
                       .homeMatches(matchService.findAllByHomeTeamId(entityId))
                       .createdAt(createdAt)
                       .updatedAt(updatedAt)
                       .build());
                return team;
            }
        } catch (SQLException e) {
            JDBCUtil.printSQLException(e);
        }
        return team;
    }

    public List<Team> findAll() {
        String FIND_TEAM_BY_ID = "select * from teams;";
        Team team;
        List<Team> teams = new ArrayList<>();

        try (Connection connection = jdbcUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_TEAM_BY_ID)) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Long entityId = rs.getLong("id");
                String name = rs.getString("name");

                Long coachId =  rs.getLong("coach_id");
                Coach coach = coachRepository.findById(coachId)
                        .orElse(new Coach());

                Long leagueId = rs.getLong("league_id");
                League league = leagueRepository.findById(leagueId)
                        .orElse(new League());

                LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();
                LocalDateTime updatedAt = rs.getTimestamp("updated_at") != null
                        ? rs.getTimestamp("updated_at").toLocalDateTime()
                        : null ;

                team = Team.builder()
                        .id(entityId)
                        .name(name)
                        .coach(coach)
                        .league(league)
                        .players(playerService.getPlayersByTeamId(entityId))
                        .awayMatches(matchService.findAllByAwayTeamId(entityId))
                        .homeMatches(matchService.findAllByHomeTeamId(entityId))
                        .createdAt(createdAt)
                        .updatedAt(updatedAt)
                        .build();

                teams.add(team);
            }
        } catch (SQLException e) {
            JDBCUtil.printSQLException(e);
        }
        return teams;
    }

    public Team save(Team team) {
        String INSERT_TEAM = "INSERT INTO teams (name, coach_id, league_id, created_at) VALUES (?, ?, ?, ?);";
        String UPDATE_TEAM = "UPDATE teams SET name = ?, coach_id = ?, league_id = ? WHERE id = ?;";

        try (Connection connection = jdbcUtil.getConnection()) {

            if (team.getId() == null) {
                // INSERT logic
                try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_TEAM, Statement.RETURN_GENERATED_KEYS)) {
                    preparedStatement.setString(1, team.getName());
                    preparedStatement.setObject(2, team.getCoach() != null ? team.getCoach().getId() : null, Types.BIGINT);
                    preparedStatement.setObject(3, team.getLeague() != null ? team.getLeague().getId() : null, Types.BIGINT);
                    preparedStatement.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
                    preparedStatement.executeUpdate();

                    ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        team.setId(generatedKeys.getLong(1));
                    }
                }
            } else {
                // UPDATE logic
                try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_TEAM)) {
                    preparedStatement.setString(1, team.getName());
                    preparedStatement.setObject(2, team.getCoach() != null && team.getCoach().getId() != null ? team.getCoach().getId() : null);
                    preparedStatement.setObject(3, team.getLeague() != null && team.getLeague().getId() != null ? team.getLeague().getId() : null);
                    preparedStatement.setLong(4, team.getId());
                    preparedStatement.executeUpdate();
                }
            }

        } catch (SQLException e) {
            JDBCUtil.printSQLException(e);
        }

        return team;
    }

    public void delete(Long id) {
        String DELETE_TEAM = "delete from teams where id = ?;";

        try (Connection connection = jdbcUtil.getConnection();

             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_TEAM)) {
            preparedStatement.setLong(1, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            JDBCUtil.printSQLException(e);
        }
    }

    /*public Optional<Team> getTeamByName(String name){
        String FIND_TEAM_BY_ID = "select * from teams where name = ?;";
        Optional<Team> team = Optional.empty();

        try (Connection connection = jdbcUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_TEAM_BY_ID)) {

            preparedStatement.setString(1, name);

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Long entityId = rs.getLong("id");
                String entityName = rs.getString("name");

                Long coachId =  rs.getLong("coach_id");
                Coach coach = coachRepository.findById(coachId)
                        .orElse(new Coach());

                Long leagueId = rs.getLong("league_id");
                League league = leagueRepository.findById(leagueId)
                        .orElse(new League());

                LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();
                LocalDateTime updatedAt = rs.getTimestamp("updated_at") != null
                        ? rs.getTimestamp("updated_at").toLocalDateTime()
                        : null ;

                team = Optional.ofNullable(Team.builder()
                        .id(entityId)
                        .name(entityName)
                        .coach(coach)
                        .league(league)
                        .players(playerService.getPlayersByTeamId(entityId))
                        .awayMatches(matchService.getAwayMatchesByTeamId(entityId))
                        .homeMatches(matchService.getHomeMatchesByTeamId(entityId))
                        .createdAt(createdAt)
                        .updatedAt(updatedAt)
                        .build());
                return team;
            }
        } catch (SQLException e) {
            JDBCUtil.printSQLException(e);
        }
        return team;
    }*/
}
