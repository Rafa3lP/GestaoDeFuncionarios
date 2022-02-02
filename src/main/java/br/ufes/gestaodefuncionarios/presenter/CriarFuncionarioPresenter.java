/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufes.gestaodefuncionarios.presenter;

import br.ufes.gestaodefuncionarios.dao.FuncionarioDAO;
import br.ufes.gestaodefuncionarios.logger.IMetodoLog;
import br.ufes.gestaodefuncionarios.logger.Log;
import br.ufes.gestaodefuncionarios.model.BonusGeneroso;
import br.ufes.gestaodefuncionarios.model.BonusNormal;
import br.ufes.gestaodefuncionarios.model.Funcionario;
import br.ufes.gestaodefuncionarios.view.ManterFuncionarioView;
import java.io.IOException;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Rafael
 */
public class CriarFuncionarioPresenter {
    private FuncionarioDAO dao;
    private ManterFuncionarioView view;
    private PrincipalPresenter principalPresenter;

    public CriarFuncionarioPresenter(PrincipalPresenter principalPresenter) {
        this.view = new ManterFuncionarioView();
        this.principalPresenter = principalPresenter;
        this.view.setTitle("Novo Funcionário");
        this.view.getBtnExcluir().setVisible(false);
        this.view.getBtnEditar().setVisible(false);
   
        this.view.getBtnFechar().addActionListener((e) -> {
            fechar();
        });
        this.view.getBtnSalvar().addActionListener((e) -> {
            salvar();
        });
        this.principalPresenter.addToDesktopPane(view);
        this.view.setVisible(true);
    }
    private void fechar() {
        this.view.dispose();
    }
    private void salvar() {
        try {
            
            Funcionario novoFuncionario = getFuncionario();
   
            dao = new FuncionarioDAO();
            dao.criar(novoFuncionario);
            
            JOptionPane.showMessageDialog(
                    view, 
                    "Funcionario Adicionado!", 
                    "Sucesso", 
                    JOptionPane.INFORMATION_MESSAGE
            );
            
            App.AppLogger.escreveLog(new Log(IMetodoLog.LOG_INFORMATION, "Funcionario " + novoFuncionario.getNome() + " adicionado"));
            
            limparCampos();
            
            this.principalPresenter.atualizaNumFuncionarios();
            
        } catch(RuntimeException ex) {
            JOptionPane.showMessageDialog(view, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            App.AppLogger.escreveLog(new Log(IMetodoLog.LOG_ERROR, "Falha ao realizar a operação"));
        } catch(IOException ex) {
            JOptionPane.showMessageDialog(view, ex.getMessage(), "Aviso", JOptionPane.WARNING_MESSAGE);
        }
        
    }
    
    private void limparCampos() {
        view.getTxtNome().setText("");
        view.getTxtIdade().setText("0");
        view.getTxtFaltas().setText("0");
        view.getTxtSalario().setText("0");
        view.getCbCargo().setSelectedIndex(0);
        view.getCbBonus().setSelectedIndex(0);
        view.getChkFuncionarioMes().setSelected(false);
        view.getDtAdmissao().setDate(new Date());
    }
    
    private Funcionario getFuncionario() throws IOException {
        
        String nome = view.getTxtNome().getText();
        int faltas = Integer.parseInt(view.getTxtFaltas().getText());
        int idade = Integer.parseInt(view.getTxtIdade().getText());
        double salario = Double.parseDouble(view.getTxtSalario().getText());
        String cargo = (String) view.getCbCargo().getSelectedItem();
        boolean funcionarioMes = view.getChkFuncionarioMes().isSelected();
        Date dtAdmissao = this.view.getDtAdmissao().getDate();

        if(nome.trim().isEmpty()) {
            throw new IOException("Preencha corretamente o campo nome");
        } else if (idade == 0) {
            throw new IOException("Preencha corretamente o campo idade");
        } else if (salario == 0) {
            throw new IOException("Preencha corretamente o campo salário");
        } else if(cargo.equals("<selecione>")) {
            throw new IOException("Selecione um cargo válido");
        } else {
            int bonus = view.getCbBonus().getSelectedIndex();
            Funcionario novoFuncionario = null;
            switch(bonus) {
                case 0:
                    throw new IOException("Selecione um bonus válido");
                case 1:
                case 2:
                    novoFuncionario = new Funcionario(
                        nome, 
                        idade, 
                        salario, 
                        cargo, 
                        dtAdmissao, 
                        funcionarioMes, 
                        faltas, 
                        bonus
                    );
                    break;
                    
                default:
                    throw new IOException("O bônus selecionado é desconhecido");
            }
            
            return novoFuncionario;
            
        }

    } 
    
}
