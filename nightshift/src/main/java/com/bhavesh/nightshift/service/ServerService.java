package com.bhavesh.nightshift.service;

import com.bhavesh.nightshift.dto.CreateServerRequest;
import com.bhavesh.nightshift.model.Server;
import com.bhavesh.nightshift.repository.ServerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ServerService {
    private final ServerRepository serverRepository;

    public ServerService(ServerRepository serverRepository) {
        this.serverRepository = serverRepository;
    }

    public Server createServer(CreateServerRequest request) {

        long id = serverRepository.create(
                request.hostname(),
                request.environment()
        );

        return getServer(id);
    }

    public List<Server> getAllServers() {
        return serverRepository.findAll();
    }

    public Server getServer(long id) {
        return serverRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Server not found"
                        )
                );
    }
}
