#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>

typedef struct {
    int ano;
    int mes;
    int dia;
} Data;

Data parseData(char* str){
    Data d;
    sscanf(str, "%d-%d-%d", &d.ano, &d.mes, &d.dia);
    return d;
}

void formatData(Data d, char* buffer){
    sprintf(buffer, "%02d/%02d/%04d", d.dia, d.mes, d.ano);
}

typedef struct {
    int id;
    char marca[5000];
    char modelo[5000];
    int ano;
    char categoria[5000];
    char combustivel[3][5000];
    int cilindros;
    double cilindrada;
    int qtdCombustivel;
    char transmissao[5000];
    char tracao[5000];
    double consumoCidade;
    double consumoEstrada;
    double co2;
    bool turbo;
    Data dataRegistro;
} Veiculo;

Veiculo parseVeiculo(char* str){
    Veiculo v;
    v.qtdCombustivel = 0;
    char copia[5000];
    sprintf(copia, "%s", str);
    int len = 0;
    while(copia[len] != '\0'){
        len++;
    }
    while(len > 0 && (copia[len-1] == '\n' || copia[len-1] == '\r')){
        copia[len-1] = '\0';
        len--;
    }
    char* token = strtok(copia, ",");
    v.id = atoi(token);
    token = strtok(NULL, ",");
    sprintf(v.marca, "%s", token);
    token = strtok(NULL, ",");
    sprintf(v.modelo, "%s", token);
    token = strtok(NULL, ",");
    v.ano = atoi(token);
    token = strtok(NULL, ",");
    sprintf(v.categoria, "%s", token);
    token = strtok(NULL, ",");
    char combustivelBruto[5000];
    sprintf(combustivelBruto, "%s", token);
    int inicio = 0;
    int i = 0;
    while(combustivelBruto[i] != '\0'){
        if(combustivelBruto[i] == ';'){
            combustivelBruto[i] = '\0';
            sprintf(v.combustivel[v.qtdCombustivel], "%s", combustivelBruto + inicio);
            v.qtdCombustivel++;
            inicio = i + 1;
        }
        i++;
    }
    sprintf(v.combustivel[v.qtdCombustivel], "%s", combustivelBruto + inicio);
    v.qtdCombustivel++;
    token = strtok(NULL, ",");
    v.cilindros = atoi(token);
    token = strtok(NULL, ",");
    v.cilindrada = atof(token);
    token = strtok(NULL, ",");
    sprintf(v.transmissao, "%s", token);
    token = strtok(NULL, ",");
    sprintf(v.tracao, "%s", token);
    token = strtok(NULL, ",");
    v.consumoCidade = atof(token);
    token = strtok(NULL, ",");
    v.consumoEstrada = atof(token);
    token = strtok(NULL, ",");
    v.co2 = atof(token);
    token = strtok(NULL, ",");
    v.turbo = (strcmp(token, "true") == 0);
    token = strtok(NULL, ",");
    v.dataRegistro = parseData(token);
    return v;
}

void formatVeiculo(Veiculo v, char* buffer){
    char combustiveis[5000];
    int posicao = 0;
    for(int i = 0; i < v.qtdCombustivel; i++){
        posicao += sprintf(combustiveis + posicao, "%s", v.combustivel[i]);
        if(i < v.qtdCombustivel - 1){
            posicao += sprintf(combustiveis + posicao, ",");
        }
    }
    char dataRegistro[12];
    formatData(v.dataRegistro, dataRegistro);
    sprintf(buffer, "[%d ## %s ## %s ## %d ## %s ## [%s] ## %d ## %.1f ## %s ## %s ## %.2f ## %.2f ## %.1f ## %s ## %s]",
        v.id, v.marca, v.modelo, v.ano, v.categoria, combustiveis, v.cilindros, v.cilindrada,
        v.transmissao, v.tracao, v.consumoCidade, v.consumoEstrada, v.co2,
        v.turbo ? "true" : "false", dataRegistro);
}
Veiculo* lerCsv(char* caminhoArquivo, int* n){
    FILE* arquivo = fopen(caminhoArquivo, "r");
    if(arquivo == NULL){
        printf("Erro\n");
        *n = 0;
        return NULL;
    }
    char linha[5000];
    int total = 0;
    Veiculo* veiculos = (Veiculo*) malloc(sizeof(Veiculo) * 5000);
    fgets(linha, 5000, arquivo);
    while(fgets(linha, 5000, arquivo) != NULL){
        veiculos[total] = parseVeiculo(linha);
        total++;
    }
    fclose(arquivo);
    *n = total;
    return veiculos;
}
void selectionSort(Veiculo* veiculos, int n){ // ordena o array de veiculos pelo modelo usando selection sort
    for(int i = 0; i < n - 1; i++){
        int menor = i;
        for(int j = i + 1; j < n; j++){
            if(strcmp(veiculos[j].modelo, veiculos[menor].modelo) < 0){
                menor = j;
            }
        }
        if(menor != i){
            Veiculo temp = veiculos[i];
            veiculos[i] = veiculos[menor];
            veiculos[menor] = temp;
        }
    }
}
bool buscaBinaria(Veiculo* veiculos, int n, char* modelo){ // realiza busca binaria pelo modelo no array de veiculos ordenado
    int inicio = 0;
    int fim = n - 1;
    while(inicio <= fim){
        int meio = (inicio + fim) / 2;
        int cmp = strcmp(veiculos[meio].modelo, modelo);
        if(cmp == 0){
            return true;
        } else if(cmp < 0){
            inicio = meio + 1;
        } else {
            fim = meio - 1;
        }
    }
    return false;
}
int main(){
    int n;
    Veiculo* veiculos = lerCsv("/tmp/veiculos.csv", &n);
    Veiculo* base = (Veiculo*) malloc(sizeof(Veiculo) * 5000);
    int qtdBase = 0;
    int id;
    scanf("%d", &id);
    while(id != -1){ // lê os IDs dos veículos até encontrar -1
        for(int i = 0; i < n; i++){
            if(veiculos[i].id == id){
                base[qtdBase] = veiculos[i];
                qtdBase++;
                break;
            }
        }
        scanf("%d", &id);
    }
    selectionSort(base, qtdBase); // ordena a base de veículos pelo modelo
    int c;
    while((c = getchar()) != '\n' && c != EOF); // limpa o resto da linha (inclusive \r se o arquivo for windows)
    char linha[5000];
    while(fgets(linha, 5000, stdin) != NULL){
        int len = 0;
        while(linha[len] != '\0'){
            len++;
        }
        while(len > 0 && (linha[len-1] == '\n' || linha[len-1] == '\r')){ // remove os caracteres de nova linha e retorno de carro do final da linha
            linha[len-1] = '\0';
            len--;
        }
        if(strcmp(linha, "FIM") == 0){
            break;
        }
        if(buscaBinaria(base, qtdBase, linha)){ // verifica se o modelo lido está presente na base de veículos
            printf("SIM\n");
        } else {
            printf("NAO\n");
        }
    }
    free(veiculos);
    free(base);
    return 0;
}