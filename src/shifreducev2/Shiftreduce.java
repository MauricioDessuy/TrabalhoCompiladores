package shifreducev2;

import java.util.ArrayList;
import java.util.regex.Pattern;
import javax.swing.JTextArea;

/**
 *
 * @author manuel
 */
public class Shiftreduce {

    public String[][] gramatica = {{"E", "E+T", "E-T", "T"},
    {"T", "T*F", "T/F", "F"},
    {"F", "F^G", "FvG", "G"},
    {"G", "(E)", "i"}};
    public String pilha_E = "";
    public String pilha_D = "i*i";
    public String pilhaNumeros_E = "";
    public String pilhaNumeros_D = "";
    public String[] vetorNumeros_E;
    public String[] vetorNumeros_D;
    public ArrayList<Double> listaNumeros_D = new ArrayList();
    public ArrayList<Double> listaNumeros_E = new ArrayList();
    public static ArrayList<String> expressoes = new ArrayList();

    public void transfere() {
        if (pilha_D.length() > 0) {
            String valorTransferido = pilha_D.substring(0, 1);
            pilha_E = pilha_E + valorTransferido;
            pilha_D = pilha_D.substring(1);
            if (!listaNumeros_D.isEmpty() && valorTransferido.equals("i")) {
                Double numero = listaNumeros_D.get(0);
                listaNumeros_E.add(numero);
                listaNumeros_D.remove(0);
            }
        }
    }

    public int eh_inicio_de_regra() {
        if (pilha_D.length() == 0) {
            return -1;
        }

        int res = -1;
        int cont = 0;
        while ((res < 0) && (cont < pilha_E.length())) {
            String s = pilha_E.substring(cont) + pilha_D.substring(0, 1);
            for (String[] regra : gramatica) {
                for (int i = 1; i < regra.length; i++) {
                    if (regra[i].length() >= s.length()) {
                        if (s.equals(regra[i].substring(0, s.length()))) {
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
                    if (expressao.length() > 2 && !expressao.contains("(") && (expressao.contains("*")
                            || expressao.contains("/") || expressao.contains("-") || expressao.contains("+")
                            || expressao.contains("v") || expressao.contains("^"))) {
                        calcularOperacoes(expressao);
                    }
                    res = regra[0];
                }
            }
        }
        return res;

    }

    public void calcularOperacoes(String expressao) {
        //System.out.println("Expressao: " + expressao);
        expressoes.add(expressao);
        Double primeiro = listaNumeros_E.get(listaNumeros_E.size() - 2);
        Double segundo = listaNumeros_E.get(listaNumeros_E.size() - 1);
        //System.out.println("Lista antes: " + listaNumeros_E);
        listaNumeros_E.remove(listaNumeros_E.size() - 1);
//        System.out.println("Lista dps primeira remocao: " + listaNumeros_E);
        listaNumeros_E.remove(listaNumeros_E.size() - 1);
//        System.out.println("Lista dps segunda remocao: " + listaNumeros_E);
        Double resultado = 0.0;
//        if (expressao.contains("*")) {
//            resultado = primeiro * segundo;
//            listaNumeros_E.add(resultado);
//        } else if (expressao.contains("/")) {
//            resultado = primeiro / segundo;
//            listaNumeros_E.add(resultado);
//        } else if (expressao.contains("+")) {
//            resultado = primeiro + segundo;
//            listaNumeros_E.add(resultado);
//        } else if (expressao.contains("-")) {
//            resultado = primeiro - segundo;
//            listaNumeros_E.add(resultado);
//        } else if (expressao.contains("v")) {
//            resultado = Math.pow(primeiro, 1 / segundo);
//            listaNumeros_E.add(resultado);
//        } else if (expressao.contains("^")) {
//            resultado = Math.pow(primeiro, segundo);
//            listaNumeros_E.add(resultado);
//        }
        switch (expressao.charAt(1)) {
            
            case '*':
                resultado = primeiro * segundo;
                listaNumeros_E.add(resultado);
                break;
            case '+':
                resultado = primeiro + segundo;
                listaNumeros_E.add(resultado);
                break;
            case '-':
                resultado = primeiro - segundo;
                listaNumeros_E.add(resultado);
                break;
            case '/':
                resultado = primeiro / segundo;
                listaNumeros_E.add(resultado);
                break;
            case '^':
                resultado = Math.pow(primeiro, segundo);
                listaNumeros_E.add(resultado);
                break;
            case 'v':
                resultado = Math.pow(primeiro, 1 / segundo);
                listaNumeros_E.add(resultado);
                break;
        }
        //System.out.println("Lista dps add result: " + listaNumeros_E);
    }

    public boolean tenta_reduzir() {
        boolean res = false;
        int cont = 0;
        while (!res && (cont < pilha_E.length())) {
            String reducao = reduz(pilha_E.substring(cont));
            if (!(reducao.equals(""))) {
                pilha_E = pilha_E.substring(0, cont) + reducao;
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
            boolean reduziu = false;
            if (eh_inicio_de_regra() >= 0) {
                transfere();
            } else {
                reduziu = tenta_reduzir();
                if (!reduziu) {
                    transfere();
                }
            }
            if ((tam_pilhaD == 0) && !reduziu) {
                return pilha_E.equals(gramatica[0][0]);
            }
        }

    }

    public void trocarNumerosPorId(String expressao, Shiftreduce sr) {
        char[] letras = expressao.toCharArray();
        for (char letra : letras) {
            if (Character.isDigit(letra)) {
                sr.pilhaNumeros_D += letra;
            } else {
                if (!sr.pilhaNumeros_D.endsWith("|") && !sr.pilhaNumeros_D.isEmpty()) {
                    sr.pilhaNumeros_D += "|";
                }
            }
        }
        sr.vetorNumeros_D = sr.pilhaNumeros_D.split(Pattern.quote("|"));
        for (String vetorNumero : sr.vetorNumeros_D) {
            listaNumeros_D.add(Double.valueOf(vetorNumero));
        }
    }

    public static void main(String[] args) {
        boolean aceita = true;
        String expr = "(((5+2*3-4)/7)*4^2+11)v3";
        Lexico lexico = new Lexico(expr);
        String expressao = lexico.analise();
        if (expressao.length() > 0) {
            Shiftreduce sr = new Shiftreduce();
            sr.pilha_E = "";
            sr.pilha_D = expressao;
            sr.pilhaNumeros_E = "";
            sr.pilhaNumeros_D = "";
            sr.trocarNumerosPorId(expr, sr);
            if (sr.shiftreduce()) {
                //System.out.println("Resultado: " + sr.listaNumeros_E);
                //System.out.println("aceita!!!");
                JCompilador jCompilador = new JCompilador(sr.listaNumeros_E, aceita, expr, expressoes, sr.gramatica);
                jCompilador.setVisible(true);
            } else {
                //System.out.println("rejeita!!!");
            }
        }
    }
}
