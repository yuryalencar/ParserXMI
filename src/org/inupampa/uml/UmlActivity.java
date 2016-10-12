/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.inupampa.uml;

/**
 *
 * @author yuryalencar
 */
public class UmlActivity extends UmlElement{
    
    //<editor-fold defaultstate="collapsed" desc="Construtor">
    
    /**
     * Construtor básico para a criação de uma atividade
     * @param id - número identificador único de uma ação
     * @param label - label que pode conter ou nao
     */
    public UmlActivity(String id, String label) {
        super(id, label);
    }
    
    //</editor-fold>
    
    
}
