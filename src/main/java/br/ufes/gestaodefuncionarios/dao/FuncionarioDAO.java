/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufes.gestaodefuncionarios.dao;

import br.ufes.gestaodefuncionarios.factory.ConnectionFactory;
import br.ufes.gestaodefuncionarios.model.Bonus;
import br.ufes.gestaodefuncionarios.model.Funcionario;
import br.ufes.gestaodefuncionarios.model.FuncionarioBonus;
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
            criaTFuncionarioBonus();
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
                + "idFuncionario INTEGER NOT NULL, " 
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
    
    private void criaTFuncionarioBonus() {
        String sql = "CREATE TABLE IF NOT EXISTS funcionarioBonus ("
                + "idfuncionarioBonus INTEGER PRIMARY KEY AUTOINCREMENT, " 
                + "idFuncionario INTEGER NOT NULL, "
                + "dataCalculo DATE NOT NULL, "
                + "cargo VARCHAR NOT NULL, "
                + "tipoBonus VARCHAR NOT NULL, "
                + "valorBonus DECIMAL(19,4) NOT NULL, "
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
            throw new RuntimeException("Falha ao criar a tabela funcionarioBonus no banco", ex);
            
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
    
    public void insereFuncionarioBonus(Funcionario f, Bonus b) {
        String sql = "INSERT INTO funcionarioBonus ("
                + "idFuncionario, " 
                + "dataCalculo, "
                + "cargo, "
                + "tipoBonus, "
                + "valorBonus"
                + ") VALUES(?,?,?,?,?)";
        Connection con = null;
        PreparedStatement pst = null;
        try {
            con = ConnectionFactory.getConnection();
            pst = con.prepareStatement(sql);
            pst.setInt(1, f.getId());
            pst.setDate(2, new java.sql.Date(b.getData().getTime()));
            pst.setString(3, f.getCargo());
            pst.setString(4, b.getNome());
            pst.setDouble(5, b.getValor());
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(FuncionarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("Falha ao inserir em funcionarioBonus", ex);
        } finally {
            ConnectionFactory.closeConnection(con, pst);
        }
    }
    
    public void insereFuncionarioSalario(FuncionarioSalario fs) {
        /*String sql = "INSERT INTO funcionarioSalario("
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
        */
        String sql = "INSERT INTO funcionarioSalario("
                + "idFuncionario, "
                + "dataCalculo, "
                + "salarioBase, "
                + "bonus, "
                + "salarioTotal"
                + ") VALUES(?,?,?,?,?)";
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
        String sql = "SELECT * FROM funcionario "
                + "ORDER BY nome COLLATE NOCASE ASC";
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
    
    public List<FuncionarioBonus> getFuncionarioBonusList(Funcionario f) {
        String sql = "SELECT * FROM funcionarioBonus "
                + "WHERE idFuncionario = ? "
                + "ORDER BY dataCalculo DESC";
        FuncionarioBonus funcionarioBonus;
        List<FuncionarioBonus> funcionarioBonusList = new ArrayList<>();
        
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet resultSet = null;
        try {
            con = ConnectionFactory.getConnection();
            pst = con.prepareStatement(sql);
            pst.setInt(1, f.getId());
            resultSet = pst.executeQuery();

            while (resultSet.next()) {
                String tipo = resultSet.getString("tipoBonus");
                String cargo = resultSet.getString("cargo");
                Date dataCalculo = resultSet.getDate("dataCalculo");
                double valor = resultSet.getDouble("valorBonus");
               
                funcionarioBonus = new FuncionarioBonus(tipo, valor, cargo, dataCalculo);
                funcionarioBonusList.add(funcionarioBonus);
            }
        } catch(SQLException ex) {
            Logger.getLogger(FuncionarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("Falha ao consultar tabela funcionarioSalario", ex);
        } finally {
            ConnectionFactory.closeConnection(con, pst, resultSet);
        }
        
        return Collections.unmodifiableList(funcionarioBonusList);
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
                + "ON f.id = fs.idFuncionario "
                + "ORDER BY f.nome COLLATE NOCASE ASC";
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
    
    public List<FuncionarioSalario> getFuncionarioSalarioListByDate(Date date) {
        String sql = "SELECT "
                + "f.id, "
                + "f.nome, "
                + "fs.dataCalculo, "
                + "fs.salarioBase, "
                + "fs.bonus, "
                + "fs.salarioTotal "
                + "FROM funcionario f "
                + "LEFT JOIN funcionarioSalario fs "
                + "ON f.id = fs.idFuncionario "
                + "WHERE fs.dataCalculo = ? "
                + "ORDER BY f.nome COLLATE NOCASE ASC";
        FuncionarioSalario funcionarioSalario;
        List<FuncionarioSalario> funcionarioSalarioList = new ArrayList<>();
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet resultSet = null;
        try {
            con = ConnectionFactory.getConnection();
            pst = con.prepareStatement(sql);
            pst.setDate(1, new java.sql.Date(date.getTime()));
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
        String sql = "SELECT * FROM funcionario "
                + "WHERE nome LIKE ? "
                + "ORDER BY nome COLLATE NOCASE ASC";
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
    
    private void deletarFuncionarioBonus(Funcionario funcionario) {
        String sql = "DELETE FROM funcionarioBonus WHERE idFuncionario = ?";
        Connection con = null;
        PreparedStatement pst = null;
        try {
            con = ConnectionFactory.getConnection();
            pst = con.prepareStatement(sql);
            pst.setInt(1, funcionario.getId());
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(FuncionarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("Falha ao deletar da tabela funcionarioBonus", ex);
        } finally {
            ConnectionFactory.closeConnection(con, pst);
        }
    }
    
    private void deletarFuncionarioSalario(Funcionario funcionario) {
        String sql = "DELETE FROM funcionarioSalario WHERE idFuncionario = ?";
        Connection con = null;
        PreparedStatement pst = null;
        try {
            con = ConnectionFactory.getConnection();
            pst = con.prepareStatement(sql);
            pst.setInt(1, funcionario.getId());
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(FuncionarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("Falha ao deletar da tabela funcionarioSalario", ex);
        } finally {
            ConnectionFactory.closeConnection(con, pst);
        }
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
            deletarFuncionarioBonus(funcionario);
            deletarFuncionarioSalario(funcionario);
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
    
    public boolean isSalarioCalculatedFor(Funcionario f, Date dataCalculo) {
        String sql = "SELECT COUNT(*) AS rowcount "
                + "FROM funcionarioSalario "
                + "WHERE idFuncionario = ? AND dataCalculo = ?";
        int count = 0;
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet resultSet = null;
        try {
            con = ConnectionFactory.getConnection();
            pst = con.prepareStatement(sql);
            pst.setInt(1, f.getId());
            pst.setDate(2, new java.sql.Date(dataCalculo.getTime()));
            resultSet = pst.executeQuery();
            resultSet.next();
            count = resultSet.getInt("rowcount");
            if(count == 0) return false;
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(FuncionarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("Falha ao obter count de salario calculado", ex);
        } finally {
            ConnectionFactory.closeConnection(con, pst, resultSet);
        }
    }
    
}
