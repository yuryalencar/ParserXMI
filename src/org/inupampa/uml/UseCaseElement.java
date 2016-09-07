package org.inupampa.uml;

/**
 *
 * @author andersond
 * Represent um elemento de Caso de Uso de um
 * diagrama de caso de uso da UML. 
 */
public class UseCaseElement extends UmlElement{
    
    /**
     * Construtor padrão. Classe imutável.
     * @param id Identificador único do elemento.
     * @param label Rótulo do elemento.
     */
    public UseCaseElement(String id, String label) {
        super(id, label);
    }
    
}
