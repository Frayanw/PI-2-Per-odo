package controle;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import modelos.Gastos;
import modelos.IGastosCRUD;
import persistencia.GastosDAO;

public class GastosControle implements IGastosCRUD {

    //Atributo
    private String nomeDoArquivoNoDisco = null;//Variável que vai armazenar o caminho do arquivo txt
    IGastosCRUD gastosPersistencia = null;// Variável do tipo IMarcaCRUD

    public GastosControle() {
        {
            nomeDoArquivoNoDisco = "./src/bancodedadosPI/gastosBD.txt";// Variável do caminho recebendo o caminho
            gastosPersistencia = new GastosDAO();//instanciando a variável com o objeto da GastosDAO

        }
    }

    public static boolean validarData(String data) {
        String dateFormat = "dd/MM/uuuu";

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter
                .ofPattern(dateFormat)
                .withResolverStyle(ResolverStyle.STRICT);
        try {
            LocalDate dataFornecida = LocalDate.parse(data, dateTimeFormatter);
            LocalDate dataAtual = LocalDate.now();
            if (dataFornecida.isAfter(dataAtual)) {
                return false;
            }
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    @Override
    public void incluir(Gastos objGastos) throws Exception {
        //Campo ID não pode estar vazio
        if (objGastos.getId().equalsIgnoreCase("")) {
            throw new Exception("Campo ID não pode estar vazio");
        }
        if (gastosPersistencia.buscar(objGastos.getId()) != null) {
            throw new Exception("Este id já está cadastrado");
        }
        //Validar a comboBox de nome se tiver nulo
        if (objGastos.getProprietario().equalsIgnoreCase("")) {
            throw new Exception("Selecione ou cadastre um Proprietário primeiro antes de cadastrar um gasto");
        }

        //Validar a comboBox de Marca se tiver nulo
        if (objGastos.getVeiculo().equalsIgnoreCase("")) {
            throw new Exception("Selecione ou cadastre um veículo primeiro antes de cadastrar um gasto");
        }

        //Validar a comboBox de Categoria de gastos tiver nulo
        if (objGastos.getCategoriaGastos().equalsIgnoreCase("")) {
            throw new Exception("Selecione ou cadastre uma categoria de gastos primeiro antes de cadastrar um gasto");
        }

        //Validar data
        if (validarData(objGastos.getData())) {
        } else {
            throw new Exception("Data Inválida");
        }
        gastosPersistencia.incluir(objGastos);

    }

    @Override
    public ArrayList<Gastos> obterListagem() throws Exception {
        //retorna arraylist de contatos
        return gastosPersistencia.obterListagem();
    }

    @Override
    public void alterar(Gastos objGastos) throws Exception {

        Gastos gastos = new Gastos();
        gastos = gastosPersistencia.buscar(objGastos.getId());
        if (gastos == null) {
            throw new Exception("Gasto não cadastrado");
        }
        //Campo ID não pode estar vazio
        if (objGastos.getId().equalsIgnoreCase("")) {
            throw new Exception("Campo ID não pode estar vazio");
        }

        //Validar a comboBox de nome se tiver nulo
        if (objGastos.getProprietario().equalsIgnoreCase("")) {
            throw new Exception("Selecione ou cadastre um Proprietário primeiro antes de cadastrar um gasto");
        }

        //Validar a comboBox de Marca se tiver nulo
        if (objGastos.getVeiculo().equalsIgnoreCase("")) {
            throw new Exception("Selecione ou cadastre um veículo primeiro antes de cadastrar um gasto");
        }

        //Validar a comboBox de Categoria de gastos tiver nulo
        if (objGastos.getCategoriaGastos().equalsIgnoreCase("")) {
            throw new Exception("Selecione ou cadastre uma categoria de gastos primeiro antes de cadastrar um gasto");
        }

        //Validar data
        if (validarData(objGastos.getData())) {
        } else {
            throw new Exception("Data Inválida");
        }

        gastosPersistencia.alterar(objGastos);
    }

    @Override
    public Gastos buscar(String id) throws Exception {
        try {
            //instancia com  referencia a contato e verifica passando parametro CPF

            if (id.equalsIgnoreCase("")) {
                throw new Exception("Digite um id para buscar");
            } else {

                GastosDAO gastosDAO = new GastosDAO();
                Gastos gastos = new Gastos();
                gastos = gastosDAO.buscar(id);

                //verifica se retorno do contato esta null, caso esteja null -> retorna contato inexistente;
                if (gastos == null) {
                    throw new Exception("ID INEXISTENTE!");
                }
                //se passar pela condição ele retorna o contato;
                return gastos;
            }
        } catch (Exception erro) {
            String msg = erro.getMessage();
            throw new Exception(msg);
        }
    }

    @Override
    public void excluir(String id) throws Exception {
        gastosPersistencia.excluir(id);
    }
}
