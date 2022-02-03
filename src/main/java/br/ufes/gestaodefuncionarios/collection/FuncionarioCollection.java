/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.ufes.gestaodefuncionarios.collection;

import br.ufes.gestaodefuncionarios.dao.FuncionarioDAO;
import br.ufes.gestaodefuncionarios.model.Funcionario;
import br.ufes.gestaodefuncionarios.observer.Observable;
import br.ufes.gestaodefuncionarios.observer.Observer;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Rafael
 */
public class FuncionarioCollection implements Observable{
    private FuncionarioDAO fDao;
    private List<Funcionario> funcionarioList;
    private List<Observer> observers;
    
    private static FuncionarioCollection instance = null;
    
    private FuncionarioCollection() {
        fDao = new FuncionarioDAO();
        funcionarioList = new ArrayList<>();
        populateList();
        this.observers = new ArrayList<>();
    }
    
    public static synchronized FuncionarioCollection getInstance() {
        if(instance == null) {
            instance =  new FuncionarioCollection();
        }
        return instance;
    }
    
    private void populateList() {
        try {
            funcionarioList = fDao.getFuncionarios();
        } catch(RuntimeException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    public List<Funcionario> getFuncionarios() {
        return funcionarioList;
    }
    
    public List<Funcionario> getFuncionariosByNome(String nome) {
        try {
            return fDao.getFuncionariosByNome(nome);
        } catch(RuntimeException ex) {
            throw new RuntimeException(ex);
        }
       
    }
    
    public Funcionario getFuncionarioById(int id) {
        try {
            return fDao.getFuncionarioById(id);
        } catch(RuntimeException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    public int getFuncionarioCount() {
        return funcionarioList.size();
    }
    
    public void criarFuncionario(Funcionario f) {
        try {
            fDao.criar(f);
            populateList();
            notifyObservers();
        } catch(RuntimeException ex) {
            throw new RuntimeException(ex);
        }
    
    }
    
    public void deleteFuncionario(Funcionario f) {
        try {
            fDao.deletar(f);
            populateList();
            notifyObservers();
        } catch(RuntimeException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    public void atualizaFuncionario(Funcionario f) {
        try {
            fDao.atualizar(f);
            populateList();
            notifyObservers();
        } catch(RuntimeException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void addObserver(Observer o) {
        this.observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        this.observers.remove(o);
    }

    @Override
    public void notifyObservers() {
        for(Observer o: this.observers) {
            o.update();
        }
    }
    
}
