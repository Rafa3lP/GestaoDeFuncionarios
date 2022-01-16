/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufes.gestaodefuncionarios.presenter;

import br.ufes.gestaodefuncionarios.logger.IMetodoLog;
import br.ufes.gestaodefuncionarios.view.CalcularSalarioView;
import br.ufes.gestaodefuncionarios.view.PrincipalView;

/**
 *
 * @author Rafael
 */
public class CalcularSalarioPresenter {
    private CalcularSalarioView view;
    private PrincipalView principalView;
    private IMetodoLog metodoLog;

    public CalcularSalarioPresenter(PrincipalView principalView, IMetodoLog metodoLog) {
        this.metodoLog = metodoLog;
        this.principalView = principalView;
        this.view = new CalcularSalarioView();
        this.view.setTitle("Calcular Salário");
        
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
