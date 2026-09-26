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
    // reserva espaço para até 5000 veículos
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
typedef struct Celula { // struct celula que guarda um veículo e o endereço da celula prox 
    Veiculo* veiculo;
    struct Celula* prox;
} Celula;
typedef struct { // lista que guarda o primeiro e o último elemento e tamanho
    Celula* primeiro;
    Celula* ultimo;
    int n;
} Lista;
void inserir(Lista* lista, Veiculo* veiculo, int posicao){
    if(veiculo == NULL || posicao < 0 || posicao > lista->n) return; // se nao achar a posicao = return
    Celula* nova = malloc(sizeof(Celula)); // aloca memória para a próxima célula
    if(nova == NULL) return;
    nova->veiculo = veiculo;
    nova->prox = NULL;
    if(posicao == 0){
        nova->prox = lista->primeiro;
        lista->primeiro = nova;
        if(lista->n == 0) lista->ultimo = nova;
    } else if(posicao == lista->n){
        lista->ultimo->prox = nova;
        lista->ultimo = nova;
    } else {
        Celula* anterior = lista->primeiro;
        for(int i = 0; i < posicao - 1; i++) anterior = anterior->prox;
        nova->prox = anterior->prox;
        anterior->prox = nova;
    }
    lista->n++; // incrementa o tamanho da lista dps de inserir
}
Veiculo* remover(Lista* lista, int posicao){
    if(posicao < 0 || posicao >= lista->n) return NULL;
    Celula* retirada;
    if(posicao == 0){
        retirada = lista->primeiro; // guarda a célula que será removida
        lista->primeiro = retirada->prox; // atualiza o primeiro elemento da lista
        if(lista->n == 1) lista->ultimo = NULL; // se sair o único elemento = lista null 
    } else {
        Celula* anterior = lista->primeiro; // percorre a lista até a célula anterior à que será removida
        for(int i = 0; i < posicao - 1; i++) anterior = anterior->prox; // percorre até a célula anterior à que será removida
        retirada = anterior->prox; // guarda a célula que será removida
        anterior->prox = retirada->prox; // atualiza o ponteiro da célula anterior para pular a célula removida
        if(retirada == lista->ultimo) lista->ultimo = anterior; // atualiza o último elemento caso a celula removida seja a última
    }
    Veiculo* veiculo = retirada->veiculo;
    free(retirada); // libera a célula
    lista->n--;
    return veiculo;
}
Veiculo* buscarPorId(Veiculo* veiculos, int n, int id){
    for(int i = 0; i < n; i++){
        if(veiculos[i].id == id) return &veiculos[i]; // retorna o ponteiro para o veículo 
    }
    return NULL;
}
int main(){
    int n;
    Veiculo* veiculos = lerCsv("/tmp/veiculos.csv", &n);
    if(veiculos == NULL) return 1;
    Lista lista = {NULL, NULL, 0}; // começa sem nenhuma célula
    int id;
    while(scanf("%d", &id) == 1 && id != -1){
        inserir(&lista, buscarPorId(veiculos, n, id), lista.n);
    }
    int quantidade = 0;
    scanf("%d", &quantidade);
    for(int i = 0; i < quantidade; i++){
        char comando[3];
        int posicao;
        Veiculo* removido = NULL;
        scanf("%2s", comando);
        // O segundo caractere diz onde mexer: início, fim ou posição.
        if(comando[0] == 'I'){
            posicao = lista.n;
            if(comando[1] == 'I') posicao = 0;
            else if(comando[1] == '*') scanf("%d", &posicao);
            scanf("%d", &id);
            inserir(&lista, buscarPorId(veiculos, n, id), posicao);
        } else if(comando[0] == 'R'){
            posicao = lista.n - 1;
            if(comando[1] == 'I') posicao = 0;
            else if(comando[1] == '*') scanf("%d", &posicao);
            removido = remover(&lista, posicao);
        }
        if(removido != NULL){
            printf("(R)%s %s\n", removido->marca, removido->modelo);
        }
    }
    Celula* atual = lista.primeiro;
    while(atual != NULL){
        char buffer[60000];
        formatVeiculo(*atual->veiculo, buffer);
        printf("%s\n", buffer);
        Celula* proxima = atual->prox;
        free(atual);
        atual = proxima;
    }
    free(veiculos); // os veículos só são liberados depois das células
    return 0;
}
