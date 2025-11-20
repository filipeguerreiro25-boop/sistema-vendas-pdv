// telainicial.model.Conexao.java

package telainicial.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Conexao {

    // Altere estas variáveis conforme seu Banco de Dados
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver"; // Para MySQL
    private static final String URL = "jdbc:mysql://localhost:3306/caixafrente"; // Seu URL
    private static final String USER = "root"; // Seu usuário
    private static final String PASS = "root"; // Sua senha

    public static Connection getConnection() {
        try {
            // 1. Carrega o driver
            Class.forName(DRIVER); 
            // 2. Cria a conexão
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Driver JDBC não encontrado. Adicione o .jar ao projeto.", "Erro de Conexão", JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException("Driver não encontrado.", ex);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao conectar ao banco de dados: " + ex.getMessage(), "Erro de Conexão", JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException("Erro de conexão.", ex);
        }
    }

    public static void closeConnection(Connection con) {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException ex) {
                // Loga o erro de fechamento, mas não interrompe a aplicação
                System.err.println("Erro ao fechar conexão: " + ex.getMessage());
            }
        }
    }
}
