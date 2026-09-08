package com.bhavesh.nightshift.repository;

import com.bhavesh.nightshift.model.Server;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ServerRepository {
    private final JdbcTemplate jdbcTemplate;

    public ServerRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public long create(String hostname, String environment) {

        String sql = """
                INSERT INTO dbo.servers (hostname, environment)
                OUTPUT INSERTED.id
                VALUES (?, ?)
                """;

        Long id = jdbcTemplate.queryForObject(
                sql,
                Long.class,
                hostname,
                environment
        );

        return id;
    }

    public List<Server> findAll() {

        String sql = """
                SELECT id,
                       hostname,
                       environment,
                       status,
                       created_at
                FROM dbo.servers
                ORDER BY id
                """;

        return jdbcTemplate.query(
                sql,
                (rs, rowNum) -> new Server(
                        rs.getLong("id"),
                        rs.getString("hostname"),
                        rs.getString("environment"),
                        rs.getString("status"),
                        rs.getTimestamp("created_at").toLocalDateTime()
                )
        );
    }

    public Optional<Server> findById(long id) {

        String sql = """
                SELECT id,
                       hostname,
                       environment,
                       status,
                       created_at
                FROM dbo.servers
                WHERE id = ?
                """;

        return jdbcTemplate.query(
                sql,
                (rs, rowNum) -> new Server(
                        rs.getLong("id"),
                        rs.getString("hostname"),
                        rs.getString("environment"),
                        rs.getString("status"),
                        rs.getTimestamp("created_at").toLocalDateTime()
                ),
                id
        ).stream().findFirst();
    }

    public void updateStatus(long id, String status) {

        String sql = """
                UPDATE dbo.servers
                SET status = ?
                WHERE id = ?
                """;

        jdbcTemplate.update(
                sql,
                status,
                id
        );
    }
}
