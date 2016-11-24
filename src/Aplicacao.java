
import br.edu.unipampa.uml.UmlDiagram;
import br.edu.unipampa.astah.xmi.ParserXMI;
import br.edu.unipampa.json.FromJSON;
import br.edu.unipampa.uml.*;
import java.util.List;

/**
 *
 * @author Yury Alencar
 */
public class Aplicacao {

    //<editor-fold defaultstate="collapsed" desc="Main">
    public static void main(String[] args) throws Exception {

        ParserXMI parser = new ParserXMI("/home/yuryalencar/NetBeansProjects/Parser_XMI/src/br/edu/unipampa/arquivo/xmi/Activity Diagram Example1.xml");
        ParserXMI parser1 = new ParserXMI("/home/yuryalencar/NetBeansProjects/Parser_XMI/src/br/edu/unipampa/arquivo/xmi/PintarCasa.xml");
        
        new FromJSON().fileToJson(parser.getDiagrams().get(0), "DiagramaAtividades");
        new FromJSON().listToJson(parser.getDiagrams().get(0).getAssociations(), "DiagramaAtividadesAssociacoes");
        new FromJSON().listToJson(parser.getDiagrams().get(0).getElements(), "DiagramaAtividadesElementos");
        new FromJSON().fileToJson(parser1.getDiagrams().get(0), "DiagramaCasosDeUso");
        new FromJSON().listToJson(parser1.getDiagrams().get(0).getAssociations(), "DiagramaCasosDeUsoAssociacoes");
        new FromJSON().listToJson(parser1.getDiagrams().get(0).getElements(), "DiagramaCasosDeUsoElementos");
        new FromJSON().listToJson(parser1.getDiagrams().get(0).getDependencies(), "DiagramaCasosDeUsoDependencias");
        System.out.println("-------------- ACTIVITY DIAGRAM --------------");
        for (UmlDiagram d : parser.getDiagrams()) {
            System.out.println(d.toString());
        }

        System.out.println("----------------------------------------------");

        System.out.println("-------------- USE CASE DIAGRAM --------------");
        for (UmlDiagram d : parser1.getDiagrams()) {
            System.out.println(d.toString());
        }
        System.out.println("----------------------------------------------");
    }

    //</editor-fold>
}
