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
import br.ufes.gestaodefuncionarios.model.IMetodoCalculoBonus;
import br.ufes.gestaodefuncionarios.view.CalcularSalarioView;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTable;

/**
 *
 * @author Rafael
 */
public class CalcularSalarioPresenter {
    private CalcularSalarioView view;
    private PrincipalPresenter principalPresenter;
    private IMetodoLog metodoLog;
    private JTable tabela;

    public CalcularSalarioPresenter(PrincipalPresenter principalPresenter, IMetodoLog metodoLog) {
        this.metodoLog = metodoLog;
        this.principalPresenter = principalPresenter;
        this.view = new CalcularSalarioView();
        this.view.setTitle("Calcular Salário");
        this.tabela = view.getTSalario();

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
        
    }
    
    private void calcular() {
        try {
            FuncionarioDAO fDao = new FuncionarioDAO();
            int funcionarioId = Integer.parseInt(this.tabela.getValueAt(this.tabela.getSelectedRow(), 0).toString());
            Funcionario funcionario = fDao.getFuncionarioById(funcionarioId);
            
            ArrayList<IMetodoCalculoBonus> tiposDeBonus = new ArrayList<>();
            tiposDeBonus.add(new BonusAssiduidade());
            tiposDeBonus.add(new BonusTempodeServico());
            
            
            switch(funcionario.getTipoBonus()) {
                //bonus normal
                case 1:
                    break;
                //bonus generoso
                case 2:
                    break;
            }
            
            //para todos os outros tipos de bonus...
            for(IMetodoCalculoBonus metodoBonus: tiposDeBonus) {
                
            }
            
        } catch(RuntimeException ex) {
            JOptionPane.showMessageDialog(view, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            metodoLog.escreveLog(new Log(IMetodoLog.LOG_ERROR, "Falha ao realizar operação - " + ex.getMessage()));
        }
        
    }
    
    private void habilitarBotoes(Boolean flag) {
        view.getBtnCalcular().setEnabled(flag);
    }
    
}
