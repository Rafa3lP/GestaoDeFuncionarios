/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufes.gestaodefuncionarios.logger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author Rafael
 */
public class LogTxt implements IMetodoLog {

    private String logFile;
    private FileWriter fw;
    private BufferedWriter bw;
    private FileReader fr;
    private BufferedReader br;
    
    @Override
    public void escreveLog(Log log) {
       String msg = log.getMsg();
       int tipoLog = log.getTipo();
       try {
            fw = new FileWriter("log/log.txt", true);
            bw = new BufferedWriter(fw);
            switch(tipoLog) {
                case IMetodoLog.LOG_ERROR:
                    msg = "ERROR->" + msg;
                    break;
                case IMetodoLog.LOG_INFORMATION:
                    msg = "INFO->" + msg;
                    break;
                default:
                    break;
            }
            bw.write(msg);
            bw.newLine();
            bw.close();
            fw.close();
            List<Log> l = getLogs();
            l.forEach(lg -> {
                System.out.println(lg);
            });
        } catch(IOException ex) {
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
        List<Log> logs = new ArrayList<>();
        FileReader fileReader = null;
        String line = null;
        int type;
        String message;
        try { 
            fileReader = new FileReader("log/log.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while((line = bufferedReader.readLine()) != null) {
                type = IMetodoLog.LOG_UNKNOWN;
                String[] logSeparado = line.split("->");
                if(logSeparado[0].equals("ERROR")) {
                    type = IMetodoLog.LOG_ERROR;
                } else if(logSeparado[0].equals("INFO")) {
                    type = IMetodoLog.LOG_INFORMATION;
                }
                message = logSeparado[1];
                Log newLog = new Log(type, message);
                logs.add(newLog);
                
            }
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Não foi possivel encontrar o arquivo de log", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Não foi possivel ler o arquivo de log", "Erro", JOptionPane.ERROR_MESSAGE);
        }
        
        return logs;
    }
    
}
