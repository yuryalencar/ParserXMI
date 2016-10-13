/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.unipampa.uml;
/**
 *
 * @author yuryalencar
 */
public class UmlGeneralizationDependency extends UmlDependency{

    //<editor-fold defaultstate="collapsed" desc="Construtor">
    
    /**
     * Construtor para a criação de uma dependência do tipo generalization
     * @param id - Número identificador da generalization a ser inserida
     * @param label - Label contendo o nome da generalization (Opcional)
     * @param parent - Elemento Pai que a partir dele o outro é criado herdando
     * atributos e métodos (Relação de herança).
     * @param child - Elemento filho que é gerado a partir do elemento pai.
     */
    public UmlGeneralizationDependency(String id, String label, UmlElement parent, UmlElement child){
        super(id, label, parent, child);
    }
    
    //</editor-fold>
    
}
