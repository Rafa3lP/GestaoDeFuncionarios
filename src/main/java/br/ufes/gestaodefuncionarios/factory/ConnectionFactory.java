/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufes.gestaodefuncionarios.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rafael
 */
public class ConnectionFactory {
    private static final String URL = "jdbc:sqlite:banco.db";
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL);
        }         
        catch(SQLException ex) {
            throw new RuntimeException("Falha ao conectar com banco de dados", ex);
        }
    }
    
    public static void closeConnection(Connection con) {
        
        try {
            if(con != null) {
                con.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void closeConnection(Connection con, PreparedStatement pst) {
        closeConnection(con);
        try {
            if(pst != null) {
                pst.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void closeConnection(Connection con, PreparedStatement pst, ResultSet rs) {
        closeConnection(con, pst);
        try {
            if(rs != null) {
                rs.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
