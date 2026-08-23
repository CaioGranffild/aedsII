import java.util.Scanner;
public class questao8{ 
    public static boolean validacao(String senha){
        boolean maiscula = false;
        boolean minuscula = false;
        boolean numero = false;
        boolean especial = false;
        if(senha.length() <8) return false;
        for(int i = 0; i < senha.length(); i++){
            if(senha.charAt(i) >= 'A' && senha.charAt(i) <= 'Z') maiscula = true;
            else if(senha.charAt(i) >= 'a' && senha.charAt(i) <= 'z') minuscula = true;
            else if(senha.charAt(i) >= '0' && senha.charAt(i) <= '9') numero = true;
            else especial = true;
        }
        if(maiscula && minuscula && numero && especial) return true;
        else return false;
    }
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        String senha = sc.nextLine();
        while(!(senha.length()==3 && senha.charAt(0)=='F' && senha.charAt(1)=='I' && senha.charAt(2)=='M')){
            if(validacao(senha)){
                System.out.println("SIM");
            }
            else System.out.println("NAO");
            senha = sc.nextLine();
        }
        sc.close();
    }
}
