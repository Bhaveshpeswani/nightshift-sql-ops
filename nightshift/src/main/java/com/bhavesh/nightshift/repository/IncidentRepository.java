package com.bhavesh.nightshift.repository;

import com.bhavesh.nightshift.model.Incident;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class IncidentRepository {

    private final JdbcTemplate jdbcTemplate;

    public IncidentRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public long create(long serverId, String title) {

        String sql = """
                INSERT INTO dbo.incidents (server_id, title)
                OUTPUT INSERTED.id
                VALUES (?, ?)
                """;

        Long id = jdbcTemplate.queryForObject(
                sql,
                Long.class,
                serverId,
                title
        );

        return id;
    }

    public Optional<Incident> findById(long id) {

        String sql = """
                SELECT id,
                       server_id,
                       title,
                       status,
                       opened_at,
                       resolved_at
                FROM dbo.incidents
                WHERE id = ?
                """;

        return jdbcTemplate.query(
                sql,
                (rs, rowNum) -> new Incident(
                        rs.getLong("id"),
                        rs.getLong("server_id"),
                        rs.getString("title"),
                        rs.getString("status"),
                        rs.getTimestamp("opened_at").toLocalDateTime(),
                        rs.getTimestamp("resolved_at") == null
                                ? null
                                : rs.getTimestamp("resolved_at").toLocalDateTime()
                ),
                id
        ).stream().findFirst();
    }

    public List<Incident> findOpen() {

        String sql = """
                SELECT id,
                       server_id,
                       title,
                       status,
                       opened_at,
                       resolved_at
                FROM dbo.incidents
                WHERE status = 'OPEN'
                ORDER BY opened_at DESC
                """;

        return jdbcTemplate.query(
                sql,
                (rs, rowNum) -> new Incident(
                        rs.getLong("id"),
                        rs.getLong("server_id"),
                        rs.getString("title"),
                        rs.getString("status"),
                        rs.getTimestamp("opened_at").toLocalDateTime(),
                        null
                )
        );
    }

    public void resolve(long id) {

        String sql = """
                UPDATE dbo.incidents
                SET status = 'RESOLVED',
                    resolved_at = SYSUTCDATETIME()
                WHERE id = ?
                """;

        jdbcTemplate.update(sql, id);
    }

    public int countOpenForServer(long serverId) {

        String sql = """
                SELECT COUNT(*)
                FROM dbo.incidents
                WHERE server_id = ?
                  AND status = 'OPEN'
                """;

        Integer count = jdbcTemplate.queryForObject(
                sql,
                Integer.class,
                serverId
        );

        return count == null ? 0 : count;
    }
}
