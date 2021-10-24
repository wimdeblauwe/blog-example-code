package com.wimdeblauwe.examples.htmxsse;

import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Collection;

@Controller
public class PdfGenerationController {
    private static final Logger LOGGER = LoggerFactory.getLogger(PdfGenerationController.class);

    private final PdfGenerator pdfGenerator;
    private final Multimap<String, SseEmitter> sseEmitters = MultimapBuilder.hashKeys().arrayListValues().build();

    public PdfGenerationController(PdfGenerator pdfGenerator) {
        this.pdfGenerator = pdfGenerator;
    }

    @GetMapping
    public String index() {
        return "index";
    }

    @GetMapping("/progress-events")
    public SseEmitter progressEvents(@AuthenticationPrincipal UserDetails userDetails) {
        SseEmitter sseEmitter = new SseEmitter(Long.MAX_VALUE);
        sseEmitters.put(userDetails.getUsername(), sseEmitter);
        System.out.println("Adding SseEmitter for user: " + userDetails.getUsername());
        sseEmitter.onCompletion(() -> LOGGER.info("SseEmitter is completed"));
        sseEmitter.onTimeout(() -> LOGGER.info("SseEmitter is timed out"));
        sseEmitter.onError((ex) -> LOGGER.info("SseEmitter got error:", ex));

        return sseEmitter;
    }

    @PostMapping
    public String generatePdf(@AuthenticationPrincipal UserDetails userDetails) {
        Collection<SseEmitter> sseEmitter = sseEmitters.get(userDetails.getUsername());
        pdfGenerator.generatePdf(new SseEmitterProgressListener(sseEmitter));

        return "index";
    }

    private static class SseEmitterProgressListener implements ProgressListener {
        private final Collection<SseEmitter> sseEmitters;

        public SseEmitterProgressListener(Collection<SseEmitter> sseEmitter) {
            this.sseEmitters = sseEmitter;
        }

        @Override
        public void onProgress(int value) {
            String html = """
                    <div id="progress-container" class="progress-container"> \
                        <div class="progress-bar" style="width:%s%%"></div> \
                    </div>
                    """.formatted(value);
            sendToAllClients(html);
        }

        @Override
        public void onCompletion() {
            String html = "<div><a href=\"#\">Download PDF</div>";
            sendToAllClients(html);
        }

        private void sendToAllClients(String html) {
            for (SseEmitter sseEmitter : sseEmitters) {
                try {
                    sseEmitter.send(html);
                } catch (IOException e) {
                    LOGGER.error(e.getMessage());
                }
            }
        }
    }
}
