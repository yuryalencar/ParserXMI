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
public class UmlInitialActivityElement extends UmlActivityElement {

    /**
     * Construtor com os dados para se criar uma atividade
     * inicial presente em um diagrama de atividades
     * @param id - número identificador da atividade incial a ser
     * adicionada
     * @param label - label da atividade inicial pode estar em branco 
     */
    public UmlInitialActivityElement(String id, String label) {
        super(id, label);
    }
    
}
