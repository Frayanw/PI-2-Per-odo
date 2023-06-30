/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controle;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import modelos.IMarcaCRUD;
import modelos.Marca;
import persistencia.MarcaDAO;

/**
 *
 * @author Frayan
 */
public class MarcaControle implements IMarcaCRUD {
    //Atributo

    private String nomeDoArquivoNoDisco = null; //Variável que vai armazenar o caminho do arquivo txt
    IMarcaCRUD marcaPersistencia = null; // Variável do tipo IMarcaCRUD

    public MarcaControle() {
        {
            nomeDoArquivoNoDisco = "./src/bancodedadosPI/marcaBD.txt"; // Variável do caminho recebendo o caminho
            marcaPersistencia = new MarcaDAO();//instanciando a variável com o objeto da MarcaDAO

        }
    }

    //Método para validar se outra pessoa já cadastrou um id igual
    public Marca validarIdIgual(String id) throws Exception {
        try {
            FileReader fr = new FileReader(nomeDoArquivoNoDisco); //Lendo o arquivo txt
            BufferedReader br = new BufferedReader(fr);
            String linha = "";
            while ((linha = br.readLine()) != null) {
                String vetorStr[] = linha.split(";");//Dividindo a ToString em ";"

                String idAux = vetorStr[0];
                String descricao = vetorStr[1];
                String logo = vetorStr[2];

                if (id.equalsIgnoreCase(idAux)) {
                    Marca objetoMarca = new Marca(idAux, descricao, logo);
                    br.close();
                    return objetoMarca;
                }
            }
            br.close();
            return null;
        } catch (Exception erro) {
            throw erro;
        }
    }

    // Método validar se tem uma descrição igual cadastrada
    public Marca validarDescricaoIgual(String id, String descricao) throws Exception {
        try {
            FileReader fr = new FileReader(nomeDoArquivoNoDisco);//Lendo o arquivo txt
            BufferedReader br = new BufferedReader(fr);
            String linha = "";
            while ((linha = br.readLine()) != null) {
                String vetorStr[] = linha.split(";");//Dividindo a ToString em ";"
                if (!linha.contains(id)) {// Se tiver o mesmo id na linha não precisa ler

                    String idAux = vetorStr[0];
                    String descricaoAux = vetorStr[1];
                    String logo = vetorStr[2];

                    if (descricao.equalsIgnoreCase(descricaoAux)) {
                        Marca objetoMarca = new Marca(idAux, descricaoAux, logo);
                        br.close();
                        return objetoMarca;
                    }
                }
            }
            br.close();
            return null;
        } catch (Exception erro) {
            throw erro;
        }
    }

    // Método validar se tem uma logo igual já cadastrado
    public Marca validarLogoIgual(String id, String logo) throws Exception {
        try {
            FileReader fr = new FileReader(nomeDoArquivoNoDisco);//Lendo o arquivo txt
            BufferedReader br = new BufferedReader(fr);
            String linha = "";
            while ((linha = br.readLine()) != null) {
                String vetorStr[] = linha.split(";");//Dividindo a ToString em ";"
                if (!linha.contains(id)) {// Se tiver o mesmo id na linha não precisa ler

                    String idAux = vetorStr[0];
                    String descricaoAux = vetorStr[1];
                    String logoAux = vetorStr[2];

                    if (logo.equalsIgnoreCase(logoAux)) {
                        Marca objetoMarca = new Marca(idAux, descricaoAux, logoAux);
                        br.close();
                        return objetoMarca;
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
    public void incluir(Marca objMarca) throws Exception {

        //Campo ID não pode estar vazio
        if (objMarca.getId().equalsIgnoreCase("")) {
            throw new Exception("Campo ID não pode estar vazio");
        }
        if (validarIdIgual(objMarca.getId()) != null) {
            throw new Exception("Este id já está cadastrado");
        }
        //Campo de Descrição não pode estar vazio
        if (objMarca.getDescricao().equalsIgnoreCase("")) {
            throw new Exception("O campo de descrição da marca não pode estar vazio");
        }

        //Deve selecionar a foto
        if (objMarca.getLogo().equalsIgnoreCase("")) {
            throw new Exception("Por favor selecione uma foto para a logo");
        }

        //Id já cadastrado não pode ser igual
        if (validarIdIgual(objMarca.getId()) != null) {
            throw new Exception("Esse ID já está cadastrado!!!");
        }

        //Descrição já cadastrado não pode ser igual
        if (validarDescricaoIgual(objMarca.getId(), objMarca.getDescricao()) != null) {
            throw new Exception("Essa descrição já está cadastrado!!!");
        }

        //Logo já cadastrado não pode ser igual
        if (validarLogoIgual(objMarca.getId(), objMarca.getLogo()) != null) {
            throw new Exception("Essa logo já está cadastrado!!!");
        }

        marcaPersistencia.incluir(objMarca);
    }

    @Override
    public ArrayList<Marca> obterListagem() throws Exception {
        //retorna arraylist de contatos
        return marcaPersistencia.obterListagem();
    }

    @Override
    public void alterar(Marca objMarca) throws Exception {
        Marca marca = new Marca();
        marca = validarIdIgual(objMarca.getId());
        if (marca == null) {
            throw new Exception("Marca não cadastrada");
        }
        //Campo ID não pode estar vazio
        if (objMarca.getId().equalsIgnoreCase("")) {
            throw new Exception("Campo ID não pode estar vazio");
        }

        //Deve selecionar a foto
        if (objMarca.getLogo().equalsIgnoreCase("")) {
            throw new Exception("Por favor selecione uma foto para a logo");
        }

        //Validar se outra pessoa já cadastrou a descrição da marca
        if (validarDescricaoIgual(objMarca.getId(), objMarca.getDescricao()) != null) {
            throw new Exception("Essa descrição já está cadastrado");
        }
        //Validar se outra pessoa já cadastrou a logo da marca
        if (validarLogoIgual(objMarca.getId(), objMarca.getLogo()) != null) {
            throw new Exception("Essa logo já está cadastrado");
        }

        marcaPersistencia.alterar(objMarca);
    }

    @Override
    public void excluir(String id) throws Exception {
        marcaPersistencia.excluir(id);
    }
}
