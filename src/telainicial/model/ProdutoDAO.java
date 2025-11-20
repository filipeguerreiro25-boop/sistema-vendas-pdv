package telainicial.model;

import telainicial.model.Produto;
// Ajuste o import da Conexao se ela estiver em outro pacote (ex: telainicial.util)
import telainicial.model.Conexao; 
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {

    /**
     * Salva um novo produto no banco de dados.
     * A coluna id_produto é gerada automaticamente (auto_increment).
     */
    public void salvar(Produto produto) throws SQLException {
        // Query correta: omite o id_produto e insere apenas os 3 campos restantes
        String sql = "INSERT INTO produto (nome, preco, estoque) VALUES (?, ?, ?)";
        Connection con = null;
        PreparedStatement stmt = null;

        try {
            con = Conexao.getConnection(); 
            stmt = con.prepareStatement(sql);

            // Seta os valores nos parâmetros da query
            stmt.setString(1, produto.getNome());    
            stmt.setDouble(2, produto.getPreco());   
            stmt.setInt(3, produto.getEstoque());    

            stmt.executeUpdate();
            
        } finally {
            if (stmt != null) stmt.close();
            Conexao.closeConnection(con);
        }
    }

    /**
     * Lista todos os produtos cadastrados no banco de dados.
     */
    public List<Produto> listarTodos() throws SQLException {
        // SELECT correto: busca id_produto
        String sql = "SELECT id_produto, nome, preco, estoque FROM produto";
        List<Produto> produtos = new ArrayList<>();
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            con = Conexao.getConnection();
            stmt = con.prepareStatement(sql);
            rs = stmt.executeQuery(); 

            while (rs.next()) {
                // Mapeamento corrigido: usa "id_produto" e converte para String
                Produto produto = new Produto(
                    // 🚨 CORREÇÃO AQUI: id_produto (INT) mapeado para id (String)
                    String.valueOf(rs.getInt("id_produto")), 
                    rs.getString("nome"),   
                    rs.getDouble("preco"),  
                    rs.getInt("estoque")    
                );
                produtos.add(produto);
            }
        } finally {
            // Fechamento de recursos (essencial no JDBC)
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            Conexao.closeConnection(con);
        }
        return produtos;
    }
    public void excluir(int idProduto) throws SQLException {
        // Query de exclusão que utiliza o id_produto como chave
        String sql = "DELETE FROM produto WHERE id_produto = ?";

        try (Connection con = Conexao.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, idProduto);
            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas == 0) {
                // Lança exceção se o produto não for encontrado
                throw new SQLException("Nenhum produto encontrado com o ID: " + idProduto + ". A exclusão não foi realizada.");
            }
        }
    }
}