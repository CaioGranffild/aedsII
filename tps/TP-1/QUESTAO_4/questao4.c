#include <stdio.h>
void inverter(char *string){
    int count = 0;
    while(string[count] != '\0'){ // ver o tamanho da string
        count++; 
    }
    int i = 0; 
    int j = count - 1; 
    while(i < j){ // swapzinho para reescrever a string invertida
        char tmp = string[i];
        string[i] = string[j];
        string[j] = tmp;
        i++, j--;
    }
}
int main(){
    char string[100];
    while(scanf(" %[^\n]", string) != EOF && !(string[0]=='F' && string[1]=='I' && string[2]=='M' && string[3]=='\0')){ // ler a string ate achar o FIM
        inverter(string); // chamar a funcao para inverter
        printf("%s\n", string);
    }
}