import java.util.Scanner;

public class questao11 {
    public static String inversao(String frase, int i, String resp){
        if(i < 0){
            return resp; // caso base se i < 0 return resp 
        }
        resp += frase.charAt(i); // concatenando o char i na string resp 
        return inversao(frase, i - 1, resp); // chamada recursiva chamando a função novamente com i - 1 
    }
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        String frase = sc.nextLine();
        while(frase.length() != 3 || frase.charAt(0) != 'F' || frase.charAt(1) != 'I' || frase.charAt(2) != 'M'){
            System.out.println(inversao(frase, frase.length() - 1, "")); // passamos a frase do teste, o indice do ultimo caractere válido e uma string vazia pra poder escrever nela 
            frase = sc.nextLine();
        }
        sc.close();
    }
}