/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufes.gestaodefuncionarios.model;

import br.ufes.gestaodefuncionarios.dao.FuncionarioDAO;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author Rafael
 */
/**
 * Bonus de 15% no valor do salário
 */
public class BonusGeneroso implements IMetodoCalculoBonus {

    @Override
    public void calcular(Funcionario funcionario, Date dataCalculo) {
        if(funcionario.getTipoBonus() == 2) {
            double valorBonus = funcionario.getSalarioBase() * 0.15;
            Bonus bonus = new Bonus("Generoso", dataCalculo, valorBonus);
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
