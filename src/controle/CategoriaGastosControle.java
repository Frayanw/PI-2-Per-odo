package controle;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import modelos.CategoriaGastos;
import modelos.ICategoriaGastosCRUD;
import persistencia.CategoriaGastosDAO;

public class CategoriaGastosControle implements ICategoriaGastosCRUD {
    //Atributo

    private String nomeDoArquivoNoDisco = null; //Variável que vai armazenar o caminho do arquivo txt
    ICategoriaGastosCRUD CategoriaGastosPersistencia = null; // Variável do tipo IMarcaCRUD

    public CategoriaGastosControle() {
        {
            nomeDoArquivoNoDisco = "./src/bancodedadosPI/categoriaGastosBD.txt"; // Variável do caminho recebendo o caminho
            CategoriaGastosPersistencia = new CategoriaGastosDAO();//instanciando a variável com o objeto da MarcaDAO

        }
    }

    //Método para validar se outra pessoa já cadastrou um id igual
    public CategoriaGastos validarIdIgual(String id) throws Exception {
        try {
            FileReader fr = new FileReader(nomeDoArquivoNoDisco); //Lendo o arquivo txt
            BufferedReader br = new BufferedReader(fr);
            String linha = "";
            while ((linha = br.readLine()) != null) {
                String vetorStr[] = linha.split(";");//Dividindo a ToString em ";"

                String idAux = vetorStr[0];
                String descricao = vetorStr[1];
                String icone = vetorStr[2];

                if (id.equalsIgnoreCase(idAux)) {
                    CategoriaGastos objetoCategoriaGastos = new CategoriaGastos(idAux, descricao, icone);
                    br.close();
                    return objetoCategoriaGastos;
                }
            }
            br.close();
            return null;
        } catch (Exception erro) {
            throw erro;
        }
    }

    // Método validar se tem uma descrição igual cadastrada
    public CategoriaGastos validarDescricaoIgual(String id, String descricao) throws Exception {
        try {
            FileReader fr = new FileReader(nomeDoArquivoNoDisco);//Lendo o arquivo txt
            BufferedReader br = new BufferedReader(fr);
            String linha = "";
            while ((linha = br.readLine()) != null) {
                String vetorStr[] = linha.split(";");//Dividindo a ToString em ";"
                if (!linha.contains(id)) {// Se tiver o mesmo id na linha não precisa ler

                    String idAux = vetorStr[0];
                    String descricaoAux = vetorStr[1];
                    String icone = vetorStr[2];

                    if (descricao.equalsIgnoreCase(descricaoAux)) {
                        CategoriaGastos objetoCategoriaGastos = new CategoriaGastos(idAux, descricaoAux, icone);
                        br.close();
                        return objetoCategoriaGastos;
                    }
                }
            }
            br.close();
            return null;
        } catch (Exception erro) {
            throw erro;
        }
    }

    @Override
    public void incluir(CategoriaGastos objCategoriaGastos) throws Exception {

        //Campo ID não pode estar vazio
        if (objCategoriaGastos.getId().equalsIgnoreCase("")) {
            throw new Exception("Campo ID não pode estar vazio");
        }
        //Ver no txt se tem algum id igual
        if (validarIdIgual(objCategoriaGastos.getId()) != null) {
            throw new Exception("Este id já está cadastrado");
        }
        //Campo de Descrição não pode estar vazio
        if (objCategoriaGastos.getDescricao().equalsIgnoreCase("")) {
            throw new Exception("O campo de descrição de categoria de gastos não pode estar vazio");
        }
        
        //Deve selecionar a foto
        if (objCategoriaGastos.getIcone().equalsIgnoreCase("")) {
            throw new Exception("Por favor selecione um icone");
        }

        //Id já cadastrado não pode ser igual
        if (validarIdIgual(objCategoriaGastos.getId()) != null) {
            throw new Exception("Esse ID já está cadastrado!!!");
        }

        //Descrição já cadastrado não pode ser igual
        if (validarDescricaoIgual(objCategoriaGastos.getId(), objCategoriaGastos.getDescricao()) != null) {
            throw new Exception("Essa descrição já está cadastrado!!!");
        }

        CategoriaGastosPersistencia.incluir(objCategoriaGastos);
    }

    @Override
    public ArrayList<CategoriaGastos> obterListagem() throws Exception {
        //retorna arraylist de contatos
        return CategoriaGastosPersistencia.obterListagem();
    }

    @Override
    public void alterar(CategoriaGastos objCategoriaGastos) throws Exception {
        CategoriaGastos categoriaGastos = new CategoriaGastos();
        categoriaGastos = validarIdIgual(objCategoriaGastos.getId());
        if (categoriaGastos == null) {
            throw new Exception("Categoria não cadastrada");
        }
        //Campo ID não pode estar vazio
        if (objCategoriaGastos.getId().equalsIgnoreCase("")) {
            throw new Exception("Campo ID não pode estar vazio");
        }

        //Deve selecionar a foto
        if (objCategoriaGastos.getIcone().equalsIgnoreCase("")) {
            throw new Exception("Por favor selecione um icone");
        }

        //Validar se outra pessoa já cadastrou a descrição da categoriaGastos
        if (validarDescricaoIgual(objCategoriaGastos.getId(), objCategoriaGastos.getDescricao()) != null) {
            throw new Exception("Essa descrição já está cadastrado");
        }

        CategoriaGastosPersistencia.alterar(objCategoriaGastos);
    }

    @Override
    public void excluir(String id) throws Exception {
        CategoriaGastosPersistencia.excluir(id);
    }
}
