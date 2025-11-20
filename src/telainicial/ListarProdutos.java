/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package telainicial;

import telainicial.model.ProdutoDAO; // Importa a camada de acesso a dados (DAO)
import telainicial.model.Produto; // Importa o modelo Produto
import java.sql.SQLException; // Para tratar erros de BD
import java.util.List; // Para usar listas
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel; // Para manipular a JTable
/**
 *
 * @author Filipe
 */
public class ListarProdutos extends javax.swing.JFrame {

    // 1. Instancia o DAO para comunicação com o Banco de Dados
    private final ProdutoDAO produtoDAO = new ProdutoDAO();
    
    // -----------------------------------------------------------
    // MÉTODO: Lógica para Excluir um Produto Selecionado
    // -----------------------------------------------------------
    private void excluirProdutoSelecionado() {
        // Assume que a JTable se chama jtableProdutos
        int linhaSelecionada = jtableProdutos.getSelectedRow(); 

        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um produto na tabela para excluir.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        DefaultTableModel model = (DefaultTableModel) jtableProdutos.getModel();
        
        // CRÍTICO: Pega o ID na coluna 0 e converte para int
        Object idObj = model.getValueAt(linhaSelecionada, 0);
        int idProduto;
        
        try {
            idProduto = Integer.parseInt(idObj.toString());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Erro ao ler o ID do produto. A primeira coluna deve ser numérica.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirmacao = JOptionPane.showConfirmDialog(this, 
                "Tem certeza que deseja excluir o produto com ID: " + idProduto + "?", 
                "Confirmação de Exclusão", 
                JOptionPane.YES_NO_OPTION);

        if (confirmacao == JOptionPane.YES_OPTION) {
            try {
                produtoDAO.excluir(idProduto);
                JOptionPane.showMessageDialog(this, "Produto excluído com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                
                // Recarrega a lista
                carregarDadosProdutos(); 
                
            } catch (SQLException ex) {
                // Mensagem específica para erro de Foreign Key
                JOptionPane.showMessageDialog(this, 
                        "Erro ao excluir produto. Ele pode estar sendo usado em alguma Venda e não pode ser removido. Detalhes: " + ex.getMessage(), 
                        "Erro de Exclusão", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
    }
    /**
     * Creates new form ListarProdutos
     */
    public ListarProdutos() {
        initComponents();
        // Configurações iniciais da janela: fecha apenas esta janela
        // Mudado de EXIT_ON_CLOSE para DISPOSE_ON_CLOSE
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE); 
        
        // Chama o método para carregar os dados ao iniciar a tela
        carregarDadosProdutos();
    }
    
    // -----------------------------------------------------------
    // MÉTODO: Busca dados no DAO e preenche a JTable
    // -----------------------------------------------------------
    private void carregarDadosProdutos() {
        // Obtém o modelo da sua JTable (jtableProdutos)
        DefaultTableModel model = (DefaultTableModel) jtableProdutos.getModel();
        model.setRowCount(0); // Limpa as linhas existentes antes de carregar novos dados

        try {
            // 1. Busca a lista de produtos usando o ProdutoDAO
            List<Produto> produtos = produtoDAO.listarTodos();

            if (produtos.isEmpty()) {
                 JOptionPane.showMessageDialog(this, "Nenhum produto encontrado no banco de dados.", 
                                              "Aviso", JOptionPane.INFORMATION_MESSAGE);
                 return;
            }

            // 2. Adiciona cada produto como uma nova linha na JTable
            for (Produto produto : produtos) {
                model.addRow(new Object[]{
                    // 🚨 CORREÇÃO AQUI: Adiciona o ID (auto-incremento) primeiro
                    produto.getId(),
                    produto.getNome(),
                    produto.getPreco(),
                    produto.getEstoque()
                });
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, 
                "Erro ao carregar dados do banco. Verifique a Conexão e o ProdutoDAO: " + ex.getMessage(), 
                "Erro de BD", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtableProdutos = new javax.swing.JTable();
        btnVoltar = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel1.setText("Produtos");

        jtableProdutos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nome", "Preço", "Estoque"
            }
        ));
        jScrollPane1.setViewportView(jtableProdutos);

        btnVoltar.setText("Voltar");
        btnVoltar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVoltarActionPerformed(evt);
            }
        });

        btnExcluir.setText("Excluir");
        btnExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(150, 150, 150)
                .addComponent(jLabel1)
                .addContainerGap(153, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnExcluir)
                .addGap(18, 18, 18)
                .addComponent(btnVoltar)
                .addGap(49, 49, 49))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnVoltar)
                    .addComponent(btnExcluir))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnVoltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVoltarActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnVoltarActionPerformed

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        excluirProdutoSelecionado();
    }//GEN-LAST:event_btnExcluirActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnVoltar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jtableProdutos;
    // End of variables declaration//GEN-END:variables
}
