/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufes.gestaodefuncionarios.model;

import br.ufes.gestaodefuncionarios.dao.FuncionarioDAO;
import java.util.Date;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Rafael
 */
/**
 * Bonus de 5% no valor do salário
 */
public class BonusNormal implements IMetodoCalculoBonus {

    @Override
    public void calcular(Funcionario funcionario, Date dataCalculo) {
        if(funcionario.getTipoBonus() == 1) {
            double valorBonus = funcionario.getSalarioBase() * 0.05;
            Bonus bonus = new Bonus("Normal", dataCalculo, valorBonus);
            funcionario.addBonus(bonus);
            try {
                FuncionarioDAO fDao = new FuncionarioDAO();
                fDao.insereFuncionarioBonus(funcionario, bonus);
            } catch(RuntimeException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
            
        }
        
    }
    
}
