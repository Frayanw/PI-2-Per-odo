package visao;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import controle.GastosControle;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;

import java.time.Year;
import java.util.ArrayList;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;

import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import modelos.Gastos;

public class TelaRelatorio extends javax.swing.JFrame {

    //Atributos
    GastosControle gastosControle = null;
    private String nomeDoArquivoNoDiscoProprietario = null;//Variável que vai armazenar o caminho do arquivo txt
    private String nomeDoArquivoNoDiscoCategotiaGastos = null; //Variável que vai armazenar o caminho do arquivo txt
    private String nomeDoArquivoNoDiscoVeiculo = null;//Variável que vai armazenar o caminho do arquivo txt
    private String nomeDoArquivoNoDiscoGastos = null;//Variável que vai armazenar o caminho do arquivo txt
    String ano = "";
    
    //Construtor
    public TelaRelatorio() {
        try {
            initComponents();
            this.setLocationRelativeTo(null);//Abrir no meio da tela
            this.setExtendedState(MAXIMIZED_BOTH);//Maximizar quando iniciar
            gastosControle = new GastosControle();
            nomeDoArquivoNoDiscoProprietario = "./src/bancodedadosPI/proprietarioBD.txt";// Variável do caminho recebendo o caminho
            nomeDoArquivoNoDiscoCategotiaGastos = "./src/bancodedadosPI/categoriaGastosBD.txt"; // Variável do caminho recebendo o caminho
            nomeDoArquivoNoDiscoVeiculo = "./src/bancodedadosPI/veiculoBD.txt";// Variável do caminho recebendo o caminho
            nomeDoArquivoNoDiscoGastos = "./src/bancodedadosPI/gastosBD.txt";// Variável do caminho recebendo o caminho
        } catch (Exception ex) {
            Logger.getLogger(TelaRelatorio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Métodos Mostrar Dados na tabela
    private void mostrarTabelaAno(String ano) {
        try {
            ArrayList<Gastos> listaDeGastos = gastosControle.obterListagem();
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.setNumRows(0);

            for (int pos = 0; pos < listaDeGastos.size(); pos++) {
                Gastos gastos = listaDeGastos.get(pos);
                String proprietario = colocarNomeProprietarioNaTable(gastos.getProprietario());
                String veiculo = colocarVeiculoNaTable((gastos.getVeiculo()));
                String categoriaGasto = colocarCategoriaGastoNaTable(gastos.getCategoriaGastos());
                String data = gastos.getData();
                String anoAux = data.substring(6);
                String valor = "R$ " + gastos.getValor();
                if (ano.equalsIgnoreCase(anoAux)) {
                    Object[] rowData = {proprietario, veiculo, categoriaGasto, data, valor};
                    model.addRow(rowData);
                }

            }
            if (model.getRowCount() == 0) {
                throw new Exception("ESSE ANO NÃO TEM NENHUM GASTO");
            }
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, erro.getMessage(), "Método mostrarTabela", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void mostrarTabelaMes(String ano) {
        try {

            FileReader fr = new FileReader(nomeDoArquivoNoDiscoGastos);

            BufferedReader br = new BufferedReader(fr);

            String[] resultado = new String[3];
            DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
            model.setNumRows(0);
            String linha;
            String linhaAux;
            float somaTotal = 0;
            while ((linha = br.readLine()) != null) {
                String vetorStr[] = linha.split(";");
                String categoriaTxt = colocarCategoriaGastoNaTable(vetorStr[3]);
                String data = vetorStr[4];
                String mesTxt = data.substring(3, 5);
                String anoTxt = data.substring(6);

                if (ano.equalsIgnoreCase(anoTxt)) {
                    FileReader frAux = new FileReader(nomeDoArquivoNoDiscoGastos);
                    BufferedReader brAux = new BufferedReader(frAux);
                    while ((linhaAux = brAux.readLine()) != null) {
                        String vetorSoma[] = linhaAux.split(";");

                        String categoriaSoma = colocarCategoriaGastoNaTable(vetorSoma[3]);
                        String dataSoma = vetorSoma[4];
                        String mesSoma = dataSoma.substring(3, 5);
                        String anoSoma = dataSoma.substring(6);
                        float valorSoma = Float.parseFloat(vetorSoma[5]);

                        if (categoriaSoma.equals(categoriaTxt)) {
                            if (anoSoma.equals(anoTxt)) {
                                if (mesSoma.equals(mesTxt)) {
                                    somaTotal += valorSoma;
                                }
                            }

                        }

                    }
                    String somaString = Float.toString(somaTotal);
                    resultado[0] = categoriaTxt;
                    resultado[1] = mesTxt + "/" + anoTxt;
                    resultado[2] = "R$ " + somaString;
                    somaTotal = 0;

                    model.addRow(resultado);

                    brAux.close();
                }
            }

            //ler a tabela inteira se tiver categoria igual, verificar a data se for igual apaga
            // Verificação e remoção de linhas duplicadas
            int totalLinhas = model.getRowCount();
            for (int i = 0; i < totalLinhas; i++) {
                Object valorCategoria = model.getValueAt(i, 1);
                Object valorData = model.getValueAt(i, 2);

                for (int j = i + 1; j < totalLinhas; j++) {
                    if (valorCategoria.equals(model.getValueAt(j, 1))
                            && valorData.equals(model.getValueAt(j, 2))) {
                        model.removeRow(j);
                        totalLinhas--;
                        j--; // Ajusta o índice após a remoção
                    }
                }
            }

            br.close();
            if (model.getRowCount() == 0) {
                throw new Exception("ESSE ANO NÃO TEM NENHUM GASTO");
            }
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, erro.getMessage(), "Método mostraTabelaMes", JOptionPane.ERROR_MESSAGE);
        }
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

    private void gerarPDFAnual() throws DocumentException {
        // Verifica se a tabela está vazia
        if (jTable1.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "A tabela está vazia. Não é possível gerar o PDF.", "Erro", JOptionPane.ERROR_MESSAGE);
            return; // Retorna imediatamente para evitar a execução do restante do código
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Salvar PDF");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Arquivos PDF (*.pdf)", "pdf"));

        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            try {
                String filePath = fileChooser.getSelectedFile().getAbsolutePath();

                // Verifica se a extensão .pdf está presente no nome do arquivo
                if (!filePath.toLowerCase().endsWith(".pdf")) {
                    filePath += ".pdf"; // Acrescenta a extensão .pdf caso não esteja presente
                }

                // Criação do documento PDF
                Document document = new Document(PageSize.A4);
                PdfWriter.getInstance(document, new FileOutputStream(filePath));

                document.open();
                
                // Criação do título
                Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
                Paragraph title = new Paragraph("RELATÓRIO ANUAL ANO "
                        + this.ano, titleFont);
                title.setAlignment(Element.ALIGN_CENTER);
                title.setSpacingAfter(20); // Espaçamento após o título
                document.add(title);
                
                // Criação da tabela
                PdfPTable table = new PdfPTable(jTable1.getColumnCount());

                // Preenchimento da tabela com os dados da jTable1
                for (int i = 0; i < jTable1.getColumnCount(); i++) {
                    table.addCell(jTable1.getColumnName(i));
                }

                for (int i = 0; i < jTable1.getRowCount(); i++) {
                    for (int j = 0; j < jTable1.getColumnCount(); j++) {
                        table.addCell(jTable1.getValueAt(i, j).toString());
                    }
                }

                // Adiciona a tabela ao documento
                document.add(table);

                document.close();

                JOptionPane.showMessageDialog(this, "PDF gerado com sucesso!");

            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(this, "Erro ao gerar PDF: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void gerarPDFMensal() throws DocumentException {
        // Verifica se a tabela está vazia
        if (jTable2.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "A tabela está vazia. Não é possível gerar o PDF.", "Erro", JOptionPane.ERROR_MESSAGE);
            return; // Retorna imediatamente para evitar a execução do restante do código
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Salvar PDF");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Arquivos PDF (*.pdf)", "pdf"));

        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            try {
                String filePath = fileChooser.getSelectedFile().getAbsolutePath();

                // Verifica se a extensão .pdf está presente no nome do arquivo
                if (!filePath.toLowerCase().endsWith(".pdf")) {
                    filePath += ".pdf"; // Acrescenta a extensão .pdf caso não esteja presente
                }

                // Criação do documento PDF
                Document document = new Document(PageSize.A4);
                PdfWriter.getInstance(document, new FileOutputStream(filePath));

                document.open();
                // Criação do título
                Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
                Paragraph title = new Paragraph("RELATÓRIO MENSAL ANO "
                        + this.ano, titleFont);
                title.setAlignment(Element.ALIGN_CENTER);
                title.setSpacingAfter(20); // Espaçamento após o título
                document.add(title);

                // Criação da tabela
                PdfPTable table = new PdfPTable(jTable2.getColumnCount());

                // Preenchimento da tabela com os dados da jTable1
                for (int i = 0; i < jTable2.getColumnCount(); i++) {
                    table.addCell(jTable2.getColumnName(i));
                }

                for (int i = 0; i < jTable2.getRowCount(); i++) {
                    for (int j = 0; j < jTable2.getColumnCount(); j++) {
                        table.addCell(jTable2.getValueAt(i, j).toString());
                    }
                }

                // Adiciona a tabela ao documento
                document.add(table);

                document.close();

                JOptionPane.showMessageDialog(this, "PDF gerado com sucesso!");

            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(this, "Erro ao gerar PDF: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    @SuppressWarnings("unchecked")

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jFormattedTextFieldAno = new javax.swing.JFormattedTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jButtonVoltar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jButtonMensal = new javax.swing.JButton();
        jButtonAnual = new javax.swing.JButton();
        jButtonPdfAnual = new javax.swing.JButton();
        jButtonPdfMensal = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

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

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(33, 42, 70));
        jLabel4.setText("Ano:");

        try {
            jFormattedTextFieldAno.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(702, 702, 702)
                        .addComponent(jLabel4))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(669, 669, 669)
                        .addComponent(jFormattedTextFieldAno, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addComponent(jFormattedTextFieldAno, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 3, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(195, 218, 231));
        jLabel1.setText(" Relatório de Gastos");

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

        jTable1.setAutoCreateRowSorter(true);
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Proprietário", "Veículo", "Categoria", "Data", "Valor"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setResizable(false);
            jTable1.getColumnModel().getColumn(1).setResizable(false);
            jTable1.getColumnModel().getColumn(2).setResizable(false);
            jTable1.getColumnModel().getColumn(3).setResizable(false);
            jTable1.getColumnModel().getColumn(4).setResizable(false);
        }

        jTable2.setAutoCreateRowSorter(true);
        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Categoria", "Mês / Ano", "Valor"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable2.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(jTable2);
        if (jTable2.getColumnModel().getColumnCount() > 0) {
            jTable2.getColumnModel().getColumn(0).setResizable(false);
            jTable2.getColumnModel().getColumn(1).setResizable(false);
            jTable2.getColumnModel().getColumn(2).setResizable(false);
        }

        jButtonMensal.setBackground(new java.awt.Color(92, 116, 123));
        jButtonMensal.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButtonMensal.setForeground(new java.awt.Color(195, 218, 231));
        jButtonMensal.setText("RELATÓRIO MENSAL");
        jButtonMensal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonMensalActionPerformed(evt);
            }
        });

        jButtonAnual.setBackground(new java.awt.Color(92, 116, 123));
        jButtonAnual.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButtonAnual.setForeground(new java.awt.Color(195, 218, 231));
        jButtonAnual.setText("RELATÓRIO ANUAL");
        jButtonAnual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAnualActionPerformed(evt);
            }
        });

        jButtonPdfAnual.setBackground(new java.awt.Color(92, 116, 123));
        jButtonPdfAnual.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButtonPdfAnual.setForeground(new java.awt.Color(195, 218, 231));
        jButtonPdfAnual.setText("GERAR PDF ANUAL");
        jButtonPdfAnual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPdfAnualActionPerformed(evt);
            }
        });

        jButtonPdfMensal.setBackground(new java.awt.Color(92, 116, 123));
        jButtonPdfMensal.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButtonPdfMensal.setForeground(new java.awt.Color(195, 218, 231));
        jButtonPdfMensal.setText("GERAR PDF MENSAL");
        jButtonPdfMensal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPdfMensalActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 411, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jButtonVoltar)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(523, 523, 523))))
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jScrollPane1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 698, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(292, 292, 292)
                .addComponent(jButtonAnual)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonMensal)
                .addGap(326, 326, 326))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(298, 298, 298)
                .addComponent(jButtonPdfAnual)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonPdfMensal)
                .addGap(327, 327, 327))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addGap(5, 5, 5)
                        .addComponent(jButtonVoltar))
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonMensal)
                    .addComponent(jButtonAnual))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1)
                    .addComponent(jScrollPane2))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonPdfAnual)
                    .addComponent(jButtonPdfMensal))
                .addGap(113, 113, 113))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1440, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 818, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
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

    private void jButtonAnualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAnualActionPerformed
        try {
            String ano = jFormattedTextFieldAno.getText().trim();

            //Validando se o campo anos tem 4 digítos
            if (ano.length() != 4) {
                throw new Exception("Ano deve ter 4 dígitos");
            }

            //Validando ano válido
            int anoAtual = Year.now().getValue();
            int anoInt = Integer.parseInt(ano);
            if (anoInt > anoAtual) {
                throw new Exception("Ano inválido. O ano deve ser menor ou igual ao ano atual");
            }

            mostrarTabelaAno(ano);

            jFormattedTextFieldAno.setValue(null); //Limpar campo do ano
            this.ano = ano;
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, erro.getMessage(), "Relatorio Anual", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButtonAnualActionPerformed

    private void jButtonMensalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonMensalActionPerformed
        try {
            String ano = jFormattedTextFieldAno.getText().trim();

            //Validando se o campo anos tem 4 digítos
            if (ano.length() != 4) {
                throw new Exception("Ano deve ter 4 dígitos");
            }

            //Validando ano válido
            int anoAtual = Year.now().getValue();
            int anoInt = Integer.parseInt(ano);
            if (anoInt > anoAtual) {
                throw new Exception("Ano inválido. O ano deve ser menor ou igual ao ano atual");
            }

            mostrarTabelaMes(ano);

            jFormattedTextFieldAno.setValue(null); //Limpar campo do ano
            this.ano = ano;
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, erro.getMessage(), "Relatorio Anual", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButtonMensalActionPerformed

    private void jButtonPdfAnualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPdfAnualActionPerformed
        try {
            gerarPDFAnual();
        } catch (DocumentException ex) {
            Logger.getLogger(TelaRelatorio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButtonPdfAnualActionPerformed

    private void jButtonPdfMensalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPdfMensalActionPerformed
        try {
            gerarPDFMensal();
        } catch (DocumentException ex) {
            Logger.getLogger(TelaRelatorio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButtonPdfMensalActionPerformed

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
            java.util.logging.Logger.getLogger(TelaRelatorio.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaRelatorio.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaRelatorio.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaRelatorio.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaRelatorio().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAnual;
    private javax.swing.JButton jButtonMensal;
    private javax.swing.JButton jButtonPdfAnual;
    private javax.swing.JButton jButtonPdfMensal;
    private javax.swing.JButton jButtonVoltar;
    private javax.swing.JFormattedTextField jFormattedTextFieldAno;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    // End of variables declaration//GEN-END:variables
}
