/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufes.gestaodefuncionarios.presenter;

import br.ufes.gestaodefuncionarios.collection.FuncionarioCollection;
import br.ufes.gestaodefuncionarios.logger.IMetodoLog;
import br.ufes.gestaodefuncionarios.logger.Log;
import br.ufes.gestaodefuncionarios.model.Funcionario;
import br.ufes.gestaodefuncionarios.observer.Observer;
import br.ufes.gestaodefuncionarios.view.BuscarFuncionarioView;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Rafael
 */
public class BuscarFuncionarioPresenter implements Observer{
    private JTable tabela;
    private PrincipalPresenter principalPresenter;
    private BuscarFuncionarioView view;
    private FuncionarioCollection fCollection;

    public BuscarFuncionarioPresenter(PrincipalPresenter principalPresenter) {
        this.principalPresenter = principalPresenter;
        this.view = new BuscarFuncionarioView();
        this.view.setTitle("Buscar Funcionário");
        this.tabela = this.view.getTFuncionarios();
        this.fCollection = FuncionarioCollection.getInstance();
        
        try {
            fCollection.addObserver(this);
            lerTabela();
            habilitarBotoes(false);
            
            this.view.getBtnFechar().addActionListener((e) -> {
                fechar();
            });
            
            this.view.getBtnBuscar().addActionListener((e) -> {
                buscar();
            });
            
            this.view.getBtnNovo().addActionListener((e) -> {
                novo();
            });
            
            this.view.getBtnVisualizar().addActionListener((e) -> {
                visualizar();
            });
            
            this.view.getBtnVerBonus().addActionListener((e) -> {
                verBonus();
            });
            
            this.tabela.getSelectionModel().addListSelectionListener((e) -> {
                if(tabela.getSelectedRow() != -1) {
                    habilitarBotoes(true);
                } else {
                    habilitarBotoes(false);
                }
            });
            this.principalPresenter.addToDesktopPane(this.view);
            this.view.setVisible(true);
        } catch(RuntimeException ex) {
            JOptionPane.showMessageDialog(
                this.view, 
                ex.getMessage(), 
                "Erro", 
                JOptionPane.ERROR_MESSAGE
            );
            App.AppLogger.escreveLog(new Log(IMetodoLog.LOG_ERROR, "Falha ao realizar operação - " + ex.getMessage()));
            fechar();
        }
        
    }
    
    private void novo() {
        new CriarFuncionarioPresenter(this.principalPresenter);
        this.view.dispose();
    }
    
    private void buscar() {
        if(this.view.getTxtNome().getText().isEmpty()) {
            lerTabela();
        } else {
            lerTabelaByNome(view.getTxtNome().getText());
        }
    }
    
    private void visualizar() {
        Funcionario funcionario;
        funcionario = fCollection.getFuncionarioById(
            Integer.parseInt(
                this.tabela.getValueAt(
                    this.tabela.getSelectedRow(), 0
                )
                .toString()
            )
        );
        new VisualizarFuncionarioPresenter(funcionario, principalPresenter);
    }
    
    private void verBonus() {
        try {
            Funcionario funcionario;
            funcionario = fCollection.getFuncionarioById(
                Integer.parseInt(
                    this.tabela.getValueAt(
                        this.tabela.getSelectedRow(), 0
                    )
                    .toString()
                )
            );
            new VerBonusPresenter(true, funcionario);
            
        } catch(RuntimeException ex) {
            JOptionPane.showMessageDialog(this.view, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            App.AppLogger.escreveLog(new Log(IMetodoLog.LOG_ERROR, "Falha ao realizar operação - " + ex.getMessage()));
        }
        
    }
    
    private void fechar() {
        this.view.dispose();
        fCollection.removeObserver(this);
    }
    
    private void lerTabela() throws RuntimeException {
        try {
            DefaultTableModel modelo = (DefaultTableModel) this.tabela.getModel();
            modelo.setNumRows(0);

            for(Funcionario f: fCollection.getFuncionarios()) {
                modelo.addRow(new Object[]{
                    f.getId(),
                    f.getNome(),
                    f.getIdade(),
                    f.getCargo(),
                    String.format("%.2f", f.getSalarioBase())
                });
            }
        } catch(RuntimeException ex) {
            JOptionPane.showMessageDialog(this.view, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            App.AppLogger.escreveLog(new Log(IMetodoLog.LOG_ERROR, "Falha ao realizar operação - " + ex.getMessage()));
        }
 
    }
    
    private void lerTabelaByNome(String nome) {
        DefaultTableModel modelo = (DefaultTableModel) this.tabela.getModel();
        modelo.setNumRows(0);
        
        try {
            for(Funcionario f: fCollection.getFuncionariosByNome(nome)) {
                modelo.addRow(new Object[]{
                    f.getId(),
                    f.getNome(),
                    f.getIdade(),
                    f.getCargo(),
                    String.format("%.2f", f.getSalarioBase())
                });
            }
        } catch(RuntimeException ex) {
            JOptionPane.showMessageDialog(this.view, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            App.AppLogger.escreveLog(new Log(IMetodoLog.LOG_ERROR, "Falha ao realizar operação - " + ex.getMessage()));
        }
  
    }
    
    private void habilitarBotoes(boolean flag) {
        this.view.getBtnVerBonus().setEnabled(flag);
        this.view.getBtnVisualizar().setEnabled(flag);
    }

    @Override
    public void update() {
       try {
           if(!this.view.getTxtNome().getText().isEmpty()) {
               this.view.getTxtNome().setText("");
           }
           lerTabela();
       } catch(RuntimeException ex) {
            JOptionPane.showMessageDialog(
                this.view, 
                ex.getMessage(), 
                "Erro", 
                JOptionPane.ERROR_MESSAGE
            );
            App.AppLogger.escreveLog(new Log(IMetodoLog.LOG_ERROR, "Falha ao realizar operação - " + ex.getMessage()));
            fechar();
       }
    }
    
}
