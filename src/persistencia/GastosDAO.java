package persistencia;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import modelos.Gastos;
import modelos.IGastosCRUD;

public class GastosDAO implements IGastosCRUD {

    //Atributos
    private String nomeDoArquivoNoDisco = null;//Criando a variavel que irá armazenar o caminho pro arquivo txt

    public GastosDAO() {
        nomeDoArquivoNoDisco = "./src/bancodedadosPI/gastosBD.txt";//Armazendo o caminho na variavel
    }

    @Override
    public void incluir(Gastos objGastos) throws Exception {
        try {
            //cria o arquivo
            FileWriter fw = new FileWriter(nomeDoArquivoNoDisco, true);
            //Criar o buffer do arquivo
            BufferedWriter bw = new BufferedWriter(fw);
            //Escreve no arquivo
            bw.write(objGastos.toString());
            //fecha o arquivo
            bw.close();
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, erro.getMessage(), "ERRO MÉTODO INCLUIR DAO", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public Gastos buscar(String id) throws Exception {
        try {
            FileReader fr = new FileReader(nomeDoArquivoNoDisco);//Lendo arquivo txt através do FileReader
            BufferedReader br = new BufferedReader(fr);//O BufferedReader melhora a eficiência da leitura de dados.
            String linha = "";//Criando a variavel linha
            while ((linha = br.readLine()) != null) {//A variavel "linha" recebe cada linha do arquivo txt lido, uma por vez
                String vetorStr[] = linha.split(";");//Criado o vetorStr e fazer ele dividir a linha por ";"
                //Atribuindo cada item em um espaço de memória do vetor F
                String idAux = vetorStr[0];
                String proprietario = vetorStr[1];
                String veiculo = vetorStr[2];
                String categoriaGastos = vetorStr[3];
                String data = vetorStr[4];
                float valor = Float.parseFloat(vetorStr[5]);
                String iconeCategoria = vetorStr[6];

                if (id.equalsIgnoreCase(idAux)) { //Se o Cpf digitado for igual ao cpf de uma linha será realizado os comandos abaixo
                    Gastos objetoGastoso = new Gastos(idAux, proprietario, veiculo, categoriaGastos, data, valor, iconeCategoria);
                    br.close();
                    return objetoGastoso;
                }
            }
            br.close();
            return null;
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, erro.getMessage(), "ERRO MÉTODO BUSCAR DAO", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    @Override
    public ArrayList<Gastos> obterListagem() throws Exception {
        try {
            ArrayList<Gastos> listaGastos = new ArrayList<Gastos>();
            FileReader fr = new FileReader(nomeDoArquivoNoDisco);
            BufferedReader br = new BufferedReader(fr);
            String linha = "";
            while ((linha = br.readLine()) != null) {
                String vetorStr[] = linha.split(";");
                String idAux = vetorStr[0];
                String proprietario = vetorStr[1];
                String veiculo = vetorStr[2];
                String categoriaGastos = vetorStr[3];
                String data = vetorStr[4];
                float valor = Float.parseFloat(vetorStr[5]);
                String iconeCategoria = vetorStr[6];

                Gastos objetoGastoso = new Gastos(idAux, proprietario, veiculo, categoriaGastos, data, valor,iconeCategoria);
                listaGastos.add(objetoGastoso);
            }
            br.close();
            return listaGastos;
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, erro.getMessage(), "ERRO MÉTODO OBTER LISTAGEM DAO", JOptionPane.ERROR_MESSAGE);
        }
        return null;

    }

    @Override
    public void alterar(Gastos objGastos) throws Exception {
        try {
            excluir(objGastos.getId());
            incluir(objGastos);
        } catch (Exception erro) {
            String msg = "Metodo Alterar Gastos DAO - " + erro.getMessage();
            throw new Exception(msg);
        }
    }

    @Override
    public void excluir(String id) throws Exception {
        ArrayList<Gastos> agenda = new ArrayList();
        Gastos gastos = new Gastos();
        try {

            FileReader fr = new FileReader(nomeDoArquivoNoDisco);
            BufferedReader br = new BufferedReader(fr);
            String linha;
            String idAux;
            String proprietario;
            String veiculo;
            String categoriaGastos;
            String data;
            float valor;
            String iconeCategoria;

            while ((linha = br.readLine()) != null) {
                String vetorStr[] = linha.split(";");
                idAux = vetorStr[0];
                proprietario = vetorStr[1];
                veiculo = vetorStr[2];
                categoriaGastos = vetorStr[3];
                data = vetorStr[4];
                valor = Float.parseFloat(vetorStr[5]);
                iconeCategoria = vetorStr[6];

                Gastos objetoGastos = new Gastos(idAux, proprietario, veiculo, categoriaGastos, data, valor, iconeCategoria);
                agenda.add(objetoGastos);
            }
            //Exclui o que tem no arquivo e reescreve
            FileWriter fw = new FileWriter(nomeDoArquivoNoDisco);
            //Criar o buffer do arquivo
            BufferedWriter bw = new BufferedWriter(fw);

            for (int i = 0; i < agenda.size(); i++) {
                if (!id.equals(agenda.get(i).getId())) {
                    gastos = new Gastos();
                    gastos.setId(agenda.get(i).getId());
                    gastos.setProprietario(agenda.get(i).getProprietario());
                    gastos.setVeiculo(agenda.get(i).getVeiculo());
                    gastos.setCategoriaGastos(agenda.get(i).getCategoriaGastos());
                    gastos.setData(agenda.get(i).getData());
                    gastos.setValor(agenda.get(i).getValor());
                    gastos.setIconeCategoria(agenda.get(i).getIconeCategoria());

                    bw.write(gastos.toString());
                }
            }

            bw.close();
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, erro.getMessage(), "ERRO MÉTODO EXCLUIR DAO", JOptionPane.ERROR_MESSAGE);
        }
    }
}
