package com.fernandocanabarro.desafio_simplify.factories;

import com.fernandocanabarro.desafio_simplify.entities.Todo;

public class TodoFactory {

    public static Todo createTodo(){
        return new Todo(1L, "nome", "descricao", true, 1);
    }
}
