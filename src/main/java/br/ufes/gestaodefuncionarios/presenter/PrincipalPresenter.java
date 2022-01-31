/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufes.gestaodefuncionarios.presenter;

import br.ufes.gestaodefuncionarios.dao.FuncionarioDAO;
import br.ufes.gestaodefuncionarios.logger.IMetodoLog;
import br.ufes.gestaodefuncionarios.logger.Log;
import br.ufes.gestaodefuncionarios.logger.LogJSON;
import br.ufes.gestaodefuncionarios.logger.LogTxt;
import br.ufes.gestaodefuncionarios.logger.LogXml;
import br.ufes.gestaodefuncionarios.prop.PropertyManager;
import br.ufes.gestaodefuncionarios.view.PrincipalView;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Rafael
 */
public class PrincipalPresenter {
    
    private final String CONFIG_FILE = "config.ini";
    private IMetodoLog metodoLog;
    private PrincipalView view;
    private String logFormat;
    private PropertyManager propertieManager;
    
    public PrincipalPresenter() {
        this.view = new PrincipalView();
        this.propertieManager = new PropertyManager();
        this.view.getLblVersao().setText(this.propertieManager.getProperty("version"));
        
        this.logFormat = this.propertieManager.getProperty("logFormat");
        
        this.metodoLog = null;
        
        if(logFormat.equals("txt")) {
            this.view.getLblLogFormat().setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/txt-48.png")));
            this.metodoLog = new LogTxt();
        } else if(logFormat.equals("json")) {
            this.view.getLblLogFormat().setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/json-48.png")));
            this.metodoLog = new LogJSON();
        } else if(logFormat.equals("xml")){
            this.view.getLblLogFormat().setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/xml-48.png")));
            this.metodoLog = new LogXml();
        }
        
        if(this.metodoLog == null) {
            JOptionPane.showMessageDialog(view, "Não foi possível encontrar o formato de log", "Erro Fatal", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
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
        System.exit(0);
    }
    
    private void criarFuncionario() {
        new CriarFuncionarioPresenter(this, this.metodoLog);
    }
    
    private void buscarFuncionario() {
        new BuscarFuncionarioPresenter(this, this.metodoLog);
    }
    
    private void calcularSalario() {
        new CalcularSalarioPresenter(this, this.metodoLog);
    }
    
    private void configuracoes() {
        new ConfigPresenter(this, this.metodoLog);
    }
    
    public void addToDesktopPane(JInternalFrame frame) {
        this.view.getDesktopPane().add(frame);
    }
    
    public void atualizaNumFuncionarios() {
        try {
            FuncionarioDAO fDao = new FuncionarioDAO();
            int count = fDao.getFuncionarioCount();
            this.view.getLblNumFuncionarios().setText(Integer.toString(count)); 
        } catch(RuntimeException ex) {
            JOptionPane.showMessageDialog(view, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            this.metodoLog.escreveLog(new Log(IMetodoLog.LOG_ERROR, "Falha ao executar operação - " + ex.getMessage()));
        }
        
    }
    
}
