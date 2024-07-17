package com.fernandocanabarro.desafio_simplify.repositories;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.fernandocanabarro.desafio_simplify.entities.Todo;

@Repository
public interface TodoRepository extends ReactiveCrudRepository<Todo, Long>{

}
