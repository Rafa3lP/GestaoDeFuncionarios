/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufes.gestaodefuncionarios.dao;

import br.ufes.gestaodefuncionarios.factory.ConnectionFactory;
import br.ufes.gestaodefuncionarios.model.Funcionario;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Rafael
 */
public class FuncionarioDAO {
    
    private Connection conexao;

    public FuncionarioDAO() {
        try {
            this.conexao = new ConnectionFactory().getConnection();
            criaTFuncionario();
        } catch(RuntimeException ex) {
            throw new RuntimeException(ex);
        }
        
    }
    
    private void criaTFuncionario() {
        String sql = "CREATE TABLE IF NOT EXISTS funcionario("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "nome VARCHAR NOT NULL, "
                + "idade INTEGER NOT NULL, "
                + "salario DECIMAL(19, 4) NOT NULL, "
                + "cargo VARCHAR NOT NULL, "
                + "admissao DATE NOT NULL, "
                + "funcionarioMes BOOLEAN NOT NULL"
                + ")";
        
        try {
            Statement st = conexao.createStatement();
            st.execute(sql);
            st.close();
        } catch (SQLException ex) {
            throw new RuntimeException("Falha ao criar a tabela funcionario no banco", ex);
        }
        
    }
    
    public void criar(Funcionario funcionario) {
        String sql = "INSERT INTO funcionario("
                + "nome, "
                + "idade, "
                + "salario, "
                + "cargo, "
                + "admissao, "
                + "funcionarioMes"
                + ") VALUES(?,?,?,?,?,?)";
        
        try { 
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setString(1, funcionario.getNome());
            stmt.setInt(2, funcionario.getIdade());
            stmt.setDouble(3, funcionario.getSalario());
            stmt.setString(4, funcionario.getCargo());
            stmt.setDate(5, new java.sql.Date(funcionario.getDtAdmissao().getTime()));
            stmt.setBoolean(6, funcionario.isFuncionarioMes());
            stmt.execute();
            stmt.close();
        } catch (SQLException ex) { 
            throw new RuntimeException("Falha ao inserir funcionario no banco", ex);
        } 
    }
    
    public ArrayList<Funcionario> getFuncionarios() {
        String sql = "SELECT * FROM funcionario";
        Funcionario funcionario;
        ArrayList<Funcionario> funcionarios = new ArrayList<>();
        try {
            PreparedStatement stmt = conexao.prepareStatement(sql);
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nome = resultSet.getString("nome");
                int idade = resultSet.getInt("idade");
                double salario = resultSet.getDouble("salario");
                String cargo = resultSet.getString("cargo");
                Date dtAdmissao = resultSet.getDate("admissao");
                boolean funcionarioMes = resultSet.getBoolean("funcionarioMes");
                funcionario = new Funcionario(id, nome, idade, salario, cargo, dtAdmissao, funcionarioMes);
                System.out.println(funcionario.toString());
            }
            stmt.close();
            resultSet.close();
        } catch(SQLException ex) {
            throw new RuntimeException("Falha ao consultar funcionario no banco", ex);
        }
        
        return funcionarios;
    }
    
    public void deletar(Funcionario funcionario) {
        String sql = "DELETE FROM funcionario WHERE id = ?";
        try { 
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, funcionario.getId());
            stmt.execute();
            stmt.close();
        } catch (SQLException ex) { 
            throw new RuntimeException("Falha ao deletar funcionario no banco", ex);
        } 
    }
    
    public void atualizar(Funcionario funcionario) {
        String sql = "UPDATE funcionario SET "
                + "nome = ?, "
                + "idade = ?, "
                + "salario = ?, "
                + "cargo = ?, "
                + "admissao = ?, "
                + "funcionarioMes = ? "
                + "WHERE id = ?";
        try { 
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setString(1, funcionario.getNome());
            stmt.setInt(2, funcionario.getIdade());
            stmt.setDouble(3, funcionario.getSalario());
            stmt.setString(4, funcionario.getCargo());
            stmt.setDate(5, new java.sql.Date(funcionario.getDtAdmissao().getTime()));
            stmt.setBoolean(6, funcionario.isFuncionarioMes());
            stmt.setInt(7, funcionario.getId());
            stmt.execute();
            stmt.close();
        } catch (SQLException ex) { 
            throw new RuntimeException("Falha ao atualizar funcionario no banco", ex);
        } 
    }
    
}
