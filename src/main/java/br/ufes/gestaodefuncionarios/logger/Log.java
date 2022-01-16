/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufes.gestaodefuncionarios.logger;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

/**
 *
 * @author Rafael
 */
public class Log {
    
    private int tipo;
    private String msg;

    public Log(
        @JacksonXmlProperty(localName = "tipo") int tipo, 
        @JacksonXmlProperty(localName = "msg") String msg
    ) {
        this.tipo = tipo;
        this.msg = msg;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
    
    @Override
    public String toString() {
        return Integer.toString(this.tipo) + " - " + this.msg;
    }
    
}
