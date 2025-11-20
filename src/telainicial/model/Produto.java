package telainicial.model;

import java.text.DecimalFormat;

public class Produto {
    
    // Atributos privados (campos) do Produto
    private String id;
    private String nome;
    private double preco;
    private int estoque;

    /**
     * Construtor COMPLETO: Usado ao CARREGAR dados do Banco de Dados.
     * @param id O ID/código do produto.
     * @param nome O nome do produto.
     * @param preco O preço unitário.
     * @param estoque A quantidade em estoque.
     */
    public Produto(String id, String nome, double preco, int estoque) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.estoque = estoque;
    }

    /**
     * NOVO Construtor SIMPLIFICADO: Usado ao CADASTRAR um novo produto.
     * O ID é omitido pois será gerado pelo AUTO_INCREMENT do BD.
     * @param nome O nome do produto.
     * @param preco O preço unitário.
     * @param estoque A quantidade em estoque.
     */
    public Produto(String nome, double preco, int estoque) {
        // O ID é deixado nulo/vazio, pois o banco de dados cuidará dele.
        this.nome = nome;
        this.preco = preco;
        this.estoque = estoque;
    }

    // --- Métodos Getters ---

    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public double getPreco() {
        return preco;
    }

    public int getEstoque() {
        return estoque;
    }

    // --- Métodos Setters (Necessários para Edição) ---
    
    public void setId(String id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public void setEstoque(int estoque) {
        this.estoque = estoque;
    }

    // --- Método para formatar o objeto como uma linha CSV (mantido por compatibilidade) ---
    public String toCSV() {
        // Formata o preço para 2 casas decimais, usando ponto como separador decimal.
        DecimalFormat df = new DecimalFormat("0.00");
        return id + ";" + nome + ";" + df.format(preco).replace(",", ".") + ";" + estoque;
    }
}