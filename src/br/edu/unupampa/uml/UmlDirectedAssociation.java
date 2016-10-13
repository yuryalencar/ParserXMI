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
public class UmlDirectedAssociation extends UmlAssociation{

    /**
     * Construtor que criar uma associação direcionada para um diagrama
     * de atividades.
     * @param id - número identificador único para a associação
     * @param label - rótulo que pode estar em branco
     * @param a - elemento que sai a associação direcionada
     * @param b - elemento onde chega a associação
     */
    public UmlDirectedAssociation(String id, String label, UmlElement a, UmlElement b) {
        super(id, label, a, b);
    }
    
}
