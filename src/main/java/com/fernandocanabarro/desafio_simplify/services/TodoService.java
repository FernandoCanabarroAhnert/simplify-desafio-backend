package com.fernandocanabarro.desafio_simplify.services;

import java.util.Comparator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fernandocanabarro.desafio_simplify.dtos.TodoDTO;
import com.fernandocanabarro.desafio_simplify.entities.Todo;
import com.fernandocanabarro.desafio_simplify.repositories.TodoRepository;
import com.fernandocanabarro.desafio_simplify.services.exceptions.ResourceNotFoundException;

import reactor.core.publisher.Mono;

@Service
public class TodoService {

    @Autowired
    private TodoRepository repository;

    public Mono<Page<TodoDTO>> findAll(String page){
        Pageable pageable = PageRequest.of(Integer.parseInt(page), 12);

        return repository.findAllBy(pageable)
            .collectList()
            .zipWith(repository.count())
            .map(content -> new PageImpl<>(content.getT1().stream().map(TodoDTO::new)
                .sorted(Comparator.comparing(TodoDTO::getPrioridade).reversed().thenComparing(Comparator.comparing(TodoDTO::getNome))).toList(),
                pageable, content.getT2()));
    }

    public Mono<TodoDTO> findById(Long id){
        return repository.findById(id)
            .switchIfEmpty(Mono.error(new ResourceNotFoundException(id)))
            .map(TodoDTO::new);
    }

    public Mono<Page<TodoDTO>> insert(Mono<TodoDTO> mono){
        return mono.map(this::toEntity).flatMap(repository::save).then(findAll("0"));
    }

    public Mono<Page<TodoDTO>> update(Long id, Mono<TodoDTO> mono){
        return repository.findById(id)
            .switchIfEmpty(Mono.error(new ResourceNotFoundException(id)))
            .flatMap(entity -> mono)
            .map(this::toEntity)
            .doOnNext(x -> x.setId(id))
            .flatMap(repository::save)
            .then(findAll("0"));
    }

    private Todo toEntity(TodoDTO dto){
        Todo entity = new Todo();
        entity.setId(dto.getId());
        entity.setNome(dto.getNome());
        entity.setDescricao(dto.getDescricao());
        entity.setRealizado(dto.getRealizado());
        entity.setPrioridade(dto.getPrioridade());
        return entity;
    }

    public Mono<Page<TodoDTO>> delete(Long id){
        return repository.findById(id)
            .switchIfEmpty(Mono.error(new ResourceNotFoundException(id)))
            .flatMap(x -> repository.delete(x))
            .then(findAll("0"));
    }
}
