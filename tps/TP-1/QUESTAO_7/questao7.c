#include <stdio.h>
char minusculo(char c){
    if(c >= 'A' && c <= 'Z'){ // se a letra for maiscula vai virar minuscula
        return c + 32; // num de dif na tabela ascii 
    }
    return c; // senao ela é minuscula msm
}
int substring(char str[]){
    int i = 0;
    int maior = 0;
    for (int j = 0; str[j] != '\0'; j++){
        for (int k = i; k < j; k++) {
            if (minusculo(str[k]) == minusculo(str[j])){
                i = k + 1; // atualiza o inicio da substring pra prox letra
                break;
            }
        }
        int tamanhoAtual = j - i + 1; // tamanho do substring 
        if (tamanhoAtual > maior) { 
            maior = tamanhoAtual; // atualiza o maior tamanho 
        } 
    }
    return maior;
}
int main(){
    char str[100];
    while(scanf("%s", str) != EOF && !(str[0]=='F' && str[1]=='I' && str[2]=='M' && str[3]=='\0')){
        printf("%d\n", substring(str));
    }
    return 0;
}