/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufes.gestaodefuncionarios.presenter;

import br.ufes.gestaodefuncionarios.model.Bonus;
import br.ufes.gestaodefuncionarios.model.Funcionario;
import br.ufes.gestaodefuncionarios.model.IMetodoCalculoBonus;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

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
    public void calcular(Funcionario funcionario) {
        double porcentagem;
        LocalDateTime dataAdmissao = funcionario.getDtAdmissao()
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        LocalDateTime dataAtual = LocalDateTime.now();
        long anos = dataAdmissao.until(dataAtual, ChronoUnit.YEARS);
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
        
        double valorBonus = funcionario.getSalarioBase() * porcentagem;
       
        funcionario.addBonus(
            new Bonus("Tempo de Serviço", new Date(), valorBonus)
        );
        
    }
    
}
