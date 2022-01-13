/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufes.gestaodefuncionarios.presenter;

import br.ufes.gestaodefuncionarios.dao.FuncionarioDAO;
import br.ufes.gestaodefuncionarios.model.Funcionario;
import br.ufes.gestaodefuncionarios.view.ManterFuncionarioView;
import java.util.Date;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;

/**
 *
 * @author Rafael
 */
public class CriarFuncionarioPresenter {
    private FuncionarioDAO dao;
    private ManterFuncionarioView view;

    public CriarFuncionarioPresenter(JDesktopPane desktopPane) {
        this.view = new ManterFuncionarioView();
        this.view.setTitle("Novo Funcionário");
        this.view.getBtnExcluir().setVisible(false);
        this.view.getBtnEditar().setVisible(false);
        this.view.getBtnFechar().addActionListener((e) -> {
            fechar();
        });
        this.view.getBtnSalvar().addActionListener((e) -> {
            salvar();
        });
        desktopPane.add(view);
        this.view.setVisible(true);
    }
    private void fechar() {
        this.view.dispose();
    }
    private void salvar() {
        try {
            String nome = view.getTxtNome().getText();
            int idade = Integer.parseInt(view.getTxtIdade().getText());
            double salario = Double.parseDouble(view.getTxtSalario().getText());
            String cargo = (String) view.getCbCargo().getSelectedItem();
            boolean funcionarioMes = view.getChkFuncionarioMes().isSelected();

            Date dtAdmissao = this.view.getDtAdmissao().getDate();

            Funcionario novoFuncionario = new Funcionario(nome, idade, salario, cargo, dtAdmissao, funcionarioMes);
        
            dao = new FuncionarioDAO();
            dao.criar(novoFuncionario);
            limparCampos();
            JOptionPane.showMessageDialog(view, "Funcionario Adicionado!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            dao.getFuncionarios();
        } catch(RuntimeException ex) {
            JOptionPane.showMessageDialog(view, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
        
    }
    
    private void limparCampos() {
        view.getTxtNome().setText("");
        view.getTxtIdade().setText("");
        view.getTxtFaltas().setText("");
        view.getTxtSalario().setText("");
        view.getCbCargo().setSelectedIndex(0);
        view.getCbBonus().setSelectedIndex(0);
        view.getChkFuncionarioMes().setSelected(false);
        view.getDtAdmissao().setDate(new Date());
    }
}
