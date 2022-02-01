/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.ufes.gestaodefuncionarios.model;

import java.util.Date;

/**
 *
 * @author Rafael
 */
public class FuncionarioSalario {
    private int funcionarioId;
    private String funcionario;
    private Date dataCalculo;
    private double salarioBase;
    private double bonus;
    private double salario;

    public FuncionarioSalario(int funcionarioId, String funcionario, Date dataCalculo, double salarioBase, double bonus, double salario) {
        this.funcionarioId = funcionarioId;
        this.funcionario = funcionario;
        this.dataCalculo = dataCalculo;
        this.salarioBase = salarioBase;
        this.bonus = bonus;
        this.salario = salario;
    }

    public int getFuncionarioId() {
        return funcionarioId;
    }

    public void setFuncionarioId(int funcionarioId) {
        this.funcionarioId = funcionarioId;
    }

    public String getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(String funcionario) {
        this.funcionario = funcionario;
    }

    public Date getDataCalculo() {
        return dataCalculo;
    }

    public void setDataCalculo(Date dataCalculo) {
        this.dataCalculo = dataCalculo;
    }

    public double getSalarioBase() {
        return salarioBase;
    }

    public void setSalarioBase(double salarioBase) {
        this.salarioBase = salarioBase;
    }

    public double getBonus() {
        return bonus;
    }

    public void setBonus(double bonus) {
        this.bonus = bonus;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }
    
}
