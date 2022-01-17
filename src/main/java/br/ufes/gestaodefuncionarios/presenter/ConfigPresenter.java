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
import br.ufes.gestaodefuncionarios.prop.PropertieManager;
import br.ufes.gestaodefuncionarios.view.ConfigView;
import br.ufes.gestaodefuncionarios.view.PrincipalView;
import com.sun.tools.javac.Main;
import java.io.IOException;
import java.net.URL;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import javax.swing.JOptionPane;
import org.sqlite.SQLiteConfig;

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
            } else if(logFormat.equals("xml")) {
                newMetodoLog = new LogXml();
            } else {
                throw new RuntimeException("Não foi possível alterar o formato do log");
            }
            newMetodoLog.migraLog(this.metodoLog);
            propertieManager.setProp("logFormat", logFormat);
            //restart();
        }  
        fechar();
        
    }
    
    private void restart() {
        Class cls = Main.class;
        ProtectionDomain pDomain = cls.getProtectionDomain();
        CodeSource cSource = pDomain.getCodeSource();
        URL loc = cSource.getLocation();
        JOptionPane.showMessageDialog(null, loc.toString().substring(5));

        String comando = "java -jar " + loc.toString().substring(5);
        JOptionPane.showMessageDialog(null, comando);
        try {
            Process Processo = Runtime.getRuntime().exec(comando);
        } catch ( IOException MensagemdeErro ) {
            System.out.println(MensagemdeErro);
        }
        System.exit(0);
    }
    
}
