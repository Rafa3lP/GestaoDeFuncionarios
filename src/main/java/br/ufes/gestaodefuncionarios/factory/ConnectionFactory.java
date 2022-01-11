/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufes.gestaodefuncionarios.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Rafael
 */
public class ConnectionFactory {
    public Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:sqlite:banco.db");
        }         
        catch(SQLException ex) {
            throw new RuntimeException("Falha ao conectar com banco de dados", ex);
        }
    }
}
