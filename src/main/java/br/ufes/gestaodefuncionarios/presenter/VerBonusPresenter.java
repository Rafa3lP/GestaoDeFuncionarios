/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.ufes.gestaodefuncionarios.presenter;

import br.ufes.gestaodefuncionarios.dao.FuncionarioDAO;
import br.ufes.gestaodefuncionarios.logger.IMetodoLog;
import br.ufes.gestaodefuncionarios.logger.Log;
import br.ufes.gestaodefuncionarios.model.Funcionario;
import br.ufes.gestaodefuncionarios.model.FuncionarioBonus;
import br.ufes.gestaodefuncionarios.model.FuncionarioSalario;
import br.ufes.gestaodefuncionarios.view.VerBonusView;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Rafael
 */
public class VerBonusPresenter {
    private VerBonusView view;
    private Funcionario funcionario;
    private JTable tabela;
    private IMetodoLog metodoLog;

    public VerBonusPresenter(boolean modal, Funcionario funcionario, IMetodoLog metodoLog) {
        this.metodoLog = metodoLog;
        this.funcionario = funcionario;
        this.view = new VerBonusView(new java.awt.Frame(), modal);
        this.view.setTitle("Ver Bônus");
        this.view.getLblNome().setText(this.funcionario.getNome());
        
        this.tabela = view.getTabela();
        
        lerTabela();
        
        this.view.getBtnOk().addActionListener((e) -> {
            fechar();
        });
        
        this.view.setVisible(true);
    }
    
    private void fechar() {
        this.view.dispose();
    }
    
    private void lerTabela() {
        try {
            DefaultTableModel modelo = (DefaultTableModel) this.tabela.getModel();
            modelo.setNumRows(0);

            FuncionarioDAO fDao = new FuncionarioDAO();
           
            for(FuncionarioBonus fb: fDao.getFuncionarioBonusList(this.funcionario)) {
                modelo.addRow(new Object[]{
                    fb.getDataCalculo(),
                    fb.getCargoFuncionario(),
                    fb.getTipoBonus(),
                    fb.getValorBonus()
                });
            }
        } catch(RuntimeException ex) {
            JOptionPane.showMessageDialog(this.view, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            metodoLog.escreveLog(new Log(IMetodoLog.LOG_ERROR, "Falha ao realizar operação - " + ex.getMessage()));
        }
    }
}
