package br.edu.unupampa.uml;

/**
 *
 * @author yuryalencar
 * Represent um elemento de Caso de Uso de um
 * diagrama de caso de uso da UML. 
 */
public class UmlUseCaseElement extends UmlElement{

    //<editor-fold defaultstate="collapsed" desc="Construtor">
    
    /**
     * Construtor padrão. Classe imutável.
     * @param id Identificador único do elemento.
     * @param label Rótulo do elemento.
     */
    public UmlUseCaseElement(String id, String label) {
        super(id, label);
    }
    
    //</editor-fold>
}
