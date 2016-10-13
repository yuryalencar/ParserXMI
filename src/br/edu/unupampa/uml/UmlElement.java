package br.edu.unupampa.uml;

/**
 * @author yuryalencar
 * Esta classe representa um elemento genérico da UML, do qual podemos 
 * especializar novos elementos. Possui os atributos comuns a todos os
 * elementos de diagramas da UML.
 */
public abstract class UmlElement {

    //<editor-fold defaultstate="collapsed" desc="Variáveis">
    
    /**
     * Cada elemento da UML possui um identificador único, chamado
     * ID. Este identificador serve para diferenciarmos dois elementos
     * quaisquer */
    private final String id;
    
    /**
     * Rótulos são os nomes visíveis dos elementos dentro dos diagramas.
     * Enquanto um id é único para diferentes elemento, rótulos podem se
     * repetir. Podemos ter várias atividades com um mesmo nome em um 
     * diagrama de atividades, por exemplo.
     */
    private final String label;
    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Construtor">
    
    /**
     * Constructor padrão. Classe imutável.
     * @param id Identificador único do objeto
     * @param label Rótulo do objeto
     */
    public UmlElement(String id, String label){
        this.id = id;
        this.label = label;
    }
    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Métodos Getter">
    
    /**
     * Getter para o atributo id.
     * @return id Identificador único do elemento
     */
    public String getId(){
        return this.id;
    }
    
    /**
     * Getter para o atributo label.
     * @return label Rótulo do elemento.
     */
    public String getLabel(){
        return this.label;
    }

    //</editor-fold>
    
}
