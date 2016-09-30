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
 * @author yuryalencar Esta classe possui os métodos necessários para extrair
 * informações de modelos UML armazenados em arquivos XMI.
 */
public class ParserXMI {
        
    //<editor-fold defaultstate="collapsed" desc="Variáveis">
    
    private final ArrayList<UmlDiagram> diagrams;

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Documento na memória">
    
    /**
     * Construtor padrão. Classe imutável.
     *
     * @param fileName Nome do arquivo a ser interpretado.
     */
    public ParserXMI(String fileName) {

        
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
            
            // A Ordem importa, ou seja, primeiro tem que ser sempre adicionados
            //os modelos e a partir deles os elementos, em seguida as associações e
            //dependencias.
            createAndAdd(doc, "UML:Model");
            createAndAdd(doc, "UML:Actor");
            createAndAdd(doc, "UML:UseCase");
            createAndAdd(doc, "UML:Association");
            createAndAdd(doc, "UML:Include");
            createAndAdd(doc, "UML:Extend");
            createAndAdd(doc, "UML:Generalization");

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

    //<editor-fold defaultstate="collapsed" desc="Gerenciamento">
   
    /**
     * Método para criar uma lista de nodos que será utilizada tanto para a
     * pesquisa de diagramas, atores, casos de uso e associações.
     *
     * @param fileName - nome do arquivo XMI que deve fazer a pesquisa
     * @param nameTag - nome da tag que referencia os elementos, associações ou
     * diagramas.
     * @param option - opção que tem haver com qual quer adicionar, no momento
     * está 1.p/ addDiagramas ou addElementos e 2.p/ addAssociações
     * @throws ParserConfigurationException - Pode dar erro pra criar um
     * documento builder por isso é necessário esta Exception
     * @throws SAXException - Pode acontecer um erro ao criar um documento na
     * memória.
     * @throws IOException - Referente a outro erro que pode dar na
     * transferência do XMI para a memória.
     */
    private void createAndAdd(Document doc, String nameTag) throws ParserConfigurationException,
            SAXException, IOException {

        //Criando uma lista com todos os nodos de acordo com a tag Fornecida
        NodeList nList = doc.getElementsByTagName(nameTag);

        if(nList.getLength() != 0){
            // Percorrer toda a lista de nodos criadas anteriormente
            for (int i = 0; i < nList.getLength(); i++) {
                
                // Pegando o nodo da "rodada"
                Node nNode = nList.item(i);

                // Verificando o tipo do nodo
                if (isElementNode(nNode)) {
                    //Passo o nodo para Element para poder tirar seus dados.
                    Element eElement = (Element) nNode;

                    //Verifico qual tipo é o elemento
                    
                    if (eElement.getNodeName().equalsIgnoreCase("UML:Model") 
                            && !(getId(eElement).equals(""))) {
                        addModel(eElement);

                    } else if (eElement.getNodeName().equalsIgnoreCase("UML:Actor") 
                            && !(getId(eElement).equals(""))) {
                        addActor(eElement);

                    } else if (eElement.getNodeName().equalsIgnoreCase("UML:UseCase") 
                            && !(getId(eElement).equals(""))) {
                        addUseCase(eElement);

                    } else if (eElement.getNodeName().equalsIgnoreCase("UML:Association") 
                            && !(getId(eElement).equals(""))) {
                        addAssociation(eElement);

                    } else if (eElement.getNodeName().equalsIgnoreCase("UML:Include") 
                            && !(getId(eElement).equals(""))){
                        addInclude(eElement);

                    } else if (eElement.getNodeName().equalsIgnoreCase("UML:Extend") 
                            && !(getId(eElement).equals(""))){
                        addExtend(eElement);
                        
                    } else if (eElement.getNodeName().equalsIgnoreCase("UML:Generalization") 
                            && !(getId(eElement).equals(""))){
                        addGeneralization(eElement);
                    }
                }
            }
        }
    }

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Criar e Adicionar um Modelo">
    
    /**
     * Método para adicionar um novo modelo, pegando seu id e nome e o adiciona
     * na lista de diagramas.
     *
     * @param eElement - O elemento para que assim tirar seus atributos
     * necessários para sua criação.
     */
    private void addModel(Element eElement) {
        UmlDiagram newDiagramUseCase;
        newDiagramUseCase = new UseCaseDiagram(getId(eElement), getName(eElement));
        diagrams.add(newDiagramUseCase);
    }

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Criar e adicionar Elementos(Actors and UseCases)">

    /**
     * Método para criar um Ator e o adiciona dentro da lista de elementos,
     * lista presente dentro da classe UmlDiagram.
     *
     * @param eElement - Elemento para que possa retirar seus atributos
     * necessários para sua criação.
     */
    private void addActor(Element eElement) {
        String id = getId(eElement);
        UmlElement newActor = new ActorElement(id, getName(eElement));
        getDiagram(id).addElement(newActor);
    }

    /**
     * Método para criar um Caso de uso e o adicionar na lista de elementos,
     * lista que está contida dentro da classe UmlDiagram.
     *
     * @param eElement - O elemento do caso de uso para retirar deste elemento
     * todos os atributos necessários para sua criação e o adiciona.
     */
    private void addUseCase(Element eElement) {
        String id = getId(eElement);
        UmlElement newUseCase = new UseCaseElement(id, getName(eElement));
        getDiagram(id).addElement(newUseCase);
    }

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Criar, Adicionar e Verificar Associações">
    
    /**
     * Método para criar e adicionar uma associação, a lista de associações esta
     * presente na classe UmlDiagram, para usar ela utilizo a lista presente
     * nesta classe.
     *
     * @param eElement - Elemento para que eu possa retirar dele seus atributos
     * e também retirar os elementos e os dados contidos neles necessários.
     */
    private void addAssociation(Element eElement) {
    
        // Como eu eu vou ter que fazer a pesquisa dos elementos
        //preciso guardar antes o ID e o nome da associação, devido
        //que ao fazer isto posteriormente pegarei dados errados por 
        //por causa da reutilização de variáveis.

        String id, name, idrefElementA="", idrefElementB="";
        UmlAssociation newAssociation; //Associação que vai ser criada e adicionada ao diagrama
        id = getId(eElement);
        name = getName(eElement);

        // Crio uma Lista com as conexões finais contidas dentro do diagrama
        //para poder pegar as referência dos elementos de outros elementos.
        NodeList nList = eElement.getElementsByTagName("UML:AssociationEnd");

        // Como esta tag tem 2 em cada associação, mas não se faz a necessidade
        //da utilização de ambas, pois somente em uma já contem todos os dados
        //tendo em vista que em uma Association não importa a ordem dos elmentos
        //pegar somente o primeiro nodo é uma melhor opção, já que os dois contem
        //as mesmas referências do elementos.
        
        if(nList.getLength() != 0){
            Node nNode = nList.item(0);

            // Verificando o tipo do nodo
            if (isElementNode(nNode)) {

                // Transformando ele em um elemento para que se possa manipular e pegar
                //todos os seus outros elementos e atributos se for necessário.
                eElement = (Element) nNode;

                // Verificando se a associação END realmente pertence à tag que a engloba 
                if (verifyAssociation(eElement, id)) {

                    if (isElementNode(nList.item(0))) {

                        eElement = (Element) nList.item(0);

                        // Neste elemento eu pego o proprietário da associação
                        nList = eElement.getElementsByTagName("UML:Feature.owner");
                        
                        if(nList.getLength() != 0){
                            // Somente um para cada ASSOCIATIONEND
                            idrefElementA = getIdref(getClassifier(nList.item(0)));
                        } else {
                            //Lançar exception
                        }
                        // Neste elemento eu pego 
                        nList = eElement.getElementsByTagName("UML:AssociationEnd.participant");

                        if(nList.getLength() != 0){
                            // Somente um para cada ASSOCIATIONEND
                            idrefElementB = getIdref(getClassifier(nList.item(0)));
                        } else {
                            //Lançar exception
                        }
                        // Criando um diagrama para nao ficar toda hora realizando uma
                        // pesquisa dentro da lista.
                        UmlDiagram diagram = getDiagram(extractId(id));

                        newAssociation = new UmlAssociation(id, name,
                                diagram.getElement(idrefElementA),
                                diagram.getElement(idrefElementB));

                        //Para não precisar atualizar o item presente na lista.
                        diagram.addAssociation(newAssociation);
                    }
                } else {
                    //Lançar uma exception
                }
            }            
        }
    }

    /**
     * Método para que verificar se a AssociationEnd pertence a associação que a
     * engloba.
     *
     * @param eElement - Elemento que é o relativo à associação end para que se
     * possa extrair o id que refencia uma a Associação que o engloba.
     * @param idAssociation - Id da associação que engloba tal end.
     * @return - retorna true caso a associação end realmente pertença à
     * associação que a engloba, e false caso contrário.
     */
    private boolean verifyAssociation(Element eElement, String idAssociation) {
        String idref = "";

        NodeList nList = eElement.getElementsByTagName("UML:Association");

        if(nList.getLength() != 0){
            if (isElementNode(nList.item(0))) {
                eElement = (Element) nList.item(0);

                idref = getIdref(eElement);
                return idref.equals(idAssociation);
            }
        }
    
        return idref.equals(idAssociation);//LançarException
    }
                
    /**
     * Método para pegar o Elemento da tag <UML:Classifier> e assim poder tirar
     * seu atributo, que é uma referência crucial para conseguir pegar os
     * elementos que vão estar contidos dentro de uma associação.(Associados)
     *
     * @param nNode - Nodo que contém o elemento UML:Classifier.
     * @return - retorna o elemento classifier para sua manipulação.
     */
    private Element getClassifier(Node nNode) {

        // Recebo o nodo e o transformo em elemento para pegar a tag
        //do Classifier e depois a tranformar em um elemento também e
        //assim poder pegar seus atributos através de outro método.
        Element eElement = (Element) nNode;
        NodeList nList = eElement.getElementsByTagName("UML:Classifier");

        if(nList.getLength() != 0){
            //Pegando somente o nodo do classifier já que é único.
            nNode = nList.item(0);

            if (isElementNode(nNode)) {
                // Transformando em elemento para que se possa retirar seu atributo
                //e com isso poder enviar à outro método para a extração.
                eElement = (Element) nNode;

                return eElement;
            }
        }
        return null; // Lançar Exception
    }

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Criar e Adicionar Generalization">
    
    /**
     * Método para a criação e inserção de uma generalização dentro de um diagrama
     * de casos de Uso da Uml.
     * @param eElement - Recebe o elemento que contém a generalization, para que assim
     * se possa extrair todos os dados necessários.
     */
    private void addGeneralization(Element eElement){
        String id = getId(eElement), label = getName(eElement), idChild="", idParent="";
        UmlDiagram diagram;
        UmlGeneralization newGeneralization;
        
        NodeList nList = eElement.getElementsByTagName("UML:Generalization.child");
        
        if(nList.getLength() != 0){
            idChild = getGeneralizableElement(nList);

        } else {
            //lançar exception
        }
        
        nList = eElement.getElementsByTagName("UML:Generalization.parent");
        
        if(nList.getLength() != 0){
            idParent = getGeneralizableElement(nList);
        
        } else {
            //lançar exception
        }
        
        diagram = getDiagram(id);
        
        newGeneralization = new UmlGeneralization(id, label,
                diagram.getElement(idParent), diagram.getElement(idChild));
        
        diagram.addDependency(newGeneralization);
    }
    
    
    
    /**
     * Método para a extração do número identificador do elemento que participa 
     * da generalization, este elemento é refenciado dentro da mesma.
     * @param nList - Lista de nodos que dentro contém o atributo do id, podendo
     * ser uma lista tanto para um elemento filho quanto um pai.
     * @return - retorna uma String com o valor do idRef do elemento escolhido.
     */
    private
        String getGeneralizableElement(NodeList nList){
        String idGeneralization;
        
        if(isElementNode(nList.item(0))){
            
            Element eElement = (Element) nList.item(0);
            
            nList = eElement.getElementsByTagName("UML:GeneralizableElement");
            
            if(nList.getLength() != 0 && isElementNode((Element) nList.item(0))){
                idGeneralization = getIdref((Element) nList.item(0));
                return idGeneralization;
            } else {
                //lançar Exception
            }
        }
        
        return null;//lançar exception
    }
    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Criar e Adicionar Include">

    /**
     * Método para adicionar um include na lista de dependencias de um diagrama
     * UML.
     * @param eElement - Elemento que contenha os dados a ser retirados. 
     */
    private void addInclude(Element eElement){
        
        String idInclude, nameInclude, idrefBase="", idrefAddition="";
        
        idInclude = getId(eElement);
        nameInclude = getName(eElement);
        
        NodeList nList = eElement.getElementsByTagName("UML:Include.base");
        
        if(nList.getLength() != 0){
            if(isElementNode(nList.item(0)))
                idrefBase = getIdref(getElementUseCase((Element) nList.item(0)));
        } else {
            //lançar exception
        }
        
        
        nList = eElement.getElementsByTagName("UML:Include.addition");

        if(nList.getLength() != 0){
            if(isElementNode(nList.item(0)))
                idrefAddition = getIdref(getElementUseCase((Element) nList.item(0)));
        } else {
            //lançar exception
        }
        
        // Criando um diagrama para nao ficar toda hora realizando uma
        // pesquisa dentro da lista.
        UmlDiagram diagram = getDiagram(idInclude);

        UmlInclude newInclude = new UmlInclude(idInclude, nameInclude,
            diagram.getElement(idrefBase),
            diagram.getElement(idrefAddition));
        //Para não precisar atualizar o item presente na lista.
        
        diagram.addDependency(newInclude);
    }
    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Criar e Adicionar Extend">

    /**
     * Método para adicionar um Extend na lista de dependencias de um diagrama
     * UML
     * @param eElement - Elemento para que se possa fazer a extração dos dados. 
     */
    private void addExtend(Element eElement){
        
        String idExtend, nameExtend, idrefBase="", idrefExtension="";
        
        idExtend = getId(eElement);
        nameExtend = getName(eElement);
        
        NodeList nList = eElement.getElementsByTagName("UML:Extend.base");
        
        if(nList.getLength() != 0){
            if(isElementNode(nList.item(0)))
                idrefBase = getIdref(getElementUseCase((Element) nList.item(0)));
        } else{
            //lançar exception
        }
            
        nList = eElement.getElementsByTagName("UML:Extend.extension");
        
        if(nList.getLength() != 0){
            if(isElementNode(nList.item(0)))
                idrefExtension = getIdref(getElementUseCase((Element) nList.item(0)));
        } else {
            //lançar exception
        }
        
        // Criando um diagrama para nao ficar toda hora realizando uma
        // pesquisa dentro da lista.
        UmlDiagram diagram = getDiagram(idExtend);

        UmlExtend newExtend = new UmlExtend(idExtend, nameExtend,
                diagram.getElement(idrefBase),
                diagram.getElement(idrefExtension));
        //Para não precisar atualizar o item presente na lista.
        
        diagram.addDependency(newExtend);
    }
    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Busca de diagrama e extração de ID">
    
    /**
     * Método para extrair a parte do id que é comum em todo o documento XMI.
     *
     * @param id - id que se deseja fazer a extração da parte em comum.
     * @return - retorna uma String com a tal parte, caso nao esteja no padrão
     * lança uma exception.
     */
    private String extractId(String id) {
    
        for (int i = 0; i < id.length(); i++) {
            if (id.charAt(i) == '-') {
                id = id.substring(i + 1);
                return id;
            }
        }
        return ""; //Lançar Exception
    }

    /**
     * Método que pesquisa na lista o diagrama com base no id extraído no método
     * de extração -extractId-.
     *
     * @param id - Número identificador sem ter feitor sua extração.
     * @return - retorna o diagrama caso o encontre, caso contrário lança uma
     * exception(Falta implementar a Exception).
     */
    private UmlDiagram getDiagram(String id) {
        // Retirando a parte em comum para todo o documento
        id = extractId(id);

        for (UmlDiagram diagram : diagrams) {
            if (extractId(diagram.getId()).equals(id)) {
                return diagram;
            }
        }
        
        return null; //Lançar Exception
    }

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Verificar tipo do nodo">
    
    /**
     * Método para verificar se o nodo pode ser convertido para um Element,
     * verificando assim o seu tipo.
     *
     * @param nNode - nodo a ser verificado.
     * @return - true caso seja e false caso o contrário.
     */
    private boolean isElementNode(Node nNode) {
        return nNode.getNodeType() == Node.ELEMENT_NODE;
    }

    //</editor-fold>    
    
    //<editor-fold defaultstate="collapsed" desc="getId, getName, getIdref, getElementUseCase">
    
    /**
     * Método para pegar o ID do elemento, independentemente do mesmo ser
     * associação, UseCase, Actor, ou até mesmo o diagrama.
     *
     * @param eElement - Que se quer extrair o id.
     * @return - retorna o ID em String.
     */
    private String getId(Element eElement) {
        String id;
        id = eElement.getAttribute("xmi.id");
        return id;
    }

    /**
     * Método para pegar a Label do elemento, independentemente do mesmo ser
     * associação, UseCase, Actor, ou até mesmo o diagrama.
     *
     * @param eElement - Que se quer extrair a label.
     * @return - retorna a label em String.
     */
    private String getName(Element eElement) {
        String name;
        name = eElement.getAttribute("name");
        return name;
    }

    /**
     * Método utilizado para pegar o idref de uma associação, o idref se refere
     * aos id dos elementos presentes dentro de uma associação.
     *
     * @param eElement - Elemento que vai ser extraído o idref
     * @return - retorna uma String com o ID (idref).
     */
    private String getIdref(Element eElement) {
        String idref;
        idref = eElement.getAttribute("xmi.idref");
        return idref;
    }

    /**
     * Método para pegar o elemento UML:UseCase, que está presente dentro dos
     * includes e extends.
     * @param eElement - Elemento que contém o elemento UML:UseCase
     * @return - retorna o Elemento UML:UseCase
     */
    private Element getElementUseCase(Element eElement){
        NodeList nList = eElement.getElementsByTagName("UML:UseCase");
        
        if(nList.getLength() != 0)
            if(isElementNode(nList.item(0)))
                return (Element) nList.item(0);
        
        return null; //LançarException
    }
    
    //</editor-fold>     
    
    //<editor-fold defaultstate="collapsed" desc="getDiagrams">
    
    public ArrayList<UmlDiagram> getDiagrams() {

        //@TODO: clonagem dos objetos (ver imutabilidade)
        return this.diagrams;
    }

    //</editor-fold>
    
}
