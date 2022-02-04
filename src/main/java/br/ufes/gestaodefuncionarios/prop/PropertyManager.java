/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufes.gestaodefuncionarios.prop;

import br.ufes.gestaodefuncionarios.logger.IMetodoLog;
import br.ufes.gestaodefuncionarios.logger.Log;
import br.ufes.gestaodefuncionarios.presenter.App;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author Rafael
 */
public class PropertyManager {
    private final String CONFIG_PATH = "config.ini";
    
    public String getProperty(String key) {
    
        File file = new File(CONFIG_PATH);
        Properties properties = new Properties();

        try {
            FileInputStream fis = new FileInputStream(file);
            properties.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
            App.AppLogger.escreveLog(new Log(IMetodoLog.LOG_ERROR,"Falha ao executar operação - " + e.getMessage()));
        }
        
        return properties.getProperty(key);
        
    }
    
    public void setProp(String key, String value) {
        
        File file = new File(CONFIG_PATH);
        Properties properties = new Properties();

        try {
            FileInputStream fis = new FileInputStream(file);
            properties.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
            App.AppLogger.escreveLog(new Log(IMetodoLog.LOG_ERROR,"Falha ao executar operação - " + e.getMessage()));
        }
       
        properties.setProperty(key, value);
        
        try { 
            FileOutputStream fos = new FileOutputStream(file); 
            properties.store(fos, "ARQUIVO DE CONFIGURACOES"); 
            fos.close(); 
        } catch (IOException ex) {  
            ex.printStackTrace(); 
            App.AppLogger.escreveLog(new Log(IMetodoLog.LOG_ERROR,"Falha ao executar operação - " + ex.getMessage()));
        }
 
    }
   
}
