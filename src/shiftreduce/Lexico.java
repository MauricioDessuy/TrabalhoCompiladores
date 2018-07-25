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
        
    public static String reconheceNumero(){
        String resultado = "";
        int indice = 0;
        int estado = 0;
        
        while (true) {
            if (estado == 0) {
                String s = fonte.substring(0, 1);
                if(s.charAt(0) >= '0' && s.charAt(0) <='9'){
                    estado = 0;
                    resultado = resultado + s;
                    fonte = fonte.substring(1);
                }
                else if (s.equals(".")) {
                    estado = 1;
                    resultado = resultado + s;
                    fonte = fonte.substring(1);
                } else {
                    return resultado;
                }
            }
        }
        
    }
    
    public static String reconheceOperador(){
        
    }
}
