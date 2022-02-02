/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufes.gestaodefuncionarios.model;

import br.ufes.gestaodefuncionarios.dao.FuncionarioDAO;
import br.ufes.gestaodefuncionarios.logger.IMetodoLog;
import br.ufes.gestaodefuncionarios.logger.Log;
import br.ufes.gestaodefuncionarios.presenter.App;
import java.util.Date;
import javax.swing.JOptionPane;

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
    public void calcular(Funcionario funcionario, Date dataCalculo) {
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
        
        Bonus bonus = new Bonus("Assiduidade", dataCalculo, valorBonus);
        funcionario.addBonus(bonus);
        try {
            FuncionarioDAO fDao = new FuncionarioDAO();
            fDao.insereFuncionarioBonus(funcionario, bonus);
        } catch(RuntimeException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            App.AppLogger.escreveLog(new Log(IMetodoLog.LOG_ERROR, "Falha ao realizar operação - " + ex.getMessage()));
        }
       
    }
    
}
