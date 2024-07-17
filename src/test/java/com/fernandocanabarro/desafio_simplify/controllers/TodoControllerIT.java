package com.fernandocanabarro.desafio_simplify.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fernandocanabarro.desafio_simplify.dtos.TodoDTO;
import com.fernandocanabarro.desafio_simplify.factories.TodoFactory;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@ActiveProfiles("it")
public class TodoControllerIT {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private ObjectMapper objectMapper;

    private Long existingId, nonExistingId;
    private TodoDTO dto;

    @BeforeEach
    public void setup() throws Exception{
        existingId = 1L;
        nonExistingId = 99L;
        dto = new TodoDTO(TodoFactory.createTodo());
        dto.setId(null);
    }

    @Test
    public void findByIdShouldReturnHttpStatus200WhenIdExists() throws Exception{
        webTestClient.get().uri("/todos/{id}", existingId)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus().isOk()
                    .expectBody()
                    .jsonPath("$.id").isEqualTo(1L)
                    .jsonPath("$.nome").isEqualTo("Comprar mantimentos")
                    .jsonPath("$.descricao").isEqualTo("Ir ao supermercado e comprar comida para a semana")
                    .jsonPath("$.realizado").isEqualTo(false)
                    .jsonPath("$.prioridade").isEqualTo(2);
    }

    @Test
    public void findByIdShouldReturnHttpStatus404WhenIdDoesNotExist() throws Exception{
        webTestClient.get().uri("/todos/{id}", nonExistingId)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus().isNotFound();
    }

    @Test
    public void findAllShouldReturnHttpStatus200() throws Exception{
        webTestClient.get().uri("/todos")
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus().isOk()
                    .expectBody()
                    .jsonPath("$.content[0].id").isEqualTo(3L)
                    .jsonPath("$.content[0].nome").isEqualTo("Fazer exercícios físicos")
                    .jsonPath("$.content[0].descricao").isEqualTo("Correr por 30 minutos todos os dias")
                    .jsonPath("$.content[0].realizado").isEqualTo(false)
                    .jsonPath("$.content[0].prioridade").isEqualTo(3);
    }

    @Test
    public void insertShouldReturnHttpStatus201WhenDataIsValid() throws Exception{
        webTestClient.post().uri("/todos")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(objectMapper.writeValueAsString(dto))
                    .exchange()
                    .expectStatus().isCreated()
                    .expectBody()
                    .jsonPath("$.content[4].nome").isEqualTo("nome")
                    .jsonPath("$.content[4].descricao").isEqualTo("descricao")
                    .jsonPath("$.content[4].realizado").isEqualTo(true)
                    .jsonPath("$.content[4].prioridade").isEqualTo(1);
    }

    @Test
    public void insertShouldReturnHttpStatus422WhenNameIsBlank() throws Exception{
        dto.setNome("");
        webTestClient.post().uri("/todos")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(objectMapper.writeValueAsString(dto))
                    .exchange()
                    .expectStatus().isEqualTo(422)
                    .expectBody()
                    .jsonPath("$.errors[0].fieldName").isEqualTo("nome")
                    .jsonPath("$.errors[0].message").isEqualTo("Campo Obrigatório");
    }

    @Test
    public void insertShouldReturnHttpStatus422WhenDescriptionIsBlank() throws Exception{
        dto.setDescricao("");
        webTestClient.post().uri("/todos")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(objectMapper.writeValueAsString(dto))
                    .exchange()
                    .expectStatus().isEqualTo(422)
                    .expectBody()
                    .jsonPath("$.errors[0].fieldName").isEqualTo("descricao")
                    .jsonPath("$.errors[0].message").isEqualTo("Campo Obrigatório");
    }

    @Test
    public void insertShouldReturnHttpStatus422WhenRealizadoIsNull() throws Exception{
        dto.setRealizado(null);
        webTestClient.post().uri("/todos")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(objectMapper.writeValueAsString(dto))
                    .exchange()
                    .expectStatus().isEqualTo(422)
                    .expectBody()
                    .jsonPath("$.errors[0].fieldName").isEqualTo("realizado")
                    .jsonPath("$.errors[0].message").isEqualTo("Campo Obrigatório");
    }

    @Test
    public void insertShouldReturnHttpStatus422WhenPriorityIsNull() throws Exception{
        dto.setPrioridade(null);
        webTestClient.post().uri("/todos")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(objectMapper.writeValueAsString(dto))
                    .exchange()
                    .expectStatus().isEqualTo(422)
                    .expectBody()
                    .jsonPath("$.errors[0].fieldName").isEqualTo("prioridade")
                    .jsonPath("$.errors[0].message").isEqualTo("Campo Obrigatório");
    }

    @Test
    public void insertShouldReturnHttpStatus422WhenPriorityIsLessThan1() throws Exception{
        dto.setPrioridade(0);
        webTestClient.post().uri("/todos")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(objectMapper.writeValueAsString(dto))
                    .exchange()
                    .expectStatus().isEqualTo(422)
                    .expectBody()
                    .jsonPath("$.errors[0].fieldName").isEqualTo("prioridade")
                    .jsonPath("$.errors[0].message").isEqualTo("Prioridade deve estar entre 1 e 3");
    }

    @Test
    public void insertShouldReturnHttpStatus422WhenPriorityIsGreaterThan3() throws Exception{
        dto.setPrioridade(4);
        webTestClient.post().uri("/todos")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(objectMapper.writeValueAsString(dto))
                    .exchange()
                    .expectStatus().isEqualTo(422)
                    .expectBody()
                    .jsonPath("$.errors[0].fieldName").isEqualTo("prioridade")
                    .jsonPath("$.errors[0].message").isEqualTo("Prioridade deve estar entre 1 e 3");
    }

    @Test
    public void updateShouldHttpStatus200WhenDataIsValidAndIdExists() throws Exception{
        webTestClient.put().uri("/todos/{id}",4L)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(objectMapper.writeValueAsString(dto))
                    .exchange()
                    .expectStatus().isOk()
                    .expectBody()
                    .jsonPath("$.content[3].id").isEqualTo(4L)
                    .jsonPath("$.content[3].nome").isEqualTo("nome")
                    .jsonPath("$.content[3].descricao").isEqualTo("descricao")
                    .jsonPath("$.content[3].realizado").isEqualTo(true)
                    .jsonPath("$.content[3].prioridade").isEqualTo(1);
    }

    @Test
    public void updateShouldReturnHttpStatus404WhenIdDoesNotExist() throws Exception{
        webTestClient.put().uri("/todos/{id}",nonExistingId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(objectMapper.writeValueAsString(dto))
                    .exchange()
                    .expectStatus().isNotFound();
    }

    @Test
    public void updateShouldReturnHttpStatus422WhenNameIsBlank() throws Exception{
        dto.setNome("");
        webTestClient.put().uri("/todos/{id}",existingId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(objectMapper.writeValueAsString(dto))
                    .exchange()
                    .expectStatus().isEqualTo(422)
                    .expectBody()
                    .jsonPath("$.errors[0].fieldName").isEqualTo("nome")
                    .jsonPath("$.errors[0].message").isEqualTo("Campo Obrigatório");
    }

    @Test
    public void updateShouldReturnHttpStatus422WhenDescriptionIsBlank() throws Exception{
        dto.setDescricao("");
        webTestClient.put().uri("/todos/{id}",existingId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(objectMapper.writeValueAsString(dto))
                    .exchange()
                    .expectStatus().isEqualTo(422)
                    .expectBody()
                    .jsonPath("$.errors[0].fieldName").isEqualTo("descricao")
                    .jsonPath("$.errors[0].message").isEqualTo("Campo Obrigatório");
    }

    @Test
    public void updateShouldReturnHttpStatus422WhenRealizadoIsNull() throws Exception{
        dto.setRealizado(null);
        webTestClient.put().uri("/todos/{id}",existingId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(objectMapper.writeValueAsString(dto))
                    .exchange()
                    .expectStatus().isEqualTo(422)
                    .expectBody()
                    .jsonPath("$.errors[0].fieldName").isEqualTo("realizado")
                    .jsonPath("$.errors[0].message").isEqualTo("Campo Obrigatório");
    }

    @Test
    public void updateShouldReturnHttpStatus422WhenPriorityIsNull() throws Exception{
        dto.setPrioridade(null);
        webTestClient.put().uri("/todos/{id}",existingId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(objectMapper.writeValueAsString(dto))
                    .exchange()
                    .expectStatus().isEqualTo(422)
                    .expectBody()
                    .jsonPath("$.errors[0].fieldName").isEqualTo("prioridade")
                    .jsonPath("$.errors[0].message").isEqualTo("Campo Obrigatório");
    }

    @Test
    public void updateShouldReturnHttpStatus422WhenPriorityIsLessThan1() throws Exception{
        dto.setPrioridade(0);
        webTestClient.put().uri("/todos/{id}",existingId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(objectMapper.writeValueAsString(dto))
                    .exchange()
                    .expectStatus().isEqualTo(422)
                    .expectBody()
                    .jsonPath("$.errors[0].fieldName").isEqualTo("prioridade")
                    .jsonPath("$.errors[0].message").isEqualTo("Prioridade deve estar entre 1 e 3");
    }

    @Test
    public void updateShouldReturnHttpStatus422WhenPriorityIsGreaterThan3() throws Exception{
        dto.setPrioridade(4);
        webTestClient.put().uri("/todos/{id}",existingId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(objectMapper.writeValueAsString(dto))
                    .exchange()
                    .expectStatus().isEqualTo(422)
                    .expectBody()
                    .jsonPath("$.errors[0].fieldName").isEqualTo("prioridade")
                    .jsonPath("$.errors[0].message").isEqualTo("Prioridade deve estar entre 1 e 3");
    }

    @Test
    public void deleteShouldReturnHttpStatus204WhenIdExists() throws Exception{
        webTestClient.delete().uri("/todos/{id}",2L)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus().isOk()
                    .expectBody()
                    .jsonPath("$.content[0].id").isEqualTo(3L)
                    .jsonPath("$.content[0].nome").isEqualTo("Fazer exercícios físicos")
                    .jsonPath("$.content[0].descricao").isEqualTo("Correr por 30 minutos todos os dias")
                    .jsonPath("$.content[0].realizado").isEqualTo(false)
                    .jsonPath("$.content[0].prioridade").isEqualTo(3);
                    
    }

    @Test
    public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() throws Exception{
        webTestClient.delete().uri("/todos/{id}",nonExistingId)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus().isNotFound();
    }
}
