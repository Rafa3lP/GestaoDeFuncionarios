/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufes.gestaodefuncionarios.presenter;

import br.ufes.gestaodefuncionarios.dao.FuncionarioDAO;
import br.ufes.gestaodefuncionarios.logger.IMetodoLog;
import br.ufes.gestaodefuncionarios.model.Funcionario;
import br.ufes.gestaodefuncionarios.view.BuscarFuncionarioView;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Rafael
 */
public class BuscarFuncionarioPresenter {
    private JTable tabela;
    private PrincipalPresenter principalPresenter;
    private BuscarFuncionarioView view;
    private IMetodoLog metodoLog;

    public BuscarFuncionarioPresenter(PrincipalPresenter principalPresenter, IMetodoLog metodoLog) {
        this.metodoLog = metodoLog;
        this.principalPresenter = principalPresenter;
        this.view = new BuscarFuncionarioView();
        this.view.setTitle("Buscar Funcionário");
        this.tabela = this.view.getTFuncionarios();
        
        try {
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
            fechar();
        }
        
    }
    
    private void novo() {
        new CriarFuncionarioPresenter(this.principalPresenter, this.metodoLog);
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
        new VisualizarFuncionarioPresenter(
                Integer.parseInt(this.tabela.getValueAt(this.tabela.getSelectedRow(), 0).toString()), 
                principalPresenter,
                this.metodoLog
        );
    }
    
    private void fechar() {
        this.view.dispose();
    }
    
    private void lerTabela() throws RuntimeException {
        DefaultTableModel modelo = (DefaultTableModel) this.tabela.getModel();
        modelo.setNumRows(0);
        
        FuncionarioDAO fDao = new FuncionarioDAO();

        for(Funcionario f: fDao.getFuncionarios()) {
            modelo.addRow(new Object[]{
                f.getId(),
                f.getNome(),
                f.getIdade(),
                f.getCargo(),
                f.getSalarioBase()
            });
        }
        
    }
    
    private void lerTabelaByNome(String nome) {
        DefaultTableModel modelo = (DefaultTableModel) this.tabela.getModel();
        modelo.setNumRows(0);
        FuncionarioDAO fDao = new FuncionarioDAO();
        
        for(Funcionario f: fDao.getFuncionariosByNome(nome)) {
            modelo.addRow(new Object[]{
                f.getId(),
                f.getNome(),
                f.getIdade(),
                f.getCargo(),
                f.getSalarioBase()
            });
        }
    }
    
    private void habilitarBotoes(boolean flag) {
        this.view.getBtnVerBonus().setEnabled(flag);
        this.view.getBtnVisualizar().setEnabled(flag);
    }
    
}
