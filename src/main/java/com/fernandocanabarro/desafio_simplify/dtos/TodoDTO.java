package com.fernandocanabarro.desafio_simplify.dtos;

import com.fernandocanabarro.desafio_simplify.entities.Todo;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class TodoDTO {

    private Long id;
    @NotBlank(message = "Campo Obrigatório")
    private String nome;
    @NotBlank(message = "Campo Obrigatório")
    private String descricao;
    @NotNull(message = "Campo Obrigatório")
    private Boolean realizado;
    @Min(value = 1, message = "Prioridade deve estar entre 1 e 3")
    @Max(value = 3, message = "Prioridade deve estar entre 1 e 3")
    @NotNull(message = "Campo Obrigatório")
    private Integer prioridade;
    
    public TodoDTO() {
    }

    public TodoDTO(Long id, String nome, String descricao, Boolean realizado, Integer prioridade) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.realizado = realizado;
        this.prioridade = prioridade;
    }

    public TodoDTO(Todo entity){
        id = entity.getId();
        nome = entity.getNome();
        descricao = entity.getDescricao();
        realizado = entity.getRealizado();
        prioridade = entity.getPrioridade();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Boolean getRealizado() {
        return realizado;
    }

    public void setRealizado(Boolean realizado) {
        this.realizado = realizado;
    }

    public Integer getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(Integer prioridade) {
        this.prioridade = prioridade;
    }

}
