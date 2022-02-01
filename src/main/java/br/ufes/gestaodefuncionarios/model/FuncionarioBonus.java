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
public class FuncionarioBonus {
    private String tipoBonus;
    private double valorBonus;
    private String cargoFuncionario;
    private Date dataCalculo;

    public FuncionarioBonus(String tipoBonus, double valorBonus, String cargoFuncionario, Date dataCalculo) {
        this.tipoBonus = tipoBonus;
        this.valorBonus = valorBonus;
        this.cargoFuncionario = cargoFuncionario;
        this.dataCalculo = dataCalculo;
    }

    public String getTipoBonus() {
        return tipoBonus;
    }

    public void setTipoBonus(String tipoBonus) {
        this.tipoBonus = tipoBonus;
    }

    public double getValorBonus() {
        return valorBonus;
    }

    public void setValorBonus(double valorBonus) {
        this.valorBonus = valorBonus;
    }

    public String getCargoFuncionario() {
        return cargoFuncionario;
    }

    public void setCargoFuncionario(String cargoFuncionario) {
        this.cargoFuncionario = cargoFuncionario;
    }

    public Date getDataCalculo() {
        return dataCalculo;
    }

    public void setDataCalculo(Date dataCalculo) {
        this.dataCalculo = dataCalculo;
    }
    
}
