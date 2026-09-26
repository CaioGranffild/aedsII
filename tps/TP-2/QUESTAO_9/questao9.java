import java.io.File;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
public class questao9 {
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
            return String.format("%02d/%02d/%04d", dia, mes, ano); // formatando a data d m a
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
                BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo));
                br.readLine(); // pula o cabeçalho do CSV
                String linha;
                int i = 0;
                while((linha = br.readLine()) != null){
                    veiculos[i] = Veiculo.parseVeiculo(linha); // parse a linha do CSV para o objeto Veiculo
                    i++;
                }
                br.close();
            } catch (IOException e){
                System.out.println("Erro" + e.getMessage()); // msg de erro
            }
            return veiculos;
        }
        private int contarLinhas(String caminhoArquivo){
            int quantidadeLinhas = 0;
            try {
                BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo));
                br.readLine(); // pula o cabeçalho do CSV
                while(br.readLine() != null){ // conta as linhas de veículos
                    quantidadeLinhas++;
                }
                br.close();
            } catch (IOException e){
                System.out.println("Erro" + e.getMessage()); // msg de erro
            }
            return quantidadeLinhas;
        }
    }
    public static class ListaVeiculos { // lista sequencial: os veículos ficam no array e n conta os que estão na lista
        private Veiculo[] array;
        private int n;
        public ListaVeiculos(int capacidadeInicial){
            array = new Veiculo[capacidadeInicial];
            n = 0;
        }
        public int getN(){
            return n;
        }
        public Veiculo getPosicao(int posicao){
            return array[posicao];
        }
        private void garanteEspaco(){ // se o array encher, copia tudo para outro com o dobro do tamanho
            if(n == array.length){
                Veiculo[] novo = new Veiculo[array.length * 2];
                for(int i = 0; i < n; i++){
                    novo[i] = array[i];
                }
                array = novo;
            }
        }
        public void inserirInicio(Veiculo veiculo){ // abre espaço no começo, deslocando os outros para a direita
            garanteEspaco();
            for(int i = n; i > 0; i--){
                array[i] = array[i - 1];
            }
            array[0] = veiculo;
            n++;
        }
        public void inserir(Veiculo veiculo, int posicao){ // abre espaço a partir da posição escolhida
            garanteEspaco();
            for(int i = n; i > posicao; i--){
                array[i] = array[i - 1];
            }
            array[posicao] = veiculo;
            n++;
        }
        public void inserirFim(Veiculo veiculo){ // no fim é direto: coloca na primeira posição livre
            garanteEspaco();
            array[n] = veiculo;
            n++;
        }
        public Veiculo removerInicio(){ // guarda o primeiro e puxa o restante uma posição para a esquerda
            Veiculo removido = array[0];
            for(int i = 0; i < n - 1; i++){
                array[i] = array[i + 1];
            }
            n--;
            return removido;
        }
        public Veiculo remover(int posicao){ // guarda o removido e fecha o espaço que ficou
            Veiculo removido = array[posicao];
            for(int i = posicao; i < n - 1; i++){
                array[i] = array[i + 1];
            }
            n--;
            return removido;
        }
        public Veiculo removerFim(){ // basta diminuir n e devolver quem estava no fim
            n--;
            return array[n];
        }
    }
    public static void main(String[] args){
        LeitorCsv leitor = new LeitorCsv(); // objeto leitor para ler o csv
        Veiculo[] veiculos = leitor.lerCsv("/tmp/veiculos.csv"); // le o csv e cria um array de veiculo
        ListaVeiculos lista = new ListaVeiculos(10); // começa com 10 posições; o array cresce quando precisar
        Scanner sc = new Scanner(System.in);
        int id = sc.nextInt(); // le o id do carro
        while(id != -1){ // os IDs iniciais entram no fim da lista até chegar o -1
            for(int i = 0; i < veiculos.length; i++){
                if(veiculos[i].getId() == id){
                    lista.inserirFim(veiculos[i]);
                    break;
                }
            }
            id = sc.nextInt();
        }
        int n = sc.nextInt(); // quantidade de comandos que vêm depois do -1
        for(int cmd = 0; cmd < n; cmd++){
            String comando = sc.next(); // lê II, I*, IF, RI, R* ou RF
            if(comando.equals("II")){
                int idInserir = sc.nextInt();
                lista.inserirInicio(buscaPorId(veiculos, idInserir));
            } else if(comando.equals("I*")){
                int posicao = sc.nextInt();
                int idInserir = sc.nextInt();
                lista.inserir(buscaPorId(veiculos, idInserir), posicao);
            } else if(comando.equals("IF")){
                int idInserir = sc.nextInt();
                lista.inserirFim(buscaPorId(veiculos, idInserir));
            } else if(comando.equals("RI")){
                Veiculo removido = lista.removerInicio();
                System.out.println("(R)" + removido.getMarca() + " " + removido.getModelo());
            } else if(comando.equals("R*")){
                int posicao = sc.nextInt();
                Veiculo removido = lista.remover(posicao);
                System.out.println("(R)" + removido.getMarca() + " " + removido.getModelo());
            } else if(comando.equals("RF")){
                Veiculo removido = lista.removerFim();
                System.out.println("(R)" + removido.getMarca() + " " + removido.getModelo());
            }
        }
        sc.close();
        for(int i = 0; i < lista.getN(); i++){ // mostra os veículos que sobraram, na ordem da lista
            System.out.println(lista.getPosicao(i).format());
        }
    }
    private static Veiculo buscaPorId(Veiculo[] veiculos, int id){ // procura no CSV o veículo que será inserido
        for(int i = 0; i < veiculos.length; i++){
            if(veiculos[i].getId() == id){
                return veiculos[i];
            }
        }
        return null;
    }
}
