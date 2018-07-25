/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shifreducev2;

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

    public static void main(String[] args) {
        Lexico lexico = new Lexico("12*3.2+15*3+(2+7)");
        String expressao = lexico.analise();
        if (expressao.length() > 0) {
            Shiftreduce sr = new Shiftreduce();
            sr.pilha_E = "";
            sr.pilha_D = expressao;
            if (sr.shiftreduce()) {
                System.out.println("aceita!!!");
            } else {
                System.out.println("rejeita!!!");
            }
        }
    }

}
