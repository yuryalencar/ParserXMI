/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.unupampa.uml;

/**
 *
 * @author yuryalencar
 */
public class UmlFinalActivityElement extends UmlActivityElement{

    /**
     * Construtor com os dados para se criar uma atividade
     * final de um diagrama de atividades
     * @param id - número identificador da atividade final a ser
     * adicionada
     * @param label - label da atividade final que pode estar em branco 
     */    
    public UmlFinalActivityElement(String id, String label) {
        super(id, label);
    }
    
}
