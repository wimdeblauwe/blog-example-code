package com.wimdeblauwe.examples.errorhandling.inforequest;

import com.wimdeblauwe.examples.errorhandling.inforequest.web.CreateInfoRequestRequestBody;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class InfoRequestServiceImpl implements InfoRequestService {
    private static final AtomicLong ID_COUNTER = new AtomicLong(0);

    @Override
    public InfoRequest createInfoRequest(CreateInfoRequestRequestBody requestBody) {
        return new InfoRequest(ID_COUNTER.incrementAndGet(), requestBody.getName(), requestBody.getPhoneNumber(), requestBody.getEmail());
    }

    @Override
    public Optional<InfoRequest> getInfoRequest(Long id) {
        return Optional.empty();
    }
}
