package com.car.carservices.service;

import com.car.carservices.dto.PRUserPlatesResponse;
import com.car.carservices.repository.PRUserPlatesReadRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PRUserPlatesService {

    private final PRUserPlatesReadRepository repo;

    public PRUserPlatesService(PRUserPlatesReadRepository repo) {
        this.repo = repo;
    }

    public PRUserPlatesResponse listPlatesForUser(Long userId) {
        List<String> raw = repo.findPlatesByUserId(userId);
        // prefix each with "p-"
        List<String> prefixed = raw.stream()
                .filter(s -> s != null && !s.isBlank())
                .map(s -> "p-" + s)
                .toList();
        return new PRUserPlatesResponse(prefixed);
    }
}
