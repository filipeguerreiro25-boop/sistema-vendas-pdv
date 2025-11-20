package telainicial.model;

/**
 * Classe que representa a entidade Cliente do Sistema de Vendas.
 */
public class Cliente {

    // Atributos privados (campos) do Cliente
    private String cpf;
    private String nome;
    private String telefone;
    private String email;

    /**
     * Construtor para criar um novo objeto Cliente.
     * @param cpf O CPF do cliente (identificador principal).
     * @param nome O nome completo do cliente.
     * @param telefone O telefone de contato.
     * @param email O e-mail de contato.
     */
    public Cliente(String cpf, String nome, String telefone, String email) {
        this.cpf = cpf;
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
    }

    // --- Métodos Getters e Setters ---

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Converte os dados do cliente em uma string formatada para CSV,
     * utilizando ponto e vírgula (;) como delimitador.
     * @return Uma string representando o cliente em formato CSV.
     */
    public String toCSV() {
        return cpf + ";" + nome + ";" + telefone + ";" + email;
    }
}