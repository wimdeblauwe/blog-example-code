package com.wimdeblauwe.examples.generateenumvaluesspringrestdocs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ExtendWith(RestDocumentationExtension.class)
class DayInfoRestControllerDocumentation {

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext,
                      RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                                      .apply(documentationConfiguration(restDocumentation))
                                      .build();
    }

    @Test
    void getSeasonsExample() throws Exception {
        this.mockMvc.perform(get("/api/dayinfo").accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andDo(document("get-dayinfo-example",
                                    responseFields(
                                            dateField(),
                                            seasonField()
                                    )));
    }

    private FieldDescriptor dateField() {
        return fieldWithPath("date").description("The current date");
    }

    private FieldDescriptor seasonField() {
        String formattedEnumValues = Arrays.stream(Season.values())
                                           .map(type -> String.format("`%s`", type))
                                           .collect(Collectors.joining(", "));
        return fieldWithPath("season").description("The current season, one of " + formattedEnumValues + ".");
    }
}
