import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
public class questao13 {
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
            return String.format(java.util.Locale.US, "%02d/%02d/%04d", dia, mes, ano); // formatando a data d m a
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
            return String.format(java.util.Locale.US, "[%d ## %s ## %s ## %d ## %s ## [%s] ## %d ## %.1f ## %s ## %s ## %.2f ## %.2f ## %.1f ## %b ## %s]",
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
    public static class Celula {
        Veiculo veiculo;
        Celula ant, prox; // agr que é duplamente encadeada, tem prox e ant 
        public Celula(Veiculo veiculo){
            this.veiculo = veiculo; // armazena o veículo na célula
        }
    }
    public static class Lista {
        private Celula primeiro, ultimo; 
        private int n;
        public void inserir(Veiculo veiculo, int posicao){
            if(posicao < 0 || posicao > n || veiculo == null) return; // vendo se a posição é válida 
            Celula nova = new Celula(veiculo);
            Celula seguinte = null;
            if(posicao < n){
                seguinte = primeiro; // começa do primeiro elemento
                for(int i = 0; i < posicao; i++) seguinte = seguinte.prox; // percorre até a posição desejada
            }
            Celula anterior;
            if(seguinte == null){
                anterior = ultimo; // se nao tem prox o anterior é o último elemento
            } else {
                anterior = seguinte.ant; // se tem prox o anterior é o anterior do seguinte
            }
            nova.ant = anterior; // ajusta o ponteiro anterior da nova célula
            nova.prox = seguinte; // ajusta o ponteiro próximo da nova célula
            if(anterior == null) primeiro = nova; // se não tem anterior, a nova célula é a primeira
            else anterior.prox = nova; // caso contrário, ajusta o próximo do anterior
            if(seguinte == null) ultimo = nova; // se não tem próximo, a nova célula é a última
            else seguinte.ant = nova; // caso contrário, ajusta o anterior do próximo
            n++;
        }
        public Veiculo remover(int posicao){
            if(posicao < 0 || posicao >= n) return null; // vendo se a posição é válida para remoção igual no inserir
            Celula retirada = ultimo; // começa assumindo que a célula a ser retirada é a última
            if(posicao != n - 1){
                retirada = primeiro; // começa do primeiro elemento para encontrar a posição desejada
                for(int i = 0; i < posicao; i++) retirada = retirada.prox; // percorre até a posição desejada para remoção
            }
            if(retirada.ant == null) primeiro = retirada.prox; // se não tem anterior, o primeiro é o próximo
            else retirada.ant.prox = retirada.prox; // caso contrário, ajusta o próximo do anterior
            if(retirada.prox == null) ultimo = retirada.ant; // se não tem próximo, o último é o anterior
            else retirada.prox.ant = retirada.ant; // caso contrário, ajusta o anterior do próximo
            n--;
            retirada.ant = retirada.prox = null; // solta a célula da lista para o GARBAGE COLLECTOR
            return retirada.veiculo; // retorna o veículo que foi removido da lista
        }

    }

    public static void main(String[] args){
        LeitorCsv leitor = new LeitorCsv();
        Veiculo[] veiculos = leitor.lerCsv("/tmp/veiculos.csv");
        Scanner sc = new Scanner(System.in);
        Lista lista = new Lista();
        int id = sc.nextInt();
        while(id != -1){ // vai ate o -1 
            for(int i = 0; i < veiculos.length; i++){
                if(veiculos[i].getId() == id){
                    lista.inserir(veiculos[i], lista.n); 
                    break;
                }
            }
            id = sc.nextInt();
        }
        int quantidade = sc.nextInt();
        for(int i = 0; i < quantidade; i++){
            String comando = sc.next();
            if(comando.equals("II") || comando.equals("IF") || comando.equals("I*")){
                int posicao = lista.n; // posição padrão é o final da lista
                if(comando.equals("II")) posicao = 0;
                else if(comando.equals("I*")) posicao = sc.nextInt(); // posição específica fornecida pelo usuário
                id = sc.nextInt();
                for(int j = 0; j < veiculos.length; j++){ // percorre todos os veículos para encontrar o que corresponde ao ID fornecido
                    if(veiculos[j].getId() == id){
                        lista.inserir(veiculos[j], posicao); // insere o veículo na posição determinada
                        break;
                    }
                }
            } else if(comando.equals("RI") || comando.equals("RF") || comando.equals("R*")){
                int posicao = lista.n - 1;
                if(comando.equals("RI")) posicao = 0; // posição inicial
                else if(comando.equals("R*")) posicao = sc.nextInt(); // posição específica fornecida pelo usuário
                Veiculo removido = lista.remover(posicao); // remove o veículo da posição determinada
                if(removido != null){
                    System.out.println("(R)" + removido.getMarca() + " " + removido.getModelo()); // imprime o veículo removido
                }
            }
        }
        for(Celula atual = lista.primeiro; atual != null; atual = atual.prox){ // percorre a lista do começo ao fim para mostrar o que sobrou
            System.out.println(atual.veiculo.format()); // imprime o veículo formatado
        }
        sc.close();
    }
}
