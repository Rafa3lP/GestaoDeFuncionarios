/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufes.gestaodefuncionarios.presenter;

import br.ufes.gestaodefuncionarios.dao.FuncionarioDAO;
import br.ufes.gestaodefuncionarios.model.Funcionario;
import br.ufes.gestaodefuncionarios.view.ManterFuncionarioView;
import br.ufes.gestaodefuncionarios.view.PrincipalView;

/**
 *
 * @author Rafael
 */
public class VisualizarFuncionarioPresenter {
    private ManterFuncionarioView view;
    private PrincipalView principalView;
    private int idFuncionario;

    public VisualizarFuncionarioPresenter(int idFuncionario, PrincipalView principalView) {
        this.idFuncionario = idFuncionario;
        this.principalView = principalView;
        this.view = new ManterFuncionarioView();
        
        this.view.getBtnSalvar().setEnabled(false);
        
        getFuncionario();
        
        desabilitaCampos();
        
        this.view.getBtnFechar().addActionListener((e) -> {
            fechar();
        });
        this.view.getBtnEditar().addActionListener((e) -> {
            editar();
        });
        this.view.getBtnExcluir().addActionListener((e) -> {
            excluir();
        });
        principalView.getDesktopPane().add(view);
        this.view.setVisible(true);
    }
    
    private void fechar() {
        this.view.dispose();
    }
    
    private void excluir() {
        
    }
    
    private void editar() {
        
    }
    
    private void desabilitaCampos() {
        this.view.getTxtNome().setEditable(false);
        this.view.getTxtFaltas().setEditable(false);
        this.view.getTxtIdade().setEditable(false);
        this.view.getTxtSalario().setEditable(false);
        this.view.getCbBonus().setEnabled(false);
        this.view.getCbCargo().setEnabled(false);
        this.view.getChkFuncionarioMes().setEnabled(false);
        this.view.getDtAdmissao().setEnabled(false);
        this.view.getDtAdmissao().setEnabled(false);
    }
    
    private void getFuncionario() {
        FuncionarioDAO fDao = new FuncionarioDAO();
        Funcionario funcionario = fDao.getFuncionarioById(this.idFuncionario);
        this.view.getTxtNome().setText(funcionario.getNome());
        this.view.getTxtFaltas().setText(Integer.toString(funcionario.getFaltas()));
        this.view.getTxtIdade().setText(Integer.toString(funcionario.getIdade()));
        this.view.getTxtSalario().setText(Double.toString(funcionario.getSalario()));
        this.view.getCbBonus().setSelectedIndex(0);
        this.view.getCbCargo().setSelectedIndex(0);
        this.view.getChkFuncionarioMes().setEnabled(funcionario.isFuncionarioMes());
        this.view.getDtAdmissao().setDate(funcionario.getDtAdmissao());
    }
    
}
