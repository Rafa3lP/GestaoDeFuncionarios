/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufes.gestaodefuncionarios.presenter;

import br.ufes.gestaodefuncionarios.view.PrincipalView;

/**
 *
 * @author Rafael
 */
public class PrincipalPresenter {
    
    private PrincipalView view;
    
    public PrincipalPresenter() {
        this.view = new PrincipalView();
        
        this.view.getBtnFechar().addActionListener((e) -> {
            fechar();
        });
        
        this.view.getBtnNovo().addActionListener((e) -> {
            criarFuncionario();
        });
        
        this.view.getBtnBuscar().addActionListener((e) -> {
            buscarFuncionario();
        });
        
        this.view.setVisible(true);
    }
    
    private void fechar() {
        System.exit(0);
    }
    
    private void criarFuncionario() {
        new CriarFuncionarioPresenter(this.view.getDesktopPane());
    }
    
    private void buscarFuncionario() {
        new BuscarFuncionarioPresenter(this.view);
    }
    
}
