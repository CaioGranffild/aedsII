#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>
typedef struct {
    int ano;
    int mes;
    int dia;
} Data; // struct data 
Data parseData(char* str){
    Data d;
    sscanf(str, "%d-%d-%d", &d.ano, &d.mes, &d.dia); // parse a string no formato ano mes dia para a struct Data
    return d;
}
void formatData(Data d, char* buffer){
    sprintf(buffer, "%02d/%02d/%04d", d.dia, d.mes, d.ano); // formata a struct Data para uma string no formato dia mes ano
}
typedef struct {
    int id;
    char marca[5000]; 
    char modelo[5000];
    int ano;
    char categoria[5000]; 
    char combustivel[3][5000]; // array para armazenar até 3 tipos de combustíveis embora um carro so pode ter 2 no máximo
    int cilindros;
    double cilindrada;
    int qtdCombustivel; // colocando aqui a qtd de combustivel porque precisamos saber quantos combustiveis foram lidos
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
    v.qtdCombustivel = 0; // inicializa a quantidade de combustíveis lidos como 0
    char copia[5000]; // cria uma cópia da string de entrada para manipulação segura
    sprintf(copia, "%s", str); // copia a string de entrada para a variável copia
    int len = 0;
    while(copia[len] != '\0') {
        len++; // conta o número de caracteres na string copia
    }
    while(len > 0 && (copia[len-1] == '\n' || copia[len-1] == '\r')) {
        copia[len-1] = '\0';
        len--;
    } // remove possíveis caracteres de nova linha ou retorno de carro do final da string copia
    char* token = strtok(copia, ","); // inicia a tokenização da string copia usando vírgula como delimitador
    v.id = atoi(token); // converte o token para inteiro e atribui ao id do veículo
    token = strtok(NULL, ",");
    sprintf(v.marca, "%s", token); // copia o token para o campo marca do veículo
    token = strtok(NULL, ",");
    sprintf(v.modelo, "%s", token); // copia o token para o campo modelo do veículo
    token = strtok(NULL, ",");
    v.ano = atoi(token); // converte o token para inteiro e atribui ao ano do veículo
    token = strtok(NULL, ",");
    sprintf(v.categoria, "%s", token); // copia o token para o campo categoria do veículo
    token = strtok(NULL, ",");
    char combustivelBruto[5000]; // cria uma string para armazenar os combustíveis brutos separados por ;
    sprintf(combustivelBruto, "%s", token); // copia o token para a string combustivelBruto
    int inicio = 0;
    int i = 0;
    while(combustivelBruto[i] != '\0'){ // percorre a string combustivelBruto
        if(combustivelBruto[i] == ';'){ // verifica se o caractere atual é ;
            combustivelBruto[i] = '\0'; // substitui ; por \0 para terminar a string
            sprintf(v.combustivel[v.qtdCombustivel], "%s", combustivelBruto + inicio); // copia o combustível para o array de combustíveis do veículo
            v.qtdCombustivel++; // incrementa a quantidade de combustíveis lidos
            inicio = i + 1; // atualiza o início do próximo combustível
        }
        i++;
    }
    sprintf(v.combustivel[v.qtdCombustivel], "%s", combustivelBruto + inicio); // copia o último combustível
    v.qtdCombustivel++; // incrementa a quantidade de combustíveis lidos 
    token = strtok(NULL, ","); // obtém o próximo token da string de entrada    
    v.cilindros = atoi(token); // converte o token para inteiro e atribui ao número de cilindros do veículo
    token = strtok(NULL, ",");
    v.cilindrada = atof(token); // converte o token para float e atribui à cilindrada do veículo
    token = strtok(NULL, ",");
    sprintf(v.transmissao, "%s", token); // copia o token para o campo transmissao do veículo
    token = strtok(NULL, ",");
    sprintf(v.tracao, "%s", token); // copia o token para o campo tracao do veículo
    token = strtok(NULL, ",");
    v.consumoCidade = atof(token); // converte o token para float e atribui ao consumo na cidade do veículo
    token = strtok(NULL, ",");
    v.consumoEstrada = atof(token); // converte o token para float e atribui ao consumo na estrada do veículo   
    token = strtok(NULL, ",");
    v.co2 = atof(token); // converte o token para float e atribui ao CO2 do veículo
    token = strtok(NULL, ",");
    v.turbo = (strcmp(token, "true") == 0); // verifica se o token é "true" e atribui ao campo turbo do veículo
    token = strtok(NULL, ",");
    v.dataRegistro = parseData(token); // converte o token para Data e atribui ao campo dataRegistro do veículo
    return v;
}
void formatVeiculo(Veiculo v, char* buffer){
    char combustiveis[5000];
    int posicao = 0;
    for(int i = 0; i < v.qtdCombustivel; i++){
    posicao += sprintf(combustiveis + posicao, "%s", v.combustivel[i]); // sem ; nem , aqui
    if(i < v.qtdCombustivel - 1){
        posicao += sprintf(combustiveis + posicao, ","); // vírgula só entre eles
        }
    }
    char dataRegistro[12];
    formatData(v.dataRegistro, dataRegistro); // formata a data de registro do veículo para uma string
    sprintf(buffer, "[%d ## %s ## %s ## %d ## %s ## [%s] ## %d ## %.1f ## %s ## %s ## %.2f ## %.2f ## %.1f ## %s ## %s]",
        v.id, v.marca, v.modelo, v.ano, v.categoria, combustiveis, v.cilindros, v.cilindrada,
        v.transmissao, v.tracao, v.consumoCidade, v.consumoEstrada, v.co2,
        v.turbo ? "true" : "false", dataRegistro); // armazena todas as informações do veículo no buffer 
}
Veiculo* lerCsv(char* caminhoArquivo, int* n){
    FILE* arquivo = fopen(caminhoArquivo, "r"); 
    if(arquivo == NULL){
        printf("Erro\n");
        *n = 0; // indica que nenhum veículo foi lido 
        return NULL;
    }
    char linha[5000];
    int total = 0;
    // aloca o array do tamanho exato
    Veiculo* veiculos = (Veiculo*) malloc(sizeof(Veiculo) * 5000);
    fgets(linha, 5000, arquivo); // pula o cabeçalho de novo
    while(fgets(linha, 5000, arquivo) != NULL){
        veiculos[total] = parseVeiculo(linha); // parse a linha do CSV e armazena as informações no array 
        total++;
    }
    fclose(arquivo);
    *n = total;
    return veiculos;
}
char paraMinuscula(char c){ // converte uma letra maiúscula para minúscula
    if(c >= 'A' && c <= 'Z'){ // verifica se o caractere é uma letra maiúscula
        return c + 32; // desloca pro correspondente minúsculo na tabela ASCII
    }
    return c; // se não for maiúscula, retorna sem alterar
}
int compararModelo(char* a, char* b){ 
    int i = 0;
    while(a[i] != '\0' && b[i] != '\0'){ // percorre as duas strings ao mesmo tempo
        char ca = paraMinuscula(a[i]); // normaliza o caractere de a
        char cb = paraMinuscula(b[i]); // normaliza o caractere de b
        if(ca != cb){ // se forem diferentes depois de normalizados, já decide
            return ca - cb;
        }
        i++;
    }
    return paraMinuscula(a[i]) - paraMinuscula(b[i]); // decide pelo tamanho
}
typedef struct Celula {
    Veiculo* veiculo; // ponteiro para o veículo armazenado na célula
    struct Celula* prox; // ponteiro para a próxima célula na lista encadeada
} Celula;
Celula* novaCelula(Veiculo* v){
    Celula* nova = (Celula*) malloc(sizeof(Celula)); // aloca memória para a nova célula
    nova->veiculo = v; // armazena o ponteiro para o veículo na nova célula
    nova->prox = NULL; // inicialmente, a nova célula não aponta para nenhuma outra
    return nova; // retorna o ponteiro para a nova célula
}
typedef struct {
    Celula* primeiro; // no cabeca, nao guarda veiculo
    Celula* ultimo;
} Lista;
Lista* novaLista(){
    Lista* lista = (Lista*) malloc(sizeof(Lista)); // aloca memória para a lista
    lista->primeiro = novaCelula(NULL); // cria a célula cabeça
    lista->ultimo = lista->primeiro; // inicialmente, a última célula é a cabeça
    return lista; // retorna o ponteiro para a lista
}
void inserirFim(Lista* lista, Veiculo* v){
    lista->ultimo->prox = novaCelula(v); // liga a nova célula depois da última
    lista->ultimo = lista->ultimo->prox; // atualiza o ponteiro para a última célula
}
void swapCelulas(Celula* a, Celula* b){ // troca so os ponteiros dos veiculos
    Veiculo* temp = a->veiculo; // armazena temporariamente o ponteiro do veículo de a
    a->veiculo = b->veiculo; // copia o ponteiro do veículo de b para a
    b->veiculo = temp; // copia o ponteiro temporário para b
}
void selecao(Lista* lista){
    for(Celula* i = lista->primeiro->prox; i != NULL && i->prox != NULL; i = i->prox){ // percorre a lista até a penúltima célula
        Celula* menor = i; // assume que a célula i contém o menor modelo 
        for(Celula* j = i->prox; j != NULL; j = j->prox){ // percorre as células seguintes
            if(compararModelo(menor->veiculo->modelo, j->veiculo->modelo) > 0){ // acha o menor modelo
                menor = j; // atualiza a célula com o menor modelo encontrado
            }
        }
        swapCelulas(menor, i); // coloca o menor na posição i
    }
}
void mostrar(Lista* lista){
    char buffer[5000];
    for(Celula* i = lista->primeiro->prox; i != NULL; i = i->prox){ // passa em todos menos na cabeça
        formatVeiculo(*(i->veiculo), buffer);
        printf("%s\n", buffer);
    }
}
void liberar(Lista* lista){
    Celula* atual = lista->primeiro;
    while(atual != NULL){
        Celula* apagar = atual;
        atual = atual->prox;
        free(apagar); // libera a memória da célula atual
    }
    free(lista); // libera a memória da lista
}
int main(){
    int n;
    Veiculo* veiculos = lerCsv("/tmp/veiculos.csv", &n);
    Lista* lista = novaLista();
    int id;
    while(scanf("%d", &id) && id != -1){
        for(int i = 0; i < n; i++){
            if(veiculos[i].id == id){ 
                inserirFim(lista, &veiculos[i]); // insere o veículo no final da lista
                break;
            }
        }
    }
    selecao(lista); // ordena 
    mostrar(lista); // exibe os carros 
    liberar(lista); // libera a memoria da lista 
    free(veiculos); // libera a memoria do array 
    return 0;
}