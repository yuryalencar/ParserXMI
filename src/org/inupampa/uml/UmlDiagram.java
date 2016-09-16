package org.inupampa.uml;

//<editor-fold defaultstate="collapsed" desc="Importações">

import java.util.ArrayList;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

//</editor-fold>

/**
 * @author yuryalencar
 * Representa um diagrama abstrato da UML. * 
 */
public abstract class UmlDiagram {
    
    //<editor-fold defaultstate="collapsed" desc="Variáveis">
    
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
    private final ArrayList<UmlDependency> dependencies;
    
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Construtor">
    
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
        this.dependencies = new ArrayList<>();
    }
    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Métodos Getters(Atributos)">
    
    /**
     * Método getter para pegar o id e poder fazer comparações
     * e com isto encontrar o diagrama a ser adicionado os elementos e
     * também as suas associações
     * @return - retorna uma String com o id do Diagrama.
     */
    public String getId(){
        return this.id;
    }
    
    /**
     * Método para saber o nome do diagrama, pode conter ou não.
     * Mas caso seja necessário é possível através deste método getter
     * @return - uma String com o nome do diagrama
     */
    public String getNome(){
        return this.nome;
    }
    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Método getElement">
    
    /**
     * Método para pegar um elemento da lista,
     * este elemento vai ser pego para adicionar a uma associação,
     * pois, no documento XMI na tag UML:Association vai ter somente o
     * ID do elemento para o referenciar, então eu uso aquele ID, para
     * pesquisar dentro da minha lista, e pegar o elemento que necessita.
     * @param id - Número identificador do elemento a ser pegado.
     * @return - retorna o Elemento de acordo com o seu id específicado.
     */
    public UmlElement getElement(String id){
        
        for (UmlElement element : elements) {
            if(element.getId().equals(id))
                return element;
        }
        
        return null; //Lançar exception
    }
    
    /**
     *  Método para pegar uma associação relacionada a um diagrama específico
     * aquisição feita através do id da mesma.
     * @param id - String que pode conter números e letras(Único para cada associação).
     * @return  - Retorna o Objeto da associação.
     */
    public UmlAssociation getAssociation(String id){
        
        for (UmlAssociation association : associations) {
            if(association.getId().equals(id))
                return association;
        }
        
        return null; //Lançar exception
    }
    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Adicionar Elementos, Associações e Dependências">
    
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
     * Adiciona uma dependência na lista de dependências do
     * diagrama. Se a dependência já existir, dispara uma 
     * exception do tipo DuplicatedAssociationException.
     * Se os elementos que compõem a dependência não fizerem
     * parte do diagrama, dispara uma exceção do tipo
     * InconsistentAssociationException.
     * @param a Dependência a ser adicionada.
     */
    public void AddDependency(UmlDependency a){
        boolean existA = false;
        boolean existB = false;
        
        for (UmlDependency dependency : dependencies) {
            if(dependency.getId().equals(a.getId())){
                // Lançar exception
            }
        }
        
        for (UmlElement element : elements) {
            if(a.getBase().getId().equals(element.getId())){
                existA = true;
            }
            
            if(a.getDependent().getId().equals(element.getId())){
                existB = true;
            }
        }

        if(existA && existB)
            dependencies.add(a);
        else
            System.out.println("");//Lançar exception
    }
    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Remoção de elementos e associações">
    
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
     * @param id - String do id da associação da associação que quer excluir
     */
    public void removeAssociation(String id){
        for (UmlAssociation association : associations) {
            if(association.getId().equals(id)){
                associations.remove(association);
                break;
            }
        }
    }

    /**
     * Exclusão de uma dependência através do seu ID.
     * A escolha do ID veio a partir de saber que cada dependência, só pode
     * conter um único.
     * @param id - String do id da associação da dependência que quer excluir
     */
    public void removeDependencies(String id){
        for (UmlDependency dependency : dependencies) {
            if(dependency.getId().equals(id)){
                dependencies.remove(dependency);
                break;
            }
        }
    }

    //</editor-fold>
}
