import org.inupampa.uml.*;
import org.unipampa.xmi.*;

/**
 *
 * @author Yury Alencar
 */
public class Aplicacao {
    
    
    public static void main(String[] args) {
    
            ParserXMI parser = new ParserXMI("/a/b/c.xmi");
            
            for(UmlDiagram d : parser.getDiagrams()){
                System.out.println(d.toString());
            }
            
        
    }
    
}
