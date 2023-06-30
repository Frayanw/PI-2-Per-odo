/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package visao;

import controle.GastosControle;
import static java.awt.Frame.MAXIMIZED_BOTH;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import modelos.Gastos;

/**
 *
 * @author Note
 */
public class TelaGastos extends javax.swing.JFrame {

    //Atributos
    Gastos gastos = new Gastos();
    GastosControle gastosControle = null;
    private String nomeDoArquivoNoDiscoProprietario = null;//Variável que vai armazenar o caminho do arquivo txt
    private String nomeDoArquivoNoDiscoCategotiaGastos = null; //Variável que vai armazenar o caminho do arquivo txt
    private String nomeDoArquivoNoDiscoVeiculo = null;//Variável que vai armazenar o caminho do arquivo txt

    public TelaGastos() {
        try {
            initComponents();
            nomeDoArquivoNoDiscoProprietario = "./src/bancodedadosPI/proprietarioBD.txt";// Variável do caminho recebendo o caminho
            nomeDoArquivoNoDiscoCategotiaGastos = "./src/bancodedadosPI/categoriaGastosBD.txt"; // Variável do caminho recebendo o caminho
            nomeDoArquivoNoDiscoVeiculo = "./src/bancodedadosPI/veiculoBD.txt";// Variável do caminho recebendo o caminho
            gastosControle = new GastosControle();
            comboBoxProprietario();
            comboBoxCategoriaGastos();
            this.setLocationRelativeTo(null);
            this.setExtendedState(MAXIMIZED_BOTH);

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
        } catch (Exception ex) {
            Logger.getLogger(TelaGastos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @SuppressWarnings("unchecked")
    //Métodos Mostrar Dados no arquivo txt
    private void mostrarTabela(String idVeiculo) {
        try {
            ArrayList<Gastos> listaDeGastos = gastosControle.obterListagem();
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.setNumRows(0);

            for (int pos = 0; pos < listaDeGastos.size(); pos++) {
                Gastos gastos = listaDeGastos.get(pos);
                String id = gastos.getId();
                String proprietario = colocarNomeProprietarioNaTable(gastos.getProprietario());
                String veiculo = colocarVeiculoNaTable((gastos.getVeiculo()));
                String categoriaGasto = colocarCategoriaGastoNaTable(gastos.getCategoriaGastos());
                String data = gastos.getData();
                String valor = "R$ " + gastos.getValor();
                if (idVeiculo.equalsIgnoreCase(gastos.getVeiculo())) {
                    Object[] rowData = {id, proprietario, veiculo, categoriaGasto,
                        data, valor};
                    model.addRow(rowData);
                }
            };
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, erro.getMessage(), "Método mostrarTabela", JOptionPane.ERROR_MESSAGE);
        }
    }

    //Método Limpar tela
    private void limparCampos() {
        gastos = new Gastos();
        jTextFieldId.setText("");
        jComboBoxNomeProprietario.setSelectedIndex(0);
        jComboBoxVeiculo.setSelectedIndex(0);
        jComboBoxCategoria.setSelectedIndex(0);
        jFormattedTextFieldData.setValue(null);
        jFormattedTextFieldValor.setText("");
        jLabelIconeIconeCategoria.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/AdicionarFotoVeiculo.png")));
        jTextFieldId.setEditable(true); // Define como editável

    }

    //Método para colocar os nomes dos proprietários na comboBox
    public void comboBoxProprietario() throws Exception {
        try {
            ArrayList<String> nomes = new ArrayList<>();
            FileReader fr = new FileReader(nomeDoArquivoNoDiscoProprietario);
            BufferedReader br = new BufferedReader(fr);
            String linha = "";
            while ((linha = br.readLine()) != null) {
                String vetorStr[] = linha.split(";");
                String nome = vetorStr[0];
                nomes.add(nome);
            }

            Collections.sort(nomes, String.CASE_INSENSITIVE_ORDER);

            for (int i = 0; i < nomes.size(); i++) {
                jComboBoxNomeProprietario.addItem(nomes.get(i));
            }
            br.close();

        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, erro.getMessage(), "Método comboBoxNome", JOptionPane.ERROR_MESSAGE);
        }

    }

    //Método para colocar os nomes das categoriasGastos na comboBox
    public void comboBoxCategoriaGastos() throws Exception {
        try {
            ArrayList<String> categoriaGastos = new ArrayList<>();
            FileReader fr = new FileReader(nomeDoArquivoNoDiscoCategotiaGastos);
            BufferedReader br = new BufferedReader(fr);
            String linha = "";
            while ((linha = br.readLine()) != null) {
                String vetorStr[] = linha.split(";");
                String descricao = vetorStr[1];
                categoriaGastos.add(descricao);
            }

            Collections.sort(categoriaGastos, String.CASE_INSENSITIVE_ORDER);

            for (int i = 0; i < categoriaGastos.size(); i++) {
                jComboBoxCategoria.addItem(categoriaGastos.get(i));
            }
            br.close();

        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, erro.getMessage(), "Método comboBoxMarca", JOptionPane.ERROR_MESSAGE);
        }

    }
    //Método para colocar os nomes das placas do veículo na comboBox

    public ArrayList<String> comboBoxVeiculos(String cpf) throws Exception {
        try {
            ArrayList<String> modelos = new ArrayList<>();
            FileReader fr = new FileReader(nomeDoArquivoNoDiscoVeiculo);
            BufferedReader br = new BufferedReader(fr);
            String linha = "";
            while ((linha = br.readLine()) != null) {
                String vetorStr[] = linha.split(";");
                String cpfAux = vetorStr[1];
                String placaVeiculo = vetorStr[5];

                if (cpf.equalsIgnoreCase(cpfAux)) {
                    modelos.add(placaVeiculo);
                }
            }
            br.close();
            return modelos;

        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, erro.getMessage(), "Método comboBoxModelo", JOptionPane.ERROR_MESSAGE);
        }
        return null;

    }

    //Método para colocar o nome do Proprietário na table  e não o cpf
    public String colocarNomeProprietarioNaTable(String CPF) throws Exception {
        try {
            FileReader fr = new FileReader(nomeDoArquivoNoDiscoProprietario);
            BufferedReader br = new BufferedReader(fr);
            String linha = "";
            while ((linha = br.readLine()) != null) {//A variavel "linha" recebe cada linha do arquivo txt lido, uma por vez
                String vetorStr[] = linha.split(";");//Criado o vetorStr e fazer ele dividir a linha por ";"
                //Atribuindo cada item em um espaço de memória do vetor F
                String nomeCompleto = vetorStr[0];
                String cpfAux = vetorStr[2];
                if (CPF.equalsIgnoreCase(cpfAux)) { //Se o Cpf digitado for igual ao cpf de uma linha será realizado os comandos abaixo
                    br.close();
                    return nomeCompleto;
                }
            }
            br.close();
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, erro.getMessage(), "Método colocarNomeProprietarioNaTable", JOptionPane.ERROR_MESSAGE);
        }
        return null;

    }

    //Método para pegar veiculo da ComboBox e transformar em id para pegar o id do veículo com outro método
    public String colocarVeiculoNaTable(String id) throws Exception {
        try {
            FileReader fr = new FileReader(nomeDoArquivoNoDiscoVeiculo);
            BufferedReader br = new BufferedReader(fr);
            String linha = "";
            while ((linha = br.readLine()) != null) {
                String vetorStr[] = linha.split(";");
                String idAux = vetorStr[0];
                String nomeVeiculo = vetorStr[5];
                if (id.equalsIgnoreCase(idAux)) {
                    br.close();
                    return nomeVeiculo;
                }

            }
            br.close();
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, erro.getMessage(), "Método pegarNomeModeloPeloId", JOptionPane.ERROR_MESSAGE);
        }
        return ("");

    }
    //Método para colocar o nome do modelo na table de não o id

    public String colocarCategoriaGastoNaTable(String id) throws Exception {
        try {
            FileReader fr = new FileReader(nomeDoArquivoNoDiscoCategotiaGastos);
            BufferedReader br = new BufferedReader(fr);
            String linha = "";
            while ((linha = br.readLine()) != null) {//A variavel "linha" recebe cada linha do arquivo txt lido, uma por vez
                String vetorStr[] = linha.split(";");//Criado o vetorStr e fazer ele dividir a linha por ";"
                //Atribuindo cada item em um espaço de memória do vetor F
                String idAux = vetorStr[0];
                String nomeCategoria = vetorStr[1];
                if (id.equalsIgnoreCase(idAux)) { //Se o Cpf digitado for igual ao cpf de uma linha será realizado os comandos abaixo
                    br.close();
                    return nomeCategoria;
                }
            }
            br.close();
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, erro.getMessage(), "Método colocarNomeCategoriaNaTable", JOptionPane.ERROR_MESSAGE);
        }
        return ("");

    }

    //Método para pegar o nome da ComboBox e transformar em cpf para salvar no arquivo txt
    public String pegarCpfProprietarioPeloNome(String nome) throws Exception {
        try {
            FileReader fr = new FileReader(nomeDoArquivoNoDiscoProprietario);
            BufferedReader br = new BufferedReader(fr);
            String linha = "";
            while ((linha = br.readLine()) != null) {
                String vetorStr[] = linha.split(";");
                String nomeAux = vetorStr[0];
                String cpf = vetorStr[2];
                if (nome.equalsIgnoreCase(nomeAux)) {
                    br.close();
                    return cpf;
                }

            }
            br.close();
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, erro.getMessage(), "Método pegarNomeProprietarioPeloCpf", JOptionPane.ERROR_MESSAGE);
        }
        return "";

    }

    //Pegar id do veículo pela placa da comboBox para aparecer a jtable de acordo com o veiculo
    public String pegarIdVeiculoPelaPlacaVeiculo(String placa) throws Exception {
        try {
            FileReader fr = new FileReader(nomeDoArquivoNoDiscoVeiculo);
            BufferedReader br = new BufferedReader(fr);
            String linha = "";
            while ((linha = br.readLine()) != null) {
                String vetorStr[] = linha.split(";");
                String id = vetorStr[0];
                String placaAux = vetorStr[5];

                if (placa.equalsIgnoreCase(placaAux)) {
                    return id;
                }
            }
            br.close();

        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, erro.getMessage(), "Método comboBoxModelo", JOptionPane.ERROR_MESSAGE);
        }
        return ("");

    }
    //Método para pegar a categoria de Gastos da ComboBox e transformar em id para salvar no arquivo txt

    public String pegarIdPeloNomeCategoria(String descricao) throws Exception {
        try {
            FileReader fr = new FileReader(nomeDoArquivoNoDiscoCategotiaGastos);
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
        return ("");

    }

    
    //Método pegar icone de categoria de gastos para colocar na label
    public String labelPegarLogoDaMarca(String categoriaGastos) throws Exception {
        try {
            FileReader fr = new FileReader(nomeDoArquivoNoDiscoCategotiaGastos);
            BufferedReader br = new BufferedReader(fr);
            String linha = "";
            while ((linha = br.readLine()) != null) {//A variavel "linha" recebe cada linha do arquivo txt lido, uma por vez
                String vetorStr[] = linha.split(";");//Criado o vetorStr e fazer ele dividir a linha por ";"
                //Atribuindo cada item em um espaço de memória do vetor F
                String categoriaGastosAux = vetorStr[1];
                String icone = vetorStr[2];
                if (categoriaGastos.equalsIgnoreCase(categoriaGastosAux)) {
                    br.close();
                    return icone;
                }
            }
            br.close();
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, erro.getMessage(), "Método labelPegarLogoDaMarca", JOptionPane.ERROR_MESSAGE);
        }
        return null;

    }
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jButtonLimparCampos = new javax.swing.JButton();
        jButtonIncluir = new javax.swing.JButton();
        jButtonCadastrarProprietario = new javax.swing.JButton();
        jButtonBuscar = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jComboBoxNomeProprietario = new javax.swing.JComboBox<>();
        jComboBoxVeiculo = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        jComboBoxCategoria = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jFormattedTextFieldData = new javax.swing.JFormattedTextField();
        jFormattedTextFieldValor = new javax.swing.JFormattedTextField();
        jTextFieldId = new javax.swing.JTextField();
        jButtonCadastrarVeiculo = new javax.swing.JButton();
        jButtonCadastrarCategoria = new javax.swing.JButton();
        jButtonAlterar = new javax.swing.JButton();
        jLabelIconeIconeCategoria = new javax.swing.JLabel();
        jButtonVoltar = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1481, 922));

        jPanel1.setBackground(new java.awt.Color(60, 95, 143));
        jPanel1.setMaximumSize(new java.awt.Dimension(1440, 900));
        jPanel1.setMinimumSize(new java.awt.Dimension(1440, 900));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 3, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(195, 218, 231));
        jLabel1.setText("GASTOS VEÍCULARES");

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/LogoCarro.png"))); // NOI18N

        jPanel2.setBackground(new java.awt.Color(182, 202, 221));
        jPanel2.setForeground(new java.awt.Color(182, 202, 221));

        jLabel5.setBackground(new java.awt.Color(33, 42, 70));
        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(33, 42, 70));
        jLabel5.setText("ID:");

        jLabel6.setBackground(new java.awt.Color(33, 42, 70));
        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(33, 42, 70));
        jLabel6.setText("VEÍCULO:");

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

        jButtonCadastrarProprietario.setBackground(new java.awt.Color(92, 116, 123));
        jButtonCadastrarProprietario.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButtonCadastrarProprietario.setForeground(new java.awt.Color(195, 218, 231));
        jButtonCadastrarProprietario.setText("CADASTRAR PROPRIETÁRIO");
        jButtonCadastrarProprietario.setBorder(null);
        jButtonCadastrarProprietario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCadastrarProprietarioActionPerformed(evt);
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

        jLabel10.setBackground(new java.awt.Color(33, 42, 70));
        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(33, 42, 70));
        jLabel10.setText("NOME DO PROPRIETÁRIO:");

        jComboBoxNomeProprietario.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jComboBoxNomeProprietario.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SELECIONE UM PROPRIETÁRIO" }));
        jComboBoxNomeProprietario.setBorder(null);
        jComboBoxNomeProprietario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxNomeProprietarioActionPerformed(evt);
            }
        });

        jComboBoxVeiculo.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jComboBoxVeiculo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SELECIONE UM VEÍCULO" }));
        jComboBoxVeiculo.setBorder(null);
        jComboBoxVeiculo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxVeiculoActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(33, 42, 70));
        jLabel7.setText("CATEGORIA:");

        jComboBoxCategoria.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jComboBoxCategoria.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SELECIONE UMA CATEGORIA" }));
        jComboBoxCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxCategoriaActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(33, 42, 70));
        jLabel8.setText("DATA:");

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(33, 42, 70));
        jLabel11.setText("VALOR:");

        try {
            jFormattedTextFieldData.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        jButtonCadastrarVeiculo.setBackground(new java.awt.Color(92, 116, 123));
        jButtonCadastrarVeiculo.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButtonCadastrarVeiculo.setForeground(new java.awt.Color(195, 218, 231));
        jButtonCadastrarVeiculo.setText("CADASTRAR VEÍCULO");
        jButtonCadastrarVeiculo.setBorder(null);
        jButtonCadastrarVeiculo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCadastrarVeiculoActionPerformed(evt);
            }
        });

        jButtonCadastrarCategoria.setBackground(new java.awt.Color(92, 116, 123));
        jButtonCadastrarCategoria.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButtonCadastrarCategoria.setForeground(new java.awt.Color(195, 218, 231));
        jButtonCadastrarCategoria.setText("CADASTRAR CATEGORIA DE GASTO");
        jButtonCadastrarCategoria.setBorder(null);
        jButtonCadastrarCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCadastrarCategoriaActionPerformed(evt);
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

        jLabelIconeIconeCategoria.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/AdicionarFotoVeiculo.png"))); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8)
                    .addComponent(jLabel10)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jComboBoxCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButtonCadastrarCategoria))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jComboBoxVeiculo, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButtonCadastrarVeiculo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jFormattedTextFieldData, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(1352, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jComboBoxNomeProprietario, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButtonCadastrarProprietario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(141, 141, 141))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jButtonIncluir, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(188, 188, 188)
                                .addComponent(jButtonAlterar, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jFormattedTextFieldValor, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jTextFieldId, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(498, 498, 498)
                                .addComponent(jButtonLimparCampos)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(239, 239, 239))))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(1003, 1003, 1003)
                    .addComponent(jLabelIconeIconeCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(1004, Short.MAX_VALUE)))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jButtonLimparCampos)
                    .addComponent(jTextFieldId, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBoxNomeProprietario, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(jButtonCadastrarProprietario, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jComboBoxVeiculo, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonCadastrarVeiculo, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jComboBoxCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonCadastrarCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(13, 13, 13)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jFormattedTextFieldData, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jLabel11))
                    .addComponent(jFormattedTextFieldValor, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonIncluir, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonAlterar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(68, 68, 68))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(127, 127, 127)
                    .addComponent(jLabelIconeIconeCategoria)
                    .addContainerGap(118, Short.MAX_VALUE)))
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
                "Id", "Proprietário", "Veículo", "Categoria de Gastos", "Data", "Valor"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.getTableHeader().setReorderingAllowed(false);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setResizable(false);
            jTable1.getColumnModel().getColumn(1).setResizable(false);
            jTable1.getColumnModel().getColumn(2).setResizable(false);
            jTable1.getColumnModel().getColumn(3).setResizable(false);
            jTable1.getColumnModel().getColumn(5).setResizable(false);
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
                        .addGap(281, 281, 281)
                        .addComponent(jLabel1)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonVoltar)
                        .addContainerGap())))
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(jLabel1))
                    .addComponent(jButtonVoltar, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 314, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 333, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(113, 113, 113))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 6, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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

    private void jComboBoxVeiculoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxVeiculoActionPerformed
        try {
            String comboBoxVeiculo = pegarIdVeiculoPelaPlacaVeiculo((String) jComboBoxVeiculo.getSelectedItem());
            mostrarTabela(comboBoxVeiculo);
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, erro.getMessage(), "Erro mostrarTabelo", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jComboBoxVeiculoActionPerformed

    private void jComboBoxNomeProprietarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxNomeProprietarioActionPerformed
        try {
            //Removo todos os itens da comboBoxNomeVeiculo começando do último item até o da posição(1)
            for (int i = jComboBoxVeiculo.getItemCount() - 1; i > 0; i--) {
                jComboBoxVeiculo.removeItemAt(i);
            }
            //Pego todas os veiculos e coloco na comboBox veiculos de acordo com o proprietario
            String cpfProprietario = pegarCpfProprietarioPeloNome((String) jComboBoxNomeProprietario.getSelectedItem());
            ArrayList<String> veiculos = new ArrayList<>();
            veiculos = comboBoxVeiculos(cpfProprietario);

            //Ordenar modelos de carro em ordem alfabética, esse "String.CASE_INSENSITIVE_ORDER" é para ordenar independente de ser letra maiusucula ou minuscula
            Collections.sort(veiculos, String.CASE_INSENSITIVE_ORDER);

            for (int i = 0; i < veiculos.size(); i++) {
                String veiculo = veiculos.get(i);
                jComboBoxVeiculo.addItem(veiculo);
            }

        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, erro.getMessage(), "Erro comboBoxMarca", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jComboBoxNomeProprietarioActionPerformed

    private void jButtonBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBuscarActionPerformed
        try {

            //Chamando o método buscar que retorna o objeto
            gastos = gastosControle.buscar(jTextFieldId.getText());

            //Colocando as infromações que foram buscadas e colocando nos campos
            jTextFieldId.setText(gastos.getId());

            jComboBoxNomeProprietario.setSelectedItem(colocarNomeProprietarioNaTable(gastos.getProprietario()));

            jComboBoxVeiculo.setSelectedItem(colocarVeiculoNaTable(gastos.getVeiculo()));

            jComboBoxCategoria.setSelectedItem(colocarCategoriaGastoNaTable(gastos.getCategoriaGastos()));

            jFormattedTextFieldData.setText(gastos.getData());

            jFormattedTextFieldValor.setText(Float.toString(gastos.getValor()));

            jTextFieldId.setEditable(false); // Define como não editável

            JOptionPane.showMessageDialog(null, "ID: " + gastos.getId() + "\n"
                    + "Nome Proprietário: " + colocarNomeProprietarioNaTable(gastos.getProprietario()) + "\n"
                    + "Veículo: " + colocarVeiculoNaTable(gastos.getVeiculo()) + "\n"
                    + "Categoria de Gasto: " + colocarCategoriaGastoNaTable(gastos.getCategoriaGastos()) + "\n"
                    + "Data: " + gastos.getData() + "\n"
                    + "Valor: "+"R$ " + gastos.getValor() + "\n",
                    "BUSCA CONCLUÍDA", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, erro.getMessage(), "Erro Buscar", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButtonBuscarActionPerformed

    private void jButtonCadastrarProprietarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCadastrarProprietarioActionPerformed
        TelaProprietario telaProprietario = new TelaProprietario();

        //Fechar tela atula
        dispose();

        //Abrir outra tela
        telaProprietario.setVisible(true);
    }//GEN-LAST:event_jButtonCadastrarProprietarioActionPerformed

    private void jButtonIncluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonIncluirActionPerformed
        try {

            String comboBoxNome = pegarCpfProprietarioPeloNome((String) jComboBoxNomeProprietario.getSelectedItem());
            String comboBoxVeiculo = pegarIdVeiculoPelaPlacaVeiculo((String) jComboBoxVeiculo.getSelectedItem());
            String comboBoxCategoriaGastos = pegarIdPeloNomeCategoria((String) jComboBoxCategoria.getSelectedItem());

            //Pegando o caminho do icone para colocar no txt
            String caminhoIconeCategoria = gastos.getIconeCategoria();

            float valor = Float.parseFloat(jFormattedTextFieldValor.getText().trim());

            Gastos objGastos = new Gastos(jTextFieldId.getText().trim(), comboBoxNome, comboBoxVeiculo,
                    comboBoxCategoriaGastos, jFormattedTextFieldData.getText().trim(),
                    valor, caminhoIconeCategoria);

            gastosControle.incluir(objGastos);

            gastos = new Gastos();
            limparCampos();
            JOptionPane.showMessageDialog(null, "INCLUÍDO COM SUCESSO!", "INCLUÍDO", JOptionPane.INFORMATION_MESSAGE);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Valor inválido! Insira um número válido.", "Erro Incluir", JOptionPane.ERROR_MESSAGE);

        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, erro.getMessage(), "Erro Incluir", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButtonIncluirActionPerformed

    private void jButtonLimparCamposActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLimparCamposActionPerformed
        limparCampos();
    }//GEN-LAST:event_jButtonLimparCamposActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked

        int row = jTable1.getSelectedRow();//A linha que selecionar pegar
        String id = jTable1.getValueAt(row, 0).toString();//Pegando id da coluna 1
        String nomeProprietario = jTable1.getValueAt(row, 1).toString();//Pegando nomeP da coluna 2
        String veiculo = jTable1.getValueAt(row, 2).toString();//Pegando marca da coluna 3
        String categoriaGasto = jTable1.getValueAt(row, 3).toString();//Pegando modelo da coluna 4
        String data = jTable1.getValueAt(row, 4).toString();//Pegando cor da coluna 5
        String valor = jTable1.getValueAt(row, 5).toString();//Pegando placa da coluna 6

        //Colocando os dados da table nos campos
        jTextFieldId.setText(id);
        jComboBoxNomeProprietario.setSelectedItem(nomeProprietario);
        jComboBoxVeiculo.setSelectedItem(veiculo);
        jComboBoxCategoria.setSelectedItem(categoriaGasto);
        jFormattedTextFieldData.setText(data);
        jFormattedTextFieldValor.setText(valor);
        jTextFieldId.setEditable(false);
    }//GEN-LAST:event_jTable1MouseClicked

    private void jButtonCadastrarVeiculoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCadastrarVeiculoActionPerformed
        TelaDeVeiculo telaDeVeiculo = new TelaDeVeiculo();

        //Fechar tela atula
        dispose();

        //Abrir outra tela
        telaDeVeiculo.setVisible(true);
    }//GEN-LAST:event_jButtonCadastrarVeiculoActionPerformed

    private void jButtonCadastrarCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCadastrarCategoriaActionPerformed
        TelaCategoriaGastos telaCategoriaGastos = new TelaCategoriaGastos();

        //Fechar tela atula
        dispose();

        //Abrir outra tela
        telaCategoriaGastos.setVisible(true);
    }//GEN-LAST:event_jButtonCadastrarCategoriaActionPerformed

    private void jButtonAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAlterarActionPerformed
        try {

            String comboBoxNome = pegarCpfProprietarioPeloNome((String) jComboBoxNomeProprietario.getSelectedItem());
            String comboBoxVeiculo = pegarIdVeiculoPelaPlacaVeiculo((String) jComboBoxVeiculo.getSelectedItem());
            String comboBoxCategoriaGastos = pegarIdPeloNomeCategoria((String) jComboBoxCategoria.getSelectedItem());

            //Pegando o caminho do icone para colocar no txt
            String caminhoIconeCategoria = gastos.getIconeCategoria();

            float valor = Float.parseFloat(jFormattedTextFieldValor.getText().trim());

            Gastos objGastos = new Gastos(jTextFieldId.getText().trim(), comboBoxNome, comboBoxVeiculo,
                    comboBoxCategoriaGastos, jFormattedTextFieldData.getText().trim(),
                    valor, caminhoIconeCategoria);

            gastosControle.alterar(objGastos);

            gastos = new Gastos();
            limparCampos();
            JOptionPane.showMessageDialog(null, "ALTERADO COM SUCESSO!", "INCLUÍDO", JOptionPane.INFORMATION_MESSAGE);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Valor inválido! Insira um número válido.", "Erro Alterar", JOptionPane.ERROR_MESSAGE);

        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, erro.getMessage(), "Erro Alterar", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButtonAlterarActionPerformed

    private void jComboBoxCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxCategoriaActionPerformed
        try {
            //Pego o nome da categoriaGastos e vou percorrer o txt da categoriaGastos para pegar o endereço do icone da categoriaGastos e colocar na label
            String itemSelecionado = (String) jComboBoxCategoria.getSelectedItem();
            
            String categoriaGasto = colocarCategoriaGastoNaTable(pegarIdPeloNomeCategoria(itemSelecionado));
            
            String label = labelPegarLogoDaMarca(categoriaGasto);
            
            if (itemSelecionado.equalsIgnoreCase(categoriaGasto)) {
            ImageIcon imagem = new ImageIcon(label);
            Image image = imagem.getImage().getScaledInstance(jLabelIconeIconeCategoria.getWidth(), jLabelIconeIconeCategoria.getHeight(), Image.SCALE_SMOOTH);
            imagem = new ImageIcon(image);
            jLabelIconeIconeCategoria.setIcon(imagem);

            } else {
            jLabelIconeIconeCategoria.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/AdicionarFotoVeiculo.png")));

            }
            //Coloco o endereço da foto do modelo no objeto veiculo para dps eu colocar dentro do construtor
            gastos.setIconeCategoria(label);
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, erro.getMessage(), "JComboBoxCategoria evento", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jComboBoxCategoriaActionPerformed

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
            java.util.logging.Logger.getLogger(TelaGastos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaGastos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaGastos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaGastos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaGastos().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAlterar;
    private javax.swing.JButton jButtonBuscar;
    private javax.swing.JButton jButtonCadastrarCategoria;
    private javax.swing.JButton jButtonCadastrarProprietario;
    private javax.swing.JButton jButtonCadastrarVeiculo;
    private javax.swing.JButton jButtonIncluir;
    private javax.swing.JButton jButtonLimparCampos;
    private javax.swing.JButton jButtonVoltar;
    private javax.swing.JComboBox<String> jComboBoxCategoria;
    private javax.swing.JComboBox<String> jComboBoxNomeProprietario;
    private javax.swing.JComboBox<String> jComboBoxVeiculo;
    private javax.swing.JFormattedTextField jFormattedTextFieldData;
    private javax.swing.JFormattedTextField jFormattedTextFieldValor;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelIconeIconeCategoria;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextFieldId;
    // End of variables declaration//GEN-END:variables
}
