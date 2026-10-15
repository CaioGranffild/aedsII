import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
public class questao3 {
    public static class Data {
        private int ano;
        private int mes;
        private int dia;
        public int getAno(){ // get ano 
            return ano;
        }
        public void setAno(int ano){ // set ano
            this.ano = ano;
        }
        public int getMes(){ // get mes
            return mes;
        }
        public void setMes(int mes){ // set mes
            this.mes = mes;
        }
        public int getDia(){ // get dia
            return dia;
        }
        public void setDia(int dia){ // set dia 
            this.dia = dia;
        }
        public Data(int ano, int mes, int dia){ // construtor
        this.ano = ano;
        this.mes = mes;
        this.dia = dia;
        }
        public String format(){
            return String.format("%02d/%02d/%04d", dia, mes, ano);  // formatando a data d m a 
        }
        public static Data parseData(String data){ // vai vir ano-mes-dia
            String[] partes = data.split("-"); // separando ano mes e dia
            int ano = Integer.parseInt(partes[0]);
            int mes = Integer.parseInt(partes[1]);
            int dia = Integer.parseInt(partes[2]);
            return new Data(ano, mes, dia); // objeto data com ano mes e dia
        }
    }
    public static class Veiculo {
        private int id;
        private String marca;
        private String modelo;
        private int ano;
        private String categoria;
        private String[] combustivel;
        private int cilindros;
        private double cilindrada;
        private String transmissao;
        private String tracao;
        private double consumoCidade;
        private double consumoEstrada;
        private double co2;
        private boolean turbo;
        private Data dataRegistro;
        // getters e setters
        public int getId(){ // get id 
            return id;
        }
        public void setId(int id){ // set id
            this.id = id;
        }
        public String getMarca(){ // get marca
            return marca;
        }
        public void setMarca(String marca){ // set marca
            this.marca = marca;
        }
        public String getModelo(){ // get modelo
            return modelo;
        }
        public void setModelo(String modelo){ // set modelo
            this.modelo = modelo;
        }
        public int getAno(){ // get ano
            return ano;
        }
        public void setAno(int ano){ // set ano
            this.ano = ano;
        }
        public String getCategoria(){ // get categoria
            return categoria;
        }
        public void setCategoria(String categoria){ // set categoria
            this.categoria = categoria;
        }
        public String[] getCombustivel(){ // get combustivel
            return combustivel;
        }
        public void setCombustivel(String[] combustivel){ // set combustivel
            this.combustivel = combustivel;
        }
        public int getCilindros(){ // get cilindros
            return cilindros;
        }
        public void setCilindros(int cilindros){ // set cilindros   
            this.cilindros = cilindros;
        }
        public double getCilindrada(){ // get cilindrada
            return cilindrada;
        }
        public void setCilindrada(double cilindrada){ // set cilindrada
            this.cilindrada = cilindrada;
        }
        public String getTransmissao(){ // get transmissao
            return transmissao;
        }
        public void setTransmissao(String transmissao){ // set transmissao
            this.transmissao = transmissao;
        }
        public String getTracao(){ // get tracao
            return tracao;
        }
        public void setTracao(String tracao){ // set tracao
            this.tracao = tracao;
        }
        public double getConsumoCidade(){ // get consumoCidade
            return consumoCidade;
        }
        public void setConsumoCidade(double consumoCidade){ // set consumoCidade
            this.consumoCidade = consumoCidade;
        }
        public double getConsumoEstrada(){ // get consumoEstrada
            return consumoEstrada;
        }
        public void setConsumoEstrada(double consumoEstrada){ // set consumoEstrada
            this.consumoEstrada = consumoEstrada;
        }
        public double getCo2(){ // get co2
            return co2;
        }
        public void setCo2(double co2){ // set co2
            this.co2 = co2;
        }
        public boolean isTurbo(){ // get turbo
            return turbo;
        }
        public void setTurbo(boolean turbo){ // set turbo
            this.turbo = turbo;
        }
        public Data getDataRegistro(){ // get dataRegistro
            return dataRegistro;
        }
        public void setDataRegistro(Data dataRegistro){ // set dataRegistro
            this.dataRegistro = dataRegistro;
        }
        public static Veiculo parseVeiculo(String veiculo){
            String[] atributos = veiculo.split(","); // separa os atributos do veiculo pela virgula 
            Veiculo v = new Veiculo(); // objeto Veiculo
            v.setId(Integer.parseInt(atributos[0]));
            v.setMarca(atributos[1]);
            v.setModelo(atributos[2]);
            v.setAno(Integer.parseInt(atributos[3]));
            v.setCategoria(atributos[4]);
            v.setCombustivel(atributos[5].split(";"));
            v.setCilindros(Integer.parseInt(atributos[6]));
            v.setCilindrada(Double.parseDouble(atributos[7]));
            v.setTransmissao(atributos[8]);
            v.setTracao(atributos[9]);
            v.setConsumoCidade(Double.parseDouble(atributos[10]));
            v.setConsumoEstrada(Double.parseDouble(atributos[11]));
            v.setCo2(Double.parseDouble(atributos[12]));
            v.setTurbo(Boolean.parseBoolean(atributos[13]));
            v.setDataRegistro(Data.parseData(atributos[14]));
            return v;
        }
        public String format(){
            String combustiveis = "";
            for(int i = 0; i < combustivel.length; i++){ // concatenando a string combustivel caso seja um carro hibrido
                combustiveis += combustivel[i];
                if(i < combustivel.length - 1){ // adiciona uma vírgula entre os combustíveis
                    combustiveis += ",";
                }
            }
            return String.format("[%d ## %s ## %s ## %d ## %s ## [%s] ## %d ## %.1f ## %s ## %s ## %.2f ## %.2f ## %.1f ## %b ## %s]", 
                id, marca, modelo, ano, categoria, combustiveis, cilindros, cilindrada, transmissao, tracao, consumoCidade, consumoEstrada, co2, turbo, dataRegistro.format()); // formata os atributos do veiculo
        }
    }
    public static class LeitorCsv {
        public Veiculo[] lerCsv(String caminhoArquivo){
            int quantidadeLinhas = contarLinhas(caminhoArquivo); // conta a quantidade de linhas do CSV
            Veiculo[] veiculos = new Veiculo[quantidadeLinhas]; // array de veiculos com tamanho igual a quantidade de linhas
            try {
                Scanner scanner = new Scanner(new File(caminhoArquivo));
                scanner.nextLine();
                int i = 0;
                while(scanner.hasNextLine()){
                    String linha = scanner.nextLine();
                    veiculos[i] = Veiculo.parseVeiculo(linha); // parse a linha do CSV para o objeto Veiculo
                    i++;
                }
                scanner.close();
            } catch (FileNotFoundException e){
                System.out.println("Erro" + e.getMessage()); // msg de erro 
            }
            return veiculos; 
        }
        private int contarLinhas(String caminhoArquivo){
            int quantidadeLinhas = 0;
            try {
                Scanner scanner = new Scanner(new File(caminhoArquivo));
                scanner.nextLine(); 
                while(scanner.hasNextLine()){ // contador 
                    scanner.nextLine();
                    quantidadeLinhas++;
                }
                scanner.close();
            } catch (FileNotFoundException erro) {
                System.out.println("Erro" + erro.getMessage());  // msg de erro 
            }
            return quantidadeLinhas;
        }
    }
    public static int comparar(Veiculo v1, Veiculo v2){
        if(v1.getCo2() < v2.getCo2()){ // chave co2 
            return -1; // v1 tem menos CO2 que v2
        } else if(v1.getCo2() > v2.getCo2()){ // v1 tem mais CO2 que v2
            return 1; 
        } else { // v1 e v2 têm o mesmo CO2
            return v1.getTransmissao().compareTo(v2.getTransmissao()); // compara pela transmissao
        }
    }
    public static void swap(Veiculo[] veiculos, int i, int j){
        Veiculo temp = veiculos[i];
        veiculos[i] = veiculos[j];
        veiculos[j] = temp;
    }
    public static void construirHeap(Veiculo[] veiculos, int tam){
        for(int i = tam; i > 1 && comparar(veiculos[i], veiculos[i/2]) > 0; i /= 2){
            swap(veiculos, i, i/2); // sobe o filho enquanto for maior que o pai 
        }
    }
    public static boolean hasFilho(int i, int tam){
        return 2 * i <= tam; // o filho da esquerda ta dentro do heap
    }
    public static int getMaiorFilho(Veiculo[] veiculos, int i, int tam){
        int filho; 
        if(2 * i == tam || comparar(veiculos[2 * i], veiculos[2 * i + 1]) >= 0){ // so tem o filho da esquerda ou ele e maior
        filho = 2 * i;
        } else {
        filho = 2 * i + 1;
        }
        return filho;
    }
    public static void reconstruir(Veiculo[] vet, int tam){ // reconstrói o heap a partir da raiz
    int i = 1;
    while(hasFilho(i, tam) == true){
        int filho = getMaiorFilho(vet, i, tam);
        if(comparar(vet[i], vet[filho]) < 0){  // se o pai for menor que o maior filho faz o swapzin
            swap(vet, i, filho);
            i = filho;
            } else i = tam; 
        }
    }
    public static void heapsort(Veiculo[] veiculos, int n){
        Veiculo[] vet = new Veiculo[n + 1]; // o heap vai de 1 ate n, usando o indice 1 como inicial 
        for(int i = 0; i < n; i++){
            vet[i + 1] = veiculos[i];
        }
        for(int tam = 2; tam <= n; tam++){
            construirHeap(vet, tam); // constroi o heap a partir do tamanho atual
        }
        int tam = n;
        while(tam > 1){
            swap(vet, 1, tam--); // troca a raiz com o último elemento do heap
            reconstruir(vet, tam); // refaz o reap dps da troca 
        }
        for(int i = 0; i < n; i++){
            veiculos[i] = vet[i + 1]; // volta pro vetor original
        }
    }
    public static void main(String[] args){
        LeitorCsv leitor = new LeitorCsv(); // cria um leitor de CSV
        Veiculo[] veiculos = leitor.lerCsv("D:/PUC/AEDS II/tps/TP-3/veiculos.csv"); // lê os veículos do arquivo CSV
        Scanner sc = new Scanner(System.in);
        int id = sc.nextInt();
        Veiculo[] selecionados = new Veiculo[veiculos.length]; // array para armazenar os veículos selecionados pelo usuário
        int qtdSelecionados = 0;
        while(id != -1){
            for(int i = 0; i < veiculos.length; i++){
                if(veiculos[i].getId() == id){
                    selecionados[qtdSelecionados] = veiculos[i];
                    qtdSelecionados++;
                    break;
                }
            }
            id = sc.nextInt();
        }
        sc.close();
        heapsort(selecionados, qtdSelecionados); // ordena os veículos selecionados usando heapsort
        for(int i = 0; i < qtdSelecionados; i++){
            System.out.println(selecionados[i].format()); // imprime os veículos selecionados e ordenados
        }
    }
}