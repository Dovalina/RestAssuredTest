package dev;
import java.util.*;
public class palindromo {

	

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Kepa
 * 
 * Se pide realizar un programa en Java que permita comprobar sin una frase introducida por teclado en tiempo de ejecución es palíndromo.
 * La frase podrá contener, además de letras, los siguientes caracteres: espacio en blanco
 * punto y coma. Deberán eliminarse estos caracteres y, una vez eliminados, comprobar si es palindromo.
 */




    
        public static void main(String[] args) {
        Scanner sc= new Scanner(System.in);
        System.out.print("Introduce una frase (puede tener puntos, comas y espacios): ");
        String s=sc.nextLine();
        
        s=s.replace(" ", "");
        s=s.replace(",", "");
        s=s.replace(".", "");
        System.out.print(s);
        int fin = s.length()-1;
        int ini=0;
        boolean espalin=true;
        
        while(ini < fin){
            if(s.charAt(ini)!=s.charAt(fin)){
                espalin=false;
            }
        ini++;
        fin--;
        }
        if(espalin)
            System.out.print("\nEs palindromo.");
        else
            System.out.print("\nNo es palindromo.");
        
    }
}

