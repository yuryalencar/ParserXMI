/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.unupampa.uml;

/**
 *
 * @author yuryalencar
 */
public class UmlUseCaseDiagram extends UmlDiagram{
    
    //<editor-fold defaultstate="collapsed" desc="Construtor">

    /**
     * Construtor padrão onde recebe todos os itens de um diagrama
     * @param id - String contendo identificador único e obrigatório
     * @param nome  - String contendo nome do diagrama (Opcional)
     */
    public UmlUseCaseDiagram(String id, String nome) {
        super(id, nome);
    }
    
    //</editor-fold>

}
