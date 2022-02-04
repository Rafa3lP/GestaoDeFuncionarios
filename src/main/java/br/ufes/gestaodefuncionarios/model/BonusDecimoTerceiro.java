/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.ufes.gestaodefuncionarios.model;

import br.ufes.gestaodefuncionarios.dao.FuncionarioDAO;
import br.ufes.gestaodefuncionarios.logger.IMetodoLog;
import br.ufes.gestaodefuncionarios.logger.Log;
import br.ufes.gestaodefuncionarios.presenter.App;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author Rafael
 * 
 * No Bônus decimo terceiro, se o Mês for Dezembro
 * o funcionario receberá 50% do valor do salario base
 */
public class BonusDecimoTerceiro implements IMetodoCalculoBonus {
    @Override
    public void calcular(Funcionario funcionario, Date dataCalculo) {
        LocalDateTime ldtCalculo = Instant.ofEpochMilli(dataCalculo.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
        
        int mes = ldtCalculo.getMonthValue();
        
        if(mes == 12) {
            double valorBonus = funcionario.getSalarioBase() * 0.5;
            Bonus bonus = new Bonus("Décimo Terceiro", dataCalculo, valorBonus);
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
