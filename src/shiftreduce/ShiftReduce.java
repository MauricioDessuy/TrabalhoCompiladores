package shiftreduce;

/**
 *
 * @author mauricio
 */
public class ShiftReduce {

    public String[][] gramatica = {{"E", "E+T", "T"},
                                   {"T", "T*F", "F"},
                                   {"F", "(E)", "i"}};
    
    public static String pilha_e = "";
    public static String pilha_d = "i*i";
            
    private static void transfere() {
        pilha_e = pilha_e + pilha_d.substring(0, 1);
    }
    
    private static boolean ehInicioRegra() {
        
        return true;
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    }
    
}
