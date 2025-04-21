package org.moodle.springlaboratorywork.repository.jdbcRepository;


import lombok.RequiredArgsConstructor;
import org.moodle.springlaboratorywork.entity.League;
import org.moodle.springlaboratorywork.entity.Match;
import org.moodle.springlaboratorywork.entity.Team;
import org.moodle.springlaboratorywork.repository.CoachRepository;
import org.moodle.springlaboratorywork.repository.LeagueRepository;
import org.moodle.springlaboratorywork.service.LeagueService;
import org.moodle.springlaboratorywork.service.TeamService;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Repository
@RequiredArgsConstructor
public class MatchJDBCRepository {
    private final JDBCUtil jdbcUtil;
    private final TeamService teamService;
    private final LeagueRepository leagueService;

    public Optional<Match> findById(Long id) {
        String SELECT = "SELECT * FROM matches WHERE id = ?";
        try (Connection conn = jdbcUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Match match = mapResultSetToMatch(rs);
                return Optional.of(match);
            }
        } catch (SQLException e) {
            JDBCUtil.printSQLException(e);
        }
        return Optional.empty();
    }

    public List<Match> findAll() {
        String SELECT_ALL = "SELECT * FROM matches";
        List<Match> matches = new ArrayList<>();
        try (Connection conn = jdbcUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_ALL);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                matches.add(mapResultSetToMatch(rs));
            }
        } catch (SQLException e) {
            JDBCUtil.printSQLException(e);
        }
        return matches;
    }

    public Match save(Match match) {
        String INSERT = "INSERT INTO matches (league_id, home_team_id, away_team_id, match_datetime, created_at) VALUES (?, ?, ?, ?, ?)";
        String UPDATE = "UPDATE matches SET league_id = ?, home_team_id = ?, away_team_id = ?, match_datetime = ?, updated_at = ? WHERE id = ?";

        try (Connection connection = jdbcUtil.getConnection()) {
            if (match.getId() == null) {
                try (PreparedStatement stmt = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
                    stmt.setObject(1, match.getLeague() != null ? match.getLeague().getId() : null, Types.BIGINT);
                    stmt.setObject(2, match.getHomeTeam() != null ? match.getHomeTeam().getId() : null, Types.BIGINT);
                    stmt.setObject(3, match.getAwayTeam() != null ? match.getAwayTeam().getId() : null, Types.BIGINT);
                    stmt.setTimestamp(4, Timestamp.valueOf(match.getMatchDateTime()));
                    stmt.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
                    stmt.executeUpdate();

                    ResultSet keys = stmt.getGeneratedKeys();
                    if (keys.next()) {
                        match.setId(keys.getLong(1));
                    }
                }
            } else {
                try (PreparedStatement stmt = connection.prepareStatement(UPDATE)) {
                    stmt.setObject(1, match.getLeague() != null ? match.getLeague().getId() : null, Types.BIGINT);
                    stmt.setObject(2, match.getHomeTeam() != null ? match.getHomeTeam().getId() : null, Types.BIGINT);
                    stmt.setObject(3, match.getAwayTeam() != null ? match.getAwayTeam().getId() : null, Types.BIGINT);
                    stmt.setTimestamp(4, Timestamp.valueOf(match.getMatchDateTime()));
                    stmt.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
                    stmt.setLong(6, match.getId());
                    stmt.executeUpdate();
                }
            }
        } catch (SQLException e) {
            JDBCUtil.printSQLException(e);
        }

        return match;
    }

    public void delete(Long id) {
        String DELETE = "DELETE FROM matches WHERE id = ?";
        try (Connection conn = jdbcUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(DELETE)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            JDBCUtil.printSQLException(e);
        }
    }

    private Match mapResultSetToMatch(ResultSet rs) throws SQLException {

        Team homeTeam = teamService.getTeamById(rs.getLong("home_team_id"));
        Team awayTeam = teamService.getTeamById(rs.getLong("away_team_id"));
        League league = leagueService.findById(rs.getLong("league_id")).orElseThrow(()-> new RuntimeException("laegue not found"));
        return Match.builder()
                .id(rs.getLong("id"))
                .league(league)
                .homeTeam(homeTeam)
                .awayTeam(awayTeam)
                .matchDateTime(rs.getTimestamp("match_datetime").toLocalDateTime())
                .createdAt(rs.getTimestamp("created_at").toLocalDateTime())
                .updatedAt(rs.getTimestamp("updated_at") != null ? rs.getTimestamp("updated_at").toLocalDateTime() : null)
                .build();
    }
}
