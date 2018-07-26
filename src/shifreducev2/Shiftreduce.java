package shifreducev2;

import java.util.regex.Pattern;

/**
 *
 * @author manuel
 */
public class Shiftreduce {

    public String[][] gramatica = {{"E", "E+T", "T"},
    {"T", "T*F", "F"},
    {"F", "(E)", "i"}};
    public String pilha_E = "";
    public String pilha_D = "i*i";
    public String pilhaNumeros_E = "";
    public String pilhaNumeros_D = "";
    public String[] vetorNumeros;
    
    public void transfere() {
        if (pilha_D.length() > 0) {
            pilha_E = pilha_E + pilha_D.substring(0, 1);
            pilha_D = pilha_D.substring(1);
        }
    }

    public int eh_inicio_de_regra() {
        if (pilha_D.length() == 0) {
            return -1;
        }

        int res = -1;
        int cont = 0;
        while ((res < 0) && (cont < pilha_E.length())) {
            String s = pilha_E.substring(cont)
                    + pilha_D.substring(0, 1);
            for (String[] regra : gramatica) {
                for (int i = 1; i < regra.length; i++) {
                    if (regra[i].length() >= s.length()) {
                        if (s.equals(
                                regra[i].substring(
                                        0, s.length()))) {
                            res = cont;
                        }
                    }
                }
            }
            cont++;
        }
        return res;
    }

    public String reduz(String expressao) {
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

    public boolean tenta_reduzir() {
        boolean res = false;
        int cont = 0;
        while ((res == false) && (cont < pilha_E.length())) {
            String reducao = reduz(
                    pilha_E.substring(cont));
            if (!(reducao.equals(""))) {
                pilha_E = pilha_E.substring(0, cont)
                        + reducao;

                res = true;
            }
            cont++;
        }
        return res;
    }

    public boolean shiftreduce() {
        int iter = 0;
        while (true) {
            iter++;
            int tam_pilhaD = pilha_D.length();
            System.out.println("iter:"
                    + String.valueOf(iter)
                    + " $" + pilha_E + "   " + pilha_D + "$");
            boolean reduziu = false;
            if (eh_inicio_de_regra() >= 0) {
                transfere();
            } else {
                reduziu = tenta_reduzir();
                if (reduziu == false) {
                    transfere();
                }
            }
            if ((tam_pilhaD == 0)
                    && (reduziu == false)) {
                return pilha_E.equals(gramatica[0][0]);
            }
        }

    }

    private void trocarNumerosPorId(String expressao, Shiftreduce sr) {
        char[] letras = expressao.toCharArray();
        for (char letra : letras) {
            if (Character.isDigit(letra)) {
                sr.pilhaNumeros_D += letra;
            } else {
                if (!sr.pilhaNumeros_D.endsWith("|")) {
                    sr.pilhaNumeros_D += "|";
                }
            }
        }
        sr.vetorNumeros = sr.pilhaNumeros_D.split(Pattern.quote("|"));
    }
    
    public static void main(String[] args) {
        String expr = "20*3+1";
        Lexico lexico = new Lexico(expr);
        String expressao = lexico.analise();
        if (expressao.length() > 0) {
            Shiftreduce sr = new Shiftreduce();
            sr.pilha_E = "";
            sr.pilha_D = expressao;
            sr.pilhaNumeros_E = "";
            sr.pilhaNumeros_D = "";
            sr.trocarNumerosPorId(expr, sr);
            System.out.println(sr.pilhaNumeros_D);
            if (sr.shiftreduce()) {
                System.out.println("aceita!!!");
            } else {
                System.out.println("rejeita!!!");
            }
        }
    }
}
