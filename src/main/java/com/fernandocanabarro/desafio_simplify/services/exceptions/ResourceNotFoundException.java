package com.fernandocanabarro.desafio_simplify.services.exceptions;

public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(Long id){
        super("Recurso Não Encontrado! Id = " + id);
    }
}
