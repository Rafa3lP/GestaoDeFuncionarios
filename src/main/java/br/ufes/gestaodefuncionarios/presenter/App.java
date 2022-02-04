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
import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Rafael
 */
public class App {
    public static IMetodoLog AppLogger;
    private static String version;
    private static String logFormat;
    
    public static void main(String[] args) {
        getProperties();
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
            AppLogger.escreveLog(new Log(IMetodoLog.LOG_ERROR, "Falha ao Executar operação - " + ex.getMessage()));
        }
        
        new PrincipalPresenter();
        
    }
    
    private static void getProperties() {
        PropertyManager propertyManager = new PropertyManager();
        
        logFormat = propertyManager.getProperty("logFormat");
        
        AppLogger = null;
        
        switch (logFormat) {
            case "txt":
                AppLogger = new LogTxt();
                break;
            case "json":
                AppLogger = new LogJSON();
                break;
            case "xml":
                AppLogger = new LogXml();
                break;
            default:
                JOptionPane.showMessageDialog(
                    null, "Não foi possível encontrar o formato de log", "Erro Fatal", JOptionPane.ERROR_MESSAGE);
                System.exit(1);
                break;
        }
       
        version = propertyManager.getProperty("version");
    }
    
    public static void restart() {
        try {
            StringBuilder cmd = new StringBuilder();
            cmd.append(System.getProperty("java.home") + File.separator + "bin" + File.separator + "java ");
            
            cmd.append("-jar ").append(ManagementFactory.getRuntimeMXBean().getClassPath()).append(" ");
          
            Runtime.getRuntime().exec(cmd.toString());
            //System.out.println("Executar comando: \n " + cmd.toString());
            System.exit(0);
        } catch (IOException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
            AppLogger.escreveLog(new Log(IMetodoLog.LOG_ERROR, "Falha ao realizar operação - " + ex.getMessage()));
        }
    }

    public static String getVersion() {
        return version;
    }

    public static String getLogFormat() {
        return logFormat;
    }
    
    
   
}
