
package persistencia;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import modelos.*;

public class ProprietarioDAO implements IProprietarioCRUD {

    //Atributos
    private String nomeDoArquivoNoDisco = null;//Criando a variavel que irá armazenar o caminho pro arquivo txt
    Telefone telefone = new Telefone();//Instanciando o objeto modelo

    public ProprietarioDAO() {
        nomeDoArquivoNoDisco = "./src/bancodedadosPI/proprietarioBD.txt";//Armazendo o caminho na variavel
    }

    @Override
    public void incluir(Proprietario objProprietario) throws Exception {
        try {
            //cria o arquivo
            FileWriter fw = new FileWriter(nomeDoArquivoNoDisco, true);
            //Criar o buffer do arquivo
            BufferedWriter bw = new BufferedWriter(fw);
            //Escreve no arquivo
            bw.write(objProprietario.toString());
            //fecha o arquivo
            bw.close();
        } catch (Exception erro) {
            String msg = "Metodo Incluir Proprietário - " + erro.getMessage();
            throw new Exception(msg);
        }
    }

    @Override
    public Proprietario buscar(String CPF) throws Exception {
        try {
            FileReader fr = new FileReader(nomeDoArquivoNoDisco);//Lendo arquivo txt através do FileReader
            BufferedReader br = new BufferedReader(fr);//O BufferedReader melhora a eficiência da leitura de dados.
            String linha = "";//Criando a variavel linha
            while ((linha = br.readLine()) != null) {//A variavel "linha" recebe cada linha do arquivo txt lido, uma por vez
                String vetorStr[] = linha.split(";");//Criado o vetorStr e fazer ele dividir a linha por ";"
                //Atribuindo cada item em um espaço de memória do vetor F
                String nomeCompleto = vetorStr[0];
                String email = vetorStr[1];
                String cpfAux = vetorStr[2];
                String numeroCNHAux = vetorStr[3];
                String categoriaCNHAux = vetorStr[4];
                String DDI = vetorStr[5];
                String DDD = vetorStr[6];
                String numero = vetorStr[7];
                String fotoProprietario = vetorStr[8];
                String fotoCNH = vetorStr[9];

                if (CPF.equalsIgnoreCase(cpfAux)) { //Se o Cpf digitado for igual ao cpf de uma linha será realizado os comandos abaixo
                    telefone.setDDI(DDI);
                    telefone.setDDD(DDD);
                    telefone.setNumero(numero);
                    Proprietario objetoProprietario = new Proprietario(nomeCompleto, email, cpfAux, numeroCNHAux,
                            categoriaCNHAux, telefone, fotoProprietario, fotoCNH);
                    br.close();
                    return objetoProprietario;
                }
            }
            br.close();
            return null;
        } catch (Exception erro) {
            throw erro;
        }
    }

    @Override
    public ArrayList<Proprietario> obterListagem() throws Exception {
        try {
            ArrayList<Proprietario> listaDeProprietario = new ArrayList<Proprietario>();
            FileReader fr = new FileReader(nomeDoArquivoNoDisco);
            BufferedReader br = new BufferedReader(fr);
            String linha = "";
            while ((linha = br.readLine()) != null) {
                String vetorStr[] = linha.split(";");
                String nomeCompleto = vetorStr[0];
                String email = vetorStr[1];
                String cpfAux = vetorStr[2];
                String numeroCNHAux = vetorStr[3];
                String categoriaCNHAux = vetorStr[4];
                String DDI = vetorStr[5];
                String DDD = vetorStr[6];
                String numero = vetorStr[7];
                String fotoProprietario = vetorStr[8];
                String fotoCNH = vetorStr[9];

                telefone = new Telefone();
                telefone.setDDI(DDI);
                telefone.setDDD(DDD);
                telefone.setNumero(numero);
                Proprietario objetoProprietario = new Proprietario(nomeCompleto, email, cpfAux, numeroCNHAux,
                        categoriaCNHAux, telefone, fotoProprietario, fotoCNH);
                listaDeProprietario.add(objetoProprietario);
            }
            br.close();
            return listaDeProprietario;
        } catch (Exception erro) {
            throw erro;
        }

    }

    @Override
    public void alterar(Proprietario objProprietario) throws Exception {
        try {
            excluir(objProprietario.getCpf());
            incluir(objProprietario);
        } catch (Exception erro) {
            String msg = "Metodo Alterar Proprietário - " + erro.getMessage();
            throw new Exception(msg);
        }
    }

    @Override
    public void excluir(String cpf) throws Exception {
        ArrayList<Proprietario> agenda = new ArrayList();
        Proprietario proprietario = new Proprietario();
        try {

            FileReader fr = new FileReader(nomeDoArquivoNoDisco);
            BufferedReader br = new BufferedReader(fr);
            String linha;
            String nomeCompleto;
            String email;
            String cpfAux;
            String DDI;
            String DDD;
            String numero;
            String numeroCNHAux;
            String categoriaCNHAux;

            while ((linha = br.readLine()) != null) {
                String vetorStr[] = linha.split(";");
                nomeCompleto = vetorStr[0];
                email = vetorStr[1];
                cpfAux = vetorStr[2];
                numeroCNHAux = vetorStr[3];
                categoriaCNHAux = vetorStr[4];
                DDI = vetorStr[5];
                DDD = vetorStr[6];
                numero = vetorStr[7];
                String fotoProprietario = vetorStr[8];
                String fotoCNH = vetorStr[9];
                telefone = new Telefone();
                telefone.setDDI(DDI);
                telefone.setDDD(DDD);
                telefone.setNumero(numero);
                Proprietario objProprietario = new Proprietario(nomeCompleto, email, cpfAux,
                        numeroCNHAux, categoriaCNHAux, telefone, fotoProprietario, fotoCNH);
                agenda.add(objProprietario);
            }
            //Exclui o que tem no arquivo e reescreve
            FileWriter fw = new FileWriter(nomeDoArquivoNoDisco);
            //Criar o buffer do arquivo
            BufferedWriter bw = new BufferedWriter(fw);

            for (int i = 0; i < agenda.size(); i++) {
                if (!cpf.equals(agenda.get(i).getCpf())) {
                    proprietario = new Proprietario();
                    telefone = new Telefone();
                    proprietario.setNomeCompleto(agenda.get(i).getNomeCompleto());
                    proprietario.setEmail(agenda.get(i).getEmail());
                    proprietario.setCpf(agenda.get(i).getCpf());
                    proprietario.setNumeroCNH(agenda.get(i).getNumeroCNH());
                    proprietario.setCategoriaCNH(agenda.get(i).getCategoriaCNH());
                    telefone.setDDI(agenda.get(i).getTelefone().getDDI());
                    telefone.setDDD(agenda.get(i).getTelefone().getDDD());
                    telefone.setNumero(agenda.get(i).getTelefone().getNumero());
                    proprietario.setFotoProprietario(agenda.get(i).getFotoProprietario());
                    proprietario.setFotoCNH(agenda.get(i).getFotoCNH());
                    proprietario.setTelefone(telefone);

                    bw.write(proprietario.toString());
                }
            }

            bw.close();
        } catch (Exception erro) {

            String msg = "Metodo Excluir Contato - " + erro.getMessage();
            //throw new Exception(msg);

        }
    }
}
