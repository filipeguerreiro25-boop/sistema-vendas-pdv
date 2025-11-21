package telainicialdao;

import telainicial.model.Conexao;
import telainicial.model.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {

    public Usuario autenticar(String login, String senha) throws SQLException {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Usuario usuario = null;

        String sql = "SELECT id_usuario, nome, login, senha FROM usuario WHERE login = ? AND senha = ?"; 

        try {
            con = Conexao.getConnection();
            stmt = con.prepareStatement(sql);
            stmt.setString(1, login);
            stmt.setString(2, senha); 
            rs = stmt.executeQuery();

            if (rs.next()) {
                usuario = new Usuario();
                usuario.setIdUsuario(rs.getInt("id_usuario"));
                usuario.setNome(rs.getString("nome"));
                usuario.setLogin(rs.getString("login"));
                usuario.setSenha(rs.getString("senha")); 
            }

        } finally {
         // Chamada segura, pois o closeConnection agora trata as exceções internamente
        Conexao.closeConnection(con, stmt, rs);
        }

        return usuario;
    }
    
    public void inserir(Usuario usuario) throws SQLException {
        Connection con = null;
        PreparedStatement stmt = null;

        String sql = "INSERT INTO usuario (nome, login, senha) VALUES (?, ?, ?)";

        try {
            con = Conexao.getConnection();
            stmt = con.prepareStatement(sql);
            
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getLogin());
            stmt.setString(3, usuario.getSenha()); 

            stmt.executeUpdate();

        } finally {
            // CORRIGIDO: Chamando a sobrecarga do método com apenas dois parâmetros (con, stmt)
            Conexao.closeConnection(con, stmt); 
        }
    }
}