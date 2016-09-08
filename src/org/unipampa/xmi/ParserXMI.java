package org.unipampa.xmi;
//<editor-fold defaultstate="collapsed" desc="Importações">

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

//</editor-fold>

/**
 * @author Yury Alencar
 * Esta classe possui os métodos necessários para extrair
 * informações de modelos UML armazenados em arquivos XMI.
 */
public class ParserXMI {

    //<editor-fold defaultstate="collapsed" desc="Lista de diagramas - Variável da classe">
    
    private final ArrayList<UmlDiagram> diagrams;
    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Método que gerencia as criações e adições de elementos, modelos e associações">
    
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
    private void createAndAdd(Document doc, String nameTag) throws ParserConfigurationException,
            SAXException, IOException{
        
        //Criando uma lista com todos os nodos de acordo com a tag Fornecida
        NodeList nList = doc.getElementsByTagName(nameTag);

        // Percorrer toda a lista de nodos criadas anteriormente
        for(int i=0; i<nList.getLength(); i++){
            // Pegando o nodo da "rodada"
            Node nNode = nList.item(i);
            
            // Verificando o tipo do nodo
            if(isElementNode(nNode)){
                //Passo o nodo para Element para poder tirar seus dados.
                Element eElement = (Element) nNode;
                
                //Verifico qual tipo é o elemento
                if(eElement.getNodeName().equalsIgnoreCase("UML:Actor")){
                    addActor(eElement);
                
                }else if(eElement.getNodeName().equalsIgnoreCase("UML:UseCase")){
                    addUseCase(eElement);
                    
                }else if(eElement.getNodeName().equalsIgnoreCase("UML:Model")){
                    addModel(eElement);
                    
                }else if(eElement.getNodeName().equalsIgnoreCase("UML:Association")){
                    addAssociation(eElement);
                }
            }
        }
    }
    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Métodos que criam e adicionam os elementos de um modelo e o próprio modelo">
    
    /**
     * Método para adicionar um novo modelo, pegando seu id e nome e o
     * adiciona na lista de diagramas.
     * @param eElement - O elemento para que assim tirar seus atributos
     * necessários para sua criação.
     */
    private void addModel(Element eElement){
        UmlDiagram newDiagramUseCase;
        newDiagramUseCase = new UseCaseDiagram(getId(eElement), getName(eElement));
        diagrams.add(newDiagramUseCase);
    }

    //@TODO: Ainda estou bolando algo para que eu possa
    //usar este método sem setar sempre o primeiro item 
    //da lista, por enquanto estou lendo somente um diagrama,
    //mas está faltando ainda ver como vou pegar o diagrama certo
    //da lista - Elaborando...
    
    /**
     * Método para criar um Ator e o adiciona dentro da lista de elementos,
     * lista presente dentro da classe UmlDiagram.
     * @param eElement - Elemento para que possa retirar seus atributos
     * necessários para sua criação.
     */
    private void addActor(Element eElement){
        UmlElement newActor = new ActorElement(getId(eElement), getName(eElement));
        diagrams.get(0).AddElement(newActor);
    }
    
    /**
     * Método para criar um Caso de uso e o adicionar na lista de elementos,
     * lista que está contida dentro da classe UmlDiagram.
     * @param eElement - O elemento do caso de uso para retirar deste elemento
     * todos os atributos necessários para sua criação e o adiciona.
     */
    private void addUseCase(Element eElement){
        UmlElement newUseCase = new UseCaseElement(getId(eElement), getName(eElement));
        diagrams.get(0).AddElement(newUseCase);
    }
    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Método para criar e adicionar uma associação dentro do diagrama">
    
    /**
     * Método para criar e adicionar uma associação, a lista de associações
     * esta presente na classe UmlDiagram, para usar ela utilizo a lista presente
     * nesta classe.
     * @param eElement - Elemento para que eu possa retirar dele seus atributos
     * e também retirar os elementos e os dados contidos neles necessários.
     */
    private void addAssociation(Element eElement){
        // Como eu eu vou ter que fazer a pesquisa dos elementos
        //preciso guardar antes o ID e o nome da associação, devido
        //ao meu método se eu chamar na hora que eu for criar a 
        //associação vai chamar o id de um elemento que não é este.

        String id, name, idrefElementA, idrefElementB;
        UmlAssociation newAssociation;
        id = getId(eElement);
        name = getName(eElement);

        // Crio uma Lista com as conexões contidas dentro do diagrama
        //para poder pegar seus atributos e outros elementos.
        NodeList nList = eElement.getElementsByTagName("UML:Association.connection");
        
        // Como esta tag só se tem uma a cada associação não se faz a necessidade
        //da utilização de uma estrutura de repetição para fazer a utilização de
        //todos elementos possíveis.
        
        // Pegando a conexão para que eu possa adentrar e pegar todos os dados que
        //é necessário para a criação de uma associação e o adicionar.
        Node nNode = nList.item(0);

        // Verificando o tipo do nodo
        if(isElementNode(nNode)){

            // Transformando ele em um elemento para que se possa manipular e pegar
            //todos os seus outros elementos e atributos se for necessário.
            eElement = (Element) nNode;

            // Pegando as associações finais, onde ficam armazenados os dados de
            //elementos contidos dentro da associação através de um idref, onde
            //também contem dados relacionados ao elemento que a mesma faz parte
            //podendo ser utilizado para sua validação.
            nList = eElement.getElementsByTagName("UML:AssociationEnd");

            for(int i=0; i<nList.getLength();i++){

                nNode = nList.item(i);

                if(isElementNode(nNode)){

                    eElement = (Element) nNode;

                    // Neste elemento eu pego 
                    NodeList nList1 = eElement.getElementsByTagName("UML:Feature.owner");

                    // Somente um para cada ASSOCIATIONEND
                    idrefElementA = getIdref(getClassifier(nList1.item(0)));

                    // Neste elemento eu pego 
                    nList1 = eElement.getElementsByTagName("UML:AssociationEnd.participant");

                    // Somente um para cada ASSOCIATIONEND
                    idrefElementB = getIdref(getClassifier(nList1.item(0)));

                    newAssociation = new UmlAssociation(id,name,
                            this.diagrams.get(0).getElement(idrefElementA),
                            this.diagrams.get(0).getElement(idrefElementB));

                    this.diagrams.get(0).AddAssociation(newAssociation);
                }
            }
        }
    }
    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Método getClassifier">
            
    /**
     * Método para pegar o Elemento da tag <UML:Classifier> e assim poder tirar
     * seu atributo, que é uma referência crucial para conseguir pegar os 
     * elementos que vão estar contidos dentro de uma associação.(Associados)
     * @param nNode - Nodo que contém o elemento UML:Classifier.
     * @return - retorna o elemento classifier para sua manipulação.
     */
    private Element getClassifier(Node nNode){
        
        // Recebo o nodo e o transformo em elemento para pegar a tag
        //do Classifier e depois a tranformar em um elemento também e
        //assim poder pegar seus atributos através de outro método.
        Element eElement = (Element) nNode;
        NodeList nList = eElement.getElementsByTagName("UML:Classifier");
        
        //Pegando somente o nodo do classifier já que é único.
        nNode = nList.item(0);
        
        if(isElementNode(nNode)){
            // Transformando em elemento para que se possa retirar seu atributo
            //e com isso poder enviar à outro método para a extração.
            eElement = (Element) nNode;
            
            return eElement;
        }
        
        return null; // Lançar Exception
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Verificação do tipo de nodo">
            
    /**
     * Método para verificar se o nodo pode ser convertido
     * para um Element, verificando assim o seu tipo.
     * @param nNode - nodo a ser verificado.
     * @return - true caso seja e false caso o contrário.
     */
    private boolean isElementNode(Node nNode){
        return nNode.getNodeType() == Node.ELEMENT_NODE;
    }
    
    //</editor-fold>    

    //<editor-fold defaultstate="collapsed" desc="Métodos para pegar atributos">
 
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
     * Método utilizado para pegar o idref de uma associação,
     * o idref se refere aos id dos elementos presentes dentro
     * de uma associação.
     * @param eElement - Elemento que vai ser extraído o idref
     * @return - retorna uma String com o ID (idref).
     */
    private String getIdref(Element eElement){
        String idref;        
        idref = eElement.getAttribute("xmi.idref");
        return idref;
    }
    
    //</editor-fold>     

    //<editor-fold defaultstate="collapsed" desc="Método que retorna os diagramas para verificar eficiência">
    

    public ArrayList<UmlDiagram> getDiagrams(){
        
        //@TODO: clonagem dos objetos (ver imutabilidade)
        return this.diagrams;
    }
    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Onde passa o documento para a memória e chama o método de gerenciamento">
    
    /**
     * Construtor padrão. Classe imutável.
     * @param fileName Nome do arquivo a ser interpretado.
     */
    public ParserXMI(String fileName){
        
        //Inicialização das listas internas
        this.diagrams = new ArrayList<>();       
        
        try {
            
            // Abrir o arquivo XMI
            File inputFile = new File(fileName);

            // Criando um documento na memória para poder analisar com o DOMParser
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);

            // Normalizando o documento caso haja algum caracter especial
            // e não dê nenhum erro durante o Parser.
            doc.getDocumentElement().normalize();

            // Obs.: Nesta parte é onde se escolhe as operações e
            // também o nome das tags que vão ser procuradas, tanto
            // quanto o nome do arquivo onde ocorrerá as buscas.
            createAndAdd(doc, "UML:Model");
            createAndAdd(doc, "UML:Actor");
            createAndAdd(doc, "UML:UseCase");
            createAndAdd(doc, "UML:Association");
            
        } catch (Exception ex) {
            Logger.getLogger(ParserXMI.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //@TODO: Realizar a abertura do arquivo e parsing
        //das estruturas de dados. Na medida em que as 
        //estruturas foram descobertas, adicioná-las 
        //às listas internas do parser. Por ora, partimos da
        //ideia de que só existem 2 tipos de elementos em nossos
        //diagramas.
    }
    
    //</editor-fold>
    
}
