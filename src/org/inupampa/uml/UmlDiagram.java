package org.inupampa.uml;

import java.util.ArrayList;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * @author Yury Alencar
 * Representa um diagrama abstrato da UML. * 
 */
public abstract class UmlDiagram {
    
    /**
     * Cada diagrama é identificado unicamente 
     * dentro de um modelo da UML por um valor de ID. 
     */
    private final String id;
    
    /**
     * Cada diagrama pode possui um nome (que pode ser 
     * vazio).
     */
    private final String nome;
    
    /**
     * Diagramas são compostos, basicamente, de elementos
     * e associações. Existem outras formas que podem aparecer
     * em diagramas da UML, porém para estas esta classe deve
     * ser extendida.
     */
    private final ArrayList<UmlElement> elements;
    private final ArrayList<UmlAssociation> associations;
    
    /**
     * Constructor padrão.
     * @param id Identificador do Diagrama
     * @param nome Nome "amigável" do diagrama
     */
    public UmlDiagram(String id, String nome){
        this.id = id;
        this.nome = nome;
        this.elements = new ArrayList<>();
        this.associations = new ArrayList<>();
    }
    
    /**
     * Adiciona um elemento na lista de elementos do
     * diagrama. Se o elemento já existir, dispara uma 
     * exception do tipo DuplicatedElementException.
     * @param e Elemento a ser adicionado
     */
    public void AddElement(UmlElement e){
        
        for (UmlElement element : elements) {
            if(element.getId().equals(e.getId())){
                // Lançar exception
            }
        }
        
        elements.add(e);
    }
    
    /**
     * Adiciona uma associacao na lista de associações do
     * diagrama. Se a associação já existir, dispara uma 
     * exception do tipo DuplicatedAssociationException.
     * Se os elementos que compõem a associação não fizerem
     * parte do diagrama, dispara uma exceção do tipo
     * InconsistentAssociationException.
     * @param a Associação a ser adicionada.
     */
    public void AddAssociation(UmlAssociation a){
        boolean existA = false;
        boolean existB = false;
        
        for (UmlAssociation association : associations) {
            if(association.getId().equals(a.getId())){
                // Lançar exception
            }
        }
        
        for (UmlElement element : elements) {
            if(a.getA().getId().equals(element.getId())){
                existA = true;
            }
            
            if(a.getB().getId().equals(element.getId())){
                existB = true;
            }
        }

        if(existA && existB)
            associations.add(a);
        else
            System.out.println("");//Lançar exception
    }
    
    /**
     * Exclusão de uma elemento através do seu ID.
     * A escolha do ID veio a partir de saber que cada elemento, só pode
     * conter um único. 
     * @param e - O elemento que deseja excluir.
     */
    public void removeElement(UmlElement e){
        for (UmlElement element : elements) {
            if(element.getId().equals(e.getId())){
                elements.remove(element);
                break;
            }
        }
    }
    
    /**
     * Exclusão de uma associação através do seu ID.
     * A escolha do ID veio a partir de saber que cada associação, só pode
     * conter um único.
     * @param Id - Da associação que quer excluir
     * Ainda vendo como fazer esta exclusão, provavelmente será modificada,
     * por uma que recebe o objeto da associação e o pesquisa por ID, ao invés de
     * receber o ID.
     */
    public void removeAssociation(String Id){
        for (UmlAssociation association : associations) {
            if(association.getId().equals(Id)){
                associations.remove(association);
                break;
            }
        }
    }    
}
