package org.moodle.springlaboratorywork.repository.jdbcRepository;


import lombok.RequiredArgsConstructor;
import org.moodle.springlaboratorywork.entity.League;
import org.moodle.springlaboratorywork.entity.Team;
import org.moodle.springlaboratorywork.repository.TeamRepository;
import org.moodle.springlaboratorywork.service.MatchService;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class LeagueJDBCRepository {
    private final JDBCUtil jdbcUtil;
    private final TeamRepository teamRepository;
    private final MatchService matchService;

    public Optional<League> findById(Long id) {
        String QUERY = "SELECT * FROM leagues WHERE id = ?";
        try (Connection conn = jdbcUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(QUERY)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                League league = League.builder()
                        .id(rs.getLong("id"))
                        .name(rs.getString("name"))
                        .teams(new HashSet<>(teamRepository.findAllByLeagueId(id)))
                        .createdAt(rs.getTimestamp("created_at").toLocalDateTime())
                        .updatedAt(rs.getTimestamp("updated_at") != null
                                ? rs.getTimestamp("updated_at").toLocalDateTime()
                                : null)
                        .build();
                return Optional.of(league);
            }
        } catch (SQLException e) {
            JDBCUtil.printSQLException(e);
        }
        return Optional.empty();
    }

    public List<League> findAll() {
        String QUERY = "SELECT * FROM leagues";
        List<League> leagues = new ArrayList<>();
        try (Connection conn = jdbcUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(QUERY);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Long leagueId = rs.getLong("id");
                League league = League.builder()
                        .id(leagueId)
                        .name(rs.getString("name"))
                        .teams(new HashSet<>(teamRepository.findAllByLeagueId(leagueId)))
                        .createdAt(rs.getTimestamp("created_at").toLocalDateTime())
                        .updatedAt(rs.getTimestamp("updated_at") != null
                                ? rs.getTimestamp("updated_at").toLocalDateTime()
                                : null)
                        .build();
                leagues.add(league);
            }
        } catch (SQLException e) {
            JDBCUtil.printSQLException(e);
        }
        return leagues;
    }

    public League save(League league) {
        String INSERT = "INSERT INTO leagues (name, created_at) VALUES (?, ?)";
        String UPDATE = "UPDATE leagues SET name = ?, updated_at = ? WHERE id = ?";
        String UPDATE_TEAM_LEAGUE = "UPDATE teams SET league_id = ? WHERE id = ?";


        try (Connection conn = jdbcUtil.getConnection()) {
            if (league.getId() == null) {
                try (PreparedStatement stmt = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
                    stmt.setString(1, league.getName());
                    stmt.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
                    stmt.executeUpdate();
                    ResultSet keys = stmt.getGeneratedKeys();
                    if (keys.next()) {
                        league.setId(keys.getLong(1));
                    }
                }
            } else {
                try (PreparedStatement stmt = conn.prepareStatement(UPDATE)) {
                    stmt.setString(1, league.getName());
                    stmt.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
                    stmt.setLong(3, league.getId());
                    stmt.executeUpdate();
                }
            }
            if (league.getTeams() != null) {
                try (PreparedStatement teamUpdateStmt = conn.prepareStatement(UPDATE_TEAM_LEAGUE)) {
                    for (Team team : league.getTeams()) {
                        if (team.getId() != null) {
                            teamUpdateStmt.setLong(1, league.getId());
                            teamUpdateStmt.setLong(2, team.getId());
                            teamUpdateStmt.addBatch();
                        }
                    }
                    teamUpdateStmt.executeBatch();
                }
            }
        } catch (SQLException e) {
            JDBCUtil.printSQLException(e);
        }

        return league;
    }

    public void delete(Long id) {
        String DELETE = "DELETE FROM leagues WHERE id = ?";
        try (Connection conn = jdbcUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(DELETE)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            JDBCUtil.printSQLException(e);
        }
    }
}

