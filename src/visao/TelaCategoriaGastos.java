package visao;

import controle.CategoriaGastosControle;
import java.awt.Component;
import static java.awt.Frame.MAXIMIZED_BOTH;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import modelos.CategoriaGastos;

/**
 *
 * @author Aluno
 */
public class TelaCategoriaGastos extends javax.swing.JFrame {

    //Atributos
    CategoriaGastosControle categoriaGastosControle = null;
    CategoriaGastos categoriaGastos = new CategoriaGastos();

    public TelaCategoriaGastos() {
        initComponents();
        categoriaGastosControle = new CategoriaGastosControle();
        this.setLocationRelativeTo(null);
        this.setExtendedState(MAXIMIZED_BOTH);
        jTable1.setCellSelectionEnabled(false);
        mostrarTabela();

        //No textFieldId pode colocar até 10 dígitos e tbm só pode digitar números
        ((AbstractDocument) jTextFieldId.getDocument()).setDocumentFilter(new DocumentFilter() {
            private final int maxLength = 10;

            @Override
            public void insertString(FilterBypass fb, int offset, String text, AttributeSet attrs) throws BadLocationException {
                StringBuilder builder = new StringBuilder();
                builder.append(fb.getDocument().getText(0, fb.getDocument().getLength()));
                builder.insert(offset, text);

                if (builder.length() <= maxLength && isValid(builder.toString())) {
                    super.insertString(fb, offset, text, attrs);
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                StringBuilder builder = new StringBuilder();
                builder.append(fb.getDocument().getText(0, fb.getDocument().getLength()));
                builder.replace(offset, offset + length, text);

                if (builder.length() <= maxLength && isValid(builder.toString())) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }
            
            // Verifica se o texto contém apenas dígitos usando uma expressão regular
            private boolean isValid(String text) {
                return Pattern.matches("\\d*", text); // Permite apenas dígitos
            }
        });
    }

    //Método Limpar tela
    private void limparCampos() {
        categoriaGastos = new CategoriaGastos();
        jTextFieldId.setText("");
        jTextFieldDescricao.setText("");
        jLabelIcone.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/AdicionarFotoVeiculo.png")));
        jTextFieldId.setEditable(true); // Define como editável
    }

    class ImageIconCellRenderer extends DefaultTableCellRenderer {

        //Definindo o tamanho que a imagem ficará dentro da célula da table
        private static final int ICON_WIDTH = 50;
        private static final int ICON_HEIGHT = 50;

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if (value instanceof ImageIcon) {
                ImageIcon imageIcon = (ImageIcon) value;
                Image image = imageIcon.getImage().getScaledInstance(ICON_WIDTH, ICON_HEIGHT, Image.SCALE_SMOOTH);
                setIcon(new ImageIcon(image));
            } else {
                setText((value != null) ? value.toString() : "");
                setIcon(null);
            }
            return this;
        }
    }
    //Métodos Mostrar Dados no arquivo txt

    private void mostrarTabela() {
        try {
            ArrayList<CategoriaGastos> listaDeProprietario = categoriaGastosControle.obterListagem();
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.setNumRows(0);

            for (int pos = 0; pos < listaDeProprietario.size(); pos++) {
                CategoriaGastos categoriaGastos = listaDeProprietario.get(pos);
                String id = categoriaGastos.getId();
                String descricao = categoriaGastos.getDescricao();
                ImageIcon icone = new ImageIcon(categoriaGastos.getIcone());

                Object[] rowData = {id, descricao, icone};
                model.addRow(rowData);
            }

            // Configurar o renderer para exibir a imagem na coluna
            TableColumn column = jTable1.getColumnModel().getColumn(2);
            column.setCellRenderer(new ImageIconCellRenderer());
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, erro.getMessage(), "Erro Método mostrarTabela", JOptionPane.ERROR_MESSAGE);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jButtonLimparCampos = new javax.swing.JButton();
        jButtonIncluir = new javax.swing.JButton();
        jButtonAlterar = new javax.swing.JButton();
        jLabelIcone = new javax.swing.JLabel();
        jButtonSelecionarIcone = new javax.swing.JButton();
        jTextFieldDescricao = new javax.swing.JTextField();
        jTextFieldId = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jButtonVoltar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1440, 900));

        jPanel1.setBackground(new java.awt.Color(60, 95, 143));
        jPanel1.setPreferredSize(new java.awt.Dimension(1481, 922));

        jPanel2.setBackground(new java.awt.Color(182, 202, 221));
        jPanel2.setForeground(new java.awt.Color(182, 202, 221));

        jLabel5.setBackground(new java.awt.Color(33, 42, 70));
        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(33, 42, 70));
        jLabel5.setText("ID:");

        jLabel6.setBackground(new java.awt.Color(33, 42, 70));
        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(33, 42, 70));
        jLabel6.setText("DESCRIÇÃO:");

        jButtonLimparCampos.setBackground(new java.awt.Color(92, 116, 123));
        jButtonLimparCampos.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButtonLimparCampos.setForeground(new java.awt.Color(195, 218, 231));
        jButtonLimparCampos.setText("LIMPAR CAMPOS");
        jButtonLimparCampos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLimparCamposActionPerformed(evt);
            }
        });

        jButtonIncluir.setBackground(new java.awt.Color(92, 116, 123));
        jButtonIncluir.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButtonIncluir.setForeground(new java.awt.Color(195, 218, 231));
        jButtonIncluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icone/Adicionar.png"))); // NOI18N
        jButtonIncluir.setText("INCLUIR");
        jButtonIncluir.setBorder(null);
        jButtonIncluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonIncluirActionPerformed(evt);
            }
        });

        jButtonAlterar.setBackground(new java.awt.Color(92, 116, 123));
        jButtonAlterar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButtonAlterar.setForeground(new java.awt.Color(195, 218, 231));
        jButtonAlterar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icone/Alterar.png"))); // NOI18N
        jButtonAlterar.setText("ALTERAR");
        jButtonAlterar.setBorder(null);
        jButtonAlterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAlterarActionPerformed(evt);
            }
        });

        jLabelIcone.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/AdicionarFotoVeiculo.png"))); // NOI18N

        jButtonSelecionarIcone.setBackground(new java.awt.Color(92, 116, 123));
        jButtonSelecionarIcone.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButtonSelecionarIcone.setForeground(new java.awt.Color(195, 218, 231));
        jButtonSelecionarIcone.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icone/AdicionarImagem32px.png"))); // NOI18N
        jButtonSelecionarIcone.setText("FOTOS");
        jButtonSelecionarIcone.setBorder(null);
        jButtonSelecionarIcone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSelecionarIconeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6))
                .addGap(72, 72, 72)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextFieldId, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonIncluir, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, 337, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(118, 118, 118)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jButtonAlterar, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 262, Short.MAX_VALUE)
                        .addComponent(jButtonSelecionarIcone, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(81, 81, 81)
                        .addComponent(jLabelIcone, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(115, 115, 115))
                    .addComponent(jButtonLimparCampos)))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jTextFieldId, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonLimparCampos))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel6)
                                    .addComponent(jTextFieldDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(167, 167, 167))
                            .addComponent(jLabelIcone, javax.swing.GroupLayout.Alignment.LEADING)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(69, 69, 69)
                        .addComponent(jButtonSelecionarIcone, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButtonIncluir, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButtonAlterar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTable1.setAutoCreateRowSorter(true);
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Descrição", "Icone"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setRowHeight(50);
        jTable1.getTableHeader().setReorderingAllowed(false);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setResizable(false);
            jTable1.getColumnModel().getColumn(1).setResizable(false);
            jTable1.getColumnModel().getColumn(2).setResizable(false);
        }

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/LogoCarro.png"))); // NOI18N

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 3, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(195, 218, 231));
        jLabel1.setText("CADASTRAR CATEGORIA DE GASTOS");

        jButtonVoltar.setBackground(new java.awt.Color(92, 116, 123));
        jButtonVoltar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButtonVoltar.setForeground(new java.awt.Color(195, 218, 231));
        jButtonVoltar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icone/voltar.png"))); // NOI18N
        jButtonVoltar.setText("VOLTAR");
        jButtonVoltar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonVoltarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButtonVoltar)
                                .addContainerGap())
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(260, 260, 260)
                                .addComponent(jLabel1)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(4, 4, 4)
                        .addComponent(jButtonVoltar)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(247, 247, 247))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1434, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1022, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonVoltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonVoltarActionPerformed

        TelaPrincipal telaPrincipal = new TelaPrincipal();
        // Fechar a janela atual
        dispose();

        // Exibir a nova JFrame
        telaPrincipal.setVisible(true);
    }//GEN-LAST:event_jButtonVoltarActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        try {
            // TODO add your handling code here:
            int row = jTable1.getSelectedRow();
            String id = jTable1.getValueAt(row, 0).toString();
            String foto = jTable1.getValueAt(row, 1).toString();

            jTextFieldId.setText(id);
            jTextFieldDescricao.setText(foto);

            categoriaGastos = categoriaGastosControle.validarIdIgual(id);

            ImageIcon imagem = new ImageIcon(categoriaGastos.getIcone());
            Image image = imagem.getImage().getScaledInstance(jLabelIcone.getWidth(), jLabelIcone.getHeight(), Image.SCALE_SMOOTH);
            imagem = new ImageIcon(image);
            jLabelIcone.setIcon(imagem);

            jTextFieldId.setEditable(false); // Define como não editável
        } catch (Exception ex) {
            Logger.getLogger(CategoriaGastos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jTable1MouseClicked

    private void jButtonSelecionarIconeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSelecionarIconeActionPerformed
        JFileChooser escolherArquivo = new JFileChooser();
        escolherArquivo.setFileSelectionMode(JFileChooser.FILES_ONLY);

        // Defina o diretório inicial para o JFileChooser
        File pastaEscolhida = new File("./src/iconesCategoriaGastos");
        escolherArquivo.setCurrentDirectory(pastaEscolhida);

        int resultado = escolherArquivo.showOpenDialog(this);

        if (resultado == JFileChooser.APPROVE_OPTION) {
            File arquivoEscolhido = escolherArquivo.getSelectedFile();
            String caminhoDoArquivo = arquivoEscolhido.getAbsolutePath();

            try {
                BufferedImage imagem = ImageIO.read(arquivoEscolhido);
                if (imagem != null) {
                    long tamanhoArquivoEmBytes = arquivoEscolhido.length();
                    long tamanhoArquivoEmMB = tamanhoArquivoEmBytes / (1024 * 1024);

                    if (tamanhoArquivoEmMB <= 10) {
                        categoriaGastos.setIcone(caminhoDoArquivo);

                        ImageIcon imagemIcon = new ImageIcon(caminhoDoArquivo);
                        Image image = imagemIcon.getImage().getScaledInstance(jLabelIcone.getWidth(), jLabelIcone.getHeight(), Image.SCALE_SMOOTH);
                        imagemIcon = new ImageIcon(image);
                        jLabelIcone.setIcon(imagemIcon);
                    } else {
                        JOptionPane.showMessageDialog(this, "O tamanho do arquivo excede 10 MB", "Erro LogoMarca", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "O arquivo selecionado não é uma imagem válida", "Erro LogoMarca", JOptionPane.ERROR_MESSAGE);
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Erro ao ler o arquivo de imagem", "Erro LogoMarca", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_jButtonSelecionarIconeActionPerformed

    private void jButtonAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAlterarActionPerformed
        try {
            String caminhoDoArquivoIcone = categoriaGastos.getIcone();
            CategoriaGastos objCategoriaGastos = new CategoriaGastos(jTextFieldId.getText().trim(), jTextFieldDescricao.getText().trim(),
                caminhoDoArquivoIcone);

            categoriaGastosControle.alterar(objCategoriaGastos);

            categoriaGastos = new CategoriaGastos();

            limparCampos();
            mostrarTabela();

            JOptionPane.showMessageDialog(null, "ALTERADO COM SUCESSO!", "INCLUÍDO", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, erro.getMessage(), "Erro Alterar", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButtonAlterarActionPerformed

    private void jButtonIncluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonIncluirActionPerformed
        try {
            String caminhoDoArquivoIcone = categoriaGastos.getIcone();
            CategoriaGastos objCategoriaGastos = new CategoriaGastos(jTextFieldId.getText().trim(), jTextFieldDescricao.getText().trim(),
                caminhoDoArquivoIcone);

            categoriaGastosControle.incluir(objCategoriaGastos);

            categoriaGastos = new CategoriaGastos();

            limparCampos();
            mostrarTabela();

            JOptionPane.showMessageDialog(null, "INCLUÍDO COM SUCESSO!", "INCLUÍDO", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, erro.getMessage(), "Erro Incluir", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButtonIncluirActionPerformed

    private void jButtonLimparCamposActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLimparCamposActionPerformed
        limparCampos();
    }//GEN-LAST:event_jButtonLimparCamposActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TelaCategoriaGastos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaCategoriaGastos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaCategoriaGastos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaCategoriaGastos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaCategoriaGastos().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAlterar;
    private javax.swing.JButton jButtonIncluir;
    private javax.swing.JButton jButtonLimparCampos;
    private javax.swing.JButton jButtonSelecionarIcone;
    private javax.swing.JButton jButtonVoltar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelIcone;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextFieldDescricao;
    private javax.swing.JTextField jTextFieldId;
    // End of variables declaration//GEN-END:variables
}
