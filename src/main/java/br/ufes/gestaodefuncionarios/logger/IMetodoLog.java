/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufes.gestaodefuncionarios.logger;

import java.util.List;

/**
 *
 * @author Rafael
 */
public interface IMetodoLog {
    
    /**
     *Define tipo de log para erro
     */
    public static final int LOG_ERROR = 1;

    /**
     *Define tipo de log para informação
     */
    public static final int LOG_INFORMATION = 2;
    /**
     *Define tipo de log para desconhecido
     */
    public static final int LOG_UNKNOWN = 3;
    
    void escreveLog(Log log);
    
    void migraLog(IMetodoLog metodoLogOld);
    
    List<Log> getLogs();
    //void migraLog(IMetodoLog logAnterior);*/
}
