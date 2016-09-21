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
public abstract class UmlDependency {
    
    //<editor-fold defaultstate="collapsed" desc="Variáveis">
    
    /**
     * Cada dependência possui um identificador único dentro de
     * um modelo UML. Este identificador recebe o nome de ID.
     */
    private String id;
    
    /**
     * Cada depencência PODE apresentar um rótulo. Caso exista, é
     * armazenado no campo label.
     */
    private String label;
    
    /**
     *  Os elementos das dependências são armezenados como Elemento Base (elemento
     * que da suporte para o outro, os nomes variam de uma dependência para a outra,
     * no include e extend: base, generalization: parent) e dependent(onde este elemento
     * é que recebe o auxílio do elemento Base, este elemento varia de uma dependência
     * para outra, no caso do include: addition, extend: extension, generalization: child).
     *  Diferentemente de uma associação sua ordem de armazenagem importa.
     */
    private final UmlElement base;
    private final UmlElement dependent;

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Construtor padrão">
    
    /**
     * Construtor padrão. Classe imutável.
     * @param id - Número identificador único da dependência
     * @param label - Label da dependência, (Opcional)
     * @param base - Elemento que pertence dependência
     * @param dependent - Outro elemento que pertence à dependência
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
