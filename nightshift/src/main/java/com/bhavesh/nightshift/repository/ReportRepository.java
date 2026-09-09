package com.bhavesh.nightshift.repository;

import com.bhavesh.nightshift.model.IncidentSummary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ReportRepository {

    private final JdbcTemplate jdbcTemplate;

    public ReportRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<IncidentSummary> getOpenIncidentSummary() {

        return jdbcTemplate.query(
                "EXEC dbo.GetOpenIncidentSummary",

                (rs, rowNum) -> new IncidentSummary(
                        rs.getString("hostname"),
                        rs.getString("environment"),
                        rs.getString("status"),
                        rs.getInt("open_incidents")
                )
        );
    }
}
