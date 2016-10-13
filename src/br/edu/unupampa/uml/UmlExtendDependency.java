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
public class UmlExtendDependency extends UmlDependency{

    //<editor-fold defaultstate="collapsed" desc="Construtor">

    /**
     *  Construtor para criar um extend que é uma extensão de uma
     * associação.
     * @param id - que se refere ao include
     * @param label - que se refere ao extend(Opcional)
     * @param base - Elemento A(Que ja deve conter na lista de elementos do diagrama)
     * @param extension - Elemento B(Que ja deve conter na lista de elementos do diagrama) 
     */
    public UmlExtendDependency(String id, String label, UmlElement base, UmlElement extension){
        super(id, label, base, extension);
    }
    
    //</editor-fold>
}
