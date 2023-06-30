package visao;

import controle.MarcaControle;
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
import modelos.Marca;

/**
 *
 * @author Aluno
 */
public class TelaMarca extends javax.swing.JFrame {

    //Atributos
    MarcaControle marcaControle = null;
    Marca marca = new Marca();

    //Construtor
    public TelaMarca() {
        initComponents();
        jButtonVoltar.setFocusPainted(false); // Oculta a aparência de seleção do botão
        marcaControle = new MarcaControle();
        this.setLocationRelativeTo(null);
        this.setExtendedState(MAXIMIZED_BOTH);
        jTable1.setCellSelectionEnabled(false);
        mostrarTabela();

        
                //No textFieldId pode colocar até 10 dígitos e tbm só pode digitar números
        ((AbstractDocument) jTextFieldId.getDocument()).setDocumentFilter(new DocumentFilter() {
            private final int maxLength = 10;

            @Override
            public void insertString(DocumentFilter.FilterBypass fb, int offset, String text, AttributeSet attrs) throws BadLocationException {
                StringBuilder builder = new StringBuilder();
                builder.append(fb.getDocument().getText(0, fb.getDocument().getLength()));
                builder.insert(offset, text);

                if (builder.length() <= maxLength && isValid(builder.toString())) {
                    super.insertString(fb, offset, text, attrs);
                }
            }

            @Override
            public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
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

    @SuppressWarnings("unchecked")

    //Método Limpar tela
    private void limparCampos() {
        marca = new Marca();
        jTextFieldId.setText("");
        jTextFieldDescricao.setText("");
        jLabelFotoMarca.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/AdicionarFoto.png")));
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
            ArrayList<Marca> listaDeProprietario = marcaControle.obterListagem();
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.setNumRows(0);

            for (int pos = 0; pos < listaDeProprietario.size(); pos++) {
                Marca marca = listaDeProprietario.get(pos);
                String id = marca.getId();
                String descricao = marca.getDescricao();
                ImageIcon imagem = new ImageIcon(marca.getLogo());

                Object[] rowData = {id, descricao, imagem};
                model.addRow(rowData);
            }

            // Configurar o renderer para exibir a imagem na coluna
            TableColumn column = jTable1.getColumnModel().getColumn(2);
            column.setCellRenderer(new ImageIconCellRenderer());
        } catch (Exception erro) {
                      JOptionPane.showMessageDialog(null, erro.getMessage(), "Erro Método mostrarTabela", JOptionPane.ERROR_MESSAGE);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTextFieldDescricao = new javax.swing.JTextField();
        jButtonSelecionarLogo = new javax.swing.JButton();
        jButtonIncluir = new javax.swing.JButton();
        jButtonAlterar = new javax.swing.JButton();
        jLabelFotoMarca = new javax.swing.JLabel();
        jButtonLimparCampos = new javax.swing.JButton();
        jTextFieldId = new javax.swing.JTextField();
        jButtonVoltar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(62, 62, 62));
        setMinimumSize(new java.awt.Dimension(1440, 900));
        setPreferredSize(new java.awt.Dimension(1481, 922));

        jPanel1.setBackground(new java.awt.Color(60, 95, 143));
        jPanel1.setMaximumSize(new java.awt.Dimension(1440, 900));
        jPanel1.setMinimumSize(new java.awt.Dimension(1440, 900));

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/LogoCarro.png"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Segoe UI", 3, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(195, 218, 231));
        jLabel2.setText("Cadastro de Marca");

        jPanel2.setBackground(new java.awt.Color(182, 202, 221));
        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(33, 42, 70));
        jLabel3.setText("ID:");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(33, 42, 70));
        jLabel4.setText("DESCRIÇÃO:");

        jTextFieldDescricao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldDescricaoActionPerformed(evt);
            }
        });

        jButtonSelecionarLogo.setBackground(new java.awt.Color(92, 116, 123));
        jButtonSelecionarLogo.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButtonSelecionarLogo.setForeground(new java.awt.Color(195, 218, 231));
        jButtonSelecionarLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icone/AdicionarImagem32px.png"))); // NOI18N
        jButtonSelecionarLogo.setText("FOTOS");
        jButtonSelecionarLogo.setBorder(null);
        jButtonSelecionarLogo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSelecionarLogoActionPerformed(evt);
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

        jLabelFotoMarca.setBackground(new java.awt.Color(255, 255, 255));
        jLabelFotoMarca.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/AdicionarFoto.png"))); // NOI18N

        jButtonLimparCampos.setBackground(new java.awt.Color(92, 116, 123));
        jButtonLimparCampos.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButtonLimparCampos.setForeground(new java.awt.Color(195, 218, 231));
        jButtonLimparCampos.setText("LIMPAR CAMPOS");
        jButtonLimparCampos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLimparCamposActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(40, 40, 40)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButtonIncluir, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextFieldId, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(431, 431, 431)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButtonLimparCampos)
                                    .addComponent(jButtonAlterar, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 354, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonSelecionarLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(38, 38, 38)))
                .addComponent(jLabelFotoMarca, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(433, 433, 433))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonLimparCampos)
                    .addComponent(jLabelFotoMarca, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonAlterar, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonIncluir, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(56, 56, 56))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextFieldId, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextFieldDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(40, 40, 40)
                .addComponent(jButtonSelecionarLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

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

        jTable1.setAutoCreateRowSorter(true);
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Descrição", "Logo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setGridColor(new java.awt.Color(0, 0, 0));
        jTable1.setRowHeight(50);
        jTable1.setShowGrid(true);
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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jLabel9)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(434, 434, 434)
                        .addComponent(jLabel2))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonVoltar)
                        .addContainerGap())))
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1517, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonVoltar))
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 792, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextFieldDescricaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldDescricaoActionPerformed

    }//GEN-LAST:event_jTextFieldDescricaoActionPerformed

    private void jButtonIncluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonIncluirActionPerformed
        try {
            String caminhoDoArquivoLogo = marca.getLogo();
            Marca objMarca = new Marca(jTextFieldId.getText().trim(), jTextFieldDescricao.getText().trim(),
                    caminhoDoArquivoLogo);

            marcaControle.incluir(objMarca);

            marca = new Marca();

            limparCampos();
            mostrarTabela();

            JOptionPane.showMessageDialog(null, "INCLUÍDO COM SUCESSO!", "INCLUÍDO", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, erro.getMessage(), "Erro Incluir", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_jButtonIncluirActionPerformed

    private void jButtonAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAlterarActionPerformed
        try {
            String caminhoDoArquivoLogo = marca.getLogo();
            Marca objMarca = new Marca(jTextFieldId.getText().trim(), jTextFieldDescricao.getText().trim(),
                    caminhoDoArquivoLogo);

            marcaControle.alterar(objMarca);

            marca = new Marca();

            limparCampos();
            mostrarTabela();
            //JOptionPane.showMessageDialog(null, "Selecione uma foto");
            //jButtonFotoProprietarioActionPerformed(evt);
            JOptionPane.showMessageDialog(null, "ALTERADO COM SUCESSO!", "ALTERADO", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, erro.getMessage(), "Erro Alterar", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButtonAlterarActionPerformed

    private void jButtonSelecionarLogoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSelecionarLogoActionPerformed
        JFileChooser escolherArquivo = new JFileChooser();
        escolherArquivo.setFileSelectionMode(JFileChooser.FILES_ONLY);

// Defina o diretório inicial para o JFileChooser
        File pastaEscolhida = new File("./src/imagensMarca");
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
                        marca.setLogo(caminhoDoArquivo);

                        ImageIcon imagemIcon = new ImageIcon(caminhoDoArquivo);
                        Image image = imagemIcon.getImage().getScaledInstance(jLabelFotoMarca.getWidth(), jLabelFotoMarca.getHeight(), Image.SCALE_SMOOTH);
                        imagemIcon = new ImageIcon(image);
                        jLabelFotoMarca.setIcon(imagemIcon);
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
    }//GEN-LAST:event_jButtonSelecionarLogoActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked

        try {
            // TODO add your handling code here:
            int row = jTable1.getSelectedRow();
            String id = jTable1.getValueAt(row, 0).toString();
            String foto = jTable1.getValueAt(row, 1).toString();

            jTextFieldId.setText(id);
            jTextFieldDescricao.setText(foto);

            marca = marcaControle.validarIdIgual(id);

            ImageIcon imagem = new ImageIcon(marca.getLogo());
            Image image = imagem.getImage().getScaledInstance(jLabelFotoMarca.getWidth(), jLabelFotoMarca.getHeight(), Image.SCALE_SMOOTH);
            imagem = new ImageIcon(image);
            jLabelFotoMarca.setIcon(imagem);

            jTextFieldId.setEditable(false); // Define como não editável
        } catch (Exception ex) {
            Logger.getLogger(Marca.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jTable1MouseClicked

    private void jButtonLimparCamposActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLimparCamposActionPerformed
        limparCampos();
    }//GEN-LAST:event_jButtonLimparCamposActionPerformed

    private void jButtonVoltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonVoltarActionPerformed

        TelaPrincipal telaPrincipal = new TelaPrincipal();
        // Fechar a janela atual
        dispose();

        // Exibir a nova JFrame
        telaPrincipal.setVisible(true);
    }//GEN-LAST:event_jButtonVoltarActionPerformed

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaMarca().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAlterar;
    private javax.swing.JButton jButtonIncluir;
    private javax.swing.JButton jButtonLimparCampos;
    private javax.swing.JButton jButtonSelecionarLogo;
    private javax.swing.JButton jButtonVoltar;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelFotoMarca;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextFieldDescricao;
    private javax.swing.JTextField jTextFieldId;
    // End of variables declaration//GEN-END:variables
}
