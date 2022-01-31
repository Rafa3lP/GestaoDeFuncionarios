/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufes.gestaodefuncionarios.presenter;

import br.ufes.gestaodefuncionarios.dao.FuncionarioDAO;
import br.ufes.gestaodefuncionarios.logger.IMetodoLog;
import br.ufes.gestaodefuncionarios.logger.Log;
import br.ufes.gestaodefuncionarios.model.Funcionario;
import br.ufes.gestaodefuncionarios.view.ManterFuncionarioView;
import javax.swing.JOptionPane;

/**
 *
 * @author Rafael
 */
public class VisualizarFuncionarioPresenter {
    private ManterFuncionarioView view;
    private PrincipalPresenter principalPresenter;
    private Funcionario funcionario;
    private IMetodoLog metodoLog;

    public VisualizarFuncionarioPresenter(int idFuncionario, PrincipalPresenter principalPresenter, IMetodoLog metodoLog) {
        this.metodoLog = metodoLog;
        FuncionarioDAO fDao = new FuncionarioDAO();
        this.principalPresenter = principalPresenter;
        this.view = new ManterFuncionarioView();
        this.view.setTitle("Visualizar Funcionário");
        this.view.getBtnSalvar().setEnabled(false);
        try {
            
            this.funcionario = fDao.getFuncionarioById(idFuncionario);

            putFuncionario(this.funcionario);
            
            habilitaCampos(false);
            
            this.view.getBtnFechar().addActionListener((e) -> {
                fechar();
            });
            this.view.getBtnEditar().addActionListener((e) -> {
                editar();
            });
            this.view.getBtnExcluir().addActionListener((e) -> {
                excluir();
            });
            this.view.getChkFuncionarioMes().addActionListener((e) -> {
                this.view.getChkFuncionarioMes().setSelected(funcionario.isFuncionarioMes());
            });

            principalPresenter.addToDesktopPane(view);
            this.view.setVisible(true);
            
        } catch(RuntimeException ex) {
            JOptionPane.showMessageDialog(view, ex.getMessage(), "erro", JOptionPane.ERROR_MESSAGE);
            this.metodoLog.escreveLog(new Log(IMetodoLog.LOG_ERROR, "Falha ao realizar a operação"));
            fechar();
        }

    }
    
    private void fechar() {
        this.view.dispose();
    }
    
    private void excluir() {
        FuncionarioDAO dao = new FuncionarioDAO();
        int confirmacao = JOptionPane.showConfirmDialog(
                view, 
                "Confirma exclusão?", 
                "Confirmar", 
                JOptionPane.YES_NO_OPTION, 
                JOptionPane.WARNING_MESSAGE
        );
        if(confirmacao == JOptionPane.YES_OPTION) {
            try {
                dao.deletar(this.funcionario);
                this.metodoLog.escreveLog(new Log(IMetodoLog.LOG_INFORMATION, "Funcionario " + this.funcionario.getNome() + " removido"));
                this.principalPresenter.atualizaNumFuncionarios();
                fechar();
            } catch(RuntimeException ex) {
                JOptionPane.showMessageDialog(view, ex.getMessage(), "erro", JOptionPane.ERROR_MESSAGE);
                this.metodoLog.escreveLog(new Log(IMetodoLog.LOG_ERROR, "Falha ao realizar a operação"));
            }
            
        }
    }
    
    private void editar() {
        this.view.setTitle("Editar Funcionário");
        this.view.getBtnExcluir().setEnabled(false);
        this.view.getBtnSalvar().setEnabled(true);
        habilitaCampos(true);
    }
    
    private void habilitaCampos(Boolean flag) {
        this.view.getTxtNome().setEditable(flag);
        this.view.getTxtFaltas().setEditable(flag);
        this.view.getTxtIdade().setEditable(flag);
        this.view.getTxtSalario().setEditable(flag);
        this.view.getCbBonus().setEnabled(flag);
        this.view.getCbCargo().setEnabled(flag);
        this.view.getDtAdmissao().setEnabled(flag);
        this.view.getDtAdmissao().setEnabled(flag);
    }
    
    private void putFuncionario(Funcionario funcionario) {
        this.view.getTxtNome().setText(funcionario.getNome());
        this.view.getTxtFaltas().setText(Integer.toString(funcionario.getFaltas()));
        this.view.getTxtIdade().setText(Integer.toString(funcionario.getIdade()));
        this.view.getTxtSalario().setText(Double.toString(funcionario.getSalarioBase()));
        this.view.getCbBonus().setSelectedIndex(funcionario.getTipoBonus());
        this.view.getCbCargo().setSelectedItem(funcionario.getCargo());
        this.view.getChkFuncionarioMes().setSelected(funcionario.isFuncionarioMes());
        this.view.getDtAdmissao().setDate(funcionario.getDtAdmissao());
    }
    
}
