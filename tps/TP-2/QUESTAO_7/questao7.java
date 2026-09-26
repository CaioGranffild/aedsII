import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
public class questao7 {
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
    public static void Bucketsort(Veiculo[] chamados, int quantidade){
        Veiculo[][] baldes = new Veiculo[10][quantidade];
        int[] tamanhos = new int[10]; // armazena o tamanho atual de cada balde
        for(int i = 0; i < quantidade; i++){
            int indice = (int)((chamados[i].getCilindrada() / 8.1) * 10); // calcula o índice do balde 
            if(indice < 0) indice = 0;
            if(indice > 9) indice = 9; // inclui cilindrada igual a 8.1 no último balde
            baldes[indice][tamanhos[indice]] = chamados[i];
            tamanhos[indice]++;
        }
        for(int b = 0; b < 10; b++){
            for(int i = 1; i < tamanhos[b]; i++){ // ordena cada balde individualmente usando insertion sort
                Veiculo atual = baldes[b][i];
                int j = i - 1;
                while(j >= 0 && baldes[b][j].getCilindrada() > atual.getCilindrada()){ // desloca os elementos maiores para a direita
                    baldes[b][j + 1] = baldes[b][j];
                    j--;
                }
                baldes[b][j + 1] = atual;
            }
        }
        int posicao = 0;
        for(int b = 0; b < 10; b++){ 
            for(int i = 0; i < tamanhos[b]; i++){
                chamados[posicao++] = baldes[b][i]; // copia os elementos ordenados de volta para o array principal
            }
        }
    }
    public static void main(String[] args){
        LeitorCsv leitor = new LeitorCsv();
        Veiculo[] veiculos = leitor.lerCsv("/tmp/veiculos.csv");
        Veiculo[] escolhidos = new Veiculo[veiculos.length];
        int quantidade = 0;
        Scanner sc = new Scanner(System.in);
        while(sc.hasNextInt()){
            int id = sc.nextInt();
            while(id != -1) {
                for(int i = 0; i < veiculos.length; i++){
                    if(veiculos[i].getId() == id){
                        escolhidos[quantidade++] = veiculos[i];
                        break;
                    }
                }
                id = sc.nextInt();
            }
        }
        sc.close();
        Bucketsort(escolhidos, quantidade);
        for(int i = 0; i < quantidade; i++){
            System.out.println(escolhidos[i].format());
        }
    }
}