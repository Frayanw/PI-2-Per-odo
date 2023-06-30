package controle;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import modelos.IModeloCRUD;
import modelos.Modelo;
import persistencia.ModeloDAO;

public class ModeloControle implements IModeloCRUD {

    //Atributo
    private String nomeDoArquivoNoDisco = null;//Variável que vai armazenar o caminho do arquivo txt
    IModeloCRUD modeloPersistencia = null;// Variável do tipo IMarcaCRUD

    public ModeloControle() {
        {
            nomeDoArquivoNoDisco = "./src/bancodedadosPI/modeloBD.txt";// Variável do caminho recebendo o caminho
            modeloPersistencia = new ModeloDAO();//instanciando a variável com o objeto da MarcaDAO

        }
    }

    //Método para validar se outra pessoa já cadastrou um id igual
    public Modelo validarIdIgual(String id) throws Exception {
        try {
            FileReader fr = new FileReader(nomeDoArquivoNoDisco);//Lendo o arquivo txt
            BufferedReader br = new BufferedReader(fr);
            String linha = "";
            while ((linha = br.readLine()) != null) {
                String vetorStr[] = linha.split(";");//Dividindo a ToString em ";"

                String idAux = vetorStr[0];
                String descricao = vetorStr[1];
                String marca = vetorStr[2];
                String categoria = vetorStr[3];
                String modelo = vetorStr[4];

                if (id.equalsIgnoreCase(idAux)) {
                    Modelo objetoModelo = new Modelo(idAux, descricao, marca, categoria, modelo
                    );
                    br.close();
                    return objetoModelo;
                }
            }
            br.close();
            return null;
        } catch (Exception erro) {
            throw erro;
        }
    }

    // Método validar se tem uma descricao igual
    public Modelo validarDescricaoIgual(String id, String descricao) throws Exception {
        try {
            FileReader fr = new FileReader(nomeDoArquivoNoDisco);//Lendo o arquivo txt
            BufferedReader br = new BufferedReader(fr);
            String linha = "";
            while ((linha = br.readLine()) != null) {
                String vetorStr[] = linha.split(";");//Dividindo a ToString em ";"
                if (!linha.contains(id)) {// Se tiver o mesmo id na linha não precisa ler

                    String idAux = vetorStr[0];
                    String descricaoAux = vetorStr[1];
                    String marca = vetorStr[2];
                    String categoria = vetorStr[3];
                    String modelo = vetorStr[4];

                    if (descricao.equalsIgnoreCase(descricaoAux)) {
                        Modelo objetoModelo = new Modelo(idAux, descricaoAux, marca, categoria, modelo);
                        br.close();
                        return objetoModelo;
                    }
                }
            }
            br.close();
            return null;
        } catch (Exception erro) {
            throw erro;
        }
    }

    // Método validar se tem um modelo igual
    public Modelo validarLogoIgual(String id, String modelo) throws Exception {
        try {
            FileReader fr = new FileReader(nomeDoArquivoNoDisco);//Lendo o arquivo txt
            BufferedReader br = new BufferedReader(fr);
            String linha = "";
            while ((linha = br.readLine()) != null) {
                String vetorStr[] = linha.split(";");//Dividindo a ToString em ";"
                if (!linha.contains(id)) {// Se tiver o mesmo id na linha não precisa ler

                    String idAux = vetorStr[0];
                    String descricaoAux = vetorStr[1];
                    String marca = vetorStr[2];
                    String categoria = vetorStr[3];
                    String modeloAux = vetorStr[4];

                    if (modelo.equalsIgnoreCase(modeloAux)) {
                        Modelo objetoModelo = new Modelo(idAux, descricaoAux, marca, categoria, modeloAux);
                        br.close();
                        return objetoModelo;
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
    public void incluir(Modelo objModelo) throws Exception {

        //Campo ID não pode estar vazio
        if (objModelo.getId().equalsIgnoreCase("")) {
            throw new Exception("Campo ID não pode estar vazio");
        }
        if (validarIdIgual(objModelo.getId()) != null) {
            throw new Exception("Este id já está cadastrado");
        }
        //Id já cadastrado não pode ser igual
        if (validarIdIgual(objModelo.getId()) != null) {
            throw new Exception("Esse ID já está cadastrado!!!");
        }

        //Campo de Descrição não pode estar vazio
        if (objModelo.getDescricao().equalsIgnoreCase("")) {
            throw new Exception("O campo de descrição do modelo não pode estar vazio");
        }

        //Descrição já cadastrado não pode ser igual
        if (validarDescricaoIgual(objModelo.getId(), objModelo.getDescricao()) != null) {
            throw new Exception("Essa descrição já está cadastrado!!!");
        }
        //Validar a comboBox se tiver nulo
        if (objModelo.getMarca() == null) {
            throw new Exception("Selecione ou cadastre uma marca primeiro antes de cadastrar um modelo");
        }
        //Validar a comboBox de nome se tiver nulo
        if (objModelo.getCategoria().equalsIgnoreCase("SELECIONE UMA CATEGORIA")) {
            throw new Exception("Selecione uma categoria primeiro antes de cadastrar um modelo");
        }

        //Deve selecionar a foto
        if (objModelo.getModelo().equalsIgnoreCase("")) {
            throw new Exception("Por favor selecione uma foto para o modelo do veículo");
        }

        //Logo já cadastrado não pode ser igual
        if (validarLogoIgual(objModelo.getId(), objModelo.getModelo()) != null) {
            throw new Exception("Esse modelo já está cadastrado!!!");
        }

        modeloPersistencia.incluir(objModelo);
    }

    @Override
    public ArrayList<Modelo> obterListagem() throws Exception {
        //retorna arraylist de contatos
        return modeloPersistencia.obterListagem();
    }

    @Override
    public void alterar(Modelo objModelo) throws Exception {

        Modelo modelo = new Modelo();
        modelo = validarIdIgual(objModelo.getId());
        if (modelo == null) {
            throw new Exception("Modelo não cadastrado");
        }
        
        //Campo ID não pode estar vazio
        if (objModelo.getId().equalsIgnoreCase("")) {
            throw new Exception("Campo ID não pode estar vazio");
        }

        //Campo de Descrição não pode estar vazio
        if (objModelo.getDescricao().equalsIgnoreCase("")) {
            throw new Exception("O campo de descrição do modelo não pode estar vazio");
        }

        //Validar se outra pessoa já cadastrou a descrição do modelo
        if (validarDescricaoIgual(objModelo.getId(), objModelo.getDescricao()) != null) {
            throw new Exception("Essa descrição já está cadastrado");
        }

        //Deve selecionar a foto
        if (objModelo.getModelo().equalsIgnoreCase("")) {
            throw new Exception("Por favor selecione uma foto para o modelo do veículo");
        }

        //Validar a comboBox se tiver nulo
        if (objModelo.getMarca() == null) {
            throw new Exception("Selecione ou cadastre uma marca primeiro antes de cadastrar um modelo");
        }

        //Validar a comboBox de nome se tiver nulo
        if (objModelo.getCategoria().equalsIgnoreCase("SELECIONE UMA CATEGORIA")) {
            throw new Exception("Selecione uma categoria primeiro antes de cadastrar um modelo");
        }

        //Validar se outra pessoa já cadastrou modelo do carro
        if (validarLogoIgual(objModelo.getId(), objModelo.getModelo()) != null) {
            throw new Exception("Esse modelo já está cadastrado");
        }
        modeloPersistencia.alterar(objModelo);
    }

    @Override
    public void excluir(String id) throws Exception {
        modeloPersistencia.excluir(id);
    }
}
