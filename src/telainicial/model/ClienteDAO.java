// telainicial.model.ClienteDAO.java

package telainicial.model;

import telainicial.model.Cliente;
import telainicial.model.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;

public class ClienteDAO {

    public void salvar(Cliente cliente) throws SQLException {
        String sql = "INSERT INTO cliente (cpf, nome, telefone, email) VALUES (?, ?, ?, ?)";
        Connection con = null;
        PreparedStatement stmt = null;

        try {
            con = Conexao.getConnection(); // Obtém a conexão
            stmt = con.prepareStatement(sql);

            // Seta os valores (os '?' na query)
            stmt.setString(1, cliente.getCpf());
            stmt.setString(2, cliente.getNome());
            stmt.setString(3, cliente.getTelefone());
            stmt.setString(4, cliente.getEmail());

            stmt.executeUpdate(); // Executa o comando INSERT
            
        } finally {
            // Garante que o PreparedStatement e a Connection sejam fechados
            Conexao.closeConnection(con);
            // Obs: O stmt é fechado implicitamente se con.close() for chamado corretamente.
            // Para maior segurança, você também pode fechar o stmt explicitamente, se necessário.
            if (stmt != null) stmt.close();
        }
    }

    // Método de Listagem (Exemplo)
    public List<Cliente> listarTodos() throws SQLException {
        String sql = "SELECT * FROM cliente";
        List<Cliente> clientes = new ArrayList<>();
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            con = Conexao.getConnection();
            stmt = con.prepareStatement(sql);
            rs = stmt.executeQuery(); // Executa o SELECT

            while (rs.next()) {
                // Cria um novo objeto Cliente para cada linha (registro) retornado
                Cliente cliente = new Cliente(
                    rs.getString("cpf"),
                    rs.getString("nome"),
                    rs.getString("telefone"),
                    rs.getString("email")
                );
                clientes.add(cliente);
            }
        } finally {
            // Fechamento de recursos (essencial no JDBC)
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            Conexao.closeConnection(con);
        }
        return clientes;
    }
    public void excluir(String cpf) throws SQLException {
        // Query de exclusão que utiliza o CPF como chave
        String sql = "DELETE FROM cliente WHERE cpf = ?"; 

        try (Connection con = Conexao.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, cpf);
            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas == 0) {
                // Lança exceção se o cliente não for encontrado
                throw new SQLException("Nenhum cliente encontrado com o CPF: " + cpf + ". A exclusão não foi realizada.");
            }
        }
    }
}