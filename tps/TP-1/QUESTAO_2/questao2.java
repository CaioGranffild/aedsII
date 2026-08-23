import java.util.Random;
import java.util.Scanner;
public class questao2 {
    public static String Alterar( String frase, Random gerador){ // funçãozinha que vai alterar a frase
        
        String resp = ""; // string vazia para escrever a resposta
        char letra1 = ((char)('a' + (Math.abs(gerador.nextInt())%26))); // achando a letra 1 aleatoria de acordo com a seed // LETRA DA FRASE
        char letra2 = ((char)('a' + (Math.abs(gerador.nextInt())%26))); // achando a letra 2 aleatoria de acordo com a seed // LETRA QUE VAI VIRAR 
        for( int i = 0; i < frase.length(); i++){ // percorrendo a frase para achar a letra 1 e substituir pela letra 2
            if(frase.charAt(i) == letra1){
                resp += letra2; // se for a letra 1 = substitui pela letra 2
            } else resp += frase.charAt(i); // se não for a letra 1, apenas adiciona a letra normal
        }
        return resp; // retorna a resposta 
    }  
    public static void main(String[] args){
        Random gerador = new Random();
        gerador.setSeed(4);
        Scanner sc = new Scanner(System.in); // scanner para ler a frase
        String frase = sc.nextLine(); 
        while(!(frase.length()==3 && frase.charAt(0)=='F' && frase.charAt(1)=='I' && frase.charAt(2)=='M')){ // enquanto a frase não for fim continua o while 
            System.out.println(Alterar(frase,gerador)); // chama a funçaozinha e printa a resposta 
            frase = sc.nextLine(); // le a proxima frase ate achar o fim 
        }
        sc.close();
    }
}
