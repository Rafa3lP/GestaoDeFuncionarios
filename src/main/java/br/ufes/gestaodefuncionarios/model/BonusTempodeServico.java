/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufes.gestaodefuncionarios.model;

import br.ufes.gestaodefuncionarios.dao.FuncionarioDAO;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author Rafael
 * Tempo de serviço (anos)          bonus(%)
 * 0                                    0
 * 1-5                                  2
 * 6-10                                 3
 * 11-15                                8
 * 16-20                                10
 * >=20                                 15
 * OBS: apenas a diferença de anos é considerada, sem contar os meses e dias.
 */
public class BonusTempodeServico implements IMetodoCalculoBonus {

    @Override
    public void calcular(Funcionario funcionario, Date dataCalculo) {
        double porcentagem;
        LocalDateTime ldtAdmissao = Instant.ofEpochMilli(funcionario.getDtAdmissao().getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime ldtCalculo = Instant.ofEpochMilli(dataCalculo.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
        
        long anos = ldtAdmissao.until(ldtCalculo, ChronoUnit.YEARS);
        
        porcentagem = 0;
        if(anos >= 20) {
            porcentagem = 0.15;
        } else {
            if(anos >= 16) {
                porcentagem = 0.1;
            } else {
                if(anos >= 11) {
                    porcentagem = 0.08;
                } else {
                    if(anos >= 6) {
                        porcentagem = 0.03;
                    } else {
                        if(anos >= 1) {
                            porcentagem = 0.02;
                        }
                    }
                }
            }
        }
        
        if(porcentagem > 0) {
            double valorBonus = funcionario.getSalarioBase() * porcentagem;
            Bonus bonus = new Bonus("Tempo de Serviço", dataCalculo, valorBonus);
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
