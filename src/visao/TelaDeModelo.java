package visao;

import controle.ModeloControle;
import static java.awt.Frame.MAXIMIZED_BOTH;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import modelos.Marca;
import modelos.Modelo;
import visao.TelaPrincipal;

public class TelaDeModelo extends javax.swing.JFrame {

    //Atributos
    private String nomeDoArquivoNoDisco = null;
    ModeloControle modeloControle = null;
    Modelo modelo = new Modelo();

    public TelaDeModelo() throws Exception {
        initComponents();
        jButtonVoltar.setFocusPainted(false); // Oculta a aparência de seleção do botão
        nomeDoArquivoNoDisco = "./src/bancodedadosPI/marcaBD.txt";
        ComboBox();//Chamando o método de colocar as marcas na comboBox
        modeloControle = new ModeloControle();
        this.setLocationRelativeTo(null);
        this.setExtendedState(MAXIMIZED_BOTH);
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

    //Método Limpar tela
    private void limparCampos() {
        modelo = new Modelo();
        jTextFieldId.setText("");
        jTextFieldDescricao.setText("");
        jComboBoxMarca.setSelectedIndex(0);
        jComboBoxCategoria.setSelectedIndex(0);
        jLabelFotoModelo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/AdicionarFotoVeiculo.png")));
        jTextFieldId.setEditable(true); // Define como editável
    }

    //Método colocar o nome das marcas cadastradas na comboBox
    public void ComboBox() throws Exception {
        try {
            ArrayList<Marca> listaDeMarca = new ArrayList<Marca>();
            FileReader fr = new FileReader(nomeDoArquivoNoDisco);
            BufferedReader br = new BufferedReader(fr);
            String linha = "";
            while ((linha = br.readLine()) != null) {
                String vetorStr[] = linha.split(";");
                String descricao = vetorStr[1];

                jComboBoxMarca.addItem(descricao);
            }
            br.close();

        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, erro.getMessage(), "Método comboBox", JOptionPane.ERROR_MESSAGE);
        }

    }

    //Método para pegar o nome da marca e transformar para id e salvar o id no txt
    public String pegarIdPelaNomeMarca(String descricao) throws Exception {
        try {
            FileReader fr = new FileReader(nomeDoArquivoNoDisco);
            BufferedReader br = new BufferedReader(fr);
            String linha = "";
            while ((linha = br.readLine()) != null) {
                String vetorStr[] = linha.split(";");
                String id = vetorStr[0];
                String descricaoAux = vetorStr[1];
                if (descricao.equalsIgnoreCase(descricaoAux)) {
                    br.close();
                    return id;
                }

            }
            br.close();

        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, erro.getMessage(), "Método pegarNomeMarcaPeloId", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    //Método pegar a id da marca e pegar a descrição para colocar na table
    public String colocarNomeMarcaNaTable(String id) throws Exception {
        try {
            ArrayList<Marca> listaDeMarca = new ArrayList<Marca>();
            FileReader fr = new FileReader(nomeDoArquivoNoDisco);
            BufferedReader br = new BufferedReader(fr);
            String linha = "";
            while ((linha = br.readLine()) != null) {
                String vetorStr[] = linha.split(";");
                String idAux = vetorStr[0];
                String descricaoAux = vetorStr[1];
                String logo = vetorStr[2];
                if (id.equalsIgnoreCase(idAux)) {
                    br.close();
                    return descricaoAux;
                }

            }
            br.close();

        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, erro.getMessage(), "Método colocarNomeMarcaNaTable", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    //Métodos Mostrar Dados no arquivo txt
    private void mostrarTabela() {
        try {
            ArrayList<Modelo> listaDeModelo = null;
            listaDeModelo = modeloControle.obterListagem();
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.setNumRows(0);
            if (listaDeModelo.isEmpty()) {
                return;
            }

            for (int pos = 0; pos < listaDeModelo.size(); pos++) {
                Modelo modelos = listaDeModelo.get(pos);
                String[] linha = new String[4];
                linha[0] = modelos.getId();
                linha[1] = modelos.getDescricao();
                linha[2] = colocarNomeMarcaNaTable(modelos.getMarca());
                linha[3] = modelos.getCategoria();
                model.addRow(linha);
            }
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, erro.getMessage(), "Método mostrarTabela", JOptionPane.ERROR_MESSAGE);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jButtonAdicionarFotoModelo = new javax.swing.JButton();
        jTextFieldDescricao = new javax.swing.JTextField();
        jLabelFotoModelo = new javax.swing.JLabel();
        jButton1IncluirModelo = new javax.swing.JButton();
        jButtonAlterarModelo = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jComboBoxMarca = new javax.swing.JComboBox<>();
        jButtonCadastrarMarca = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jComboBoxCategoria = new javax.swing.JComboBox<>();
        jTextFieldId = new javax.swing.JTextField();
        jButtonLimparCampos = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButtonVoltar = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1481, 922));

        jPanel1.setBackground(new java.awt.Color(60, 95, 143));
        jPanel1.setMaximumSize(new java.awt.Dimension(1440, 900));
        jPanel1.setMinimumSize(new java.awt.Dimension(1440, 900));

        jPanel2.setBackground(new java.awt.Color(182, 202, 221));
        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel2.setMaximumSize(new java.awt.Dimension(1440, 900));
        jPanel2.setMinimumSize(new java.awt.Dimension(1440, 900));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(33, 42, 70));
        jLabel2.setText("ID:");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(33, 42, 70));
        jLabel3.setText("DESCRIÇÂO:");

        jButtonAdicionarFotoModelo.setBackground(new java.awt.Color(92, 116, 123));
        jButtonAdicionarFotoModelo.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButtonAdicionarFotoModelo.setForeground(new java.awt.Color(195, 218, 231));
        jButtonAdicionarFotoModelo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icone/AdicionarImagem32px.png"))); // NOI18N
        jButtonAdicionarFotoModelo.setText("FOTOS");
        jButtonAdicionarFotoModelo.setBorder(null);
        jButtonAdicionarFotoModelo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAdicionarFotoModeloActionPerformed(evt);
            }
        });

        jLabelFotoModelo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/AdicionarFotoVeiculo.png"))); // NOI18N

        jButton1IncluirModelo.setBackground(new java.awt.Color(92, 116, 123));
        jButton1IncluirModelo.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton1IncluirModelo.setForeground(new java.awt.Color(195, 218, 231));
        jButton1IncluirModelo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icone/Adicionar.png"))); // NOI18N
        jButton1IncluirModelo.setText("INCLUIR");
        jButton1IncluirModelo.setBorder(null);
        jButton1IncluirModelo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1IncluirModeloActionPerformed(evt);
            }
        });

        jButtonAlterarModelo.setBackground(new java.awt.Color(92, 116, 123));
        jButtonAlterarModelo.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButtonAlterarModelo.setForeground(new java.awt.Color(195, 218, 231));
        jButtonAlterarModelo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icone/Alterar.png"))); // NOI18N
        jButtonAlterarModelo.setText("ALTERAR");
        jButtonAlterarModelo.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButtonAlterarModelo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAlterarModeloActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(33, 42, 70));
        jLabel4.setText("MARCA:");

        jComboBoxMarca.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jComboBoxMarca.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SELECIONE UMA MARCA" }));
        jComboBoxMarca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxMarcaActionPerformed(evt);
            }
        });

        jButtonCadastrarMarca.setBackground(new java.awt.Color(92, 116, 123));
        jButtonCadastrarMarca.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButtonCadastrarMarca.setForeground(new java.awt.Color(195, 218, 231));
        jButtonCadastrarMarca.setText("CADASTRAR MARCA");
        jButtonCadastrarMarca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCadastrarMarcaActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(33, 42, 70));
        jLabel5.setText("CATEGORIA:");

        jComboBoxCategoria.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jComboBoxCategoria.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SELECIONE UMA CATEGORIA", "HATCH", "SEDAN", "SUV", "UTILITÁRIO" }));

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
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel3)
                                .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING))
                            .addComponent(jLabel4))
                        .addGap(8, 8, 8))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jButton1IncluirModelo, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jTextFieldDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldId, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jButtonAdicionarFotoModelo, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(27, 27, 27)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButtonLimparCampos)
                                    .addComponent(jButtonCadastrarMarca)
                                    .addComponent(jButtonAlterarModelo, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 410, Short.MAX_VALUE))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboBoxMarca, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBoxCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addComponent(jLabelFotoModelo, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(178, 178, 178))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(92, 92, 92)
                        .addComponent(jLabelFotoModelo, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(167, 167, 167)
                        .addComponent(jButtonAdicionarFotoModelo, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jTextFieldId, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(jButtonLimparCampos)))
                .addGap(11, 11, 11)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextFieldDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBoxMarca, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jButtonCadastrarMarca))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBoxCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(62, 62, 62)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1IncluirModelo, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonAlterarModelo, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 3, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Cadastro De Modelo");

        jTable1.setAutoCreateRowSorter(true);
        jTable1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Descrição", "Marca", "Categoria"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setGridColor(new java.awt.Color(0, 0, 0));
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
            jTable1.getColumnModel().getColumn(3).setResizable(false);
        }

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

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/LogoCarro.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jLabel9)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(368, 368, 368)
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonVoltar)
                        .addContainerGap())))
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addComponent(jLabel1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel9))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButtonVoltar)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 339, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(297, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonAlterarModeloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAlterarModeloActionPerformed
        try {
            String caminhoDoArquivoLogo = modelo.getModelo();
            String comboBoxMarca = pegarIdPelaNomeMarca((String) jComboBoxMarca.getSelectedItem());
            Modelo objModelo = new Modelo(jTextFieldId.getText().trim(), jTextFieldDescricao.getText().trim(),
                    comboBoxMarca, (String) jComboBoxCategoria.getSelectedItem(), caminhoDoArquivoLogo);

            modeloControle.alterar(objModelo);

            modelo = new Modelo();

            limparCampos();
            mostrarTabela();

            JOptionPane.showMessageDialog(null, "ALTERADO COM SUCESSO!", "ALTERADO", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, erro.getMessage());
        }
    }//GEN-LAST:event_jButtonAlterarModeloActionPerformed

    private void jButton1IncluirModeloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1IncluirModeloActionPerformed
        try {
            String caminhoDoArquivoModelo = modelo.getModelo();
            String comboBoxMarca = pegarIdPelaNomeMarca((String) jComboBoxMarca.getSelectedItem());
            Modelo objModelo = new Modelo(jTextFieldId.getText().trim(), jTextFieldDescricao.getText().trim(),
                    comboBoxMarca, (String) jComboBoxCategoria.getSelectedItem(), caminhoDoArquivoModelo);

            modeloControle.incluir(objModelo);

            modelo = new Modelo();

            limparCampos();
            mostrarTabela();
            JOptionPane.showMessageDialog(null, "INCLUIDO COM SUCESSO!", "INCLUIDO", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, erro.getMessage());
        }
    }//GEN-LAST:event_jButton1IncluirModeloActionPerformed

    private void jButtonAdicionarFotoModeloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAdicionarFotoModeloActionPerformed
        JFileChooser escolherArquivo = new JFileChooser();
        escolherArquivo.setFileSelectionMode(JFileChooser.FILES_ONLY);

// Defina o diretório inicial para o JFileChooser
        File pastaEscolhida = new File("./src/imagensModelo");
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
                        modelo.setModelo(caminhoDoArquivo);

                        ImageIcon imagemIcon = new ImageIcon(caminhoDoArquivo);
                        Image image = imagemIcon.getImage().getScaledInstance(jLabelFotoModelo.getWidth(), jLabelFotoModelo.getHeight(), Image.SCALE_SMOOTH);
                        imagemIcon = new ImageIcon(image);
                        jLabelFotoModelo.setIcon(imagemIcon);
                    } else {
                        JOptionPane.showMessageDialog(this, "O tamanho do arquivo excede 10 MB", "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "O arquivo selecionado não é uma imagem válida", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Erro ao ler o arquivo de imagem", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_jButtonAdicionarFotoModeloActionPerformed

    private void jComboBoxMarcaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxMarcaActionPerformed

    }//GEN-LAST:event_jComboBoxMarcaActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        try {
            int row = jTable1.getSelectedRow(); //A linha que selecionar pegar
            String id = jTable1.getValueAt(row, 0).toString(); //id na coluna 1
            String descricao = jTable1.getValueAt(row, 1).toString();//descrição na coluna 2
            String marcaAux = jTable1.getValueAt(row, 2).toString();//marca na coluna 3
            String categoria = jTable1.getValueAt(row, 3).toString();//categoria coluna 4

            //colocando o que pegou encima nos textField
            jTextFieldId.setText(id);
            jTextFieldDescricao.setText(descricao);
            jComboBoxMarca.setSelectedItem(marcaAux);
            jComboBoxCategoria.setSelectedItem(categoria);

            //Preenchendo o objeto para pegar o caminho da imagem
            modelo = modeloControle.validarIdIgual(id);

            //Pegando o caminho da imagem transformando em image e colocando na labl
            ImageIcon imagem = new ImageIcon(modelo.getModelo());
            Image image = imagem.getImage().getScaledInstance(jLabelFotoModelo.getWidth(), jLabelFotoModelo.getHeight(), Image.SCALE_SMOOTH);
            imagem = new ImageIcon(image);
            jLabelFotoModelo.setIcon(imagem);

            jTextFieldId.setEditable(false); // Define como não editável
        } catch (Exception ex) {
            Logger.getLogger(Marca.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jTable1MouseClicked

    private void jButtonCadastrarMarcaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCadastrarMarcaActionPerformed
        TelaMarca telaMarca = new TelaMarca();
        
        // Fechar a janela atual
        dispose();

        // Exibir a nova JFrame
        telaMarca.setVisible(true);
    }//GEN-LAST:event_jButtonCadastrarMarcaActionPerformed

    private void jButtonVoltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonVoltarActionPerformed

        TelaPrincipal telaPrincipal = new TelaPrincipal();
        // Fechar a janela atual
        dispose();

        // Exibir a nova JFrame
        telaPrincipal.setVisible(true);
    }//GEN-LAST:event_jButtonVoltarActionPerformed

    private void jButtonLimparCamposActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLimparCamposActionPerformed
        limparCampos();
    }//GEN-LAST:event_jButtonLimparCamposActionPerformed

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new TelaDeModelo().setVisible(true);
                } catch (Exception erro) {

                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1IncluirModelo;
    private javax.swing.JButton jButtonAdicionarFotoModelo;
    private javax.swing.JButton jButtonAlterarModelo;
    private javax.swing.JButton jButtonCadastrarMarca;
    private javax.swing.JButton jButtonLimparCampos;
    private javax.swing.JButton jButtonVoltar;
    private javax.swing.JComboBox<String> jComboBoxCategoria;
    private javax.swing.JComboBox<String> jComboBoxMarca;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelFotoModelo;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextFieldDescricao;
    private javax.swing.JTextField jTextFieldId;
    // End of variables declaration//GEN-END:variables
}
