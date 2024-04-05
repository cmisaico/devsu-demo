package com.devsu;

import com.devsu.models.requests.ClienteRequest;
import com.devsu.models.responses.ClienteResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ClienteControllerTest {

    @Autowired
    private WebTestClient client;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    @Order(1)
    void testCrear(){
        ClienteRequest clienteRequest = crearClienteRequest();
        client.post().uri("/clientes/")
                .bodyValue(clienteRequest)
                .exchange()
                .expectStatus().isCreated();
    }

    @Test
    @Order(2)
    void testObtener() {
        client.get().uri("/clientes/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(ClienteResponse.class)
                .consumeWith(response -> {
                    ClienteResponse cliente = response.getResponseBody();
                    assertNotNull(cliente);
                    assertEquals("Juan Pablo Mendoza", cliente.getNombre());
                    assertTrue(cliente.getEstado());
                });
    }

    private ClienteRequest crearClienteRequest(){
        return ClienteRequest.builder()
                .nombre("Juan Pablo Mendoza")
                .genero("M")
                .direccion("Calle 123")
                .telefono("938254678")
                .edad(25)
                .identificacion("12345678")
                .contrasenia("123456")
                .estado(true)
                .build();
    }


}
