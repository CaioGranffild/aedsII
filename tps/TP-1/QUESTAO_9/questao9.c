#include <stdio.h> 
void alterar(char *frase, int chave) {
	if (*frase == '\0') {
		return ; // se chegar no final da string volta
	} 
	*frase +=chave; // somando a letra com a chave 
	alterar(frase + 1, chave); // chama a funcaozinha pro prox caractere 
}
int main(){
	char frase[1000];
	int chave = 3;
	while(fgets(frase, sizeof(frase), stdin) != NULL){ // usando o fgets porque o scanf nao lia a linha vazia ( frase vazia = \n )
			int i = 0;
			while (frase[i] != '\0') i++; // contador da frase
			if (i > 0 && frase[i-1] == '\n') frase[--i] = '\0'; // isso aqui é pra tirar o \n do final da string caso tenha
			if (frase[0]=='F' && frase[1]=='I' && frase[2]=='M' && frase[3]=='\0') break;
			alterar(frase, chave);    
			printf("%s\n", frase);
	}
	return 0;
}
