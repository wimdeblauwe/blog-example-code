package com.wimdeblauwe.examples.errorhandling.supportagent;

import java.util.Optional;

public interface SupportAgentService {
    Optional<SupportAgent> getSupportAgent(long id);
}
