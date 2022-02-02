/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufes.gestaodefuncionarios.presenter;

import br.ufes.gestaodefuncionarios.logger.IMetodoLog;
import br.ufes.gestaodefuncionarios.logger.Log;
import br.ufes.gestaodefuncionarios.logger.LogJSON;
import br.ufes.gestaodefuncionarios.logger.LogTxt;
import br.ufes.gestaodefuncionarios.logger.LogXml;
import br.ufes.gestaodefuncionarios.prop.PropertyManager;
import br.ufes.gestaodefuncionarios.view.ConfigView;
import javax.swing.JOptionPane;

/**
 *
 * @author Rafael
 */
public class ConfigPresenter {
    private PrincipalPresenter principalPresenter;
    private ConfigView view;
    private PropertyManager propertieManager;

    public ConfigPresenter(PrincipalPresenter principalPresenter) {
        this.principalPresenter = principalPresenter;
        this.view = new ConfigView();
        this.propertieManager = new PropertyManager();
        this.view.setTitle("Configurar");
        
        String logFormat = propertieManager.getProperty("logFormat");
        
        this.view.getCbFormatoLog().setSelectedItem(logFormat);
        
        this.view.getBtnOk().addActionListener((e) -> {
            String selecionado = (String) this.view.getCbFormatoLog().getSelectedItem();
            if(!selecionado.equals(logFormat)) {
                try {
                    setLogFormat(selecionado);
                } catch(RuntimeException ex) {
                    JOptionPane.showMessageDialog(
                            view, 
                            ex.getMessage(), 
                            "Erro", 
                            JOptionPane.ERROR_MESSAGE
                    );
                    App.AppLogger.escreveLog(new Log(IMetodoLog.LOG_ERROR, "Falha ao realizar operação - " + ex.getMessage()));
                }
                
            }
            fechar();
        });
        
        this.principalPresenter.addToDesktopPane(this.view);
        this.view.setVisible(true);
    }
    
    private void fechar() {
        this.view.dispose();
    }
    
    private void setLogFormat(String logFormat) {
        int resposta = JOptionPane.showConfirmDialog(
                null, 
                "Deseja realmente alterar o formato de log?\n(Reinicialização necessaria)", 
                "Confirmação", 
                JOptionPane.YES_NO_OPTION
        );
        if(resposta == JOptionPane.YES_OPTION) {
            IMetodoLog newMetodoLog;
            switch (logFormat) {
                case "txt":
                    newMetodoLog = new LogTxt();
                    break;
                case "json":
                    newMetodoLog = new LogJSON();
                    break;
                case "xml":
                    newMetodoLog = new LogXml();
                    break;
                default:
                    throw new RuntimeException("Não foi possível alterar o formato do log");
            }
            
            newMetodoLog.migraLog(App.AppLogger);
            
            propertieManager.setProp("logFormat", logFormat);
            
            App.restart();
        }
        
        fechar();
        
    }
   
}
