/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufes.gestaodefuncionarios.presenter;

import br.ufes.gestaodefuncionarios.collection.FuncionarioCollection;
import br.ufes.gestaodefuncionarios.observer.Observer;
import br.ufes.gestaodefuncionarios.prop.PropertyManager;
import br.ufes.gestaodefuncionarios.view.PrincipalView;
import javax.swing.JInternalFrame;

/**
 *
 * @author Rafael
 */
public class PrincipalPresenter implements Observer {
    
    private PrincipalView view;
    private PropertyManager propertieManager;
    private FuncionarioCollection fCollection;
    
    public PrincipalPresenter() {
        this.fCollection = FuncionarioCollection.getInstance();
        this.fCollection.addObserver(this);
        this.view = new PrincipalView();
        this.view.getLblVersao().setText(App.getVersion());
        
        switch (App.getLogFormat()) {
            case "txt":
                this.view.getLblLogFormat().setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/txt-48.png")));
                break;
            case "json":
                this.view.getLblLogFormat().setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/json-48.png")));
                break;
            case "xml":
                this.view.getLblLogFormat().setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/xml-48.png")));
                break;
            default:
                break;
        }
        
        this.view.getBtnFechar().addActionListener((e) -> {
            fechar();
        });
        
        this.view.getBtnNovo().addActionListener((e) -> {
            criarFuncionario();
        });
        
        this.view.getBtnBuscar().addActionListener((e) -> {
            buscarFuncionario();
        });
        
        this.view.getBtnCalcular().addActionListener((e) -> {
            calcularSalario();
        });
        
        this.view.getBtnConfiguracoes().addActionListener((e) -> {
            configuracoes();
        });
        
        this.atualizaNumFuncionarios();
        
        this.view.setVisible(true);

    }
    
    private void fechar() {
        this.fCollection.removeObserver(this);
        this.view.dispose();
        System.exit(0);
    }
    
    private void criarFuncionario() {
        new CriarFuncionarioPresenter(this);
    }
    
    private void buscarFuncionario() {
        new BuscarFuncionarioPresenter(this);
    }
    
    private void calcularSalario() {
        new CalcularSalarioPresenter(this);
    }
    
    private void configuracoes() {
        new ConfigPresenter(this);
    }
    
    public void addToDesktopPane(JInternalFrame frame) {
        this.view.getDesktopPane().add(frame);
    }
    
    public void atualizaNumFuncionarios() {
        int count = fCollection.getFuncionarioCount();
        this.view.getLblNumFuncionarios().setText(Integer.toString(count));
       
    }

    @Override
    public void update(String message) {
        switch(message) {
            case "add":
            case "delete":
                atualizaNumFuncionarios();
                break;
            default:
                break;
        }
        
    }
    
}
