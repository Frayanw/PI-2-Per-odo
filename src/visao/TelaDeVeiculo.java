package visao;

import controle.VeiculoControle;
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
import modelos.Veiculo;

public class TelaDeVeiculo extends javax.swing.JFrame {

    //Atributos
    Veiculo veiculo = new Veiculo();
    VeiculoControle veiculoControle = null;
    private String nomeDoArquivoNoDiscoProprietario = null;//Variável que vai armazenar o caminho do arquivo txt
    private String nomeDoArquivoNoDiscoMarca = null;//Variável que vai armazenar o caminho do arquivo txt
    private String nomeDoArquivoNoDiscoModelo = null;

    //Construtor
    public TelaDeVeiculo() {
        try {
            initComponents();
            veiculoControle = new VeiculoControle();
            nomeDoArquivoNoDiscoProprietario = "./src/bancodedadosPI/proprietarioBD.txt";// Variável do caminho recebendo o caminho
            nomeDoArquivoNoDiscoMarca = "./src/bancodedadosPI/marcaBD.txt";
            nomeDoArquivoNoDiscoModelo = "./src/bancodedadosPI/modeloBD.txt";
            jButtonVoltar.setFocusPainted(false); // Oculta a aparência de seleção do botão
            comboBoxNome();
            comboBoxMarca();
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
        } catch (Exception ex) {
            Logger.getLogger(TelaDeVeiculo.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @SuppressWarnings("unchecked")
    //Métodos Mostrar Dados no arquivo txt
    private void mostrarTabela() {
        try {
            ArrayList<Veiculo> listaDeVeiculo = veiculoControle.obterListagem();
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.setNumRows(0);

            for (int pos = 0; pos < listaDeVeiculo.size(); pos++) {
                Veiculo veiculo = listaDeVeiculo.get(pos);
                String id = veiculo.getId();
                String nomeProprietario = colocarNomeProprietarioNaTable(veiculo.getNomeProprietario());
                String nomeMarca = colocarNomeMarcaNaTable(veiculo.getMarca());
                String modelo = colocarNomeModeloNaTable(veiculo.getModelo());
                String cor = veiculo.getCor();
                String placa = veiculo.getPlaca();
                String ano = veiculo.getAno();
                String tipoCombustivel = veiculo.getTipoCombustivel();

                Object[] rowData = {id, nomeProprietario, nomeMarca, modelo, cor, placa, ano, tipoCombustivel};
                model.addRow(rowData);
            };
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, erro.getMessage(), "Método mostrarTabela", JOptionPane.ERROR_MESSAGE);
        }
    }

    //Método Limpar tela
    private void limparCampos() {
        veiculo = new Veiculo();
        jTextFieldId.setText("");
        jComboBoxNomeProprietario.setSelectedIndex(0);
        jComboBoxNomeMarca.setSelectedIndex(0);
        jComboBoxNomeModelo.setSelectedIndex(0);
        jTextFieldCor.setText("");
        jFormattedTextFieldPlaca.setValue(null);
        jFormattedTextFieldAno.setValue(null);
        jComboBoxTipoCombustivel.setSelectedIndex(0);
        jLabelFotoMarca.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/AdicionarFoto.png")));
        jLabelFotoModelo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/AdicionarFotoVeiculo.png")));
        jTextFieldId.setEditable(true); // Define como editável
    }

    //Método para colocar os nomes dos proprietários na comboBox
    public void comboBoxNome() throws Exception {
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

    //Método para colocar os nomes das marcas na comboBox
    public void comboBoxMarca() throws Exception {
        try {
            ArrayList<String> marcas = new ArrayList<>();
            FileReader fr = new FileReader(nomeDoArquivoNoDiscoMarca);
            BufferedReader br = new BufferedReader(fr);
            String linha = "";
            while ((linha = br.readLine()) != null) {
                String vetorStr[] = linha.split(";");
                String descricao = vetorStr[1];
                marcas.add(descricao);
            }

            Collections.sort(marcas, String.CASE_INSENSITIVE_ORDER);

            for (int i = 0; i < marcas.size(); i++) {
                jComboBoxNomeMarca.addItem(marcas.get(i));
            }
            br.close();

        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, erro.getMessage(), "Método comboBoxMarca", JOptionPane.ERROR_MESSAGE);
        }

    }

    //Método para colocar os nomes dos modelos na comboBox
    public ArrayList comboBoxModelo(String idMarca) throws Exception {
        try {
            ArrayList<String> modelos = new ArrayList<>();
            FileReader fr = new FileReader(nomeDoArquivoNoDiscoModelo);
            BufferedReader br = new BufferedReader(fr);
            String linha = "";
            while ((linha = br.readLine()) != null) {
                String vetorStr[] = linha.split(";");
                String descricao = vetorStr[1];
                String idMarcaAux = vetorStr[2];

                if (idMarca.equalsIgnoreCase(idMarcaAux)) {
                    modelos.add(descricao);
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

    //Método para colocar o nome da marca na table de não o id
    public String colocarNomeMarcaNaTable(String id) throws Exception {
        try {
            FileReader fr = new FileReader(nomeDoArquivoNoDiscoMarca);
            BufferedReader br = new BufferedReader(fr);
            String linha = "";
            while ((linha = br.readLine()) != null) {//A variavel "linha" recebe cada linha do arquivo txt lido, uma por vez
                String vetorStr[] = linha.split(";");//Criado o vetorStr e fazer ele dividir a linha por ";"
                //Atribuindo cada item em um espaço de memória do vetor F
                String idAux = vetorStr[0];
                String nomeMarca = vetorStr[1];
                if (id.equalsIgnoreCase(idAux)) { //Se o id digitado for igual ao cpf de uma linha será realizado os comandos abaixo
                    br.close();
                    return nomeMarca;
                }
            }
            br.close();
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, erro.getMessage(), "Método colocarNomeMarcaNaTable", JOptionPane.ERROR_MESSAGE);
        }
        return ("");

    }

    //Método para colocar o nome do modelo na table de não o id
    public String colocarNomeModeloNaTable(String id) throws Exception {
        try {
            FileReader fr = new FileReader(nomeDoArquivoNoDiscoModelo);
            BufferedReader br = new BufferedReader(fr);
            String linha = "";
            while ((linha = br.readLine()) != null) {//A variavel "linha" recebe cada linha do arquivo txt lido, uma por vez
                String vetorStr[] = linha.split(";");//Criado o vetorStr e fazer ele dividir a linha por ";"
                //Atribuindo cada item em um espaço de memória do vetor F
                String idAux = vetorStr[0];
                String nomeMarca = vetorStr[1];
                if (id.equalsIgnoreCase(idAux)) { //Se o Cpf digitado for igual ao cpf de uma linha será realizado os comandos abaixo
                    br.close();
                    return nomeMarca;
                }
            }
            br.close();
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, erro.getMessage(), "Método colocarNomeModeloNaTable", JOptionPane.ERROR_MESSAGE);
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
        return null;

    }

    //Método para pegar a marca da ComboBox e transformar em id para salvar no arquivo txt
    public String pegarIdPeloNomeMarca(String descricao) throws Exception {
        try {
            FileReader fr = new FileReader(nomeDoArquivoNoDiscoMarca);
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

    //Método para pegar o modelo da ComboBox e transformar em id para salvar no arquivo txt
    public String pegarNomeModeloPeloId(String descricao) throws Exception {
        try {
            FileReader fr = new FileReader(nomeDoArquivoNoDiscoModelo);
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
            JOptionPane.showMessageDialog(null, erro.getMessage(), "Método pegarNomeModeloPeloId", JOptionPane.ERROR_MESSAGE);
        }
        return ("");

    }

    //Método pegar foto da marca para colocar na label
    public String labelPegarLogoDaMarca(String marca) throws Exception {
        try {
            FileReader fr = new FileReader(nomeDoArquivoNoDiscoMarca);
            BufferedReader br = new BufferedReader(fr);
            String linha = "";
            while ((linha = br.readLine()) != null) {//A variavel "linha" recebe cada linha do arquivo txt lido, uma por vez
                String vetorStr[] = linha.split(";");//Criado o vetorStr e fazer ele dividir a linha por ";"
                //Atribuindo cada item em um espaço de memória do vetor F
                String nomeMarca = vetorStr[1];
                String logo = vetorStr[2];
                if (marca.equalsIgnoreCase(nomeMarca)) { //Se o Cpf digitado for igual ao cpf de uma linha será realizado os comandos abaixo
                    br.close();
                    return logo;
                }
            }
            br.close();
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, erro.getMessage(), "Método labelPegarLogoDaMarca", JOptionPane.ERROR_MESSAGE);
        }
        return null;

    }
    //Método pegar foto do modelo para colocar na label

    public String labelPegarFotoModelo(String modelo) throws Exception {
        try {
            FileReader fr = new FileReader(nomeDoArquivoNoDiscoModelo);
            BufferedReader br = new BufferedReader(fr);
            String linha = "";
            while ((linha = br.readLine()) != null) {//A variavel "linha" recebe cada linha do arquivo txt lido, uma por vez
                String vetorStr[] = linha.split(";");//Criado o vetorStr e fazer ele dividir a linha por ";"
                //Atribuindo cada item em um espaço de memória do vetor F
                String nomeModelo = vetorStr[1];
                String fotoModelo = vetorStr[4];
                if (modelo.equalsIgnoreCase(nomeModelo)) { //Se o Cpf digitado for igual ao cpf de uma linha será realizado os comandos abaixo
                    br.close();
                    return fotoModelo;
                }
            }
            br.close();
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, erro.getMessage(), "Método labelPegarFotoModelo", JOptionPane.ERROR_MESSAGE);
        }
        return null;

    }
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jComboBoxNomeProprietario = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jComboBoxNomeMarca = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jComboBoxNomeModelo = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jTextFieldCor = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jButtonLimparCampos = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jComboBoxTipoCombustivel = new javax.swing.JComboBox<>();
        jButtonIncluir = new javax.swing.JButton();
        jButtonAlterar = new javax.swing.JButton();
        jButtonBuscar = new javax.swing.JButton();
        jLabelFotoMarca = new javax.swing.JLabel();
        jLabelFotoModelo = new javax.swing.JLabel();
        jFormattedTextFieldPlaca = new javax.swing.JFormattedTextField();
        jLabel10 = new javax.swing.JLabel();
        jFormattedTextFieldAno = new javax.swing.JFormattedTextField();
        jTextFieldId = new javax.swing.JTextField();
        jButtonCadastrarProprietario = new javax.swing.JButton();
        jButtonCadastrarMarca = new javax.swing.JButton();
        jButtonCadastrarModelo = new javax.swing.JButton();
        jButtonVoltar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1440, 900));

        jPanel1.setBackground(new java.awt.Color(60, 95, 143));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 3, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(195, 218, 231));
        jLabel1.setText("CADASTRAR VEÍCULO");

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/LogoCarro.png"))); // NOI18N

        jPanel2.setBackground(new java.awt.Color(182, 202, 221));
        jPanel2.setForeground(new java.awt.Color(182, 202, 221));

        jLabel2.setBackground(new java.awt.Color(33, 42, 70));
        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(33, 42, 70));
        jLabel2.setText("MARCA:");

        jComboBoxNomeProprietario.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jComboBoxNomeProprietario.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SELECIONE UM PROPRIETÁRIO" }));
        jComboBoxNomeProprietario.setBorder(null);
        jComboBoxNomeProprietario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxNomeProprietarioActionPerformed(evt);
            }
        });

        jLabel3.setBackground(new java.awt.Color(33, 42, 70));
        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(33, 42, 70));
        jLabel3.setText("ID:");

        jComboBoxNomeMarca.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jComboBoxNomeMarca.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SELECIONE UMA MARCA" }));
        jComboBoxNomeMarca.setBorder(null);
        jComboBoxNomeMarca.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBoxNomeMarcaItemStateChanged(evt);
            }
        });
        jComboBoxNomeMarca.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboBoxNomeMarcaMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jComboBoxNomeMarcaMousePressed(evt);
            }
        });
        jComboBoxNomeMarca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxNomeMarcaActionPerformed(evt);
            }
        });

        jLabel4.setBackground(new java.awt.Color(33, 42, 70));
        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(33, 42, 70));
        jLabel4.setText("MODELO:");

        jComboBoxNomeModelo.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jComboBoxNomeModelo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SELECIONE UM MODELO" }));
        jComboBoxNomeModelo.setBorder(null);
        jComboBoxNomeModelo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBoxNomeModeloItemStateChanged(evt);
            }
        });
        jComboBoxNomeModelo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxNomeModeloActionPerformed(evt);
            }
        });

        jLabel5.setBackground(new java.awt.Color(33, 42, 70));
        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(33, 42, 70));
        jLabel5.setText("COR:");

        jTextFieldCor.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTextFieldCor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldCorActionPerformed(evt);
            }
        });

        jLabel6.setBackground(new java.awt.Color(33, 42, 70));
        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(33, 42, 70));
        jLabel6.setText("PLACA:");

        jLabel7.setBackground(new java.awt.Color(33, 42, 70));
        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(33, 42, 70));
        jLabel7.setText("ANO:");

        jButtonLimparCampos.setBackground(new java.awt.Color(92, 116, 123));
        jButtonLimparCampos.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButtonLimparCampos.setForeground(new java.awt.Color(195, 218, 231));
        jButtonLimparCampos.setText("LIMPAR CAMPOS");
        jButtonLimparCampos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLimparCamposActionPerformed(evt);
            }
        });

        jLabel8.setBackground(new java.awt.Color(33, 42, 70));
        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(33, 42, 70));
        jLabel8.setText("TIPO DE COMBUSTÍVEL:");

        jComboBoxTipoCombustivel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jComboBoxTipoCombustivel.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SELECIONE UM COMBUSTÍVEL", "GASOLINA", "ETANOL", "FLEX", "DIESEL", "HÍBRIDO", "ELÉTRICO" }));
        jComboBoxTipoCombustivel.setBorder(null);
        jComboBoxTipoCombustivel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxTipoCombustivelActionPerformed(evt);
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

        jLabelFotoMarca.setBackground(new java.awt.Color(255, 255, 255));
        jLabelFotoMarca.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/AdicionarFoto.png"))); // NOI18N

        jLabelFotoModelo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/AdicionarFotoVeiculo.png"))); // NOI18N

        try {
            jFormattedTextFieldPlaca.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("UUU-#A##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        jFormattedTextFieldPlaca.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jFormattedTextFieldPlaca.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jFormattedTextFieldPlacaKeyTyped(evt);
            }
        });

        jLabel10.setBackground(new java.awt.Color(33, 42, 70));
        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(33, 42, 70));
        jLabel10.setText("NOME DO PROPRIETÁRIO:");

        try {
            jFormattedTextFieldAno.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        jButtonCadastrarProprietario.setBackground(new java.awt.Color(92, 116, 123));
        jButtonCadastrarProprietario.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButtonCadastrarProprietario.setForeground(new java.awt.Color(195, 218, 231));
        jButtonCadastrarProprietario.setText("CADASTRAR PROPRIETARIO");
        jButtonCadastrarProprietario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCadastrarProprietarioActionPerformed(evt);
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

        jButtonCadastrarModelo.setBackground(new java.awt.Color(92, 116, 123));
        jButtonCadastrarModelo.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButtonCadastrarModelo.setForeground(new java.awt.Color(195, 218, 231));
        jButtonCadastrarModelo.setText("CADASTRAR MODELO");
        jButtonCadastrarModelo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCadastrarModeloActionPerformed(evt);
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
                            .addComponent(jLabel2)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8)
                            .addComponent(jLabel3))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(50, 50, 50)
                                        .addComponent(jButtonIncluir, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(31, 31, 31)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jTextFieldCor, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jFormattedTextFieldPlaca, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jFormattedTextFieldAno, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jComboBoxTipoCombustivel, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(31, 31, 31)
                                        .addComponent(jTextFieldId, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(31, 31, 31)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jComboBoxNomeMarca, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jComboBoxNomeModelo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(56, 56, 56))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addGap(12, 12, 12)
                        .addComponent(jComboBoxNomeProprietario, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jButtonLimparCampos)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jButtonAlterar, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(jButtonCadastrarProprietario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jButtonCadastrarMarca, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jButtonCadastrarModelo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(28, 28, 28)
                                        .addComponent(jLabelFotoMarca, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabelFotoModelo, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addContainerGap())
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jButtonBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(252, 252, 252))))))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextFieldId, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonLimparCampos))
                .addGap(13, 13, 13)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBoxNomeProprietario, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(jButtonCadastrarProprietario))
                .addGap(3, 3, 3)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(9, 9, 9)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel2)
                                    .addComponent(jComboBoxNomeMarca, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButtonCadastrarMarca))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jComboBoxNomeModelo, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4)
                                    .addComponent(jButtonCadastrarModelo))
                                .addGap(12, 12, 12)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jTextFieldCor, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jFormattedTextFieldPlaca, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel7)
                                    .addComponent(jFormattedTextFieldAno, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jComboBoxTipoCombustivel, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel8)))
                            .addComponent(jLabelFotoMarca, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                                .addComponent(jButtonIncluir, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(42, Short.MAX_VALUE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jButtonBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButtonAlterar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabelFotoModelo, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
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
                "ID", "Nome Proprietário", "Marca", "Modelo", "Cor", "Placa", "Ano", "Tipo Combustível"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
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
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setResizable(false);
            jTable1.getColumnModel().getColumn(1).setResizable(false);
            jTable1.getColumnModel().getColumn(2).setResizable(false);
            jTable1.getColumnModel().getColumn(3).setResizable(false);
            jTable1.getColumnModel().getColumn(4).setResizable(false);
            jTable1.getColumnModel().getColumn(5).setResizable(false);
            jTable1.getColumnModel().getColumn(7).setResizable(false);
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
                        .addGap(376, 376, 376)
                        .addComponent(jLabel1)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonVoltar)
                        .addContainerGap())))
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addGap(14, 14, 14))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonVoltar)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(294, 294, 294))
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 875, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

    private void jButtonBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBuscarActionPerformed
        try {

            //Chamando o método buscar que retorna o objeto
            veiculo = veiculoControle.buscar(jTextFieldId.getText());

            //Colocando as infromações que foram buscadas e colocando nos campos
            jTextFieldId.setText(veiculo.getId());

            jComboBoxNomeProprietario.setSelectedItem(colocarNomeProprietarioNaTable(veiculo.getNomeProprietario()));

            jComboBoxNomeMarca.setSelectedItem(colocarNomeMarcaNaTable(veiculo.getMarca()));

            jComboBoxNomeModelo.setSelectedItem(colocarNomeModeloNaTable(veiculo.getModelo()));

            jTextFieldCor.setText(veiculo.getCor());

            jFormattedTextFieldPlaca.setText(veiculo.getPlaca());

            jFormattedTextFieldAno.setText(veiculo.getAno());

            jComboBoxTipoCombustivel.setSelectedItem(veiculo.getTipoCombustivel());

            jTextFieldId.setEditable(false); // Define como não editável

            //Pegando a imagem do objeto e colocando na label
            ImageIcon imagem = new ImageIcon(veiculo.getFotoMarca());
            Image image = imagem.getImage().getScaledInstance(jLabelFotoMarca.getWidth(), jLabelFotoMarca.getHeight(), Image.SCALE_SMOOTH);
            imagem = new ImageIcon(image);
            jLabelFotoMarca.setIcon(imagem);

            //Pegando a imagem do objeto e colocando na label
            imagem = new ImageIcon(veiculo.getFotoModelo());
            image = imagem.getImage().getScaledInstance(jLabelFotoModelo.getWidth(), jLabelFotoModelo.getHeight(), Image.SCALE_SMOOTH);
            imagem = new ImageIcon(image);
            jLabelFotoModelo.setIcon(imagem);

            mostrarTabela();
            JOptionPane.showMessageDialog(null, "ID: " + veiculo.getId() + "\n"
                    + "Nome Proprietário: " + colocarNomeProprietarioNaTable(veiculo.getNomeProprietario()) + "\n"
                    + "Marca: " + colocarNomeMarcaNaTable(veiculo.getMarca()) + "\n"
                    + "Modelo: " + colocarNomeModeloNaTable(veiculo.getModelo()) + "\n"
                    + "Cor: " + veiculo.getCor() + "\n"
                    + "Placa: " + veiculo.getPlaca() + "\n"
                    + "Ano: " + veiculo.getAno() + "\n"
                    + "Tipo De Combustível: " + veiculo.getTipoCombustivel(), "BUSCA CONCLUÍDA", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, erro.getMessage(), "Erro Buscar", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButtonBuscarActionPerformed

    private void jButtonAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAlterarActionPerformed
        try {
            //Pegando a informação no comboBox selecionada e transformando em cpf ou id para salvar no txt
            String comboBoxNome = pegarCpfProprietarioPeloNome((String) jComboBoxNomeProprietario.getSelectedItem());
            String comboBoxMarca = pegarIdPeloNomeMarca((String) jComboBoxNomeMarca.getSelectedItem());
            String comboBoxModelo = pegarNomeModeloPeloId((String) jComboBoxNomeModelo.getSelectedItem());

            //Pegando o caminho da foto para colocar no txt
            String caminhoFotoMarca = veiculo.getFotoMarca();
            String caminhoFotoModelo = veiculo.getFotoModelo();

            //Preenchendo o objeto
            Veiculo objVeiculo = new Veiculo(jTextFieldId.getText().trim(), comboBoxNome, comboBoxMarca, comboBoxModelo,
                    jTextFieldCor.getText().trim(),
                    jFormattedTextFieldPlaca.getText().toUpperCase().trim(),
                    jFormattedTextFieldAno.getText().trim(),
                    (String) jComboBoxTipoCombustivel.getSelectedItem(),
                    caminhoFotoMarca, caminhoFotoModelo);

            //Ir para controle para fazer as validações necessárias
            veiculoControle.alterar(objVeiculo);

            //Limoando o objeto
            veiculo = new Veiculo();

            limparCampos();
            mostrarTabela();

            JOptionPane.showMessageDialog(null, "ALTERADO COM SUCESSO!", "ALTERADO", JOptionPane.INFORMATION_MESSAGE);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Entrada inválida para o ano. Por favor, insira um valor numérico de 4 dígitos.", "Ano Inválido", JOptionPane.ERROR_MESSAGE);
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, erro.getMessage(), "Erro Alterar", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButtonAlterarActionPerformed

    private void jButtonIncluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonIncluirActionPerformed
        try {

            String comboBoxNome = pegarCpfProprietarioPeloNome((String) jComboBoxNomeProprietario.getSelectedItem());
            String comboBoxMarca = pegarIdPeloNomeMarca((String) jComboBoxNomeMarca.getSelectedItem());
            String comboBoxModelo = pegarNomeModeloPeloId((String) jComboBoxNomeModelo.getSelectedItem());
            String caminhoFotoMarca = veiculo.getFotoMarca();
            String caminhoFotoModelo = veiculo.getFotoModelo();

            Veiculo objVeiculo = new Veiculo(jTextFieldId.getText().trim(), comboBoxNome, comboBoxMarca, comboBoxModelo,
                    jTextFieldCor.getText().trim(),
                    jFormattedTextFieldPlaca.getText().toUpperCase().trim(),
                    jFormattedTextFieldAno.getText().trim(),
                    (String) jComboBoxTipoCombustivel.getSelectedItem(),
                    caminhoFotoMarca, caminhoFotoModelo);

            veiculoControle.incluir(objVeiculo);

            veiculo = new Veiculo();

            limparCampos();
            mostrarTabela();

            JOptionPane.showMessageDialog(null, "INCLUÍDO COM SUCESSO!", "INCLUÍDO", JOptionPane.INFORMATION_MESSAGE);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Entrada inválida para o ano. Por favor, insira um valor numérico de 4 dígitos.", "Ano Inválido", JOptionPane.ERROR_MESSAGE);
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, erro.getMessage(), "Erro Incluir", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButtonIncluirActionPerformed

    private void jComboBoxTipoCombustivelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxTipoCombustivelActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxTipoCombustivelActionPerformed

    private void jButtonLimparCamposActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLimparCamposActionPerformed
        limparCampos();
    }//GEN-LAST:event_jButtonLimparCamposActionPerformed

    private void jTextFieldCorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldCorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldCorActionPerformed

    private void jComboBoxNomeModeloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxNomeModeloActionPerformed

        try {
            //Pego o nome da marca e vou percorrer o txt do marca para pegar o endereço da foto da marca e colocar na label
            String itemSelecionado = (String) jComboBoxNomeModelo.getSelectedItem();
            String modelo = colocarNomeModeloNaTable(pegarNomeModeloPeloId(itemSelecionado));
            String label = labelPegarFotoModelo(modelo);
            if (itemSelecionado.equalsIgnoreCase(modelo)) {
                ImageIcon imagem = new ImageIcon(label);
                Image image = imagem.getImage().getScaledInstance(jLabelFotoModelo.getWidth(), jLabelFotoModelo.getHeight(), Image.SCALE_SMOOTH);
                imagem = new ImageIcon(image);
                jLabelFotoModelo.setIcon(imagem);

            } else {
                jLabelFotoModelo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/AdicionarFotoVeiculo.png")));

            }

            //Coloco o endereço da foto da marca no objeto veiculo para dps eu colocar dentro do construtor
            veiculo.setFotoModelo(label);
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, erro.getMessage(), "Erro ComboBoxModelo", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jComboBoxNomeModeloActionPerformed

    private void jComboBoxNomeMarcaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxNomeMarcaActionPerformed
        try {
            //Removo todos os itens da comboBoxNomeModelo começando do último item até o da posição(1)
            for (int i = jComboBoxNomeModelo.getItemCount() - 1; i > 0; i--) {
                jComboBoxNomeModelo.removeItemAt(i);
            }
            //Pego todas os modelos de carro e coloco na comboBox modelo de acordo com a marca
            String idMarca = pegarIdPeloNomeMarca((String) jComboBoxNomeMarca.getSelectedItem());
            ArrayList<String> modelos = new ArrayList<>();
            modelos = comboBoxModelo(idMarca);

            //Ordenar modelos de carro em ordem alfabética, esse "String.CASE_INSENSITIVE_ORDER" é para ordenar independente de ser letra maiusucula ou minuscula
            Collections.sort(modelos, String.CASE_INSENSITIVE_ORDER);

            for (int i = 0; i < modelos.size(); i++) {
                String modelo = modelos.get(i);
                jComboBoxNomeModelo.addItem(modelo);
            }

            //Pego o nome do modelo e vou percorrer o txt do modelo para pegar o endereço da foto do modelo e colocar na label
            String itemSelecionado = (String) jComboBoxNomeMarca.getSelectedItem();
            String marca = colocarNomeMarcaNaTable(pegarIdPeloNomeMarca(itemSelecionado));
            String label = labelPegarLogoDaMarca(marca);
            if (itemSelecionado.equalsIgnoreCase(marca)) {
                ImageIcon imagem = new ImageIcon(label);
                Image image = imagem.getImage().getScaledInstance(jLabelFotoMarca.getWidth(), jLabelFotoMarca.getHeight(), Image.SCALE_SMOOTH);
                imagem = new ImageIcon(image);
                jLabelFotoMarca.setIcon(imagem);

            } else {
                jLabelFotoMarca.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/AdicionarFoto.png")));

            }

            //Coloco o endereço da foto do modelo no objeto veiculo para dps eu colocar dentro do construtor
            veiculo.setFotoMarca(label);
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, erro.getMessage(), "Erro comboBoxMarca", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jComboBoxNomeMarcaActionPerformed

    private void jComboBoxNomeProprietarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxNomeProprietarioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxNomeProprietarioActionPerformed

    private void jFormattedTextFieldPlacaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jFormattedTextFieldPlacaKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_jFormattedTextFieldPlacaKeyTyped

    private void jComboBoxNomeMarcaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBoxNomeMarcaMouseClicked

    }//GEN-LAST:event_jComboBoxNomeMarcaMouseClicked

    private void jComboBoxNomeMarcaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBoxNomeMarcaItemStateChanged

    }//GEN-LAST:event_jComboBoxNomeMarcaItemStateChanged

    private void jComboBoxNomeMarcaMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBoxNomeMarcaMousePressed

    }//GEN-LAST:event_jComboBoxNomeMarcaMousePressed

    private void jComboBoxNomeModeloItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBoxNomeModeloItemStateChanged


    }//GEN-LAST:event_jComboBoxNomeModeloItemStateChanged

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked

        int row = jTable1.getSelectedRow();//A linha que selecionar pegar
        String id = jTable1.getValueAt(row, 0).toString();//Pegando id da coluna 1
        String nomeProprietario = jTable1.getValueAt(row, 1).toString();//Pegando nomeP da coluna 2
        String marca = jTable1.getValueAt(row, 2).toString();//Pegando marca da coluna 3
        String modelo = jTable1.getValueAt(row, 3).toString();//Pegando modelo da coluna 4
        String cor = jTable1.getValueAt(row, 4).toString();//Pegando cor da coluna 5
        String placa = jTable1.getValueAt(row, 5).toString();//Pegando placa da coluna 6
        String ano = jTable1.getValueAt(row, 6).toString();//Pegando ano da coluna 7
        String tipoCombustivel = jTable1.getValueAt(row, 7).toString();//Pegando tipoC da coluna 8

        //Colocando os dados da table nos campos
        jTextFieldId.setText(id);
        jComboBoxNomeProprietario.setSelectedItem(nomeProprietario);
        jComboBoxNomeMarca.setSelectedItem(marca);
        jComboBoxNomeModelo.setSelectedItem(modelo);
        jTextFieldCor.setText(cor);
        jFormattedTextFieldPlaca.setText(placa);
        jFormattedTextFieldAno.setText(ano);
        jComboBoxTipoCombustivel.setSelectedItem(tipoCombustivel);

        //Colocando as imagens da marca e do modelo nas label
        ImageIcon imagem = new ImageIcon(veiculo.getFotoMarca());
        Image image = imagem.getImage().getScaledInstance(jLabelFotoMarca.getWidth(), jLabelFotoMarca.getHeight(), Image.SCALE_SMOOTH);
        imagem = new ImageIcon(image);
        jLabelFotoMarca.setIcon(imagem);

        imagem = new ImageIcon(veiculo.getFotoModelo());
        image = imagem.getImage().getScaledInstance(jLabelFotoModelo.getWidth(), jLabelFotoModelo.getHeight(), Image.SCALE_SMOOTH);
        imagem = new ImageIcon(image);
        jLabelFotoModelo.setIcon(imagem);
        jTextFieldId.setEditable(false); // Define como não editável
    }//GEN-LAST:event_jTable1MouseClicked

    private void jButtonCadastrarProprietarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCadastrarProprietarioActionPerformed
        TelaProprietario telaProprietario = new TelaProprietario();

        // Fechar a janela atual
        dispose();

        // Exibir a nova JFrame
        telaProprietario.setVisible(true);
    }//GEN-LAST:event_jButtonCadastrarProprietarioActionPerformed

    private void jButtonCadastrarMarcaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCadastrarMarcaActionPerformed
        TelaMarca telaMarca = new TelaMarca();

        // Fechar a janela atual
        dispose();

        // Exibir a nova JFrame
        telaMarca.setVisible(true);
    }//GEN-LAST:event_jButtonCadastrarMarcaActionPerformed

    private void jButtonCadastrarModeloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCadastrarModeloActionPerformed
        try {
            TelaDeModelo telaDeModelo = new TelaDeModelo();

            // Fechar a janela atual
            dispose();

            // Exibir a nova JFrame
            telaDeModelo.setVisible(true);
        } catch (Exception ex) {
            Logger.getLogger(TelaDeVeiculo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButtonCadastrarModeloActionPerformed

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
            java.util.logging.Logger.getLogger(TelaDeVeiculo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaDeVeiculo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaDeVeiculo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaDeVeiculo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaDeVeiculo().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAlterar;
    private javax.swing.JButton jButtonBuscar;
    private javax.swing.JButton jButtonCadastrarMarca;
    private javax.swing.JButton jButtonCadastrarModelo;
    private javax.swing.JButton jButtonCadastrarProprietario;
    private javax.swing.JButton jButtonIncluir;
    private javax.swing.JButton jButtonLimparCampos;
    private javax.swing.JButton jButtonVoltar;
    private javax.swing.JComboBox<String> jComboBoxNomeMarca;
    private javax.swing.JComboBox<String> jComboBoxNomeModelo;
    private javax.swing.JComboBox<String> jComboBoxNomeProprietario;
    private javax.swing.JComboBox<String> jComboBoxTipoCombustivel;
    private javax.swing.JFormattedTextField jFormattedTextFieldAno;
    private javax.swing.JFormattedTextField jFormattedTextFieldPlaca;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelFotoMarca;
    private javax.swing.JLabel jLabelFotoModelo;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextFieldCor;
    private javax.swing.JTextField jTextFieldId;
    // End of variables declaration//GEN-END:variables
}
