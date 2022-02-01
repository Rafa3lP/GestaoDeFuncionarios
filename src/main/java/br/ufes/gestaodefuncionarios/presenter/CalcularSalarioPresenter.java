/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufes.gestaodefuncionarios.presenter;

import br.ufes.gestaodefuncionarios.model.BonusTempodeServico;
import br.ufes.gestaodefuncionarios.model.BonusAssiduidade;
import br.ufes.gestaodefuncionarios.dao.FuncionarioDAO;
import br.ufes.gestaodefuncionarios.logger.IMetodoLog;
import br.ufes.gestaodefuncionarios.logger.Log;
import br.ufes.gestaodefuncionarios.model.BonusGeneroso;
import br.ufes.gestaodefuncionarios.model.BonusNormal;
import br.ufes.gestaodefuncionarios.model.Funcionario;
import br.ufes.gestaodefuncionarios.model.FuncionarioSalario;
import br.ufes.gestaodefuncionarios.model.IMetodoCalculoBonus;
import br.ufes.gestaodefuncionarios.view.CalcularSalarioView;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Rafael
 */
public class CalcularSalarioPresenter {
    private CalcularSalarioView view;
    private PrincipalPresenter principalPresenter;
    private IMetodoLog metodoLog;
    private JTable tabela;
    private ArrayList<IMetodoCalculoBonus> tiposDeBonus;

    public CalcularSalarioPresenter(PrincipalPresenter principalPresenter, IMetodoLog metodoLog) {
        this.metodoLog = metodoLog;
        this.principalPresenter = principalPresenter;
        this.view = new CalcularSalarioView();
        this.view.setTitle("Calcular Salário");
        this.tabela = view.getTSalario();
        
        this.tiposDeBonus = new ArrayList<>();
        
        tiposDeBonus.add(new BonusNormal());
        tiposDeBonus.add(new BonusGeneroso());
        tiposDeBonus.add(new BonusAssiduidade());
        tiposDeBonus.add(new BonusTempodeServico());

        lerTabela();
        
        habilitarBotoes(false);
        
        this.view.getBtnFechar().addActionListener((e) -> {
            fechar();
        });
        
        this.tabela.getSelectionModel().addListSelectionListener((e) -> {
            if(tabela.getSelectedRow() != -1) {
                habilitarBotoes(true);
            } else {
                habilitarBotoes(false);
            }
        });
        
        this.view.getBtnCalcular().addActionListener((e) -> {
            calcular();
        });
        
        this.view.getBtnListar().addActionListener((e) -> {
            lerTabela();
        });
        
        this.principalPresenter.addToDesktopPane(this.view);
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

            for(FuncionarioSalario fs: fDao.getFuncionarioSalarioList()) {
                modelo.addRow(new Object[]{
                    fs.getFuncionarioId(),
                    fs.getFuncionario(),
                    fs.getDataCalculo(),
                    fs.getSalarioBase(),
                    fs.getBonus(),
                    fs.getSalario()
                });
            }
        } catch(RuntimeException ex) {
            JOptionPane.showMessageDialog(this.view, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            metodoLog.escreveLog(new Log(IMetodoLog.LOG_ERROR, "Falha ao realizar operação - " + ex.getMessage()));
        }
    }
    
    private void calcular() {
        try {
            FuncionarioDAO fDao = new FuncionarioDAO();
            int funcionarioId = Integer.parseInt(this.tabela.getValueAt(this.tabela.getSelectedRow(), 0).toString());
            Funcionario funcionario = fDao.getFuncionarioById(funcionarioId);
           
            //limpa a lista de bonus do funcionario
            funcionario.getBonusRecebidos().clear();
            
            Date dataCalculo = this.view.getDtCalculo().getDate();
           
            //para todos os tipos de bonus...
            for(IMetodoCalculoBonus metodoBonus: tiposDeBonus) {
                metodoBonus.calcular(funcionario, dataCalculo);
            }
            
            funcionario.calcularSalario();
            
            fDao.upsertFuncionarioSalario(
                    new FuncionarioSalario(
                            funcionario.getId(), 
                            funcionario.getNome(), 
                            dataCalculo, 
                            funcionario.getSalarioBase(), 
                            funcionario.getBonus(),
                            funcionario.getSalario()
                    )
            );
            
            lerTabela();
            
            metodoLog.escreveLog(new Log(IMetodoLog.LOG_INFORMATION, "Salario calculado para o funcionario " + funcionario.getNome()));
            
        } catch(RuntimeException ex) {
            JOptionPane.showMessageDialog(view, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            metodoLog.escreveLog(new Log(IMetodoLog.LOG_ERROR, "Falha ao realizar operação - " + ex.getMessage()));
        }
        
    }
    
    private void habilitarBotoes(Boolean flag) {
        view.getBtnCalcular().setEnabled(flag);
    }
    
}
