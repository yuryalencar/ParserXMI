/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.unupampa.uml;

/**
 *
 * @author Lucascorrea
 */
public enum UmlDependencyType {
    EXTEND(1), INCLUDE(2), GENERALIZATION(3);

    private final int type;
    
    /**
     * 
     * @param type 
     */
    private UmlDependencyType(int type) {
        this.type = type;
    }

    /**
     * @return the type
     */
    public int getType() {
        return type;
    }
    
    /**
     * Recebe o Tipo e verifica se o valor recebido possuel Enum.
     * Caso possua vai retorna o tipo de Dependência.
     * Caso não irá lançar e avisar a exceção de Tipo inválido.
     * @param type
     * @return 
     */
    public static UmlDependencyType verifyType(int type){
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
}
