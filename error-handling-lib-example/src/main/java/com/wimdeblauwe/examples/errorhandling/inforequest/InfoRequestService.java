package com.wimdeblauwe.examples.errorhandling.inforequest;

import com.wimdeblauwe.examples.errorhandling.inforequest.web.CreateInfoRequestRequestBody;

import java.util.Optional;

public interface InfoRequestService {
    InfoRequest createInfoRequest(CreateInfoRequestRequestBody requestBody);

    Optional<InfoRequest> getInfoRequest(Long id);
}
