package controle;

import java.io.BufferedReader;
import java.io.FileReader;
import java.time.Year;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import modelos.IVeiculoCRUD;
import modelos.Veiculo;
import persistencia.VeiculoDAO;

public class VeiculoControle implements IVeiculoCRUD {

    //Atributo
    private String nomeDoArquivoNoDisco = null;//Variável que vai armazenar o caminho do arquivo txt
    IVeiculoCRUD veiculoPersistencia = null;// Variável do tipo IMarcaCRUD

    public VeiculoControle() {
        {
            nomeDoArquivoNoDisco = "./src/bancodedadosPI/veiculoBD.txt";// Variável do caminho recebendo o caminho
            veiculoPersistencia = new VeiculoDAO();//instanciando a variável com o objeto da MarcaDAO

        }
    }

    // Método validar se tem uma placa igual
    public Veiculo validarPlacaIgual(String id, String placa) throws Exception {
        try {
            FileReader fr = new FileReader(nomeDoArquivoNoDisco);//Lendo o arquivo txt
            BufferedReader br = new BufferedReader(fr);
            String linha = "";
            while ((linha = br.readLine()) != null) {
                String vetorStr[] = linha.split(";");//Dividindo a ToString em ";"
                if (!linha.contains(id)) {// Se tiver o mesmo id na linha não precisa ler

                    String idAux = vetorStr[0];
                    String nomeProprietario = vetorStr[1];
                    String marca = vetorStr[2];
                    String modelo = vetorStr[3];
                    String cor = vetorStr[4];
                    String placaAux = vetorStr[5];
                    String ano = vetorStr[6];
                    String tipoCombustivel = vetorStr[7];
                    String fotoMarca = vetorStr[8];
                    String fotoModelo = vetorStr[9];

                    if (placa.equalsIgnoreCase(placaAux)) {
                        Veiculo objetoVeiculo = new Veiculo(idAux, nomeProprietario, marca, modelo,
                                cor, placaAux, ano, tipoCombustivel, fotoMarca, fotoModelo);
                        br.close();
                        return objetoVeiculo;
                    }
                }
            }
            br.close();
            return null;
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, erro.getMessage(), "ERRO MÉTODO PLACA", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    @Override
    public void incluir(Veiculo objVeiculo) throws Exception {
        //Campo ID não pode estar vazio
        if (objVeiculo.getId().equalsIgnoreCase("")) {
            throw new Exception("Campo ID não pode estar vazio");
        }
        if (veiculoPersistencia.buscar(objVeiculo.getId()) != null) {
            throw new Exception("Este id já está cadastrado");
        }

        //Validar a comboBox de nome se tiver nulo
        if (objVeiculo.getNomeProprietario() == null) {
            throw new Exception("Selecione ou cadastre um Proprietário primeiro antes de cadastrar um veículo");
        }

        //Validar a comboBox de Marca se tiver nulo
        if (objVeiculo.getMarca().equalsIgnoreCase("")) {
            throw new Exception("Selecione ou cadastre uma marca primeiro antes de cadastrar um veículo");
        }

        //Validar a comboBox de Modelose tiver nulo
        if (objVeiculo.getModelo().equalsIgnoreCase("")) {
            throw new Exception("Selecione ou cadastre um modelo primeiro antes de cadastrar um veículo");
        }

        //Campo de Cor não pode estar vazio
        if (objVeiculo.getCor().equalsIgnoreCase("")) {
            throw new Exception("O campo de cor do veículo não pode estar vazio");
        }
        //Validar se a placa foi toda preenchida
        if (objVeiculo.getPlaca().length() != 8) {
            throw new Exception("O campo da placa não pode estar vazio ou ter menos de 7 caracteres" + "\n" + "Placa inválida");
        }
        //Validar se alguém já cadastrou essa placa
        if (validarPlacaIgual(objVeiculo.getId(), objVeiculo.getPlaca()) != null) {
            throw new Exception("Essa placa já está cadastrada");
        }
        //Validar Ano
        int anoAtual = Year.now().getValue();
        int numero = Integer.parseInt(objVeiculo.getAno());
        if (numero > anoAtual) {
            throw new Exception("Ano inválido. O ano deve ser menor ou igual ao ano atual");
        }
        //Validar a comboBox de combustivel se selecionou
        if (objVeiculo.getTipoCombustivel().equalsIgnoreCase("SELECIONE UM COMBUSTÍVEL")) {
            throw new Exception("Selecione um tipo de combustível para cadastrar seu veículo");
        }
        veiculoPersistencia.incluir(objVeiculo);

    }

    @Override
    public ArrayList<Veiculo> obterListagem() throws Exception {
        //retorna arraylist de contatos
        return veiculoPersistencia.obterListagem();
    }

    @Override
    public void alterar(Veiculo objVeiculo) throws Exception {

        Veiculo modelo = new Veiculo();
        modelo = veiculoPersistencia.buscar(objVeiculo.getId());
        if (modelo == null) {
            throw new Exception("Veículo não cadastrado");
        }
        //Campo ID não pode estar vazio
        if (objVeiculo.getId().equalsIgnoreCase("")) {
            throw new Exception("Campo ID não pode estar vazio");
        }

        //Validar a comboBox de nome se tiver nulo
        if (objVeiculo.getNomeProprietario() == null) {
            throw new Exception("Selecione um Proprietário primeiro antes de alterar um veículo");
        }

        //Validar a comboBox de Marca se tiver nulo
        if (objVeiculo.getMarca().equalsIgnoreCase("")) {
            throw new Exception("Selecione uma marca primeiro antes de alterar um veículo");
        }

        //Validar a comboBox de Modelos tiver nulo
        if (objVeiculo.getModelo().equalsIgnoreCase("")) {
            throw new Exception("Selecione um modelo primeiro antes de alterar um veículo");
        }

        //Campo de Cor não pode estar vazio
        if (objVeiculo.getCor().equalsIgnoreCase("")) {
            throw new Exception("O campo de cor do veículo não pode estar vazio");
        }

        //Validar se a placa foi toda preenchida
        if (objVeiculo.getPlaca().length() != 8) {
            throw new Exception("O campo da placa não pode estar vazio ou ter menos de 7 caracteres" + "\n" + "Placa inválida");
        }
        //Validar se outra pessoa já cadastrou a placa
        if (validarPlacaIgual(objVeiculo.getId(), objVeiculo.getPlaca()) != null) {
            throw new Exception("Essa placa já está cadastrado");
        }

        //Validar Ano
        int anoAtual = Year.now().getValue();
        int numero = Integer.parseInt(objVeiculo.getAno());
        if (numero > anoAtual) {
            throw new Exception("Ano inválido. O ano deve ser menor ou igual ao ano atual.");
        }
        //Validar a comboBox de combustivel se selecionou
        if (objVeiculo.getTipoCombustivel().equalsIgnoreCase("SELECIONE UM COMBUSTÍVEL")) {
            throw new Exception("Selecione um tipo de combustível para alterar seu veículo");
        }
        veiculoPersistencia.alterar(objVeiculo);
    }

    @Override
    public Veiculo buscar(String id) throws Exception {
        try {
            //instancia com  referencia a contato e verifica passando parametro CPF

            if (id.equalsIgnoreCase("")) {
                throw new Exception("Digite um id para buscar");
            } else {

                VeiculoDAO veiculoDAO = new VeiculoDAO();
                Veiculo veiculo = new Veiculo();
                veiculo = veiculoDAO.buscar(id);

                //verifica se retorno do contato esta null, caso esteja null -> retorna contato inexistente;
                if (veiculo == null) {
                    throw new Exception("ID INEXISTENTE!");
                }
                //se passar pela condição ele retorna o contato;
                return veiculo;
            }
        } catch (Exception erro) {
            String msg = erro.getMessage();
            throw new Exception(msg);
        }
    }

    @Override
    public void excluir(String id) throws Exception {
        veiculoPersistencia.excluir(id);
    }
}
