/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufes.gestaodefuncionarios.prop;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author Rafael
 */
public class PropertieManager {
    private final String CONFIG_FILE = "config.ini";
    
    public String getPropertie(String key) {
        File file = new File(CONFIG_FILE);
        Properties properties = new Properties();

        try {
            FileInputStream fis = new FileInputStream(file);
            properties.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return properties.getProperty(key);
        
    }
    
    public void setProp(String key, String value) {
        File file = new File(CONFIG_FILE);
        Properties properties = new Properties();

        try {
            FileInputStream fis = new FileInputStream(file);
            properties.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
       
        properties.setProperty(key, value);
        
        try { 
            FileOutputStream fos = new FileOutputStream(file); 
            properties.store(fos, "ARQUIVO DE CONFIGURACOES"); 
            fos.close(); 
        } catch (IOException ex) { 
            System.out.println(ex.getMessage()); ex.printStackTrace(); 
        }
 
    }
   
}
