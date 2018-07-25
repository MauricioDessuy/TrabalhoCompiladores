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
public class Lexico {

    private String fonte = "";

    public Lexico(String f) {
        fonte = f;
    }

    public String reconhece_numero() {
        String res = "";
        int i = 0;
        int estado = 0;
        while (true) {
            String s = "$";
            if (i < fonte.length()) {
                s = fonte.substring(i, i + 1);
            }
            System.out.println("ëstado:" + String.valueOf(estado));
            if (estado == 0) {
                if ((s.charAt(0) >= '0') && (s.charAt(0) <= '9')) {
                    estado = 1;
                    res = res + s;
                    i++;
                } else {
                    return "";
                }
            } else if (estado == 1) {
                if ((s.charAt(0) >= '0') && (s.charAt(0) <= '9')) {
                    estado = 1;
                    res = res + s;
                    i++;
                } else if (s.equals(".")) {
                    estado = 2;
                    res = res + s;
                    i++;
                } else {
                    estado = 4;
                }

            } else if (estado == 2) {
                if ((s.charAt(0) >= '0') && (s.charAt(0) <= '9')) {
                    estado = 3;
                    res = res + s;
                    i++;
                } else {
                    return "";
                }

            } else if (estado == 3) {
                if ((s.charAt(0) >= '0') && (s.charAt(0) <= '9')) {
                    estado = 3;
                    res = res + s;
                    i++;
                } else {
                    estado = 4;
                }
            } else if (estado == 4) {
                fonte = fonte.substring(i);
                System.out.println(res);
                return res;
            }

            if (fonte.length() == 0) {
                return res;
            }

        }

    }

    public String reconhece_operador() {
        String res = "";
        String s = fonte.substring(0, 1);
        if (s.equals("+") || s.equals("*") || s.equals("(") || s.equals(")")) {
            res = res + s;
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
            token = reconhece_numero();
            if (token.length() > 0) {
                res = res + "i";
            } else {
                token = reconhece_operador();
                if (token.length() > 0) {
                    res = res + token;
                }
            }

            if (token.length() == 0) {
                System.out.println("ERRO");
                return "";
            } else {
                if (fonte.length() == 0) {
                    return res;
                }
            }

        }
    }

}
