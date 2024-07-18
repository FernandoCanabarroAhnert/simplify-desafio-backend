package com.fernandocanabarro.desafio_simplify.config;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.fernandocanabarro.desafio_simplify.dtos.TodoDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import reactor.core.publisher.Mono;

public interface TodoControllerOpenAPI {

    @Operation(
    description = "Consultar Todas as Tarefas Paginadas",
    summary = "Endpoint responsável por Consultar Todas as Tarefas Paginadas",
    responses = {
         @ApiResponse(description = "Consulta Realizada", responseCode = "200")
   		}
	)
    Mono<ResponseEntity<Page<TodoDTO>>> findAll(@RequestParam(name = "page", defaultValue = "0") String page);

    @Operation(
    description = "Consultar uma Tarefa por Id",
    summary = "Endpoint responsável por Consultar uma Tarefa por Id",
    responses = {
        @ApiResponse(description = "Consulta Realizada", responseCode = "200"),
         @ApiResponse(description = "O Id da Tarefa não Existe", responseCode = "404")
   		}
	)
    Mono<ResponseEntity<TodoDTO>> findById(@PathVariable Long id);

    @Operation(
        description = "Criar uma Tarefa",
        summary = "Endpoint Responsável por receber a Requisição da Criação de uma Tarefa",
        responses = {
             @ApiResponse(description = "Tarefa Criada", responseCode = "201"),
             @ApiResponse(description = "O Corpo da Requisição é Inválido", responseCode = "422")
               }
        )
    Mono<ResponseEntity<Page<TodoDTO>>> insert(@RequestBody @Valid Mono<TodoDTO> mono);

    @Operation(
    description = "Atualizar uma Tarefa",
    summary = "Endpoint responsável por receber a requisição da Atualização de uma Tarefa",
    responses = {
         @ApiResponse(description = "Tarefa Atualizada", responseCode = "200"),
         @ApiResponse(description = "O Id da Tarefa não Existe", responseCode = "404"),
         @ApiResponse(description = "O Corpo da Requisição é Inválido", responseCode = "422")
   		}
	)
    Mono<ResponseEntity<Page<TodoDTO>>> update(@PathVariable Long id, @RequestBody @Valid Mono<TodoDTO> mono);

    @Operation(
    description = "Deletar uma Tarefa",
    summary = "Endpoint responsável por receber o Id da Tarefa a ser Deletada",
    responses = {
         @ApiResponse(description = "Tarefa Deletada", responseCode = "200"),
         @ApiResponse(description = "O Id da Tarefa não Existe", responseCode = "404")
   		}
	)
    Mono<ResponseEntity<Page<TodoDTO>>> delete(@PathVariable Long id);
    
}
