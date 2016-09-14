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
public class UmlExtend extends UmlAssociation{

    //<editor-fold defaultstate="collapsed" desc="Construtor">

    /**
     *  Construtor para criar um extend que é uma extensão de uma
     * associação.
     * @param id - que se refere ao include
     * @param label - que se refere ao extend(Opcional)
     * @param a - Elemento A(Que ja deve conter na lista de elementos do diagrama)
     * @param b - Elemento B(Que ja deve conter na lista de elementos do diagrama) 
     */
    public UmlExtend(String id, String label, UmlElement a, UmlElement b){
        super(id, label, a, b);
    }
    
    //</editor-fold>
}
