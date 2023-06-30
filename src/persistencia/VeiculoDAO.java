package persistencia;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import modelos.*;

public class VeiculoDAO implements IVeiculoCRUD {

    //Atributos
    private String nomeDoArquivoNoDisco = null;//Criando a variavel que irá armazenar o caminho pro arquivo txt

    public VeiculoDAO() {
        nomeDoArquivoNoDisco = "./src/bancodedadosPI/veiculoBD.txt";//Armazendo o caminho na variavel
    }

    @Override
    public void incluir(Veiculo objVeiculo) throws Exception {
        try {
            //cria o arquivo
            FileWriter fw = new FileWriter(nomeDoArquivoNoDisco, true);
            //Criar o buffer do arquivo
            BufferedWriter bw = new BufferedWriter(fw);
            //Escreve no arquivo
            bw.write(objVeiculo.toString());
            //fecha o arquivo
            bw.close();
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, erro.getMessage(), "ERRO MÉTODO INCLUIR DAO", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public Veiculo buscar(String id) throws Exception {
        try {
            FileReader fr = new FileReader(nomeDoArquivoNoDisco);//Lendo arquivo txt através do FileReader
            BufferedReader br = new BufferedReader(fr);//O BufferedReader melhora a eficiência da leitura de dados.
            String linha = "";//Criando a variavel linha
            while ((linha = br.readLine()) != null) {//A variavel "linha" recebe cada linha do arquivo txt lido, uma por vez
                String vetorStr[] = linha.split(";");//Criado o vetorStr e fazer ele dividir a linha por ";"
                //Atribuindo cada item em um espaço de memória do vetor F
                String idAux = vetorStr[0];
                String nomeProprietario = vetorStr[1];
                String marca = vetorStr[2];
                String modelo = vetorStr[3];
                String cor = vetorStr[4];
                String placa = vetorStr[5];
                String ano = vetorStr[6];
                String tipoCombustivel = vetorStr[7];
                String fotoMarca = vetorStr[8];
                String fotoModelo = vetorStr[9];

                if (id.equalsIgnoreCase(idAux)) { //Se o Cpf digitado for igual ao cpf de uma linha será realizado os comandos abaixo
                    Veiculo objetoVeiculo = new Veiculo(idAux, nomeProprietario, marca, modelo,
                            cor, placa, ano, tipoCombustivel, fotoMarca, fotoModelo);
                    br.close();
                    return objetoVeiculo;
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
    public ArrayList<Veiculo> obterListagem() throws Exception {
        try {
            ArrayList<Veiculo> listaVeiculo = new ArrayList<Veiculo>();
            FileReader fr = new FileReader(nomeDoArquivoNoDisco);
            BufferedReader br = new BufferedReader(fr);
            String linha = "";
            while ((linha = br.readLine()) != null) {
                String vetorStr[] = linha.split(";");
                String idAux = vetorStr[0];
                String nomeProprietario = vetorStr[1];
                String marca = vetorStr[2];
                String modelo = vetorStr[3];
                String cor = vetorStr[4];
                String placa = vetorStr[5];
                String ano = vetorStr[6];
                String tipoCombustivel = vetorStr[7];
                String fotoMarca = vetorStr[8];
                String fotoModelo = vetorStr[9];

                Veiculo objetoVeiculo = new Veiculo(idAux, nomeProprietario, marca, modelo,
                        cor, placa, ano, tipoCombustivel, fotoMarca, fotoModelo);
                listaVeiculo.add(objetoVeiculo);
            }
            br.close();
            return listaVeiculo;
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, erro.getMessage(), "ERRO MÉTODO OBTER LISTAGEM DAO", JOptionPane.ERROR_MESSAGE);
        }
        return null;

    }

    @Override
    public void alterar(Veiculo objVeiculo) throws Exception {
        try {
            excluir(objVeiculo.getId());
            incluir(objVeiculo);
        } catch (Exception erro) {
            String msg = "Metodo Alterar Veículo - " + erro.getMessage();
            throw new Exception(msg);
        }
    }

    @Override
    public void excluir(String id) throws Exception {
        ArrayList<Veiculo> agenda = new ArrayList();
        Veiculo veiculo = new Veiculo();
        try {

            FileReader fr = new FileReader(nomeDoArquivoNoDisco);
            BufferedReader br = new BufferedReader(fr);
            String linha;
            String idAux;
            String nomeProprietario;
            String marca;
            String modelo;
            String cor;
            String placa;
            String ano;
            String tipoCombustivel;
            String fotoMarca;
            String fotoModelo;

            while ((linha = br.readLine()) != null) {
                String vetorStr[] = linha.split(";");
                idAux = vetorStr[0];
                nomeProprietario = vetorStr[1];
                marca = vetorStr[2];
                modelo = vetorStr[3];
                cor = vetorStr[4];
                placa = vetorStr[5];
                ano = vetorStr[6];
                tipoCombustivel = vetorStr[7];
                fotoMarca = vetorStr[8];
                fotoModelo = vetorStr[9];

                Veiculo objetoVeiculo = new Veiculo(idAux, nomeProprietario, marca, modelo,
                        cor, placa, ano, tipoCombustivel, fotoMarca, fotoModelo);
                agenda.add(objetoVeiculo);
            }
            //Exclui o que tem no arquivo e reescreve
            FileWriter fw = new FileWriter(nomeDoArquivoNoDisco);
            //Criar o buffer do arquivo
            BufferedWriter bw = new BufferedWriter(fw);

            for (int i = 0; i < agenda.size(); i++) {
                if (!id.equals(agenda.get(i).getId())) {
                    veiculo = new Veiculo();
                    veiculo.setId(agenda.get(i).getId());
                    veiculo.setNomeProprietario(agenda.get(i).getNomeProprietario());
                    veiculo.setMarca(agenda.get(i).getMarca());
                    veiculo.setModelo(agenda.get(i).getModelo());
                    veiculo.setCor(agenda.get(i).getCor());
                    veiculo.setPlaca(agenda.get(i).getPlaca());
                    veiculo.setAno(agenda.get(i).getAno());
                    veiculo.setTipoCombustivel(agenda.get(i).getTipoCombustivel());
                    veiculo.setFotoMarca(agenda.get(i).getFotoMarca());
                    veiculo.setFotoModelo(agenda.get(i).getFotoModelo());

                    bw.write(veiculo.toString());
                }
            }

            bw.close();
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, erro.getMessage(), "ERRO MÉTODO EXCLUIR DAO", JOptionPane.ERROR_MESSAGE);
        }
    }
}
