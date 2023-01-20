import algoritmo.Executar;
import jgap.Application;
import org.jgap.InvalidConfigurationException;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try {
//            if("proprio".equals(args[0])){
                Application.main(args);
//            }else{
//                Executar.main(args);
//            }
        } catch (InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }
    }
}