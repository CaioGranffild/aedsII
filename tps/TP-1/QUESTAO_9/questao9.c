#include <stdio.h> 
void alterar(char *frase, int chave) {
	if (*frase == '\0') {
		return ; 
	} 
	*frase +=chave;
	alterar(frase + 1, chave); 
}
int main(){
	char frase[1000];
	int chave = 3;
	while(fgets(frase, sizeof(frase), stdin) != NULL){
			int i = 0;
			while (frase[i] != '\0') i++;
			if (i > 0 && frase[i-1] == '\n') frase[--i] = '\0';
			if (frase[0]=='F' && frase[1]=='I' && frase[2]=='M' && frase[3]=='\0') break;
			alterar(frase, chave);		      
			printf("%s\n", frase);
	}
	return 0;
}
