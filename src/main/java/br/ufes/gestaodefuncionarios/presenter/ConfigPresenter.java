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
import javax.swing.JProgressBar;

/**
 *
 * @author Rafael
 */
public class ConfigPresenter {
    private PrincipalPresenter principalPresenter;
    private ConfigView view;
    private PropertyManager propertieManager;
    private JProgressBar pbar;
    
    
    public ConfigPresenter(PrincipalPresenter principalPresenter) {
        this.principalPresenter = principalPresenter;
        this.view = new ConfigView();
        this.propertieManager = new PropertyManager();
        this.view.setTitle("Configurar");
        
        String logFormat = propertieManager.getProperty("logFormat");
        
        pbar = new JProgressBar();
        pbar.setIndeterminate(true);
        pbar.setStringPainted(true);
        pbar.setString("Migrando Logs...");
        pbar.setBounds(145,140,130,25);
        pbar.setVisible(false);
        view.getjPainel().add(pbar);
        
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
            
            new Thread() {
                @Override
                public void run() {
                    view.getBtnOk().setEnabled(false);
                   
                    pbar.setVisible(true);
                    
                    newMetodoLog.migraLog(App.AppLogger);
                    propertieManager.setProp("logFormat", logFormat);
                    
                    view.getjPainel().remove(pbar);
                    
                    view.getBtnOk().setEnabled(true);
                    
                    JOptionPane.showMessageDialog(
                            null, 
                            "Sucesso ao migrar logs!"
                            + "\nO sistema será encerrado agora", 
                            "Sucesso", 
                            JOptionPane.WARNING_MESSAGE
                    );
                    
                    fechar();
                    
                    App.restart();
                    
                }
            }.start();
            
        }
       
    }
   
}
