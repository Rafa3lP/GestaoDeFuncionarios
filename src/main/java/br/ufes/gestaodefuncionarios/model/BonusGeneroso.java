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
/**
 * Bonus de 15% no valor do salário
 */
public class BonusGeneroso implements IMetodoCalculoBonus {

    @Override
    public Bonus calcular(Funcionario funcionario) {
        double valorBonus = funcionario.getSalario() * 0.15;
        return new Bonus("Generoso", new Date(), valorBonus);
    }
    
}
