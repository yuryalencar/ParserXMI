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
public class UmlFinalActivity extends UmlActivity{

    /**
     * Construtor com os dados para se criar uma atividade
     * final de um diagrama de atividades
     * @param id - número identificador da atividade final a ser
     * adicionada
     * @param label - label da atividade final que pode estar em branco 
     */    
    public UmlFinalActivity(String id, String label) {
        super(id, label);
    }
    
}
