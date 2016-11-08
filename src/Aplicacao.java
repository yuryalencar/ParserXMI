
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
        
        new FromJSON().listToJson(parser.getDiagrams(), "DiagramaAtividades");
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
