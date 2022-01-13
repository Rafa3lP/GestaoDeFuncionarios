/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufes.gestaodefuncionarios.presenter;

import br.ufes.gestaodefuncionarios.dao.FuncionarioDAO;
import br.ufes.gestaodefuncionarios.model.Funcionario;
import br.ufes.gestaodefuncionarios.view.BuscarFuncionarioView;
import br.ufes.gestaodefuncionarios.view.PrincipalView;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Rafael
 */
public class BuscarFuncionarioPresenter {
    private JTable tabela;
    private PrincipalView principalView;
    private BuscarFuncionarioView view;

    public BuscarFuncionarioPresenter(PrincipalView principalView) {
        this.principalView = principalView;
        this.view = new BuscarFuncionarioView();
        this.view.setTitle("Buscar Funcionário");
        this.tabela = this.view.gettFuncionarios();
        lerTabela();
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
        this.principalView.getDesktopPane().add(this.view);
        this.view.setVisible(true);
    }
    
    private void novo() {
        new CriarFuncionarioPresenter(this.principalView.getDesktopPane());
        this.view.dispose();
    }
    
    private void buscar() {
        if(this.view.getTxtNome().getText().isEmpty()) {
            JOptionPane.showMessageDialog(
                view, 
                "Digite Algo no campo nome", 
                "Aviso", 
                JOptionPane.INFORMATION_MESSAGE
            );
        } else {
            lerTabelaByNome(view.getTxtNome().getText());
        }
    }
    
    private void visualizar() {
        new VisualizarFuncionarioPresenter(
                Integer.parseInt(this.tabela.getValueAt(this.tabela.getSelectedRow(), 0).toString()), 
                principalView
        );
    }
    
    private void fechar() {
        this.view.dispose();
    }
    
    private void lerTabela() {
        DefaultTableModel modelo = (DefaultTableModel) this.tabela.getModel();
        modelo.setNumRows(0);
        FuncionarioDAO fDao = new FuncionarioDAO();
        
        for(Funcionario f: fDao.getFuncionarios()) {
            modelo.addRow(new Object[]{
                f.getId(),
                f.getNome(),
                f.getIdade(),
                f.getCargo(),
                f.getSalario()
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
                f.getSalario()
            });
        }
    }
    
}
