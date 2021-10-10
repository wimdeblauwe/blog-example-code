package com.wimdeblauwe.examples.htmxsse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@Controller
public class PdfGenerationController {
    private static final Logger LOGGER = LoggerFactory.getLogger(PdfGenerationController.class);

    private final PdfGenerator pdfGenerator;
    private final SseEmitter sseEmitter = new SseEmitter(Long.MAX_VALUE);

    public PdfGenerationController(PdfGenerator pdfGenerator) {
        this.pdfGenerator = pdfGenerator;
    }

    @GetMapping
    public String index() {
        return "index";
    }

    @GetMapping("/progress-events")
    public SseEmitter progressEvents() {

        sseEmitter.onCompletion(() -> LOGGER.info("SseEmitter is completed"));
        sseEmitter.onTimeout(() -> LOGGER.info("SseEmitter is timed out"));
        sseEmitter.onError((ex) -> LOGGER.info("SseEmitter got error:", ex));

        return sseEmitter;
    }

    @PostMapping
    public String generatePdf() {
        pdfGenerator.generatePdf(new SseEmitterProgressListener(sseEmitter));

        return "index";
    }

    private static class SseEmitterProgressListener implements ProgressListener {
        private final SseEmitter sseEmitter;

        public SseEmitterProgressListener(SseEmitter sseEmitter) {
            this.sseEmitter = sseEmitter;
        }

        @Override
        public void onProgress(int value) {
            try {
                String html = """
                        <div id="progress-container" class="progress-container"> \
                            <div class="progress-bar" style="width:%s%%"></div> \
                        </div>
                        """.formatted(value);
                sseEmitter.send(html);
            } catch (IOException e) {
                LOGGER.error(e.getMessage(), e);
            }
        }

        @Override
        public void onCompletion() {
            try {
                sseEmitter.send("<div><a href=\"#\">Download PDF</div>");
            } catch (IOException e) {
                LOGGER.error(e.getMessage(), e);
            }
        }
    }
}
