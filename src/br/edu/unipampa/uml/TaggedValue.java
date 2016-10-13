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
public class TaggedValue {
    
    //<editor-fold defaultstate="collapsed" desc="Atributos">
    
    private String tag;
    private String value;

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Construtor">
    
    /**
     * Construtor para a criação de uma TaggedValue
     * @param tag
     * @param value 
     */
    public TaggedValue(String tag, String value){
        this.tag = tag;
        this.value = value;
    }
    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Getters">
    
    /**
     * @return the tag
     */
    public String getTag() {
        return tag;
    }

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Setters">
    
    /**
     * @param tag the tag to set
     */
    public void setTag(String tag) {
        this.tag = tag;
    }

    /**
     * @param value the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }

    //</editor-fold>
    
}
