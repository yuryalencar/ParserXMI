/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.unipampa.uml;

/**
 *
 * @author Lucascorrea
 */
public enum UmlDependencyType {
    
    //<editor-fold defaultstate="collapsed" desc="Tipos">
    
    //Tipos de dependência e o inteiro que a representa.
    EXTEND(1), INCLUDE(2), GENERALIZATION(3);

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Construtor + Atributo">
    
    //Variável final para que não possa ser modificada
    private final int type;
    
    /**
     * Construtor para a escolha do tipo de
     * uma dependência
     * @param type - recebe um inteiro que corresponde
     * ao tipo, como o construtor é privado é definido
     * dentro do pŕoprio enum.
     */
    private UmlDependencyType(int type) {
        this.type = type;
    }

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="getType">
    
    /**
     * Método para retornar o tipo de uma 
     * dependência.
     * @return - um inteiro 1-Caso seja um extend,
     * 2-Caso seja um include, 3-Caso seja uma generalization
     */
    public int getType() {
        return type;
    }
    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="VerifyUmlDependency">
    
    /**
     * Método para a verificação de um tipo, e retorna
     * o respectivo tipo.
     * @param type - Inteiro que corresponde ao tipo
     * do da dependência
     * @return - UmlDependencyType.EXTEND - caso seja 1
     * UmlDependencyType.INCLUDE - caso seja 2
     * UmlDependencyType.GENERALIZATION - caso seja 3
     */
    public static UmlDependencyType verifyUmlDependency(int type){
        switch(type){
            case 1:
                return UmlDependencyType.EXTEND;
            case 2:
                return UmlDependencyType.INCLUDE;
            case 3:
                return UmlDependencyType.GENERALIZATION;
            default: 
                throw new IllegalArgumentException("Invalid Type");
        }
    }
    
    //</editor-fold>
    
}
