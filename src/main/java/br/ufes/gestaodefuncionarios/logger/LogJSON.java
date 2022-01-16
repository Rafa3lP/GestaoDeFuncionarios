/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufes.gestaodefuncionarios.logger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
            List<Log> l = getLogs();
            l.forEach(lg -> {
                System.out.println(lg);
            });
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Não foi possivel gravar o arquivo de log", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    @Override
    public void migraLog(IMetodoLog metodoLogOld) {
        List<Log> logsOld = metodoLogOld.getLogs();
        for(Log l: logsOld) {
            escreveLog(l);
        }
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
            
            bufferedReader.close();
            fileReader.close();
            
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
            
        } catch(IOException ex) {
            JOptionPane.showMessageDialog(null, "Não foi possivel encontrar o arquivo de log", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(null, "Não foi possivel ler o arquivo de log", "Erro", JOptionPane.ERROR_MESSAGE);
        }
        
        return logs;
        
    }
    
}
