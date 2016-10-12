import org.inupampa.uml.*;
import org.unipampa.xmi.*;

/**
 *
 * @author Yury Alencar
 */
public class Aplicacao {
    
    //<editor-fold defaultstate="collapsed" desc="Main">
    
    public static void main(String[] args) {
    
            ParserXMI parser = new ParserXMI("/home/yuryalencar/NetBeansProjects/Parser_XMI/src/org/unipampa/arquivoxmi/Activity Diagram Example1.xml");
            ParserXMI parser1 = new ParserXMI("/home/yuryalencar/NetBeansProjects/Parser_XMI/src/org/unipampa/arquivoxmi/PintarCasa.xml");
            
            System.out.println("-------------- ACTIVITY DIAGRAM --------------");
            for(UmlDiagram d : parser.getDiagrams()){
                System.out.println(d.toString());
            }
            System.out.println("----------------------------------------------");
            
            System.out.println("-------------- USE CASE DIAGRAM --------------");
            for(UmlDiagram d : parser1.getDiagrams()){
                System.out.println(d.toString());
            }
            System.out.println("----------------------------------------------");
    }
    //</editor-fold>
    
}
