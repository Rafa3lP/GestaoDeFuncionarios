/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.ufes.gestaodefuncionarios.presenter;

import br.ufes.gestaodefuncionarios.model.Funcionario;
import br.ufes.gestaodefuncionarios.view.VerBonusView;
import javax.swing.JDialog;
import javax.swing.JTable;

/**
 *
 * @author Rafael
 */
public class VerBonusPresenter {
    private VerBonusView view;
    private Funcionario funcionario;
    private JTable tabela;

    public VerBonusPresenter(boolean modal, Funcionario funcionario) {
        this.funcionario = funcionario;
        this.view = new VerBonusView(new java.awt.Frame(), modal);
        this.view.setTitle("Ver Bônus");
        this.view.getLblNome().setText(this.funcionario.getNome());
        
        this.tabela = view.getTabela();
        
        this.view.getBtnOk().addActionListener((e) -> {
            fechar();
        });
        
        this.view.setVisible(true);
    }
    
    private void fechar() {
        this.view.dispose();
    }
}
