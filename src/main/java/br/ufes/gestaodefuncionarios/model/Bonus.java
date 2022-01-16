/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufes.gestaodefuncionarios.model;

import java.util.Date;

/**
 *
 * @author Rafael
 */
public class Bonus {
    private String nome;
    private Date data;
    private double valor;

    public Bonus(String nome, Date data, double valor) {
        this.nome = nome;
        this.data = data;
        this.valor = valor;
    }

    public String getNome() {
        return nome;
    }

    public Date getData() {
        return data;
    }

    public double getValor() {
        return valor;
    }

}
