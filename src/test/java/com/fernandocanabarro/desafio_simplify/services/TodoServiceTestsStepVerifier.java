package com.fernandocanabarro.desafio_simplify.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.fernandocanabarro.desafio_simplify.dtos.TodoDTO;
import com.fernandocanabarro.desafio_simplify.entities.Todo;
import com.fernandocanabarro.desafio_simplify.factories.TodoFactory;
import com.fernandocanabarro.desafio_simplify.repositories.TodoRepository;
import com.fernandocanabarro.desafio_simplify.services.exceptions.ResourceNotFoundException;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
public class TodoServiceTestsStepVerifier {

    @InjectMocks
    private TodoService service;

    @Mock
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
    public void findByIdShouldReturnMonoOfTodoDTOWhenIdExists(){
        when(repository.findById(existingId)).thenReturn(Mono.just(todo));

        service.findById(existingId)
            .as(StepVerifier::create)
             .thenConsumeWhile(
                        result -> {
                            assertThat(result.getId()).isEqualTo(existingId);
                            assertThat(result.getNome()).isEqualTo("nome");
                            assertThat(result.getDescricao()).isEqualTo("descricao");
                            assertThat(result.getPrioridade()).isEqualTo(1);
                            assertThat(result.getRealizado()).isEqualTo(true);
                            return true;
                        })
                .verifyComplete();
    }

    @Test
    public void findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist(){
        when(repository.findById(nonExistingId)).thenReturn(Mono.empty());

        service.findById(nonExistingId)
            .as(StepVerifier::create)
            .expectError(ResourceNotFoundException.class)
            .verify();
    }

    @Test
    public void findAllShouldReturnMonoOfPageOfTodoDTO(){
        when(repository.findAllBy(ArgumentMatchers.any(Pageable.class))).thenReturn(Flux.just(todo));
        when(repository.count()).thenReturn(Mono.just((long) page.getTotalElements()));

        service.findAll("0")
            .as(StepVerifier::create)
             .thenConsumeWhile(
                        result -> {
                            assertThat(result.getContent().get(0).getId()).isEqualTo(existingId);
                            assertThat(result.getContent().get(0).getNome()).isEqualTo("nome");
                            assertThat(result.getContent().get(0).getDescricao()).isEqualTo("descricao");
                            assertThat(result.getContent().get(0).getPrioridade()).isEqualTo(1);
                            assertThat(result.getContent().get(0).getRealizado()).isEqualTo(true);
                            return true;
                        })
                .verifyComplete();
    }

    @Test
    public void insertShouldReturnMonoOfTodoDTO(){
        when(repository.save(any(Todo.class))).thenReturn(Mono.just(todo));
        when(repository.findAllBy(ArgumentMatchers.any(Pageable.class))).thenReturn(Flux.just(todo));
        when(repository.count()).thenReturn(Mono.just((long) page.getTotalElements()));

        service.insert(Mono.just(dto))
            .as(StepVerifier::create)
             .thenConsumeWhile(
                        result -> {
                            assertThat(result.getContent().get(0).getId()).isEqualTo(existingId);
                            assertThat(result.getContent().get(0).getNome()).isEqualTo("nome");
                            assertThat(result.getContent().get(0).getDescricao()).isEqualTo("descricao");
                            assertThat(result.getContent().get(0).getPrioridade()).isEqualTo(1);
                            assertThat(result.getContent().get(0).getRealizado()).isEqualTo(true);
                            return true;
                        })
                .verifyComplete();
    }

    @Test
    public void updateShouldReturnMonoOfTodoDTOWhenIdExists(){
        when(repository.findById(existingId)).thenReturn(Mono.just(todo));
        when(repository.save(any(Todo.class))).thenReturn(Mono.just(todo));
        when(repository.findAllBy(ArgumentMatchers.any(Pageable.class))).thenReturn(Flux.just(todo));
        when(repository.count()).thenReturn(Mono.just((long) page.getTotalElements()));

        service.update(existingId,Mono.just(dto))
            .as(StepVerifier::create)
             .thenConsumeWhile(
                        result -> {
                            assertThat(result.getContent().get(0).getId()).isEqualTo(existingId);
                            assertThat(result.getContent().get(0).getNome()).isEqualTo("nome");
                            assertThat(result.getContent().get(0).getDescricao()).isEqualTo("descricao");
                            assertThat(result.getContent().get(0).getPrioridade()).isEqualTo(1);
                            assertThat(result.getContent().get(0).getRealizado()).isEqualTo(true);
                            return true;
                        })
                .verifyComplete();
    }

    @Test
    public void updateShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist(){
        when(repository.findById(nonExistingId)).thenReturn(Mono.empty());
        when(repository.findAllBy(ArgumentMatchers.any(Pageable.class))).thenReturn(Flux.empty());
        when(repository.count()).thenReturn(Mono.just(0L));

        service.update(nonExistingId,Mono.just(dto))
            .as(StepVerifier::create)
            .expectError(ResourceNotFoundException.class)
            .verify();
    }

    @Test
    public void deleteShouldReturnEmptyMono(){
        when(repository.findById(existingId)).thenReturn(Mono.just(todo));
        when(repository.delete(any(Todo.class))).thenReturn(Mono.empty());
        when(repository.findAllBy(ArgumentMatchers.any(Pageable.class))).thenReturn(Flux.empty());
        when(repository.count()).thenReturn(Mono.just(0L));

        service.delete(existingId)
            .as(StepVerifier::create)
            .thenConsumeWhile(
                        result -> {
                            assertThat(result.getContent()).isEmpty();
                            return true;
                        })
                .verifyComplete();
    }

    @Test
    public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist(){
        when(repository.findById(nonExistingId)).thenReturn(Mono.empty());
        when(repository.findAllBy(ArgumentMatchers.any(Pageable.class))).thenReturn(Flux.empty());
        when(repository.count()).thenReturn(Mono.just(0L));

        service.delete(nonExistingId)
            .as(StepVerifier::create)
            .expectError(ResourceNotFoundException.class)
            .verify();
    }

}
