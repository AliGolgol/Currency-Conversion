package com.currencyconversion.api.controller;

import com.currencyconversion.api.services.CurrencyConversionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.internal.matchers.Any;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(value = {SpringExtension.class})
@WebMvcTest(controllers = {CurrencyController.class})
class CurrencyControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    CurrencyConversionService currencyConversionService;

    @Test
    public void should_return_aStringWhichIsResultOfConversion_when_publicServiceIsAvailable() throws Exception {

        String currency = "EUR";
        MockMultipartFile firstFile = new MockMultipartFile("file", "input.json", "text/plain", "{ \"value\": 9.95, \"currency\": \"USD\" }".getBytes());
        Mockito.when(currencyConversionService.convertCurrency(firstFile,currency)).thenReturn("\"{ \\\"value\\\": 9.95, \\\"currency\\\": \\\"USD\\\" }\"");

        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        mockMvc.perform(MockMvcRequestBuilders.multipart("/currency/")
                .file(firstFile)
                .param("currency", currency))
                .andExpect(status().is(200))
                .andExpect(content().string("\"{ \\\"value\\\": 9.95, \\\"currency\\\": \\\"USD\\\" }\""));
    }
}