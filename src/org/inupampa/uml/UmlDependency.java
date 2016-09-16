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
public class UmlDependency {
    
    //<editor-fold defaultstate="collapsed" desc="Variáveis">
    
    /**
     * Cada associação possui um identificador único dentro de
     * um modelo UML. Este identificador recebe o nome de ID.
     */
    private String id;
    
    /**
     * Cada associação PODE apresentar um rótulo. Caso exista, é
     * armazenado no campo label.
     */
    private String label;
    
    /**
     * Cada dos elementos da associação é armazenado ou como elemento
     * A ou como elemento B. Não existe distinção entre qual elemento
     * ocorre primeiro (i.e. não há relação de ordem)
     */
    private final UmlElement base;
    private final UmlElement dependent;

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Construtor padrão">
    
    /**
     * Construtor padrão. Classe imutável.
     * @param id - Número identificador único da associação
     * @param label - Label da associação, (Opcional)
     * @param base - Elemento que pertence associação
     * @param dependent - Outro elemento que pertence à associação
     */
    public UmlDependency(String id, String label, UmlElement base, UmlElement dependent){
        this.id = id;
        this.label = label;
        this.base = base;
        this.dependent = dependent;
    }

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Métodos Getter">
    
    /**
     * Getter do atributo base
     * @return base
     */
    public UmlElement getBase(){
        return this.base;
    }
    
    /**
     * Getter do atributo dependent
     * @return dependent
     */
    public UmlElement getDependent(){
        return this.dependent;
    }

    /**
     * Getter do id
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Getter da label
     * @return the label
     */
    public String getLabel() {
        return label;
    }

    //</editor-fold>    
}
