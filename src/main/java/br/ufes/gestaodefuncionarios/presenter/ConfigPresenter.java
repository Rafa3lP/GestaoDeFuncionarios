/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufes.gestaodefuncionarios.presenter;

import br.ufes.gestaodefuncionarios.logger.IMetodoLog;
import br.ufes.gestaodefuncionarios.logger.LogJSON;
import br.ufes.gestaodefuncionarios.logger.LogTxt;
import br.ufes.gestaodefuncionarios.logger.LogXml;
import br.ufes.gestaodefuncionarios.prop.PropertyManager;
import br.ufes.gestaodefuncionarios.view.ConfigView;
import com.sun.tools.javac.Main;
import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.net.URL;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Rafael
 */
public class ConfigPresenter {
    private PrincipalPresenter principalPresenter;
    private ConfigView view;
    private IMetodoLog metodoLog;
    private PropertyManager propertieManager;

    public ConfigPresenter(PrincipalPresenter principalPresenter, IMetodoLog metodoLog) {
        this.principalPresenter = principalPresenter;
        this.view = new ConfigView();
        this.metodoLog = metodoLog;
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
            if(logFormat.equals("txt")) {
                newMetodoLog = new LogTxt();
            } else if(logFormat.equals("json")) {
                newMetodoLog = new LogJSON();
            } else if(logFormat.equals("xml")) {
                newMetodoLog = new LogXml();
            } else {
                throw new RuntimeException("Não foi possível alterar o formato do log");
            }
            newMetodoLog.migraLog(this.metodoLog);
            propertieManager.setProp("logFormat", logFormat);
            restart();
        }  
        fechar();
        
    }
    
    public static void restart() {
        try {
            
            StringBuilder cmd = new StringBuilder();
            cmd.append(System.getProperty("java.home") + File.separator + "bin" + File.separator + "java ");
            
            cmd.append("-jar ").append(ManagementFactory.getRuntimeMXBean().getClassPath()).append(" ");
          
            Runtime.getRuntime().exec(cmd.toString());
            System.out.println("Executar comando: \n " + cmd.toString());
            System.exit(0);
        } catch (IOException ex) {
            Logger.getLogger(Inicializar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
