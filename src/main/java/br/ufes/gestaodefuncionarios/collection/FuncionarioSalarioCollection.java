/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.ufes.gestaodefuncionarios.collection;

import br.ufes.gestaodefuncionarios.dao.FuncionarioDAO;
import br.ufes.gestaodefuncionarios.model.Funcionario;
import br.ufes.gestaodefuncionarios.model.FuncionarioSalario;
import br.ufes.gestaodefuncionarios.observer.Observable;
import br.ufes.gestaodefuncionarios.observer.Observer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Rafael
 */
public class FuncionarioSalarioCollection implements Observable{
    private FuncionarioDAO fDao;
    private List<FuncionarioSalario> funcionarioSalarioList;
    private List<Observer> observers;
    
    private static FuncionarioSalarioCollection instance = null;
    
    private FuncionarioSalarioCollection() {
        fDao = new FuncionarioDAO();
        funcionarioSalarioList = new ArrayList<>();
        populateList();
        this.observers = new ArrayList<>();
    }
    
    public static synchronized FuncionarioSalarioCollection getInstance() {
        if(instance == null) {
            instance =  new FuncionarioSalarioCollection();
        }
        return instance;
    }
    
    private void populateList() {
        try {
            funcionarioSalarioList = fDao.getFuncionarioSalarioList();
        } catch(RuntimeException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    public List<FuncionarioSalario> getFuncionarioSalarioList() {
        populateList();
        return funcionarioSalarioList;
    }
    
    public List<FuncionarioSalario> getFuncionarioSalarioListByDate(Date date) {
        try {
            return fDao.getFuncionarioSalarioListByDate(date);
        } catch(RuntimeException ex) {
            throw new RuntimeException(ex);
        }

    }
    
    public void addFuncionarioSalario(FuncionarioSalario fs) {
        try {
            fDao.insereFuncionarioSalario(fs);
            populateList();
            notifyObservers("add");
        } catch(RuntimeException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    public boolean isSalarioCalculatedFor(Funcionario f, Date dataCalculo) {
        try {
            return fDao.isSalarioCalculatedFor(f, dataCalculo);
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
    public void notifyObservers(String message) {
        for(Observer o: this.observers) {
            o.update(message);
        }
    }
}
