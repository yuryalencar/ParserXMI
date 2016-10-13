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
public class UmlActivityDiagram extends UmlDiagram{
    
    /**
     * Construtor com os dados para se criar uma atividade
     * do diagrama de atividades
     * @param id - número identificador da atividade a ser
     * adicionada
     * @param label - label da atividade pode estar em branco 
     */
    public UmlActivityDiagram(String id, String label) {
        super(id, label);
    }
    
}
