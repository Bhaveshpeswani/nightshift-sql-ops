package com.bhavesh.nightshift.controller;

import com.bhavesh.nightshift.dto.CreateServerRequest;
import com.bhavesh.nightshift.model.Server;
import com.bhavesh.nightshift.service.ServerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/servers")
public class ServerController {
    private final ServerService serverService;

    public ServerController(ServerService serverService) {
        this.serverService = serverService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Server createServer(
            @Valid @RequestBody CreateServerRequest request
    ) {
        return serverService.createServer(request);
    }

    @GetMapping
    public List<Server> getServers() {
        return serverService.getAllServers();
    }
}
