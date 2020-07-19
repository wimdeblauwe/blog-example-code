package com.wimdeblauwe.examples.errorhandling.inforequest.web;

import com.wimdeblauwe.examples.errorhandling.inforequest.InfoRequest;
import com.wimdeblauwe.examples.errorhandling.inforequest.InfoRequestNotFoundException;
import com.wimdeblauwe.examples.errorhandling.inforequest.InfoRequestService;
import com.wimdeblauwe.examples.errorhandling.supportagent.SupportAgent;
import com.wimdeblauwe.examples.errorhandling.supportagent.SupportAgentNotFoundException;
import com.wimdeblauwe.examples.errorhandling.supportagent.SupportAgentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/info-requests")
public class InfoRequestRestController {

    private final InfoRequestService service;
    private final SupportAgentService supportAgentService;

    public InfoRequestRestController(InfoRequestService service,
                                     SupportAgentService supportAgentService) {
        this.service = service;
        this.supportAgentService = supportAgentService;
    }

    @GetMapping("{id}")
    public InfoRequest getInfoRequest(@PathVariable("id") Long id) {
        return service.getInfoRequest(id)
                      .orElseThrow(() -> new InfoRequestNotFoundException(id));
    }

    @PostMapping
    public ResponseEntity<?> addInfoRequest(@Valid @RequestBody CreateInfoRequestRequestBody requestBody) {
        InfoRequest infoRequest = service.createInfoRequest(requestBody);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(infoRequest.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("{requestId}/link-to-agent/{agentId}")
    public void linkSupportAgentToInfoRequest(@PathVariable("requestId") Long requestId,
                                              @PathVariable("agentId") Long agentId) {
        InfoRequest infoRequest = service.getInfoRequest(requestId)
                                         .orElseThrow(() -> new InfoRequestNotFoundException(requestId));
        SupportAgent supportAgent = supportAgentService.getSupportAgent(agentId)
                                                       .orElseThrow(() -> new SupportAgentNotFoundException(agentId));

        // ...

    }
}
