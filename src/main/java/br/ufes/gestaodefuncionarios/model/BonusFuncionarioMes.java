/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
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
 * 
 * Se o funcionario for o funcionario do mês
 * Receberá 5% de bônus
 */
public class BonusFuncionarioMes implements IMetodoCalculoBonus {
    @Override
    public void calcular(Funcionario funcionario, Date dataCalculo) {
        if(funcionario.isFuncionarioMes()) {
            double valorBonus = funcionario.getSalarioBase() * 0.05;
            Bonus bonus = new Bonus("Funcionário do mês", dataCalculo, valorBonus);
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
}
