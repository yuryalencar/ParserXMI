package org.unipampa.xmi;

import java.util.ArrayList;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import javax.xml.parsers.ParserConfigurationException;
import org.inupampa.uml.*;
import org.xml.sax.SAXException;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * @author anderson domingues
 * Esta classe possui os métodos necessários para extrair
 * informações de modelos UML armazenados em arquivos XMI.
 */
public class ParserXMI {

    private final ArrayList<UmlDiagram> diagrams;

    /**
     * Método para criar uma lista de nodos que será
     * utilizada tanto para a pesquisa de diagramas, atores, casos de uso
     * e associações.
     * 
     * @param fileName - nome do arquivo XMI que deve fazer a pesquisa
     * @param nameTag - nome da tag que referencia os elementos, associações
     * ou diagramas.
     * @param option - opção que tem haver com qual quer adicionar, no
     * momento está 1.p/ addDiagramas ou addElementos e 2.p/ addAssociações
     * @throws ParserConfigurationException - Pode dar erro pra criar
     * um documento builder por isso é necessário esta Exception
     * @throws SAXException - Pode acontecer um
     * erro ao criar um documento na memória.
     * @throws IOException - Referente a outro erro que pode dar na
     * transferência do XMI para a memória.
     */
    private void createAndAdd(String fileName, String nameTag, int option) throws ParserConfigurationException,
            SAXException, IOException{
        
        // Abrir o arquivo XMI
        File inputFile = new File(fileName);
        
        // Criando um documento na memória para poder analisar com o DOMParser
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(inputFile);
        
        // Normalizando o documento caso haja algum caracter especial
        // e não dê nenhum erro durante o Parser.
        doc.getDocumentElement().normalize();
        
        //Criando uma lista com todos os nodos de acordo com a tag Fornecida
        NodeList nList = doc.getElementsByTagName(nameTag);
        
        //De acordo com a opção informada se escolhe a operação nesta parte
        switch (option) {
            case 1:
                addElementsAndDiagrams(nList);
                break;
            case 2:
                addAssociations(nList);
                break;
            default:
                break;
        }
    }
    
    /**
     * Insere os Elementos e os Diagramas de casos de uso
     * @param nList - Lista de nodos para fazer a inserção somente
     * dos elementos ou Diagramas(Escolha devido à sua semelhança pegando
     * somente o id e label, sendo que estes no documento são referenciados
     * com o nome do atributo igual).
     */
    private void addElementsAndDiagrams(NodeList nList){
        
        // Percorrer toda a lista de nodos criadas anteriormente
        for(int i=0; i<nList.getLength(); i++){
            // Pegando o nodo da "rodada"
            Node nNode = nList.item(i);
            
            // Verificando o tipo do nodo
            if(nNode.getNodeType() == Node.ELEMENT_NODE){
                //Passo o nodo para Element para poder tirar seus dados.
                Element eElement = (Element) nNode;
                
                //Verifico qual tipo é o elemento
                if(eElement.getNodeName().equalsIgnoreCase("UML:Actor")){
                    UmlElement newActor = new ActorElement(getId(eElement), getName(eElement));
                    diagrams.get(0).AddElement(newActor);
                //@TODO: Ainda estou bolando algo para que eu possa
                //usar este método sem setar sempre o primeiro item 
                //da lista, por enquanto estou lendo somente um diagrama,
                //mas está faltando ainda ver como vou pegar o diagrama certo
                //da lista - Elaborando...
                }else if(eElement.getNodeName().equalsIgnoreCase("UML:UseCase")){
                    UmlElement newUseCase = new UseCaseElement(getId(eElement), getName(eElement));
                    diagrams.get(0).AddElement(newUseCase);
                }else if(eElement.getNodeName().equalsIgnoreCase("UML:Model")){
                    UmlDiagram newDiagramUseCase;
                    newDiagramUseCase = new UseCaseDiagram(getId(eElement), getName(eElement));
                    diagrams.add(newDiagramUseCase);
                }
            }
        }
    }
    
    /**
     * Método para adicionar as associações,
     * método separado dos demais devido a sua drástica diferença
     * dos demais na parte de extração dos dados.
     * @param nList - Lista de nodos com os elementos Association
     */
    private void addAssociations(NodeList nList){

        throw new NotImplementedException();
    }
    
    /**
     * Método para pegar o ID do elemento,
     * independentemente do mesmo ser associação,
     * UseCase, Actor, ou até mesmo o diagrama.
     * @param eElement - Que se quer extrair o id.
     * @return - retorna o ID em String.
     */
    private String getId(Element eElement){
        String id;
        id = eElement.getAttribute("xmi.id");
        return id;
    }
    
    /**
     * Método para pegar a Label do elemento,
     * independentemente do mesmo ser associação,
     * UseCase, Actor, ou até mesmo o diagrama.
     * @param eElement - Que se quer extrair a label.
     * @return - retorna a label em String.
     */
    private String getName(Element eElement){
        String name;
        name = eElement.getAttribute("name");
        return name;
    }
     
    /**
     * Construtor padrão. Classe imutável.
     * @param fileName Nome do arquivo a ser interpretado.
     */
    public ParserXMI(String fileName){
        
        //Inicialização das listas internas
        this.diagrams = new ArrayList<>();       
        
        try {
            
            // Obs.: Nesta parte é onde se escolhe as operações e
            // também o nome das tags que vão ser procuradas, tanto
            // quanto o nome do arquivo onde ocorrerá as buscas.
            createAndAdd(fileName, "UML:Model", 1);
            createAndAdd(fileName, "UML:Actor", 2);
            createAndAdd(fileName, "UML:UseCase", 2);
            createAndAdd(fileName, "UML:Association", 3);
            
        } catch (Exception ex) {
            Logger.getLogger(ParserXMI.class.getName()).log(Level.SEVERE, null, ex);
        }
        
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
