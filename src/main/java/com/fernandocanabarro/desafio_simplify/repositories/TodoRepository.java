package com.fernandocanabarro.desafio_simplify.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.fernandocanabarro.desafio_simplify.entities.Todo;

import reactor.core.publisher.Flux;

@Repository
public interface TodoRepository extends ReactiveCrudRepository<Todo, Long>{

    Flux<Todo> findAllBy(Pageable pageable);
}
