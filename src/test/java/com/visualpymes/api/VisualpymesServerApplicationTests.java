package com.visualpymes.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
class VisualpymesServerApplicationTests {


    @Autowired
    MockMvc mockMvc;

    @Autowired
    ClientRepository clientRepository;

    @Test
    void returnsTheExistingClients() throws Exception {

        addSampleClients();

        mockMvc.perform(get("/api/clients"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(2)))
                .andExpect(jsonPath("$[0].name", equalTo("Cliente 1: Tu estilo")))
                .andExpect(jsonPath("$[1].name", equalTo("Cliente 2: Tu pasteleria")))
                .andExpect(jsonPath("$[0].image", equalTo("../img/peinado.png")))
                .andExpect(jsonPath("$[1].image", equalTo("../img/torta.png")))
                .andExpect(jsonPath("$[0].description", equalTo("paquete completo lavado, secado, peinado")))
                .andExpect(jsonPath("$[1].description", equalTo("Torta chocolate dos pisos")))
                .andExpect(jsonPath("$[0].price", equalTo(50.0)))
                .andExpect(jsonPath("$[1].price", equalTo(20.0)))
                .andDo(print());
    }

    private void addSampleClients() {
        List<Client> clients = List.of(
                new Client("Cliente 1: Tu estilo", "../img/peinado.png", "Paquete completo lavado, secado, peinado", 50.0),
                new Client("Cliente 2: Tu pasteleria", "../img/torta.png", "Torta chocolate dos pisos", 20.0)
        );

        clientRepository.saveAll(clients);


    }
}