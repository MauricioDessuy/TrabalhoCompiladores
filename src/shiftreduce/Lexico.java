/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shiftreduce;

/**
 *
 * @author matthias.trennepohl
 */
public class Lexico {

    public static String fonte = "";

    public Lexico(String fonte) {
        this.fonte = fonte;
    }

    public static String reconheceNumero() {
        String resultado = "";
        int indice = 0;
        int estado = 0;

        while (true) {
            if (indice > fonte.length()) {
                return resultado;
            }
            String s = fonte.substring(indice, indice + 1);
            
            if (estado == 0) {
                System.out.println("Estado: " + estado);
                if (s.charAt(0) >= '0' && s.charAt(0) <= '9') {
                    estado = 1;
                    resultado = resultado + s;
                    indice++;
                } else if (s.equals(".")) {
                    estado = 1;
                    resultado = resultado + s;
                    fonte = fonte.substring(1);
                } else {
                    return resultado;
                }
            }
            else if (estado == 1) {
                if (s.charAt(0) >= '0' && s.charAt(0) <= '9') {
                    estado = 1;
                    resultado = resultado + s;
                    fonte = fonte.substring(1);
                } else if (s.equals(".")) {
                    estado = 2;
                    resultado = resultado + s;
                    fonte = fonte.substring(1);
                } else {
                    return resultado;
                }
            }
            else if (estado == 2) {
                if (s.charAt(0) >= '0' && s.charAt(0) <= '9') {
                    estado = 2;
                    resultado = resultado + s;
                    fonte = fonte.substring(1);
                } else if (s.equals(".")) {
                    estado = 2;
                    resultado = resultado + s;
                    fonte = fonte.substring(1);
                } else {
                    return resultado;
                }
            }
            else if (estado == 3) {
                if (s.charAt(0) >= '0' && s.charAt(0) <= '9') {
                    estado = 3;
                    resultado = resultado + s;
                    fonte = fonte.substring(1);
                } else if (s.equals(".")) {
                    estado = 4;
                    resultado = resultado + s;
                    fonte = fonte.substring(1);
                } else {
                    return resultado;
                }
            }
            else if (estado == 4) {
                return resultado;
            }
            if (fonte.length() == 0) {
                return resultado;
            }
        }

    }

    public static String reconheceOperador() {
        String resultado = "";
        int indice = 0;
        int estado = 0;

        String s = fonte.substring(0, 1);
        if (s.equals("+") || s.equals("*") || s.equals("(") || s.equals(")")) {
            resultado = resultado + s;
            fonte = fonte.substring(1);
            return s;
        } else {
            return "";
        }
    }

    public String analise() {
        String res = "";
        while (true) {
            String token = "";
            token = reconheceNumero();
            if (!token.isEmpty()) {
                res += "i";
            } else {
                token = reconheceOperador();
                if (!token.isEmpty()) {
                    res += token;
                }
            }

            if (!token.isEmpty()) {
                System.out.println("Erro");
                return "";
            } else {
                if (!fonte.isEmpty()) {
                    System.out.println("Certo");
                    return fonte;
                }
            }
        }
    }
}
