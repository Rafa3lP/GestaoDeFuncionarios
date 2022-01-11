/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufes.gestaodefuncionarios.presenter;

import br.ufes.gestaodefuncionarios.view.BuscarFuncionarioView;
import br.ufes.gestaodefuncionarios.view.PrincipalView;

/**
 *
 * @author Rafael
 */
public class BuscarFuncionarioPresenter {
    private PrincipalView principalView;
    private BuscarFuncionarioView view;

    public BuscarFuncionarioPresenter(PrincipalView principalView) {
        this.principalView = principalView;
        this.view = new BuscarFuncionarioView();
        this.view.getBtnFechar().addActionListener((e) -> {
            fechar();
        });
        this.principalView.getDesktopPane().add(this.view);
        this.view.setVisible(true);
    }
    
    private void fechar() {
        this.view.dispose();
    }
    
}
