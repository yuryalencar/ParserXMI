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
public class UmlDecisionNode extends UmlElement{

    /**
     * Construtor com os dados para se criar um nodo de decisão
     * do diagrama de atividades
     * @param id - número identificador do nodo de decisão a ser
     * adicionada
     * @param label - label do nodo de decisão pode estar em branco 
     */    
    public UmlDecisionNode(String id, String label) {
        super(id, label);
    }
    
}
