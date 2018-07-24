package shiftreduce;

/**
 *
 * @author mauricio
 */
public class ShiftReduce {

    public static String[][] gramatica = {{"E", "E+T", "T"},
    {"T", "T*F", "F"},
    {"F", "(E)", "i"}};

    public static String pilha_e = "";
    public static String pilha_d = "i*i";

    private static void transfere() {
        if (pilha_d.isEmpty()) {
            return;
        }
        pilha_e = pilha_e + pilha_d.substring(0, 1);
        pilha_d = pilha_d.substring(1);
    }

    private static Integer ehInicioRegra() {
        if (pilha_d.isEmpty()) {
            return -1;
        }
        int res = -1;
        int count = 0;

        while ((res < 0) && (count < pilha_e.length())) {
            String expressao = pilha_e.substring(count) + pilha_d.substring(0, 1);
            for (String[] regra : gramatica) {
                for (int i = 1; i < regra.length; i++) {
                    if (regra[i].length() >= expressao.length()) {
                        if (expressao.equals(regra[i].substring(0, expressao.length()))) {
                            res = count;
                        }
                    }
                }
            }
            count++;
        }
        return res;
    }

    public static String reduz(String expressao) {
        String res = "";
        for (String[] regra : gramatica) {
            for (int i = 1; i < regra.length; i++) {
                if (expressao.equals(regra[i])) {
                    res = regra[0];
                }
            }
        }
        return res;
    }
    
    public static boolean tentaReduzir() {
        int count = 0;
        boolean res = false;
        while ((!res) && (count < pilha_e.length())) {
            String reducao = reduz(pilha_e.substring(count));
            if (!reducao.isEmpty()) {
                pilha_e = pilha_e.substring(0, count) + reducao;
                res = true;
            }
            count++;
        }
        return res;
    }
    
    public static boolean shiftReduce() {
        int iter = 0;
        while (true) {
            iter++;
            int tamPilha_D = pilha_d.length();
            System.out.println("Iteracao -> " + iter + " " + pilha_e + "    " + pilha_d);
            boolean reduziu = false;
            if (ehInicioRegra() >= 0) {
                transfere();
            } else {
                reduziu = tentaReduzir();
                if (!reduziu) {
                    transfere();
                }
            }
            if (tamPilha_D == 0 && !reduziu) {
                return pilha_e.equals(gramatica[0][0]);
            }
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        pilha_e = "";
        pilha_d = "i*i";
        System.out.println(shiftReduce() ? "Aceita" : "Rejeita");
        //System.out.println(ehInicioRegra());
        //System.out.println(reduz("T*F"));
    }

}
