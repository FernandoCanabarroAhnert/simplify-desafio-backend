package com.fernandocanabarro.desafio_simplify.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fernandocanabarro.desafio_simplify.dtos.TodoDTO;
import com.fernandocanabarro.desafio_simplify.services.TodoService;

import jakarta.validation.Valid;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/todos")
public class TodoController {

    @Autowired
    private TodoService service;

    @GetMapping
    public Mono<ResponseEntity<Page<TodoDTO>>> findAll(@RequestParam(name = "page", defaultValue = "0") String page){
        return service.findAll(page).map(ResponseEntity::ok);
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<TodoDTO>> findById(@PathVariable Long id){
        return service.findById(id).map(ResponseEntity::ok);
    }

    @PostMapping
    public Mono<ResponseEntity<Page<TodoDTO>>> insert(@RequestBody @Valid Mono<TodoDTO> mono){
        return service.insert(mono).map(obj -> ResponseEntity.status(HttpStatus.CREATED).body(obj));
    } 

    @PutMapping("/{id}")
    public Mono<ResponseEntity<Page<TodoDTO>>> update(@PathVariable Long id, @RequestBody @Valid Mono<TodoDTO> mono){
        return service.update(id,mono).map(ResponseEntity::ok);
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Page<TodoDTO>>> delete(@PathVariable Long id){
        return service.delete(id).map(ResponseEntity::ok);
    }
}
