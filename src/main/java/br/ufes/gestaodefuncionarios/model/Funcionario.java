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
public class Funcionario {
    
    private int id;
    private String nome;
    private int idade;
    private double salario;
    private String cargo;
    private Date dtAdmissao;
    private boolean funcionarioMes;
    private int faltas;

    public void setFuncionarioMes(boolean funcionarioMes) {
        this.funcionarioMes = funcionarioMes;
    }

    public Funcionario(String nome, int idade, double salario, String cargo, Date dtAdmissao, boolean funcionarioMes) {
        this.nome = nome;
        this.idade = idade;
        this.salario = salario;
        this.cargo = cargo;
        this.dtAdmissao = dtAdmissao;
        this.funcionarioMes = funcionarioMes;
    }
    
    public Funcionario(int id, String nome, int idade, double salario, String cargo, Date dtAdmissao, boolean funcionarioMes) {
        this.id = id;
        this.nome = nome;
        this.idade = idade;
        this.salario = salario;
        this.cargo = cargo;
        this.dtAdmissao = dtAdmissao;
        this.funcionarioMes = funcionarioMes;
    }
    
    @Override
    public String toString() {
        return this.nome + " - " + this.dtAdmissao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public double getSalario() {
        return salario;
    }

    public Date getDtAdmissao() {
        return dtAdmissao;
    }

    public int getId() {
        return id;
    }

    public void setDtAdmissao(Date dtAdmissao) {
        this.dtAdmissao = dtAdmissao;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }
    
    public boolean isFuncionarioMes() {
        return funcionarioMes;
    }

    public int getFaltas() {
        return faltas;
    }

    public void setFaltas(int faltas) {
        this.faltas = faltas;
    }
    
}
