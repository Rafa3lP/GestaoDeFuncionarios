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
public interface IMetodoCalculoBonus {
    void calcular(Funcionario funcionario, Date dataCalculo);
}
