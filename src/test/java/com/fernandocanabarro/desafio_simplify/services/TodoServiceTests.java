package com.fernandocanabarro.desafio_simplify.services;

import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fernandocanabarro.desafio_simplify.dtos.TodoDTO;
import com.fernandocanabarro.desafio_simplify.entities.Todo;
import com.fernandocanabarro.desafio_simplify.factories.TodoFactory;
import com.fernandocanabarro.desafio_simplify.repositories.TodoRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TodoServiceTests {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TodoRepository repository;

    private Long existingId, nonExistingId;
    private Todo todo;
    private TodoDTO dto;
    private Page<TodoDTO> page;

    @BeforeEach
    public void setup() throws Exception{
        existingId = 1L;
        nonExistingId = 99L;
        todo = TodoFactory.createTodo();
        dto = new TodoDTO(todo);
        page = new PageImpl<>(List.of(dto));
    }

    @Test
    public void findByIdShouldReturnMonoOfTodoDTOWhenIdExists() throws Exception{
        when(repository.findById(existingId)).thenReturn(Mono.just(todo));

        webTestClient.get().uri("/todos/{id}", existingId)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus().isOk()
                    .expectBody()
                    .jsonPath("$.id").isEqualTo(1L)
                    .jsonPath("$.nome").isEqualTo("nome")
                    .jsonPath("$.descricao").isEqualTo("descricao")
                    .jsonPath("$.realizado").isEqualTo(true)
                    .jsonPath("$.prioridade").isEqualTo(1);
    }

    @Test
    public void findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() throws Exception{
        when(repository.findById(nonExistingId)).thenReturn(Mono.empty());

        webTestClient.get().uri("/todos/{id}", nonExistingId)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus().isNotFound();
    }

    @Test
    public void findAllShouldReturnMonoOfPageTodoDTO() throws Exception{
        when(repository.findAllBy(ArgumentMatchers.any(Pageable.class))).thenReturn(Flux.just(todo));
        when(repository.count()).thenReturn(Mono.just((long) page.getTotalElements()));

        webTestClient.get().uri("/todos")
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus().isOk()
                    .expectBody()
                    .jsonPath("$.content[0].id").isEqualTo(1L)
                    .jsonPath("$.content[0].nome").isEqualTo("nome")
                    .jsonPath("$.content[0].descricao").isEqualTo("descricao")
                    .jsonPath("$.content[0].realizado").isEqualTo(true)
                    .jsonPath("$.content[0].prioridade").isEqualTo(1);
    }

    @Test
    public void insertShouldReturnMonoOfTodoDTO() throws Exception{ 
        when(repository.save(todo)).thenReturn(Mono.just(todo));
        when(repository.findAllBy(ArgumentMatchers.any(Pageable.class))).thenReturn(Flux.just(todo));
        when(repository.count()).thenReturn(Mono.just((long) page.getTotalElements()));

        webTestClient.post().uri("/todos")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(objectMapper.writeValueAsString(dto))
                    .exchange()
                    .expectStatus().isCreated()
                    .expectBody()
                    .jsonPath("$.content[0].id").isEqualTo(1L)
                    .jsonPath("$.content[0].nome").isEqualTo("nome")
                    .jsonPath("$.content[0].descricao").isEqualTo("descricao")
                    .jsonPath("$.content[0].realizado").isEqualTo(true)
                    .jsonPath("$.content[0].prioridade").isEqualTo(1);
    }

    @Test
    public void updateShouldReturnMonoOfTodoDTOWhenIdExists() throws Exception{
        when(repository.findById(existingId)).thenReturn(Mono.just(todo));
        when(repository.save(todo)).thenReturn(Mono.just(todo));
        when(repository.findAllBy(ArgumentMatchers.any(Pageable.class))).thenReturn(Flux.just(todo));
        when(repository.count()).thenReturn(Mono.just((long) page.getTotalElements()));

        webTestClient.put().uri("/todos/{id}",existingId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(objectMapper.writeValueAsString(dto))
                    .exchange()
                    .expectStatus().isOk()
                    .expectBody()
                    .jsonPath("$.content[0].id").isEqualTo(1L)
                    .jsonPath("$.content[0].nome").isEqualTo("nome")
                    .jsonPath("$.content[0].descricao").isEqualTo("descricao")
                    .jsonPath("$.content[0].realizado").isEqualTo(true)
                    .jsonPath("$.content[0].prioridade").isEqualTo(1);
    }

    @Test
    public void updateShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() throws Exception{
        when(repository.findById(nonExistingId)).thenReturn(Mono.empty());
        when(repository.findAllBy(ArgumentMatchers.any(Pageable.class))).thenReturn(Flux.just(todo));
        when(repository.count()).thenReturn(Mono.just((long) page.getTotalElements()));

        webTestClient.put().uri("/todos/{id}",nonExistingId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(objectMapper.writeValueAsString(dto))
                    .exchange()
                    .expectStatus().isNotFound();
    }

    @Test
    public void deleteShouldReturnMonoOfVoidWhenIdExists() throws Exception{
        when(repository.findById(existingId)).thenReturn(Mono.just(todo));
        when(repository.delete(todo)).thenReturn(Mono.empty());
        when(repository.findAllBy(ArgumentMatchers.any(Pageable.class))).thenReturn(Flux.empty());
        when(repository.count()).thenReturn(Mono.just(0L));

        webTestClient.delete().uri("/todos/{id}",existingId)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus().isOk()
                    .expectBody()
                    .jsonPath("$.content").isEmpty();
    }

    @Test
    public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() throws Exception{
        when(repository.findById(nonExistingId)).thenReturn(Mono.empty());
        when(repository.findAllBy(ArgumentMatchers.any(Pageable.class))).thenReturn(Flux.just(todo));
        when(repository.count()).thenReturn(Mono.just((long) page.getTotalElements()));

        webTestClient.delete().uri("/todos/{id}",nonExistingId)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus().isNotFound();
    }

    

}
