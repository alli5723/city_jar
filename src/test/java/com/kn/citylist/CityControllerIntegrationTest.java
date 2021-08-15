package com.kn.citylist;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.MediaType.APPLICATION_JSON;


@SpringBootTest
@AutoConfigureMockMvc
class CityControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getAllCitiesReturnsOk() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/cities"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("\"name\":\"Dublin\""));
        assertTrue(result.getResponse().getContentAsString().contains("\"name\":\"London\""));
        assertTrue(result.getResponse().getContentAsString().contains("pageable"));
    }

    @Test
    void UpdateACityWShouldReturnOk() throws Exception {
        String stringifiedCity = "{\"name\": \"Chilean\",\"photo\": \"https://upload.local/file.jpg\"}";

        mockMvc
                .perform(
                        MockMvcRequestBuilders.patch("/cities/1")
                                .accept(APPLICATION_JSON)
                                .contentType(APPLICATION_JSON)
                                .content(stringifiedCity)
                )
                .andExpect(MockMvcResultMatchers.content().string("{\"id\":1,\"name\":\"Chilean\",\"photo\":\"https://upload.local/file.jpg\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void expectErrorWhenUpdateRequestIsInvalid() throws Exception {
        mockMvc
                .perform(
                        MockMvcRequestBuilders.patch("/cities/3")
                                .accept(APPLICATION_JSON)
                                .contentType(APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
