package persistencia;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import modelos.IMarcaCRUD;
import modelos.Marca;

/**
 *
 * @author Frayan
 */
public class MarcaDAO implements IMarcaCRUD {
    
    //Atributos
    private String nomeDoArquivoNoDisco = null;//Criando a variavel que irá armazenar o caminho pro arquivo txt
    Marca marca = new Marca();//Instanciando o objeto marca

    public MarcaDAO() {
        nomeDoArquivoNoDisco = "./src/bancodedadosPI/marcaBD.txt";//Armazendo o caminho na variavel
    }
    @Override
    public void incluir(Marca objMarca) throws Exception {
        try {
            //cria o arquivo
            FileWriter fw = new FileWriter(nomeDoArquivoNoDisco, true);
            //Criar o buffer do arquivo
            BufferedWriter bw = new BufferedWriter(fw);
            //Escreve no arquivo
            bw.write(objMarca.toString());
            //fecha o arquivo
            bw.close();
        } catch (Exception erro) {
            String msg = "Metodo Incluir Marca - " + erro.getMessage();
            throw new Exception(msg);
        }
    }
        @Override
    public ArrayList<Marca> obterListagem() throws Exception {
        try {
            ArrayList<Marca> listaDeMarca = new ArrayList<Marca>();//Criando uma ArrayList chamada listaDeMarca do tipo objeto Marca
            FileReader fr = new FileReader(nomeDoArquivoNoDisco);//Lendo arquivo txt através do FileReader
            BufferedReader br = new BufferedReader(fr);//O BufferedReader melhora a eficiência da leitura de dados.
            String linha = "";//Criando a variavel linha
            while ((linha = br.readLine()) != null) {//A variavel "linha" recebe cada linha do arquivo txt lido, uma por vez
                String vetorStr[] = linha.split(";");//Criado o vetorStr e fazer ele dividir a linha por ";"
                //Atribuindo cada item em um espaço de memória do vetor
                String id = vetorStr[0];
                String descricao = vetorStr[1];
                String logo = vetorStr[2];

                Marca objetoMarca = new Marca(id, descricao, logo);//Salvando as variáveis do vetor dentro do objeto
                listaDeMarca.add(objetoMarca);
            }
            br.close();
            return listaDeMarca;
        } catch (Exception erro) {
            throw erro;
        }

    }
    
        @Override
    public void alterar(Marca objMarca) throws Exception {
        try {
            excluir(objMarca.getId());
            incluir(objMarca);
        } catch (Exception erro) {
            String msg = "Metodo Alterar Marca - " + erro.getMessage();
            throw new Exception(msg);
        }
    }
    
    
    @Override
    public void excluir(String id) throws Exception {
        ArrayList<Marca> agenda = new ArrayList();
        Marca marca = new Marca();
        try {

            FileReader fr = new FileReader(nomeDoArquivoNoDisco);
            BufferedReader br = new BufferedReader(fr);
            String linha;
            String idAux;
            String descricao;
            String logo;

            while ((linha = br.readLine()) != null) {
                String vetorStr[] = linha.split(";");
                idAux = vetorStr[0];
                descricao = vetorStr[1];
                logo = vetorStr[2];

                Marca objMarca = new Marca(idAux, descricao, logo);
                
                agenda.add(objMarca);
            }
            //Exclui o que tem no arquivo e reescreve
            FileWriter fw = new FileWriter(nomeDoArquivoNoDisco);
            //Criar o buffer do arquivo
            BufferedWriter bw = new BufferedWriter(fw);

            for (int i = 0; i < agenda.size(); i++) {
                if (!id.equals(agenda.get(i).getId())) {
                    marca = new Marca();
                    marca.setId(agenda.get(i).getId());
                    marca.setDescricao(agenda.get(i).getDescricao());
                    marca.setLogo(agenda.get(i).getLogo());


                    bw.write(marca.toString());
                }
            }

            bw.close();
        } catch (Exception erro) {

            String msg = "Metodo Excluir Marca - " + erro.getMessage();
            //throw new Exception(msg);

        }
    }
}
