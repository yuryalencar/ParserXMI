package org.inupampa.uml;

/**
 * @author andersond
 * Representa um ator de um diagrama de caso de uso da UML.
 */
public class ActorElement extends UmlElement{
    
    /**
     * Construtor padrão. Classe imutável.
     * @param id Identificador único do objeto
     * @param label Rótulo do objeto.
     */
    public ActorElement(String id, String label) {
        super(id, label);
    }
    
}
