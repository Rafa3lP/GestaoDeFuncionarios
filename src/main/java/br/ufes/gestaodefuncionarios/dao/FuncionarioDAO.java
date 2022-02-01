/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufes.gestaodefuncionarios.dao;

import br.ufes.gestaodefuncionarios.factory.ConnectionFactory;
import br.ufes.gestaodefuncionarios.model.Funcionario;
import br.ufes.gestaodefuncionarios.model.FuncionarioSalario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rafael
 */
public class FuncionarioDAO {

    public FuncionarioDAO() {
        Connection con = null;
        try {
            con = ConnectionFactory.getConnection();
            criaTFuncionario();
        } catch(RuntimeException ex) {
            throw new RuntimeException(ex);
        } finally {
            ConnectionFactory.closeConnection(con);
        }
        
    }
    
    private void criaTFuncionario() {
        String sql = "CREATE TABLE IF NOT EXISTS funcionario("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "nome VARCHAR NOT NULL, "
                + "idade INTEGER NOT NULL, "
                + "salarioBase DECIMAL(19, 4) NOT NULL, "
                + "cargo VARCHAR NOT NULL, "
                + "admissao DATE NOT NULL, "
                + "funcionarioMes BOOLEAN NOT NULL,"
                + "faltas INTEGER NOT NULL,"
                + "tipoBonus INTEGER NOT NULL"
                + ")";
        
        Connection con = null;
        PreparedStatement pst = null;
        
        try {
            con = ConnectionFactory.getConnection();
            pst = con.prepareStatement(sql);
            pst.execute();
            criaTFuncionarioSalario();
        } catch (SQLException ex) {
            Logger.getLogger(FuncionarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("Falha ao criar a tabela funcionario no banco", ex);
            
        } finally {
            ConnectionFactory.closeConnection(con, pst);
        }
                
    }
    
    private void criaTFuncionarioSalario() {
        String sql = "CREATE TABLE IF NOT EXISTS funcionarioSalario ("
                + "idFuncionarioSalario INTEGER PRIMARY KEY AUTOINCREMENT, " 
                + "idFuncionario INTEGER NOT NULL UNIQUE, " 
                + "dataCalculo DATE NOT NULL, " 
                + "salarioBase DECIMAL(19,4) NOT NULL, " 
                + "bonus DECIMAL(19,4) NOT NULL, " 
                + "salarioTotal DECIMAL(19,4) NOT NULL, " 
                + "FOREIGN KEY (`idFuncionario`) " 
                + "REFERENCES funcionario(id)"
                + ")";
        Connection con = null;
        PreparedStatement pst = null;
        
        try {
            con = ConnectionFactory.getConnection();
            pst = con.prepareStatement(sql);
            pst.execute();
        } catch (SQLException ex) {
            Logger.getLogger(FuncionarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("Falha ao criar a tabela funcionarioSalario no banco", ex);
            
        } finally {
            ConnectionFactory.closeConnection(con, pst);
        }
    }
    
    public void criar(Funcionario funcionario) {
        String sql = "INSERT INTO funcionario("
                + "nome, "
                + "idade, "
                + "salarioBase, "
                + "cargo, "
                + "admissao, "
                + "funcionarioMes, "
                + "faltas,"
                + "tipoBonus"
                + ") VALUES(?,?,?,?,?,?,?,?)";
        
        Connection con = null;
        PreparedStatement pst = null;
        try {
            con = ConnectionFactory.getConnection();
            pst = con.prepareStatement(sql);
            pst.setString(1, funcionario.getNome());
            pst.setInt(2, funcionario.getIdade());
            pst.setDouble(3, funcionario.getSalarioBase());
            pst.setString(4, funcionario.getCargo());
            pst.setDate(5, new java.sql.Date(funcionario.getDtAdmissao().getTime()));
            pst.setBoolean(6, funcionario.isFuncionarioMes());
            pst.setInt(7, funcionario.getFaltas());
            pst.setInt(8, funcionario.getTipoBonus());
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(FuncionarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("Falha ao inserir funcionario no banco", ex);
        } finally {
            ConnectionFactory.closeConnection(con, pst);
        }
    }
    
    public void upsertFuncionarioSalario(FuncionarioSalario fs) {
        String sql = "INSERT INTO funcionarioSalario("
                + "idFuncionario, "
                + "dataCalculo, "
                + "salarioBase, "
                + "bonus, "
                + "salarioTotal"
                + ") VALUES(?,?,?,?,?) "
                + "ON CONFLICT(idFuncionario) DO "
                + "UPDATE SET "
                + "dataCalculo = excluded.dataCalculo,"
                + "salarioBase = excluded.salarioBase, "
                + "bonus = excluded.bonus, "
                + "salarioTotal = excluded.salarioTotal";
        
        Connection con = null;
        PreparedStatement pst = null;
        try {
            con = ConnectionFactory.getConnection();
            pst = con.prepareStatement(sql);
            pst.setInt(1, fs.getFuncionarioId());
            pst.setDate(2, new java.sql.Date(fs.getDataCalculo().getTime()));
            pst.setDouble(3, fs.getSalarioBase());
            pst.setDouble(4, fs.getBonus());
            pst.setDouble(5, fs.getSalario());
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(FuncionarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("Falha ao inserir funcionario no banco", ex);
        } finally {
            ConnectionFactory.closeConnection(con, pst);
        }
    }
    
    public List<Funcionario> getFuncionarios() {
        String sql = "SELECT * FROM funcionario";
        Funcionario funcionario;
        List<Funcionario> funcionarios = new ArrayList<>();
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet resultSet = null;
        try {
            con = ConnectionFactory.getConnection();
            pst = con.prepareStatement(sql);
            resultSet = pst.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nome = resultSet.getString("nome");
                int idade = resultSet.getInt("idade");
                double salarioBase = resultSet.getDouble("salarioBase");
                String cargo = resultSet.getString("cargo");
                Date dtAdmissao = resultSet.getDate("admissao");
                boolean funcionarioMes = resultSet.getBoolean("funcionarioMes");
                int faltas = resultSet.getInt("faltas");
                int tipoBonus = resultSet.getInt("tipoBonus");
                funcionario = new Funcionario(id, nome, idade, salarioBase, cargo, dtAdmissao, funcionarioMes, faltas, tipoBonus);
                funcionarios.add(funcionario);
            }
        } catch(SQLException ex) {
            Logger.getLogger(FuncionarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("Falha ao consultar funcionarios no banco", ex);
        } finally {
            ConnectionFactory.closeConnection(con, pst, resultSet);
        }
        
        return Collections.unmodifiableList(funcionarios);
    }
    
    public List<FuncionarioSalario> getFuncionarioSalarioList() {
        String sql = "SELECT "
                + "f.id, "
                + "f.nome, "
                + "fs.dataCalculo, "
                + "fs.salarioBase, "
                + "fs.bonus, "
                + "fs.salarioTotal "
                + "FROM funcionario f "
                + "LEFT JOIN funcionarioSalario fs "
                + "ON f.id = fs.idFuncionario";
        FuncionarioSalario funcionarioSalario;
        List<FuncionarioSalario> funcionarioSalarioList = new ArrayList<>();
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet resultSet = null;
        try {
            con = ConnectionFactory.getConnection();
            pst = con.prepareStatement(sql);
            resultSet = pst.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nome = resultSet.getString("nome");
                Date dataCalculo = resultSet.getDate("dataCalculo");
                double salarioBase = resultSet.getDouble("salarioBase");
                double bonus = resultSet.getDouble("bonus");
                double salarioTotal = resultSet.getDouble("salarioTotal");
               
                funcionarioSalario = new FuncionarioSalario(id, nome, dataCalculo, salarioBase, bonus, salarioTotal);
                funcionarioSalarioList.add(funcionarioSalario);
            }
        } catch(SQLException ex) {
            Logger.getLogger(FuncionarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("Falha ao consultar tabela funcionarioSalario", ex);
        } finally {
            ConnectionFactory.closeConnection(con, pst, resultSet);
        }
        
        return Collections.unmodifiableList(funcionarioSalarioList);
    }
    
    public Funcionario getFuncionarioById(int idFuncionario) {
        String sql = "SELECT * FROM funcionario WHERE id = ?";
        Funcionario funcionario;
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet resultSet = null;
        try {
            con = ConnectionFactory.getConnection();
            pst = con.prepareStatement(sql);
            pst.setInt(1, idFuncionario);
            resultSet = pst.executeQuery();

            int id = resultSet.getInt("id");
            String nome = resultSet.getString("nome");
            int idade = resultSet.getInt("idade");
            double salarioBase = resultSet.getDouble("salarioBase");
            String cargo = resultSet.getString("cargo");
            Date dtAdmissao = resultSet.getDate("admissao");
            boolean funcionarioMes = resultSet.getBoolean("funcionarioMes");
            int faltas = resultSet.getInt("faltas");
            int tipoBonus = resultSet.getInt("tipoBonus");
            funcionario = new Funcionario(id, nome, idade, salarioBase, cargo, dtAdmissao, funcionarioMes, faltas, tipoBonus);
            
        } catch(SQLException ex) {
            //Logger.getLogger(FuncionarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("Falha ao obter funcionario do banco de dados", ex);
        } finally {
            ConnectionFactory.closeConnection(con, pst, resultSet);
        }
        
        return funcionario;
    }
    
    public List<Funcionario> getFuncionariosByNome(String nomeBusca) {
        String sql = "SELECT * FROM funcionario WHERE nome LIKE ?";
        Funcionario funcionario;
        List<Funcionario> funcionarios = new ArrayList<>();
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet resultSet = null;
        try {
            con = ConnectionFactory.getConnection();
            pst = con.prepareStatement(sql);
            pst.setString(1, "%" + nomeBusca + "%");
            resultSet = pst.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nome = resultSet.getString("nome");
                int idade = resultSet.getInt("idade");
                double salarioBase = resultSet.getDouble("salarioBase");
                String cargo = resultSet.getString("cargo");
                Date dtAdmissao = resultSet.getDate("admissao");
                boolean funcionarioMes = resultSet.getBoolean("funcionarioMes");
                int faltas = resultSet.getInt("faltas");
                int tipoBonus = resultSet.getInt("tipoBonus");
                funcionario = new Funcionario(id, nome, idade, salarioBase, cargo, dtAdmissao, funcionarioMes, faltas, tipoBonus);
                funcionarios.add(funcionario);
            }
        } catch(SQLException ex) {
            Logger.getLogger(FuncionarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("Falha ao buscar funcionarios no banco", ex);
        } finally {
            ConnectionFactory.closeConnection(con, pst, resultSet);
        }
        
        return Collections.unmodifiableList(funcionarios);
    }
    
    public void deletar(Funcionario funcionario) {
        String sql = "DELETE FROM funcionario WHERE id = ?";
        Connection con = null;
        PreparedStatement pst = null;
        try {
            con = ConnectionFactory.getConnection();
            pst = con.prepareStatement(sql);
            pst.setInt(1, funcionario.getId());
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(FuncionarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("Falha ao deletar funcionario no banco", ex);
        } finally {
            ConnectionFactory.closeConnection(con, pst);
        }
    }
    
    public void atualizar(Funcionario funcionario) {
        String sql = "UPDATE funcionario SET "
                + "nome = ?, "
                + "idade = ?, "
                + "salarioBase = ?, "
                + "cargo = ?, "
                + "admissao = ?, "
                + "funcionarioMes = ?, "
                + "faltas = ?,"
                + "tipoBonus = ? "
                + "WHERE id = ?";
        
        Connection con = null;
        PreparedStatement pst = null;
        try { 
            con = ConnectionFactory.getConnection();
            pst = con.prepareStatement(sql);
            pst.setString(1, funcionario.getNome());
            pst.setInt(2, funcionario.getIdade());
            pst.setDouble(3, funcionario.getSalarioBase());
            pst.setString(4, funcionario.getCargo());
            pst.setDate(5, new java.sql.Date(funcionario.getDtAdmissao().getTime()));
            pst.setBoolean(6, funcionario.isFuncionarioMes());
            pst.setInt(7, funcionario.getFaltas());
            pst.setInt(8, funcionario.getTipoBonus());
            pst.setInt(9, funcionario.getId());
            pst.executeUpdate();
        } catch (SQLException ex) { 
            Logger.getLogger(FuncionarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("Falha ao atualizar funcionario no banco", ex);
        } finally {
            ConnectionFactory.closeConnection(con, pst);
        }
    }
    
    public int getFuncionarioCount() {
        String sql = "SELECT COUNT(*) AS rowcount FROM funcionario";
        int count = 0;
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet resultSet = null;
        try {
            con = ConnectionFactory.getConnection();
            pst = con.prepareStatement(sql);
            resultSet = pst.executeQuery();
            resultSet.next();
            count = resultSet.getInt("rowcount");
        } catch (SQLException ex) {
            Logger.getLogger(FuncionarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("Falha ao obter numero de funcionarios no banco", ex);
        } finally {
            ConnectionFactory.closeConnection(con, pst, resultSet);
        }
        
        return count;
    }
    
}
