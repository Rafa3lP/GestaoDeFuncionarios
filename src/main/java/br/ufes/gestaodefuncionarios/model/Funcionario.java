/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufes.gestaodefuncionarios.model;

import java.util.ArrayList;
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
    private double salarioBase;
    private String cargo;
    private Date dtAdmissao;
    private boolean funcionarioMes;
    private int faltas;
    private int tipoBonus;

    public void setFuncionarioMes(boolean funcionarioMes) {
        this.funcionarioMes = funcionarioMes;
    }

    public Funcionario(String nome, int idade, double salarioBase, String cargo, Date dtAdmissao, boolean funcionarioMes, int faltas, int tipoBonus) {
        setNome(nome);
        setIdade(idade);
        setSalarioBase(salarioBase);
        setCargo(cargo);
        setDtAdmissao(dtAdmissao);
        setFuncionarioMes(funcionarioMes);
        setFaltas(faltas);
        setTipoBonus(tipoBonus);
    }
    
    public Funcionario(int id, String nome, int idade, double salarioBase, String cargo, Date dtAdmissao, boolean funcionarioMes, int faltas, int tipoBonus) {
        this.id = id;
        setNome(nome);
        setIdade(idade);
        setSalarioBase(salarioBase);
        setCargo(cargo);
        setDtAdmissao(dtAdmissao);
        setFuncionarioMes(funcionarioMes);
        setFaltas(faltas);
        setTipoBonus(tipoBonus);
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

    public double getSalarioBase() {
        return salarioBase;
    }

    public void setSalarioBase(double salarioBase) {
        this.salarioBase = salarioBase;
    }

    public int getTipoBonus() {
        return tipoBonus;
    }

    public void setTipoBonus(int tipoBonus) {
        this.tipoBonus = tipoBonus;
    }
    
    public void calcularSalario(ArrayList<IMetodoCalculoBonus> metodosCalculoBonus) {
        setSalario(0);
        for(IMetodoCalculoBonus mBonus: metodosCalculoBonus) {
            this.salario += mBonus.calcular(this).getValor();
        }
    }
    
}
