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
import java.io.IOException;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author Rafael
 */
public class VisualizarFuncionarioPresenter {
    private ManterFuncionarioView view;
    private PrincipalPresenter principalPresenter;
    private Funcionario funcionario;
    private FuncionarioDAO fDao;
    
    public VisualizarFuncionarioPresenter(int idFuncionario, PrincipalPresenter principalPresenter) {
        fDao = new FuncionarioDAO();
        this.principalPresenter = principalPresenter;
        this.view = new ManterFuncionarioView();
        
        try {
            
            this.funcionario = fDao.getFuncionarioById(idFuncionario);

            putFuncionario(this.funcionario);
            
            visualizar();
            
            this.view.getBtnFechar().addActionListener((e) -> {
                fechar();
            });
            this.view.getBtnEditar().addActionListener((e) -> {
                editar();
            });
            this.view.getBtnExcluir().addActionListener((e) -> {
                excluir();
            });
            this.view.getBtnSalvar().addActionListener((e) -> {
                salvar();
            });

            principalPresenter.addToDesktopPane(view);
            this.view.setVisible(true);
            
        } catch(RuntimeException ex) {
            JOptionPane.showMessageDialog(view, ex.getMessage(), "erro", JOptionPane.ERROR_MESSAGE);
            App.AppLogger.escreveLog(new Log(IMetodoLog.LOG_ERROR, "Falha ao realizar a operação"));
            fechar();
        }

    }
    
    private void fechar() {
        this.view.dispose();
    }
    
    private void editar() {
        this.view.setTitle("Editar Funcionário");
        
        this.view.getBtnExcluir().setEnabled(false);
        this.view.getBtnSalvar().setEnabled(true);
        
        this.view.habilitaCampos(true);
    }
    
    private void visualizar() {
        this.view.setTitle("Visualizar Funcionário");
        
        this.view.getBtnSalvar().setEnabled(false);
        this.view.getBtnExcluir().setEnabled(true);
        
        this.view.habilitaCampos(false);
    }
    
    private void excluir() {
        
        int confirmacao = JOptionPane.showConfirmDialog(
                view, 
                "Confirma exclusão?", 
                "Confirmar", 
                JOptionPane.YES_NO_OPTION, 
                JOptionPane.WARNING_MESSAGE
        );
        if(confirmacao == JOptionPane.YES_OPTION) {
            try {
                fDao.deletar(this.funcionario);
                App.AppLogger.escreveLog(new Log(IMetodoLog.LOG_INFORMATION, "Funcionario " + this.funcionario.getNome() + " removido"));
                this.principalPresenter.atualizaNumFuncionarios();
                fechar();
            } catch(RuntimeException ex) {
                JOptionPane.showMessageDialog(view, ex.getMessage(), "erro", JOptionPane.ERROR_MESSAGE);
                App.AppLogger.escreveLog(new Log(IMetodoLog.LOG_ERROR, "Falha ao realizar a operação"));
            }
            
        }
    }
    
    private void salvar() {
        try {
            
            getFuncionario();
 
            fDao.atualizar(funcionario);
            
            JOptionPane.showMessageDialog(
                    view, 
                    "Funcionario Atualizado!", 
                    "Sucesso", 
                    JOptionPane.INFORMATION_MESSAGE
            );
            
            App.AppLogger.escreveLog(new Log(IMetodoLog.LOG_INFORMATION, "Funcionario " + funcionario.getNome() + " atualizado"));
            
            visualizar();
            
        } catch(RuntimeException ex) {
            JOptionPane.showMessageDialog(view, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            App.AppLogger.escreveLog(new Log(IMetodoLog.LOG_ERROR, "Falha ao realizar a operação - " + ex.getMessage()));
        } catch(IOException ex) {
            JOptionPane.showMessageDialog(view, ex.getMessage(), "Aviso", JOptionPane.WARNING_MESSAGE);
        }
        
    }
    
    private void getFuncionario() throws IOException {
        
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
            switch(bonus) {
                case 0:
                    throw new IOException("Selecione um bonus válido");
                case 1:
                case 2:
                    funcionario.setNome(nome);
                    funcionario.setIdade(idade);
                    funcionario.setCargo(cargo);
                    funcionario.setFaltas(faltas);
                    funcionario.setSalarioBase(salario);
                    funcionario.setDtAdmissao(dtAdmissao);
                    funcionario.setTipoBonus(bonus);
                    funcionario.setFuncionarioMes(funcionarioMes);
                    break;
                    
                default:
                    throw new IOException("O bônus selecionado é desconhecido");
            }
            
        }

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
