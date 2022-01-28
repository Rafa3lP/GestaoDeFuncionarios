/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufes.gestaodefuncionarios.presenter;

import br.ufes.gestaodefuncionarios.model.Bonus;
import br.ufes.gestaodefuncionarios.model.Funcionario;
import br.ufes.gestaodefuncionarios.model.IMetodoCalculoBonus;
import java.util.Date;

/**
 *
 * @author Rafael
 * nº faltas         %bonus
 * 0                  10
 * 1-3                5
 * 4-5                1
 * >=6                1
 */
public class BonusAssiduidade implements IMetodoCalculoBonus {

    @Override
    public Bonus calcular(Funcionario funcionario) {
       double porcentagem;
       int faltas = funcionario.getFaltas();
       if(faltas >= 6) {
           porcentagem = 0.01;
       } else {
           if(faltas >= 4) {
                porcentagem = 0.01;
           } else {
               if(faltas >= 1) {
                    porcentagem = 0.05;
               } else {
                    porcentagem = 0.1;
               }
           }
       }
       double valorBonus = funcionario.getSalarioBase() * porcentagem;

       return new Bonus("Assiduidade", new Date(), valorBonus);
       
    }
    
}
