package visao;

import java.awt.Image;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import modelos.*;
import controle.ProprietarioControle;
import static java.awt.Frame.MAXIMIZED_BOTH;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class TelaProprietario extends javax.swing.JFrame {

    //Atributos
    IProprietarioCRUD proprietarioControle = null;//Criando a variavel do tipo IProprietarioCRUD
    Proprietario proprietario = new Proprietario();//Instânciando o objeto
    Telefone telefone = new Telefone();//Instânciando o objeto

    //Construtor
    public TelaProprietario() {
        initComponents(); //É usado para criar e inicializar os componentes da interface gráfica gerada pelo NetBeans
        jButtonVoltar.setFocusPainted(false); // Oculta a aparência de seleção do botão
        proprietarioControle = new ProprietarioControle();//Instânciando o objeto
        this.setLocationRelativeTo(null);//Abrir no meio da tela
        this.setExtendedState(MAXIMIZED_BOTH);//Maximizar quando iniciar

        mostrarTabela();
    }

    //Método Limpar tela
    private void limparCampos() {
        proprietario = new Proprietario();
        jTextFieldNomeCompleto.setText("");
        jTextFieldEmail.setText("");
        jFormattedTextFieldNumeroCNH.setValue(null);
        jFormattedTextFieldCpf.setValue(null);
        jFormattedTextFieldDDI.setValue(null);
        jFormattedTextFieldDDD.setValue(null);
        jFormattedTextFieldNumeroTelefone.setValue(null);
        jComboBoxCategoriaDeCNH.setSelectedIndex(0);
        jLabelFotoProprietario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/FotoDoProprietario.jpg")));
        jLabelFotoCNH.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/FotoCNH.png")));
        jFormattedTextFieldCpf.setEditable(true); // Define como editável
    }

    //Métodos Mostrar Dados no arquivo txt
    private void mostrarTabela() {
        try {
            ArrayList<Proprietario> listaDeProprietario = null;
            listaDeProprietario = proprietarioControle.obterListagem();
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.setNumRows(0);
            if (listaDeProprietario.isEmpty()) {
                return;
            }

            for (int pos = 0; pos < listaDeProprietario.size(); pos++) {
                Proprietario pessoa = listaDeProprietario.get(pos);
                String[] linha = new String[6];
                linha[0] = pessoa.getNomeCompleto();
                linha[1] = pessoa.getEmail();
                linha[2] = pessoa.getCpf();
                linha[3] = pessoa.getTelefone().getDDI() + pessoa.getTelefone().getDDD() + pessoa.getTelefone().getNumero();
                linha[4] = pessoa.getNumeroCNH();
                linha[5] = pessoa.getCategoriaCNH();
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
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jComboBoxCategoriaDeCNH = new javax.swing.JComboBox<>();
        jButtonFotoCNH = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jLabelFotoProprietario = new javax.swing.JLabel();
        jTextFieldEmail = new javax.swing.JTextField();
        jTextFieldNomeCompleto = new javax.swing.JTextField();
        jLabelFotoCNH = new javax.swing.JLabel();
        jButtonIncluir = new javax.swing.JButton();
        jButtonAlterar = new javax.swing.JButton();
        jButtonBuscar = new javax.swing.JButton();
        jFormattedTextFieldCpf = new javax.swing.JFormattedTextField();
        jFormattedTextFieldDDI = new javax.swing.JFormattedTextField();
        jFormattedTextFieldDDD = new javax.swing.JFormattedTextField();
        jButton1 = new javax.swing.JButton();
        jButtonFotoProprietario1 = new javax.swing.JButton();
        jFormattedTextFieldNumeroCNH = new javax.swing.JFormattedTextField();
        jFormattedTextFieldNumeroTelefone = new javax.swing.JFormattedTextField();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        jButtonVoltar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1440, 900));

        jPanel1.setBackground(new java.awt.Color(60, 95, 143));
        jPanel1.setMaximumSize(new java.awt.Dimension(1440, 900));
        jPanel1.setMinimumSize(new java.awt.Dimension(1440, 900));
        jPanel1.setPreferredSize(new java.awt.Dimension(1481, 922));
        jPanel1.setVerifyInputWhenFocusTarget(false);

        jPanel2.setBackground(new java.awt.Color(182, 202, 221));
        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel2.setForeground(new java.awt.Color(182, 202, 221));
        jPanel2.setMaximumSize(new java.awt.Dimension(1440, 900));
        jPanel2.setMinimumSize(new java.awt.Dimension(1440, 900));

        jLabel2.setBackground(new java.awt.Color(33, 42, 70));
        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(33, 42, 70));
        jLabel2.setText("NOME COMPLETO:");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(33, 42, 70));
        jLabel3.setText("EMAIL:");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(33, 42, 70));
        jLabel4.setText("TELEFONE:");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(33, 42, 70));
        jLabel5.setText("CNH:");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(33, 42, 70));
        jLabel6.setText("CPF:");

        jComboBoxCategoriaDeCNH.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jComboBoxCategoriaDeCNH.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "A", "B", "C", "D", "E", "AB", "AC", "AD", "AE" }));
        jComboBoxCategoriaDeCNH.setBorder(null);
        jComboBoxCategoriaDeCNH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxCategoriaDeCNHActionPerformed(evt);
            }
        });

        jButtonFotoCNH.setBackground(new java.awt.Color(92, 116, 123));
        jButtonFotoCNH.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButtonFotoCNH.setForeground(new java.awt.Color(195, 218, 231));
        jButtonFotoCNH.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icone/AdicionarImagem32px.png"))); // NOI18N
        jButtonFotoCNH.setText("<html>Selecionar<br><center>Foto<br>CNH</html>");
        jButtonFotoCNH.setBorder(null);
        jButtonFotoCNH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonFotoCNHActionPerformed(evt);
            }
        });

        jLabel7.setBackground(new java.awt.Color(33, 42, 70));
        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(33, 42, 70));
        jLabel7.setText("CATEGORIA DE CNH:");

        jLabelFotoProprietario.setBackground(new java.awt.Color(0, 0, 0));
        jLabelFotoProprietario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/FotoDoProprietario.jpg"))); // NOI18N

        jTextFieldEmail.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jTextFieldNomeCompleto.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTextFieldNomeCompleto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldNomeCompletoActionPerformed(evt);
            }
        });

        jLabelFotoCNH.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/FotoCNH.png"))); // NOI18N

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

        jButtonBuscar.setBackground(new java.awt.Color(92, 116, 123));
        jButtonBuscar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButtonBuscar.setForeground(new java.awt.Color(195, 218, 231));
        jButtonBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icone/Buscar.png"))); // NOI18N
        jButtonBuscar.setText("BUSCAR");
        jButtonBuscar.setBorder(null);
        jButtonBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBuscarActionPerformed(evt);
            }
        });

        try {
            jFormattedTextFieldCpf.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###.###.###-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        jFormattedTextFieldCpf.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jFormattedTextFieldCpf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFormattedTextFieldCpfActionPerformed(evt);
            }
        });

        try {
            jFormattedTextFieldDDI.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("+##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        jFormattedTextFieldDDI.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        try {
            jFormattedTextFieldDDD.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(##)")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        jFormattedTextFieldDDD.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jButton1.setBackground(new java.awt.Color(92, 116, 123));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(195, 218, 231));
        jButton1.setText("LIMPAR CAMPOS");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButtonFotoProprietario1.setBackground(new java.awt.Color(92, 116, 123));
        jButtonFotoProprietario1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButtonFotoProprietario1.setForeground(new java.awt.Color(195, 218, 231));
        jButtonFotoProprietario1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icone/AdicionarImagem32px.png"))); // NOI18N
        jButtonFotoProprietario1.setText("<html>Selecionar<br><center>Foto<br>Proprietário</html>");
        jButtonFotoProprietario1.setBorder(null);
        jButtonFotoProprietario1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonFotoProprietario1ActionPerformed(evt);
            }
        });

        try {
            jFormattedTextFieldNumeroCNH.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###########")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        try {
            jFormattedTextFieldNumeroTelefone.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("#########")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        jFormattedTextFieldNumeroTelefone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFormattedTextFieldNumeroTelefoneActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2)
                    .addComponent(jLabel7)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6))
                .addGap(24, 24, 24)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jFormattedTextFieldDDI, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jFormattedTextFieldDDD, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jFormattedTextFieldNumeroTelefone))
                            .addComponent(jTextFieldNomeCompleto)
                            .addComponent(jTextFieldEmail)
                            .addComponent(jFormattedTextFieldCpf)
                            .addComponent(jFormattedTextFieldNumeroCNH, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jComboBoxCategoriaDeCNH, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButtonIncluir, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton1)
                            .addComponent(jButtonFotoProprietario1, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jLabelFotoProprietario, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(105, 105, 105))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonAlterar, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(299, 299, 299)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jButtonFotoCNH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                        .addComponent(jLabelFotoCNH, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(73, 73, 73))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(292, 292, 292))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButtonFotoProprietario1, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButtonFotoCNH, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(98, 98, 98))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(59, 59, 59)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabelFotoCNH, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabelFotoProprietario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(36, 36, 36)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonAlterar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonIncluir, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(545, 545, 545))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextFieldNomeCompleto, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addGap(15, 15, 15)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextFieldEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jFormattedTextFieldCpf, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(16, 16, 16)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jFormattedTextFieldNumeroTelefone, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jFormattedTextFieldDDD, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4)
                        .addComponent(jFormattedTextFieldDDI, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(jLabel5))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(jFormattedTextFieldNumeroCNH, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(16, 16, 16)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jComboBoxCategoriaDeCNH, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(614, 614, 614))
        );

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 3, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(195, 218, 231));
        jLabel1.setText("CADASTRAR PROPRIETÁRIO");

        jTable1.setAutoCreateRowSorter(true);
        jTable1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nome Completo", "Email", "CPF", "Telefone", "CNH", "Categoria"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
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
            jTable1.getColumnModel().getColumn(4).setResizable(false);
            jTable1.getColumnModel().getColumn(5).setResizable(false);
        }

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/LogoCarro.png"))); // NOI18N

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
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jLabel9)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(297, 297, 297)
                        .addComponent(jLabel1)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonVoltar)
                        .addContainerGap())))
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addGap(14, 14, 14)
                        .addComponent(jButtonVoltar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addComponent(jLabel9))
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 373, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(130, 130, 130))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAlterarActionPerformed
        try {
            //Instanciando cada atributo do objeto telefone com os dados informados nas textField
            telefone.setDDI(jFormattedTextFieldDDI.getText());
            telefone.setDDD(jFormattedTextFieldDDD.getText());
            telefone.setNumero(jFormattedTextFieldNumeroTelefone.getText());

            //Pegando o caminho das imagens
            String caminhoDoArquivoProprietario = proprietario.getFotoProprietario();
            String caminhoDoArquivoCNH = proprietario.getFotoCNH();

            //Instanciando cada atributo do objeto Proprietario
            Proprietario objProprietario = new Proprietario(jTextFieldNomeCompleto.getText().trim(), jTextFieldEmail.getText().trim(), jFormattedTextFieldCpf.getText(),
                    jFormattedTextFieldNumeroCNH.getText(), (String) jComboBoxCategoriaDeCNH.getSelectedItem(), telefone,
                    caminhoDoArquivoProprietario, caminhoDoArquivoCNH);

            //Chamando o Método Alterar do Controle passando o objeto como parâmetro
            proprietarioControle.alterar(objProprietario);

            //Instanciando o objeto 
            proprietario = new Proprietario();

            limparCampos();//Método limpar campos
            mostrarTabela();//Método mostrarTabela

            JOptionPane.showMessageDialog(null, "ALTERADO COM SUCESSO!", "ALTERADO", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, erro.getMessage());
        }

    }//GEN-LAST:event_jButtonAlterarActionPerformed

    private void jButtonBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBuscarActionPerformed
        try {

            proprietario = proprietarioControle.buscar(jFormattedTextFieldCpf.getText());
            jTextFieldNomeCompleto.setText(proprietario.getNomeCompleto());
            jTextFieldEmail.setText(proprietario.getEmail());
            jFormattedTextFieldNumeroCNH.setText(proprietario.getNumeroCNH());
            jComboBoxCategoriaDeCNH.setSelectedItem(proprietario.getCategoriaCNH());
            jFormattedTextFieldDDI.setText(proprietario.getTelefone().getDDI());
            jFormattedTextFieldDDD.setText(proprietario.getTelefone().getDDD());
            jFormattedTextFieldNumeroTelefone.setText(proprietario.getTelefone().getNumero());
            jFormattedTextFieldCpf.setEditable(false); // Define como não editável

            //Pegando a imagem do objeto e colocando na label
            ImageIcon imagem = new ImageIcon(proprietario.getFotoProprietario());
            Image image = imagem.getImage().getScaledInstance(jLabelFotoProprietario.getWidth(), jLabelFotoProprietario.getHeight(), Image.SCALE_SMOOTH);
            imagem = new ImageIcon(image);
            jLabelFotoProprietario.setIcon(imagem);

            //Pegando a imagem do objeto e colocando na label
            imagem = new ImageIcon(proprietario.getFotoCNH());
            image = imagem.getImage().getScaledInstance(jLabelFotoCNH.getWidth(), jLabelFotoCNH.getHeight(), Image.SCALE_SMOOTH);
            imagem = new ImageIcon(image);
            jLabelFotoCNH.setIcon(imagem);

            mostrarTabela();
            JOptionPane.showMessageDialog(null, "Nome: " + proprietario.getNomeCompleto() + "\n" + "Email: " + proprietario.getEmail() + "\n"
                    + "Número CNH: " + proprietario.getNumeroCNH() + "\n"
                    + "Categória CNH: " + proprietario.getCategoriaCNH() + "\n"
                    + "Número de telefone: " + proprietario.getTelefone().getDDI() + proprietario.getTelefone().getDDD()
                    + proprietario.getTelefone().getNumero(), "BUSCA CONCLUÍDA", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, erro.getMessage());
        }
    }//GEN-LAST:event_jButtonBuscarActionPerformed

    private void jButtonIncluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonIncluirActionPerformed
        try {
            telefone.setDDI(jFormattedTextFieldDDI.getText());
            telefone.setDDD(jFormattedTextFieldDDD.getText());
            telefone.setNumero(jFormattedTextFieldNumeroTelefone.getText());
            String caminhoDoArquivoProprietario = proprietario.getFotoProprietario();
            String caminhoDoArquivoCNH = proprietario.getFotoCNH();
            Proprietario objProprietario = new Proprietario(jTextFieldNomeCompleto.getText().trim(),
                    jTextFieldEmail.getText().trim(), jFormattedTextFieldCpf.getText(),
                    jFormattedTextFieldNumeroCNH.getText(), (String) jComboBoxCategoriaDeCNH.getSelectedItem(), telefone,
                    caminhoDoArquivoProprietario, caminhoDoArquivoCNH);

            proprietarioControle.incluir(objProprietario);

            proprietario = new Proprietario();

            limparCampos();
            mostrarTabela();
            //JOptionPane.showMessageDialog(null, "Selecione uma foto");
            //jButtonFotoProprietarioActionPerformed(evt);
            JOptionPane.showMessageDialog(null, "INCLUÍDO COM SUCESSO!", "INCLUÍDO", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, erro.getMessage());
        }

    }//GEN-LAST:event_jButtonIncluirActionPerformed

    private void jButtonFotoCNHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonFotoCNHActionPerformed
        //Biblioteca escolher arquivo
        JFileChooser escolherArquivo = new JFileChooser();
        escolherArquivo.setFileSelectionMode(JFileChooser.FILES_ONLY);
        //Selecionando a pasta que irá abrir
        File pastaEscolhida = new File("./src/fotosCNH");
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
                        proprietario.setFotoCNH(caminhoDoArquivo);

                        ImageIcon imagemIcon = new ImageIcon(caminhoDoArquivo);
                        Image image = imagemIcon.getImage().getScaledInstance(jLabelFotoCNH.getWidth(), jLabelFotoCNH.getHeight(), Image.SCALE_SMOOTH);
                        imagemIcon = new ImageIcon(image);
                        jLabelFotoCNH.setIcon(imagemIcon);
                    } else {
                        JOptionPane.showMessageDialog(this, "O tamanho do arquivo excede 10 MB", "Erro Foto Cnh", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "O arquivo selecionado não é uma imagem válida", "Erro Foto CNH", JOptionPane.ERROR_MESSAGE);
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Erro ao ler o arquivo de imagem", "Erro Foto CNH", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_jButtonFotoCNHActionPerformed

    private void jComboBoxCategoriaDeCNHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxCategoriaDeCNHActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxCategoriaDeCNHActionPerformed

    private void jTextFieldNomeCompletoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldNomeCompletoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldNomeCompletoActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        limparCampos();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked

        try {

            int row = jTable1.getSelectedRow();//Pegando a linha selecionada
            String nome = jTable1.getValueAt(row, 0).toString();//pegando nome coluna 1
            String email = jTable1.getValueAt(row, 1).toString();// pegando email coluna 2
            String cpf = jTable1.getValueAt(row, 2).toString();//pegando cpf coluna 3
            String telefone = jTable1.getValueAt(row, 3).toString();//pegando telefone coluna 4
            String cnh = jTable1.getValueAt(row, 4).toString();//pegando cnh coluna 5
            String categoria = jTable1.getValueAt(row, 5).toString();//pegando categoria coluna 6

            String ddi = telefone.substring(0, 3);//Dividindo o telefone e pegando só o ddi
            String ddd = telefone.substring(3, 7);//Dividindo o telefone e pegando só o ddd
            String numeroTelefone = telefone.substring(7);//Dividindo o telefone e pegando só o número

            //Colocando dados da table nos campos
            jTextFieldNomeCompleto.setText(nome);
            jTextFieldEmail.setText(email);
            jFormattedTextFieldCpf.setText(cpf);
            jFormattedTextFieldNumeroCNH.setText(cnh);
            jComboBoxCategoriaDeCNH.setSelectedItem(categoria);
            jFormattedTextFieldDDI.setText(ddi);
            jFormattedTextFieldDDD.setText(ddd);
            jFormattedTextFieldNumeroTelefone.setText(numeroTelefone);

            //Preenchendo o objeto para pegar caminho das imagens
            proprietario = proprietarioControle.buscar(cpf);

            //Colocando as imagens do proprietario e cnh nas label
            ImageIcon imagem = new ImageIcon(proprietario.getFotoProprietario());
            Image image = imagem.getImage().getScaledInstance(jLabelFotoProprietario.getWidth(), jLabelFotoProprietario.getHeight(), Image.SCALE_SMOOTH);
            imagem = new ImageIcon(image);
            jLabelFotoProprietario.setIcon(imagem);

            imagem = new ImageIcon(proprietario.getFotoCNH());
            image = imagem.getImage().getScaledInstance(jLabelFotoCNH.getWidth(), jLabelFotoCNH.getHeight(), Image.SCALE_SMOOTH);
            imagem = new ImageIcon(image);
            jLabelFotoCNH.setIcon(imagem);
            jFormattedTextFieldCpf.setEditable(false); // Define como não editável
        } catch (Exception ex) {
            Logger.getLogger(TelaProprietario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jTable1MouseClicked

    private void jButtonFotoProprietario1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonFotoProprietario1ActionPerformed
        JFileChooser escolherArquivo = new JFileChooser();
        escolherArquivo.setFileSelectionMode(JFileChooser.FILES_ONLY);

// Defina o diretório inicial para o JFileChooser
        File pastaEscolhida = new File("./src/fotosProprietarios");
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
                        proprietario.setFotoProprietario(caminhoDoArquivo);

                        ImageIcon imagemIcon = new ImageIcon(caminhoDoArquivo);
                        Image image = imagemIcon.getImage().getScaledInstance(jLabelFotoProprietario.getWidth(), jLabelFotoProprietario.getHeight(), Image.SCALE_SMOOTH);
                        imagemIcon = new ImageIcon(image);
                        jLabelFotoProprietario.setIcon(imagemIcon);
                    } else {
                        JOptionPane.showMessageDialog(this, "O tamanho do arquivo excede 10 MB", "Erro Foto Proprietário", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "O arquivo selecionado não é uma imagem válida", "Erro Foto Proprietário", JOptionPane.ERROR_MESSAGE);
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Erro ao ler o arquivo de imagem", "Erro Foto Proprietário", JOptionPane.ERROR_MESSAGE);
            }
        }    }//GEN-LAST:event_jButtonFotoProprietario1ActionPerformed

    private void jButtonVoltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonVoltarActionPerformed

        TelaPrincipal telaPrincipal = new TelaPrincipal();
        // Fechar a janela atual
        dispose();

        // Exibir a nova JFrame
        telaPrincipal.setVisible(true);
    }//GEN-LAST:event_jButtonVoltarActionPerformed

    private void jFormattedTextFieldNumeroTelefoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFormattedTextFieldNumeroTelefoneActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jFormattedTextFieldNumeroTelefoneActionPerformed

    private void jFormattedTextFieldCpfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFormattedTextFieldCpfActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jFormattedTextFieldCpfActionPerformed

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaProprietario().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButtonAlterar;
    private javax.swing.JButton jButtonBuscar;
    private javax.swing.JButton jButtonFotoCNH;
    private javax.swing.JButton jButtonFotoProprietario1;
    private javax.swing.JButton jButtonIncluir;
    private javax.swing.JButton jButtonVoltar;
    private javax.swing.JComboBox<String> jComboBoxCategoriaDeCNH;
    private javax.swing.JFormattedTextField jFormattedTextFieldCpf;
    private javax.swing.JFormattedTextField jFormattedTextFieldDDD;
    private javax.swing.JFormattedTextField jFormattedTextFieldDDI;
    private javax.swing.JFormattedTextField jFormattedTextFieldNumeroCNH;
    private javax.swing.JFormattedTextField jFormattedTextFieldNumeroTelefone;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelFotoCNH;
    private javax.swing.JLabel jLabelFotoProprietario;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextFieldEmail;
    private javax.swing.JTextField jTextFieldNomeCompleto;
    // End of variables declaration//GEN-END:variables
}
