/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufes.gestaodefuncionarios.presenter;

import br.ufes.gestaodefuncionarios.logger.IMetodoLog;
import br.ufes.gestaodefuncionarios.logger.LogJSON;
import br.ufes.gestaodefuncionarios.logger.LogTxt;
import br.ufes.gestaodefuncionarios.prop.PropertieManager;
import br.ufes.gestaodefuncionarios.view.ConfigView;
import br.ufes.gestaodefuncionarios.view.PrincipalView;
import javax.swing.JOptionPane;

/**
 *
 * @author Rafael
 */
public class ConfigPresenter {
    private PrincipalView principalView;
    private ConfigView view;
    private IMetodoLog metodoLog;
    private PropertieManager propertieManager;

    public ConfigPresenter(PrincipalView principalView, IMetodoLog metodoLog) {
        this.principalView = principalView;
        this.view = new ConfigView();
        this.metodoLog = metodoLog;
        this.propertieManager = new PropertieManager();
        this.view.setTitle("Configurar");
        
        String logFormat = propertieManager.getPropertie("logFormat");
        
        this.view.getCbFormatoLog().setSelectedItem(logFormat);
        
        this.view.getBtnOk().addActionListener((e) -> {
            String selecionado = (String) this.view.getCbFormatoLog().getSelectedItem();
            if(!selecionado.equals(logFormat)) {
                setLogFormat(selecionado);
            }
            fechar();
        });
        
        this.principalView.getDesktopPane().add(this.view);
        this.view.setVisible(true);
    }
    
    private void fechar() {
        this.view.dispose();
    }
    
    private void setLogFormat(String logFormat) {
        int resposta = JOptionPane.showConfirmDialog(
                view, 
                "Deseja realmente alterar o formato de log?\n(Reinicialização necessaria)", 
                "Confirmação", 
                JOptionPane.YES_NO_OPTION
        );
        if(resposta == JOptionPane.YES_OPTION) {
            IMetodoLog newMetodoLog;
            if(logFormat.equals("txt")) {
                newMetodoLog = new LogTxt();
            } else if(logFormat.equals("json")) {
                newMetodoLog = new LogJSON();
            } else {
                throw new RuntimeException("Não foi possível alterar o formato do log");
            }
            newMetodoLog.migraLog(this.metodoLog);
            propertieManager.setProp("logFormat", logFormat);
        }  
        fechar();
        
    }
    
}
