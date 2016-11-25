package br.edu.unipampa.astah.xmi;

//<editor-fold defaultstate="collapsed" desc="Importações">

import br.edu.unipampa.uml.*;
import java.util.ArrayList;
import java.io.File;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

//</editor-fold>

/**
 * @author yuryalencar Esta classe possui os métodos necessários para extrair
 * informações de modelos UML armazenados em arquivos XMI.
 */
public class ParserXMI {

    //<editor-fold defaultstate="collapsed" desc="Attributes">
   
    private final ArrayList<UmlDiagram> diagrams;

    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Document in memory">
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
            // Igual para qualquer modelo seja ele de casos de uso ou mesmo um
            //Diagrama de Atividades.
            addModel(doc);

            // A Ordem de adição importa, ou seja, primeiro tem que ser sempre adicionados
            //os modelos e a partir deles os elementos, em seguida as associações e
            //dependencias.
            // Diagramas de Caso de uso
            manager(doc, "UML:Actor", true);
            manager(doc, "UML:UseCase", true);
            manager(doc, "UML:Association", true);
            manager(doc, "UML:Include", true);
            manager(doc, "UML:Extend", true);
            manager(doc, "UML:Generalization", true);

            //Diagramas de Atividades
            manager(doc, "UML:Pseudostate", false);
            manager(doc, "UML:ActionState", false);
            manager(doc, "UML:FinalState", false);
            manager(doc, "UML:Transition", false);
            
            //Capturando os taggedValue (Não importa se é false ou true)
            //ele vai pegar caso o diagrama for de casos de uso ou atividades.
            manager(doc, "UML:TaggedValue", false);
           
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

    //<editor-fold defaultstate="collapsed" desc="Create and insert a model (UmlUseCaseDiagram and ActivityDiagram)">
    /**
     * Método para adicionar um novo modelo, pegando seu id e nome e o
     * adicionando na lista de diagramas.
     *
     * @param doc - O documento para que assim tirar o elemento e seus atributos
     * necessários para sua criação.
     */
    private void addModel(Document doc) {
        NodeList nList = doc.getElementsByTagName("UML:Model");
        for (int i = 0; i < nList.getLength(); i++) {
            if (isElementNode(nList.item(i))) {
                Element eElement = (Element) nList.item(i);

                UmlDiagram newDiagramUseCase;

                newDiagramUseCase = new UmlUseCaseDiagram(getId(eElement), getName(eElement));
                diagrams.add(newDiagramUseCase);
            }
        }
    }

    //</editor-fold>
     
    //<editor-fold defaultstate="collapsed" desc="Manager (UmlUseCaseDiagram and ActivityDiagram)">

    /**
     * Método para criar uma lista de nodos que será utilizada tanto para a
     * pesquisa de diagramas, atores, casos de uso e associações.
     *
     * @param fileName - nome do arquivo XMI que deve fazer a pesquisa
     * @param nameTag - nome da tag que referencia os elementos, associações ou
     * diagramas.
     * @param option - opção que tem haver com qual quer adicionar, no momento
     * está 1.p/ addDiagramas ou addElementos e 2.p/ addAssociações
     */
    private void manager(Document doc, String nameTag, boolean isUseCase) {

        //Criando uma lista com todos os nodos de acordo com a tag Fornecida
        NodeList nList = doc.getElementsByTagName(nameTag);

        if (nList.getLength() != 0) {
            // Percorrer toda a lista de nodos criadas anteriormente
            for (int i = 0; i < nList.getLength(); i++) {

                // Pegando o nodo da "rodada"
                Node nNode = nList.item(i);

                // Verificando o tipo do nodo
                if (isElementNode(nNode)) {
                    //Passo o nodo para Element para poder tirar seus dados.
                    Element eElement = (Element) nNode;

                    if(isUseCase){
                        //Verifico qual tipo é o elemento
                        if (eElement.getNodeName().equalsIgnoreCase("UML:Actor")
                                && eElement.hasAttribute("xmi.id")) {
                            addActor(eElement);

                        } else if (eElement.getNodeName().equalsIgnoreCase("UML:UseCase")
                                && eElement.hasAttribute("xmi.id")) {
                            addUseCase(eElement);

                        } else if (eElement.getNodeName().equalsIgnoreCase("UML:Association")
                                && eElement.hasAttribute("xmi.id")) {
                            addAssociation(eElement);

                        } else if (eElement.getNodeName().equalsIgnoreCase("UML:Include")
                                && eElement.hasAttribute("xmi.id")) {
                            addInclude(eElement);

                        } else if (eElement.getNodeName().equalsIgnoreCase("UML:Extend")
                                && eElement.hasAttribute("xmi.id")) {
                            addExtend(eElement);

                        } else if (eElement.getNodeName().equalsIgnoreCase("UML:Generalization")
                                && eElement.hasAttribute("xmi.id")) {
                            addGeneralization(eElement);
                        }
                    // Caso não seja um caso de uso é um diagrama de atividades
                    } else {
                        if (eElement.getNodeName().equalsIgnoreCase("UML:Pseudostate")
                                && eElement.hasAttribute("xmi.id")) {
                            addPseudostate(eElement);

                        } else if (eElement.getNodeName().equalsIgnoreCase("UML:ActionState")
                                && eElement.hasAttribute("xmi.id")) {
                            addActionState(eElement);

                        } else if(eElement.getNodeName().equalsIgnoreCase("UML:FinalState")
                                && eElement.hasAttribute("xmi.id")) {
                            addFinalState(eElement);

                        } else if(eElement.getNodeName().equalsIgnoreCase("UML:Transition")
                                && eElement.hasAttribute("xmi.id")) {
                            addTransition(eElement);

                        }
                    }
                    
                    if(eElement.getNodeName().equalsIgnoreCase("UML:TaggedValue")
                            && eElement.hasAttribute("xmi.id")){
                        addTaggedValue(eElement);
                    }
                    
                }
            }
        }
    }

    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="UmlUseCaseDiagram">
    
    //<editor-fold defaultstate="collapsed" desc="Create and insert Elements(Actors and UseCases)">
    /**
     * Método para criar um Ator e o adiciona dentro da lista de elementos,
     * lista presente dentro da classe UmlDiagram.
     *
     * @param eElement - Elemento para que possa retirar seus atributos
     * necessários para sua criação.
     */
    private void addActor(Element eElement) {
        String id = getId(eElement);
        UmlElement newActor = new UmlActorElement(id, getName(eElement));
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
        UmlElement newUseCase = new UmlUseCaseElement(id, getName(eElement));
        getDiagram(id).addElement(newUseCase);
    }

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Create, Insert and Verify Associations">
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
        String id, name, idrefElementA = "", idrefElementB = "";
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
        if (nList.getLength() != 0) {
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

                        if (nList.getLength() != 0) {
                            // Somente um para cada ASSOCIATIONEND
                            idrefElementA = getIdref(getClassifier(nList.item(0)));
                        } else {
                            //Lançar exception
                        }
                        // Neste elemento eu pego 
                        nList = eElement.getElementsByTagName("UML:AssociationEnd.participant");

                        if (nList.getLength() != 0) {
                            // Somente um para cada ASSOCIATIONEND
                            idrefElementB = getIdref(getClassifier(nList.item(0)));
                        } else {
                            //Lançar exception
                        }
                        // Criando um diagrama para nao ficar toda hora realizando uma
                        // pesquisa dentro da lista.
                        UmlDiagram diagram = getDiagram(id);
                        UmlElement element = diagram.getElement(idrefElementA);
                        UmlElement element2 = diagram.getElement(idrefElementB);

                        newAssociation = new UmlAssociation(id, name,
                                element, element2);

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

        if (nList.getLength() != 0) {
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

        if (nList.getLength() != 0) {
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

    //<editor-fold defaultstate="collapsed" desc="Create and insert a Generalization">
    /**
     * Método para a criação e inserção de uma generalização dentro de um
     * diagrama de casos de Uso da Uml.
     *
     * @param eElement - Recebe o elemento que contém a generalization, para que
     * assim se possa extrair todos os dados necessários.
     */
    private void addGeneralization(Element eElement) {
        String id = getId(eElement), label = getName(eElement), idChild = "", idParent = "";
        UmlDiagram diagram;
        UmlDependency newGeneralization;

        NodeList nList = eElement.getElementsByTagName("UML:Generalization.child");

        if (nList.getLength() != 0) {
            idChild = getGeneralizableElement(nList);

        } else {
            //lançar exception
        }

        nList = eElement.getElementsByTagName("UML:Generalization.parent");

        if (nList.getLength() != 0) {
            idParent = getGeneralizableElement(nList);

        } else {
            //lançar exception
        }

        diagram = getDiagram(id);

        newGeneralization = new UmlDependency(id, label,
                diagram.getElement(idParent), diagram.getElement(idChild),
                UmlDependencyType.verifyUmlDependency(3));

        diagram.addDependency(newGeneralization);
    }

    /**
     * Método para a extração do número identificador do elemento que participa
     * da generalization, este elemento é refenciado dentro da mesma.
     *
     * @param nList - Lista de nodos que dentro contém o atributo do id, podendo
     * ser uma lista tanto para um elemento filho quanto um pai.
     * @return - retorna uma String com o valor do idRef do elemento escolhido.
     */
    private String getGeneralizableElement(NodeList nList) {
        String idGeneralization;

        if (isElementNode(nList.item(0))) {

            Element eElement = (Element) nList.item(0);

            nList = eElement.getElementsByTagName("UML:GeneralizableElement");

            if (nList.getLength() != 0 && isElementNode((Element) nList.item(0))) {
                idGeneralization = getIdref((Element) nList.item(0));
                return idGeneralization;
            } else {
                //lançar Exception
            }
        }

        return null;//lançar exception
    }

    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Create and insert an Include">
    /**
     * Método para adicionar um include na lista de dependencias de um diagrama
     * UML.
     *
     * @param eElement - Elemento que contenha os dados a ser retirados.
     */
    private void addInclude(Element eElement) {

        String idInclude, nameInclude, idrefBase = "", idrefAddition = "";

        idInclude = getId(eElement);
        nameInclude = getName(eElement);

        NodeList nList = eElement.getElementsByTagName("UML:Include.base");

        if (nList.getLength() != 0) {
            if (isElementNode(nList.item(0))) {
                idrefBase = getIdref(getElementUseCase((Element) nList.item(0)));
            }
        } else {
            //lançar exception
        }

        nList = eElement.getElementsByTagName("UML:Include.addition");

        if (nList.getLength() != 0) {
            if (isElementNode(nList.item(0))) {
                idrefAddition = getIdref(getElementUseCase((Element) nList.item(0)));
            }
        } else {
            //lançar exception
        }

        // Criando um diagrama para nao ficar toda hora realizando uma
        // pesquisa dentro da lista.
        UmlDiagram diagram = getDiagram(idInclude);

        UmlDependency newInclude = new UmlDependency(idInclude, nameInclude,
                diagram.getElement(idrefBase),
                diagram.getElement(idrefAddition),
                UmlDependencyType.verifyUmlDependency(2));
        //Para não precisar atualizar o item presente na lista.

        diagram.addDependency(newInclude);
    }

    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Create and insert an Extend">
    /**
     * Método para adicionar um Extend na lista de dependencias de um diagrama
     * UML
     *
     * @param eElement - Elemento para que se possa fazer a extração dos dados.
     */
    private void addExtend(Element eElement) {

        String idExtend, nameExtend, idrefBase = "", idrefExtension = "";

        idExtend = getId(eElement);
        nameExtend = getName(eElement);

        NodeList nList = eElement.getElementsByTagName("UML:Extend.base");

        if (nList.getLength() != 0) {
            if (isElementNode(nList.item(0))) {
                idrefBase = getIdref(getElementUseCase((Element) nList.item(0)));
            }
        } else {
            //lançar exception
        }

        nList = eElement.getElementsByTagName("UML:Extend.extension");

        if (nList.getLength() != 0) {
            if (isElementNode(nList.item(0))) {
                idrefExtension = getIdref(getElementUseCase((Element) nList.item(0)));
            }
        } else {
            //lançar exception
        }

        // Criando um diagrama para nao ficar toda hora realizando uma
        // pesquisa dentro da lista.
        UmlDiagram diagram = getDiagram(idExtend);

        UmlDependency newExtend = new UmlDependency(idExtend, nameExtend,
                diagram.getElement(idrefBase),
                diagram.getElement(idrefExtension),
                UmlDependencyType.verifyUmlDependency(1));
        //Para não precisar atualizar o item presente na lista.

        diagram.addDependency(newExtend);
    }

    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="getElementUseCase">
    /**
     * Método para pegar o elemento UML:UseCase, que está presente dentro dos
     * includes e extends.
     *
     * @param eElement - Elemento que contém o elemento UML:UseCase
     * @return - retorna o Elemento UML:UseCase
     */
    private Element getElementUseCase(Element eElement) {
        NodeList nList = eElement.getElementsByTagName("UML:UseCase");

        if (nList.getLength() != 0) {
            if (isElementNode(nList.item(0))) {
                return (Element) nList.item(0);
            }
        }

        return null; //LançarException
    }

    //</editor-fold>

    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="ActivityDiagram">

    /**
     *  Método para a adição de um pseudostate de um diagrama de atividades
     *que pode ser tanto um estado incial ou um branche ou merge
     * @param eElement - elemento que contém a atividade inicial, merge ou branche
     */
    private void addPseudostate(Element eElement){
        String id;
        UmlElement pseudostate;
        if(eElement.getAttribute("kind").equals("initial")){
            id = getId(eElement);
            pseudostate = new UmlInitialActivityElement(getId(eElement), getName(eElement));
            getDiagram(id).addElement(pseudostate);
        } else if (eElement.getAttribute("kind").equals("junction")){
            id = getId(eElement);
            pseudostate = new UmlDecisionNodeElement(id, getName(eElement));
            //Faltando implementar a inserção das transições
            getDiagram(id).addElement(pseudostate);
        } else {
            // Lançar exception 
        }
    }
    
    /**
     * Método para a adição de uma ação (Atividade) de um
     * diagrama de atividades
     * @param eElement - elemento que contém a ação
     */
    private void addActionState(Element eElement){
        String id = getId(eElement);
        UmlActivityElement action = new UmlActivityElement(id, getName(eElement));
        //Faltando implementar sobre a entrada no estado
        getDiagram(id).addElement(action);
    }
    
    private void addTransition(Element eElement){
        String id = getId(eElement), name = getName(eElement), idSource="", idTarget="";
        UmlDirectedAssociation transition;
        UmlDiagram a = getDiagram(id);
        
        NodeList nList = eElement.getElementsByTagName("UML:Transition.source");
        
        if(isElementNode(nList.item(0))){
            idSource = getIdref(getStateVertex(nList.item(0)));
        }
        
        nList = eElement.getElementsByTagName("UML:Transition.target");
        
        if(isElementNode(nList.item(0))){
            idTarget = getIdref(getStateVertex(nList.item(0)));
        }
        
        transition = new UmlDirectedAssociation(id, name, a.getElement(idSource), a.getElement(idTarget));
        //Faltando implementar algo mais específico de uma transição
        a.addAssociation(transition);
    }
    
    /**
     * Método para pegar o elemento StateVertex,
     * utilizado para referenciar da onde saí e de onde
     * chega uma transição no diagrama de atividades.
     * @param nNode - Nodo onde contém o state vértex para ser extraído.
     * @return - o Elemento para que se possa ser retirado o seu
     * idref.
     */
    private Element getStateVertex(Node nNode){
        Element eElement = (Element) nNode;
        NodeList nList = eElement.getElementsByTagName("UML:StateVertex");
        if(isElementNode(nList.item(0)))
            return (Element) nList.item(0);
        return null; // lançar exception
    }
    
    /**
     * Método para a adição de uma ação final (Atividade) de um
     * diagrama de atividades
     * @param eElement - elemento que contém a ação final
     */
    private void addFinalState(Element eElement){
        String id = getId(eElement);
        UmlElement action = new UmlFinalActivityElement(id, getName(eElement));
        //Faltando implementar algo mais específico de uma atividade final
        getDiagram(id).addElement(action);
    }
    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="TaggedValue">
    
    /**
     * Método para adicionar uma taggedvalue presente no documento xmi
     * ao seu respectivo elemento.
     * @param eElement - Elemento que contém a taggedValue para extrair de
     * quem pertence além da tag e do seu respectivo valor.
     */
    private void addTaggedValue(Element eElement){
        UmlDiagram diagram;
        String id;
        TaggedValue tv = new TaggedValue(eElement.getAttribute("tag"), eElement.getAttribute("value"), getId(eElement));
        NodeList nList = eElement.getElementsByTagName("UML:ModelElement");
        if(isElementNode(nList.item(0))){
            eElement = (Element) nList.item(0);
        }else {
            //Lançar exception
        }
        id = getIdref(eElement);
        diagram = getDiagram(id);
        if(diagram.getId().equals(id)){
            diagram.addTaggedValue(tv);
        } else {
            if(diagram.getElement(id) != null){
                diagram.getElement(id).addTaggedValue(tv);
            } else if(diagram.getAssociation(id) != null){
                diagram.getAssociation(id).addTaggedValue(tv);
            } else if(diagram.getDependency(id) != null){
                diagram.getDependency(id).addTaggedValue(tv);
            }
        }
        
    }
    
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Search for diagram and extract ID">
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

    //<editor-fold defaultstate="collapsed" desc="Verify node type">
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

    //<editor-fold defaultstate="collapsed" desc="getId, getName, getIdref">
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

    //</editor-fold>     

    //<editor-fold defaultstate="collapsed" desc="getDiagrams">
    public List <UmlDiagram> getDiagrams() {

        //@TODO: clonagem dos objetos (ver imutabilidade)
        return this.diagrams;
    }

    //</editor-fold>

}
