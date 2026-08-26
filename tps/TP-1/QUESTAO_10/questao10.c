#include <stdio.h>
#include <stdbool.h>
bool vogal(char *frase){
    if (*frase == '\0' || *frase == '\n' || *frase == '\r'){ // colocando o \r porque no windows o fgets coloca o \r\n no final da string e no linux so o \n, e como estou rodando o codigo no windows, o \r estava atrapalhando 
        return true; // se chegar no final da string e nao encontrar mais nenhuma vogal manda true 
    }
    if (*frase == 'a' || *frase == 'e' || *frase == 'i' || *frase == 'o' || *frase == 'u' || *frase == 'A' || *frase == 'E' || *frase == 'I' || *frase == 'O' || *frase == 'U'){
        return vogal(frase + 1); // se for vogal chama a funcao e verifica o prox 
    }
    return false; 
}
bool consoante(char *frase){
    if (*frase == '\0' || *frase == '\n' || *frase == '\r'){ // colocando o \r porque no windows o fgets coloca o \r\n no final da string e no linux so o \n, e como estou rodando o codigo no windows, o \r estava atrapalhando 
        return true; // se chegar no final da string e nao encontrar mais nenhuma consoante manda true
    }
    if ((*frase >= 'a' && *frase <= 'z') || (*frase >= 'A' && *frase <= 'Z')){
        if (*frase != 'a' && *frase != 'e' && *frase != 'i' && *frase != 'o' && *frase != 'u' && *frase != 'A' && *frase != 'E' && *frase != 'I' && *frase != 'O' && *frase != 'U'){
            return consoante(frase + 1); // se for consoante chama a funcao e verifica o prox do mesmo jeito ( acho que daria pra chamar a funcao vogal e se der false chama a funcao consoante mas ficaria enorme ... )
        }
    }
    return false;
}
bool inteiro(char *frase){
    if (*frase == '\0' || *frase == '\n' || *frase == '\r'){ // colocando o \r porque no windows o fgets coloca o \r\n no final da string e no linux so o \n, e como estou rodando o codigo no windows, o \r estava atrapalhando 
        return true; // se chegar no final da string e nao encontrar mais nenhum numero manda true
    }
    if (*frase >= '0' && *frase <= '9'){
        return inteiro(frase + 1); // se for numero chama a funcao e verifica o prox
    }
    return false;
}
bool real(char *frase, int count){
    if (*frase == '\0' || *frase == '\n' || *frase == '\r'){ // colocando o \r porque no windows o fgets coloca o \r\n no final da string e no linux so o \n, e como estou rodando o codigo no windows, o \r estava atrapalhando 
        return true; // se chegar no final da string e nao encontrar mais nenhum numero manda true
    }
    if (*frase == '.' || *frase == ','){
        count++; // se encontrar um ponto ou uma virgula faz count++
        if (count > 1){
            return false; // se tiver mais de um ponto ou virgula, deixou de ser numero real ne 
        }
        return real(frase + 1, count); // se for ponto ou virgula chama a funcao e verifica o prox
    }
    if (*frase >= '0' && *frase <= '9'){
        return real(frase + 1, count); // se for numero chama a funcao e verifica o prox
    }
    return false;
}
int main(){
    char frase[1000];
while(fgets(frase, sizeof(frase), stdin) != NULL){ // usando o fgets porque o scanf nao lia a linha vazia ( frase vazia = \n )
        if (frase[0]=='F' && frase[1]=='I' && frase[2]=='M' && (frase[3]=='\0' || frase[3]=='\r' || frase[3]=='\n')){ // regrinha no indice 3 por causa do \r\n do windows, se for so \n nao tem problema pq o \0 vai estar no indice 3
        break;
    }
        if (vogal(frase)) printf("SIM ");
        else printf("NAO ");
        if (consoante(frase)) printf("SIM ");
        else printf("NAO ");
        if (inteiro(frase)) printf("SIM ");
        else printf("NAO ");
        if (real(frase, 0)) printf("SIM\n");
        else printf("NAO\n");
    }   
}