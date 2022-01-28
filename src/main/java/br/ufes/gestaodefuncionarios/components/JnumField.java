/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufes.gestaodefuncionarios.components;

import java.awt.event.KeyEvent;
import javax.swing.JTextField;

/**
 *
 * @author Rafael
 */
public class JnumField extends JTextField{
    private int maxCaracteres = -1;
    private int type = 0;

    public JnumField() {
        super();
        addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jNumFieldKeyTyped(evt);
            }
        });
        setText("0");
    }
    
    public JnumField(int maxCaracteres) {
        super();
        setMaxCaracteres(maxCaracteres);
        addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jNumFieldKeyTyped(evt);
            }
        });
        setText("0");
    }
    
    private void jNumFieldKeyTyped(KeyEvent evt) {
        if(getText().isEmpty()) setText("0");
        String caracteres = "0123456789";
        if(type == 0) {
            if(!caracteres.contains(evt.getKeyChar()+"")){
                evt.consume();
            }else if(getText().equals("0")) {
                evt.consume();
                setText(String.valueOf(evt.getKeyChar()));
            }
        } else {
            try {
                Double.parseDouble(getText() + evt.getKeyChar());
                if(getText().equals("0")) {
                    evt.consume();
                    setText(Integer.toString(Integer.parseInt(String.valueOf(evt.getKeyChar()))));
                }
            } catch(NumberFormatException ex) {
                evt.consume();
            }
        }
        
        if((getText().length()>=getMaxCaracteres())&&(getMaxCaracteres() != -1)){
            evt.consume();
            setText(getText().substring(0,getMaxCaracteres()));
        }
    }

    public int getMaxCaracteres() {
        return maxCaracteres;
    }

    public void setMaxCaracteres(int maxCaracteres) {
        this.maxCaracteres = maxCaracteres;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        if(type == 0 || type == 1)
            this.type = type;
    }
    
}
