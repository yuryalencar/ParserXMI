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
public class UmlInclude extends UmlAssociation {
    
    /**
     *  Construtor para criar um include que é uma extensão de uma
     * associação.
     * @param id - que se refere ao include
     * @param label - que se refere ao include(Opcional)
     * @param a - Elemento A(Que ja deve conter na lista de elementos do diagrama)
     * @param b - Elemento B(Que ja deve conter na lista de elementos do diagrama) 
     */
    public UmlInclude(String id, String label, UmlElement a, UmlElement b){
        super(id, label, a, b);
    }
}
