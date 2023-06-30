
package persistencia;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import modelos.CategoriaGastos;
import modelos.ICategoriaGastosCRUD;

public class CategoriaGastosDAO implements ICategoriaGastosCRUD{
        
    //Atributos
    private String nomeDoArquivoNoDisco = null;//Criando a variavel que irá armazenar o caminho pro arquivo txt
    CategoriaGastos categoriaGastos = new CategoriaGastos();//Instanciando o objeto Categoria de Gastso

    public CategoriaGastosDAO() {
        nomeDoArquivoNoDisco = "./src/bancodedadosPI/categoriaGastosBD.txt";//Armazendo o caminho na variavel
    }
    @Override
    public void incluir(CategoriaGastos objCategoriaGastos) throws Exception {
        try {
            //cria o arquivo
            FileWriter fw = new FileWriter(nomeDoArquivoNoDisco, true);
            //Criar o buffer do arquivo
            BufferedWriter bw = new BufferedWriter(fw);
            //Escreve no arquivo
            bw.write(objCategoriaGastos.toString());
            //fecha o arquivo
            bw.close();
        } catch (Exception erro) {
            String msg = "Metodo Incluir categoriaGastos - " + erro.getMessage();
            throw new Exception(msg);
        }
    }
    
        @Override
    public ArrayList<CategoriaGastos> obterListagem() throws Exception {
        try {
            ArrayList<CategoriaGastos> listaDeCategoriaDeGastos = new ArrayList<CategoriaGastos>();//Criando uma ArrayList chamada listaDeMarca do tipo objeto Marca
            FileReader fr = new FileReader(nomeDoArquivoNoDisco);//Lendo arquivo txt através do FileReader
            BufferedReader br = new BufferedReader(fr);//O BufferedReader melhora a eficiência da leitura de dados.
            String linha = "";//Criando a variavel linha
            while ((linha = br.readLine()) != null) {//A variavel "linha" recebe cada linha do arquivo txt lido, uma por vez
                String vetorStr[] = linha.split(";");//Criado o vetorStr e fazer ele dividir a linha por ";"
                //Atribuindo cada item em um espaço de memória do vetor
                String id = vetorStr[0];
                String descricao = vetorStr[1];
                String icone = vetorStr[2];

                CategoriaGastos objetoCategoriaGastos = new CategoriaGastos(id, descricao, icone);//Salvando as variáveis do vetor dentro do objeto
                listaDeCategoriaDeGastos.add(objetoCategoriaGastos);
            }
            br.close();
            return listaDeCategoriaDeGastos;
        } catch (Exception erro) {
            throw erro;
        }

    }
    
        @Override
    public void alterar(CategoriaGastos objCategoriaGastos) throws Exception {
        try {
            excluir(objCategoriaGastos.getId());
            incluir(objCategoriaGastos);
        } catch (Exception erro) {
            String msg = "Metodo Alterar CategoriaGastos - " + erro.getMessage();
            throw new Exception(msg);
        }
    }
    
    
    @Override
    public void excluir(String id) throws Exception {
        ArrayList<CategoriaGastos> agenda = new ArrayList();
        CategoriaGastos categoriaGastos = new CategoriaGastos();
        try {

            FileReader fr = new FileReader(nomeDoArquivoNoDisco);
            BufferedReader br = new BufferedReader(fr);
            String linha;
            String idAux;
            String descricao;
            String icone;

            while ((linha = br.readLine()) != null) {
                String vetorStr[] = linha.split(";");
                idAux = vetorStr[0];
                descricao = vetorStr[1];
                icone = vetorStr[2];

                CategoriaGastos objCategoriaGastos = new CategoriaGastos(idAux, descricao, icone);
                
                agenda.add(objCategoriaGastos);
            }
            //Exclui o que tem no arquivo e reescreve
            FileWriter fw = new FileWriter(nomeDoArquivoNoDisco);
            //Criar o buffer do arquivo
            BufferedWriter bw = new BufferedWriter(fw);

            for (int i = 0; i < agenda.size(); i++) {
                if (!id.equals(agenda.get(i).getId())) {
                    categoriaGastos = new CategoriaGastos();
                    categoriaGastos.setId(agenda.get(i).getId());
                    categoriaGastos.setDescricao(agenda.get(i).getDescricao());
                    categoriaGastos.setIcone(agenda.get(i).getIcone());


                    bw.write(categoriaGastos.toString());
                }
            }

            bw.close();
        } catch (Exception erro) {

            String msg = "Metodo Excluir CategoriaGastos - " + erro.getMessage();
            //throw new Exception(msg);

        }
    }
}
