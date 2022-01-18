/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufes.gestaodefuncionarios.logger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Rafael
 */
public class LogJSON implements IMetodoLog {
    
    private FileWriter fw;
    private BufferedWriter bw;
    private FileReader fr;
    private BufferedReader br;

    public LogJSON() {
        if(!new File("log").exists()) {
            new File("log").mkdir();
        }
        File arq = new File("log/log.json");
        if(!arq.exists()) {
            try {
                this.remover(new File("log/"));
                arq.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(LogTxt.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void escreveLog(Log log) {
        String msg = log.getMsg();
        int tipoLog = log.getTipo();
        JSONObject oLog = new JSONObject();
        switch(tipoLog) {
            case IMetodoLog.LOG_ERROR:
                oLog.put("type", "error");
                break;
            case IMetodoLog.LOG_INFORMATION:
                oLog.put("type", "info");
                break;
            default:
                oLog.put("type", "unknown");
                break;
        }
        oLog.put("message", msg);
        
        try {
            fw = new FileWriter("log/log.json", true);
            bw = new BufferedWriter(fw);
            bw.write(oLog.toJSONString());
            bw.newLine();
            bw.close();
            fw.close();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Não foi possivel gravar o arquivo de log", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    @Override
    public void migraLog(IMetodoLog metodoLogOld) {
        List<Log> logsOld = metodoLogOld.getLogs();
        this.remover(new File("log/"));
        for(Log l: logsOld) {
            this.escreveLog(l);
        }
        this.escreveLog(new Log(IMetodoLog.LOG_INFORMATION, "MUDANÇA PARA FORMATO DE LOG JSON"));
    }
    
    @Override
    public List<Log> getLogs() {
        List<JSONObject> logList = new ArrayList<>();
        JSONObject jO;
        List<Log> logs = new ArrayList<>();
        String line = null;
        
        try {
            FileReader fileReader = new FileReader("log/log.json");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            
            while((line = bufferedReader.readLine()) != null) {
                jO = (JSONObject) new JSONParser().parse(line);
                logList.add(jO);
      
            }
            
            for(JSONObject o: logList) {
                String sType = (String) o.get("type");
                int type = IMetodoLog.LOG_UNKNOWN;
                if(sType.equals("error")) {
                    type = IMetodoLog.LOG_ERROR;
                } else if(sType.equals("info")) {
                    type = IMetodoLog.LOG_INFORMATION;
                }
                String message = (String) o.get("message");
                Log newLog = new Log(type, message);
                logs.add(newLog);
            }
            
            bufferedReader.close();
            fileReader.close();
            
        } catch(IOException ex) {
            JOptionPane.showMessageDialog(null, "Não foi possivel encontrar o arquivo de log", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(null, "Não foi possivel ler o arquivo de log", "Erro", JOptionPane.ERROR_MESSAGE);
        }
        
        return logs;
        
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
