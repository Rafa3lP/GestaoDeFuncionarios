/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufes.gestaodefuncionarios.logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;


/**
 *
 * @author Rafael
 */
public class LogXml implements IMetodoLog {
    private FileWriter fw;
    private BufferedWriter bw;

    public LogXml() {
        if(!new File("log").exists()) {
            new File("log").mkdir();
        }
    }
    
    @Override
    public void escreveLog(Log log) {
        XmlMapper xmlMapper = new XmlMapper();
        try {
            fw = new FileWriter("log/log.xml", true);
            bw = new BufferedWriter(fw);
            bw.write(xmlMapper.writeValueAsString(log));
            bw.newLine();
            bw.close();
            fw.close();
            
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Não foi possivel gravar o arquivo de log", "Erro", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(LogXml.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void migraLog(IMetodoLog metodoLogOld) {
        List<Log> logsOld = metodoLogOld.getLogs();
        this.remover(new File("log/"));
        for(Log l: logsOld) {
            this.escreveLog(l);
        }
        this.escreveLog(new Log(IMetodoLog.LOG_INFORMATION, "MUDANÇA PARA FORMATO DE LOG XML"));
    }

    @Override
    public List<Log> getLogs() {
        List<Log> logList = new ArrayList<>();
        XmlMapper xmlMapper = new XmlMapper();
        String line = null;
        try {
      
            FileReader fileReader = new FileReader("log/log.xml");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            
            while((line = bufferedReader.readLine()) != null) {
                Log l = xmlMapper.readValue(line, new TypeReference<Log>(){});
                System.out.println(l);
                logList.add(l);
            }
            
            fileReader.close();
            bufferedReader.close();
            
        } catch (JsonProcessingException ex) {
            JOptionPane.showMessageDialog(null, "Não foi possivel ler o arquivo de log xml", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Não foi possivel encontrar o arquivo de log", "Erro", JOptionPane.ERROR_MESSAGE);
        }finally {  
            return logList;
        }
        
    }
    
    private void remover(File f) {
        if(f.isDirectory()) {
            File[] files = f.listFiles();
            for (int i = 0; i < files.length; ++i) {
                this.remover(files[i]);
            }
        } else {
            f.delete();
            System.gc();
        }
        
    }
    
}
