package com.bhavesh.nightshift.controller;

import com.bhavesh.nightshift.model.IncidentSummary;
import com.bhavesh.nightshift.repository.ReportRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportRepository reportRepository;

    public ReportController(
            ReportRepository reportRepository
    ) {
        this.reportRepository = reportRepository;
    }

    @GetMapping("/open-incidents")
    public List<IncidentSummary> getOpenIncidents() {
        return reportRepository.getOpenIncidentSummary();
    }
}
