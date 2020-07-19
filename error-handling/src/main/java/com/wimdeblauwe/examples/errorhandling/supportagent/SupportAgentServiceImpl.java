package com.wimdeblauwe.examples.errorhandling.supportagent;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SupportAgentServiceImpl implements SupportAgentService {
    @Override
    public Optional<SupportAgent> getSupportAgent(long id) {
        return Optional.empty();
    }
}
