package telainicial.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Conexao {

    private static final String DRIVER = "com.mysql.cj.jdbc.Driver"; 
    private static final String URL = "jdbc:mysql://localhost:3306/caixafrente"; 
    private static final String USER = "root"; 
    private static final String PASS = "root"; 

    public static Connection getConnection() {
        try {
            Class.forName(DRIVER); 
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Driver JDBC não encontrado. Adicione o .jar ao projeto.", "Erro de Conexão", JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException("Driver não encontrado.", ex);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao conectar ao banco de dados: " + ex.getMessage(), "Erro de Conexão", JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException("Erro de conexão.", ex);
        }
    }
    
    // =================================================================
    // MÉTODOS DE FECHAMENTO (CORRIGIDOS)
    // =================================================================

    // Versão 1: Fecha Apenas Connection
    public static void closeConnection(Connection con) {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException ex) {
                System.err.println("Erro ao fechar conexão: " + ex.getMessage());
                // Não lança exceção para não quebrar o bloco finally de quem chamou
            }
        }
    }

    // Versão 2: Fecha Connection e Statement
    public static void closeConnection(Connection con, PreparedStatement stmt) {
        // Fechamos o Statement primeiro
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException ex) {
                System.err.println("Erro ao fechar Statement: " + ex.getMessage());
            }
        }
        // Chamamos a versão que fecha a Connection
        closeConnection(con);
    }
    
    // Versão 3: Fecha Connection, Statement e ResultSet
    public static void closeConnection(Connection con, PreparedStatement stmt, ResultSet rs) {
        // Fechamos o ResultSet primeiro
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException ex) {
                System.err.println("Erro ao fechar ResultSet: " + ex.getMessage());
            }
        }
        // Chamamos a versão que fecha Statement e Connection
        closeConnection(con, stmt); 
    }
}