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
 * Bonus de 5% no valor do salário
 */
public class BonusNormal implements IMetodoCalculoBonus {

    @Override
    public Bonus calcular(Funcionario funcionario) {
        double valorBonus = funcionario.getSalarioBase() * 0.05;
        return new Bonus("Normal", new Date(), valorBonus);
    }
    
}
