/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.unupampa.uml;

//<editor-fold defaultstate="collapsed" desc="Importações">

import com.sun.nio.sctp.Association;
import java.util.Objects;

//</editor-fold>

/**
 * @author yuryalencar
 * Representa uma associação entre dois elementos 
 * da Uml. 
 */
public class UmlAssociation {
    
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
    private final UmlElement a;
    private final UmlElement b;

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Construtor padrão">
    
    /**
     * Construtor padrão. Classe imutável.
     * @param id - Número identificador único da associação
     * @param label - Label da associação, (Opcional)
     * @param a - Elemento que pertence associação
     * @param b - Outro elemento que pertence à associação
     */
    public UmlAssociation(String id, String label, UmlElement a, UmlElement b){
        this.id = id;
        this.label = label;
        this.a = a;
        this.b = b;
    }

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Métodos Getter">
    
    /**
     * Getter do atributo a
     * @return a
     */
    public UmlElement getA(){
        return this.a;
    }
    
    /**
     * Getter do atributo b
     * @return b
     */
    public UmlElement getB(){
        return this.b;
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
