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
    
    /**
     * Construtor com os dados para se criar uma atividade
     * do diagrama de atividades
     * @param id - número identificador da atividade a ser
     * adicionada
     * @param label - label da atividade pode estar em branco 
     */
    public UmlActivity(String id, String label) {
        super(id, label);
    }
    
}
