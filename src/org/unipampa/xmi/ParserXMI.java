package org.unipampa.xmi;

import java.util.ArrayList;
import org.inupampa.uml.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * @author anderson domingues
 * Esta classe possui os métodos necessários para extrair
 * informações de modelos UML armazenados em arquivos XMI.
 */
public class ParserXMI {

    private final ArrayList<UmlDiagram> diagrams;

    
    /**
     * Construtor padrão. Classe imutável.
     * @param filename Nome do arquivo a ser interpretado.
     */
    public ParserXMI(String filename){
        
        //Inicialização das listas internas
        this.diagrams = new ArrayList<>();       
        
        //@TODO: Realizar a abertura do arquivo e parsing
        //das estruturas de dados. Na medida em que as 
        //estruturas foram descobertas, adicioná-las 
        //às listas internas do parser. Por ora, partimos da
        //ideia de que só existem 2 tipos de elementos em nossos
        //diagramas.
        throw new NotImplementedException();
    }
    
    public ArrayList<UmlDiagram> getDiagrams(){
        
        //@TODO: clonagem dos objetos (ver imutabilidade)
        return this.diagrams;
    }
    
}
