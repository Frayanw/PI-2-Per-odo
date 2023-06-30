
package persistencia;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import modelos.IModeloCRUD;
import modelos.Modelo;

public class ModeloDAO implements IModeloCRUD {
    
    
    //Atributos
    private String nomeDoArquivoNoDisco = null;//Criando a variavel que irá armazenar o caminho pro arquivo txt
    Modelo modelo = new Modelo();//Instanciando o objeto modelo

    public ModeloDAO() {
        nomeDoArquivoNoDisco = "./src/bancodedadosPI/modeloBD.txt";//Armazendo o caminho na variavel
    }
    @Override
    public void incluir(Modelo objModelo) throws Exception {
        try {
            //cria o arquivo
            FileWriter fw = new FileWriter(nomeDoArquivoNoDisco, true);
            //Criar o buffer do arquivo
            BufferedWriter bw = new BufferedWriter(fw);
            //Escreve no arquivo
            bw.write(objModelo.toString());
            //fecha o arquivo
            bw.close();
        } catch (Exception erro) {
            String msg = "Metodo Incluir Modelo - " + erro.getMessage();
            throw new Exception(msg);
        }
    }
        @Override
    public ArrayList<Modelo> obterListagem() throws Exception {
        try {
            ArrayList<Modelo> listaDeModelo = new ArrayList<Modelo>();//Criando uma ArrayList chamada listaDeModelo do tipo objeto Modelo
            FileReader fr = new FileReader(nomeDoArquivoNoDisco);//Lendo arquivo txt através do FileReader
            BufferedReader br = new BufferedReader(fr);//O BufferedReader melhora a eficiência da leitura de dados.
            String linha = "";//Criando a variavel linha
            while ((linha = br.readLine()) != null) {//A variavel "linha" recebe cada linha do arquivo txt lido, uma por vez
                String vetorStr[] = linha.split(";");//Criado o vetorStr e fazer ele dividir a linha por ";"
                //Atribuindo cada item em um espaço de memória do vetor
                String id = vetorStr[0];
                String descricao = vetorStr[1];
                String marca = vetorStr[2];
                String categoria = vetorStr[3];
                String modeloAux = vetorStr[4];

                Modelo objetoModelo = new Modelo(id, descricao,marca,categoria, modeloAux);
                listaDeModelo.add(objetoModelo);
            }
            br.close();
            return listaDeModelo;
        } catch (Exception erro) {
            throw erro;
        }

    }
    
        @Override
    public void alterar(Modelo objModelo) throws Exception {
        try {
            excluir(objModelo.getId());
            incluir(objModelo);
        } catch (Exception erro) {
            String msg = "Metodo Alterar Modelo - " + erro.getMessage();
            throw new Exception(msg);
        }
    }
    
    
    @Override
    public void excluir(String id) throws Exception {
        ArrayList<Modelo> agenda = new ArrayList();
        Modelo modelo = new Modelo();
        try {

            FileReader fr = new FileReader(nomeDoArquivoNoDisco);
            BufferedReader br = new BufferedReader(fr);
            String linha;
            String idAux;
            String descricao;
            String marca;
            String categoria;
            String modeloAux;

            while ((linha = br.readLine()) != null) {
                String vetorStr[] = linha.split(";");
                idAux = vetorStr[0];
                descricao = vetorStr[1];
                marca = vetorStr[2];
                categoria = vetorStr[3];
                modeloAux = vetorStr[4];

                Modelo objModelo = new Modelo(idAux, descricao,marca,categoria,modeloAux);
                
                agenda.add(objModelo);
            }
            //Exclui o que tem no arquivo e reescreve
            FileWriter fw = new FileWriter(nomeDoArquivoNoDisco);
            //Criar o buffer do arquivo
            BufferedWriter bw = new BufferedWriter(fw);

            for (int i = 0; i < agenda.size(); i++) {
                if (!id.equals(agenda.get(i).getId())) {
                    modelo = new Modelo();
                    modelo.setId(agenda.get(i).getId());
                    modelo.setDescricao(agenda.get(i).getDescricao());
                    modelo.setMarca(agenda.get(i).getMarca());
                    modelo.setCategoria(agenda.get(i).getCategoria());
                    modelo.setModelo(agenda.get(i).getModelo());


                    bw.write(modelo.toString());
                }
            }

            bw.close();
        } catch (Exception erro) {

            String msg = "Metodo Excluir Marca - " + erro.getMessage();
            //throw new Exception(msg);

        }
    }
}