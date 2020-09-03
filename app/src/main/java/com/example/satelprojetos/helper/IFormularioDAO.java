package com.example.satelprojetos.helper;

import com.example.satelprojetos.model.Formulario;

import java.util.List;

public interface IFormularioDAO{
    boolean salvar(Formulario formulario);
    boolean atualizar(Formulario formulario);
    boolean deletar(Formulario formulario);
    List<Formulario> listar();


}
