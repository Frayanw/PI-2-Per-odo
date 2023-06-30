package controle;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import modelos.Proprietario;
import modelos.Telefone;
import modelos.IProprietarioCRUD;
import persistencia.ProprietarioDAO;

public class ProprietarioControle implements IProprietarioCRUD {

    //Atributo
    private String nomeDoArquivoNoDisco = null;//Variável para armazenar caminho do arquivo txt
    IProprietarioCRUD ProprietarioPersistencia = null;//Variável do tipo  IProprietarioCRUD
    Telefone telefone = new Telefone(); //Instanciando o objeto Telefone

    public ProprietarioControle() {
        {
            nomeDoArquivoNoDisco = "./src/bancodedadosPI/proprietarioBD.txt"; //Preenchendo variável com o caminho pro txt
            ProprietarioPersistencia = new ProprietarioDAO(); //Instanciando a varivavel com o objeto do ProprietarioDAO

        }
    }

    // Método validar se tem um email igual
    public Proprietario validarEmailIgual(String cpf, String email) throws Exception {
        try {
            FileReader fr = new FileReader(nomeDoArquivoNoDisco); //Lendo arquivo txt através do FileReader
            BufferedReader br = new BufferedReader(fr);//O BufferedReader melhora a eficiência da leitura de dados.
            String linha = "";//Criando a variavel linha
            while ((linha = br.readLine()) != null) {//A variavel "linha" recebe cada linha do arquivo txt lido, uma por vez
                String vetorStr[] = linha.split(";");//Criado o vetorStr e fazer ele dividir a linha por ";"
                if (!linha.contains(cpf)) {//Se a linha tiver o cpf informado não precisa realizar os comandos abaixo

                    //Atribuindo cada item em um espaço de memória do vetor
                    String nomeCompleto = vetorStr[0];
                    String emailAux = vetorStr[1];
                    String cpfAux = vetorStr[2];
                    String numeroCNHAux = vetorStr[3];
                    String categoriaCNHAux = vetorStr[4];
                    String DDI = vetorStr[5];
                    String DDD = vetorStr[6];
                    String numero = vetorStr[7];
                    String fotoProprietario = vetorStr[8];
                    String fotoCNH = vetorStr[9];

                    //Se o email digitado for igual ao email lido na linha pode realizar os comandos
                    if (email.equalsIgnoreCase(emailAux)) {
                        Telefone telefone = new Telefone();
                        telefone.setDDI(DDI);
                        telefone.setDDD(DDD);
                        telefone.setNumero(numero);
                        Proprietario objetoProprietario = new Proprietario(nomeCompleto, emailAux, cpfAux, numeroCNHAux,
                                categoriaCNHAux, telefone, fotoProprietario, fotoCNH);
                        br.close();
                        return objetoProprietario; //Retorna o objeto
                    }
                }
            }
            br.close();
            return null;
        } catch (Exception erro) {
            throw erro;
        }
    }

    // Método validar se tem um numero de cnh igual
    public Proprietario validarNumeroCNHIgual(String cpf, String numeroCNH) throws Exception {
        try {
            FileReader fr = new FileReader(nomeDoArquivoNoDisco);
            BufferedReader br = new BufferedReader(fr);
            String linha = "";
            while ((linha = br.readLine()) != null) {
                String vetorStr[] = linha.split(";");
                if (!linha.contains(cpf)) {

                    String nomeCompleto = vetorStr[0];
                    String emailAux = vetorStr[1];
                    String cpfAux = vetorStr[2];
                    String numeroCNHAux = vetorStr[3];
                    String categoriaCNHAux = vetorStr[4];
                    String DDI = vetorStr[5];
                    String DDD = vetorStr[6];
                    String numero = vetorStr[7];
                    String fotoProprietario = vetorStr[8];
                    String fotoCNH = vetorStr[9];

                    if (numeroCNH.equalsIgnoreCase(numeroCNHAux)) {
                        Telefone telefone = new Telefone();
                        telefone.setDDI(DDI);
                        telefone.setDDD(DDD);
                        telefone.setNumero(numero);
                        Proprietario objetoProprietario = new Proprietario(nomeCompleto, emailAux, cpfAux, numeroCNHAux,
                                categoriaCNHAux, telefone, fotoProprietario, fotoCNH);
                        br.close();
                        return objetoProprietario;
                    }
                }
            }
            br.close();
            return null;
        } catch (Exception erro) {
            throw erro;
        }
    }

    // Método validar se tem um numero de telefone igual no alterar
    public Proprietario validarNumeroTelefoneIgual(String cpf, String DDD, String numeroTelefone) throws Exception {
        try {
            FileReader fr = new FileReader(nomeDoArquivoNoDisco);
            BufferedReader br = new BufferedReader(fr);
            String linha = "";
            while ((linha = br.readLine()) != null) {
                String vetorStr[] = linha.split(";");
                if (!linha.contains(cpf)) {

                    String nomeCompleto = vetorStr[0];
                    String emailAux = vetorStr[1];
                    String cpfAux = vetorStr[2];
                    String numeroCNHAux = vetorStr[3];
                    String categoriaCNHAux = vetorStr[4];
                    String DDI = vetorStr[5];
                    String DDDAux = vetorStr[6];
                    String numeroTelefoneAux = vetorStr[7];
                    String fotoProprietario = vetorStr[8];
                    String fotoCNH = vetorStr[9];
                    String telefoneComDDDAux = DDDAux + numeroTelefoneAux;
                    String telefoneComDDD = DDD + numeroTelefone;

                    if (telefoneComDDD.equalsIgnoreCase(telefoneComDDDAux)) {
                        Telefone telefone = new Telefone();
                        telefone.setDDI(DDI);
                        telefone.setDDD(DDDAux);
                        telefone.setNumero(numeroTelefoneAux);
                        Proprietario objetoProprietario = new Proprietario(nomeCompleto, emailAux, cpfAux, numeroCNHAux,
                                categoriaCNHAux, telefone, fotoProprietario, fotoCNH);
                        br.close();
                        return objetoProprietario;

                    }
                }
            }
            br.close();
            return null;
        } catch (Exception erro) {
            throw erro;
        }
    }

    // Método validar se tem uma foto de proprietario já cadastrado no alterar
    public Proprietario validarFotoProprietario(String cpf, String fotoProprietario) throws Exception {
        try {
            FileReader fr = new FileReader(nomeDoArquivoNoDisco);
            BufferedReader br = new BufferedReader(fr);
            String linha = "";
            while ((linha = br.readLine()) != null) {
                String vetorStr[] = linha.split(";");
                if (!linha.contains(cpf)) {

                    String nomeCompleto = vetorStr[0];
                    String emailAux = vetorStr[1];
                    String cpfAux = vetorStr[2];
                    String numeroCNHAux = vetorStr[3];
                    String categoriaCNHAux = vetorStr[4];
                    String DDI = vetorStr[5];
                    String DDDAux = vetorStr[6];
                    String numeroTelefoneAux = vetorStr[7];
                    String fotoProprietarioAux = vetorStr[8];
                    String fotoCNH = vetorStr[9];

                    if (fotoProprietario.equalsIgnoreCase(fotoProprietarioAux)) {
                        Telefone telefone = new Telefone();
                        telefone.setDDI(DDI);
                        telefone.setDDD(DDDAux);
                        telefone.setNumero(numeroTelefoneAux);
                        Proprietario objetoProprietario = new Proprietario(nomeCompleto, emailAux, cpfAux, numeroCNHAux,
                                categoriaCNHAux, telefone, fotoProprietarioAux, fotoCNH);
                        br.close();
                        return objetoProprietario;

                    }
                }
            }
            br.close();
            return null;
        } catch (Exception erro) {
            throw erro;
        }
    }

    // Método validar se tem uma foto da CNH já cadastrada
    public Proprietario validarFotoCNH(String cpf, String fotoCNH) throws Exception {
        try {
            FileReader fr = new FileReader(nomeDoArquivoNoDisco);
            BufferedReader br = new BufferedReader(fr);
            String linha = "";
            while ((linha = br.readLine()) != null) {
                String vetorStr[] = linha.split(";");
                if (!linha.contains(cpf)) {

                    String nomeCompleto = vetorStr[0];
                    String emailAux = vetorStr[1];
                    String cpfAux = vetorStr[2];
                    String numeroCNHAux = vetorStr[3];
                    String categoriaCNHAux = vetorStr[4];
                    String DDI = vetorStr[5];
                    String DDDAux = vetorStr[6];
                    String numeroTelefoneAux = vetorStr[7];
                    String fotoProprietario = vetorStr[8];
                    String fotoCNHAux = vetorStr[9];

                    if (fotoCNH.equalsIgnoreCase(fotoCNHAux)) {
                        Telefone telefone = new Telefone();
                        telefone.setDDI(DDI);
                        telefone.setDDD(DDDAux);
                        telefone.setNumero(numeroTelefoneAux);
                        Proprietario objetoProprietario = new Proprietario(nomeCompleto, emailAux, cpfAux, numeroCNHAux,
                                categoriaCNHAux, telefone, fotoProprietario, fotoCNHAux);
                        br.close();
                        return objetoProprietario;

                    }
                }
            }
            br.close();
            return null;
        } catch (Exception erro) {
            throw erro;
        }
    }

    //Método Validar email
    private static final String EMAIL_REGEX
            //^ - Representa o início da string
            //\w+ - Corresponde a um ou mais caracteres alfanuméricos. Isso representa a parte inicial do endereço de e-mail antes do símbolo "@".
            //([\.-]?\w+)* - Representa um grupo que corresponde a um ou mais sequências de caracteres que podem ser um ponto (.), um traço (-) ou um conjunto de caracteres alfanuméricos após o ponto ou traço. Esse grupo é colocado entre parênteses e seguido por um asterisco (*) para indicar que ele pode ocorrer zero ou mais vezes. Essa parte representa os subdomínios ou partes separadas por pontos no domínio do endereço de e-mail.
            //@ - Corresponde ao símbolo "@".
            //\w+ - Corresponde a um ou mais caracteres alfanuméricos. Isso representa o domínio do endereço de e-mail.
            //([\.-]?\w+)* - Semelhante à parte anterior, representa os subdomínios ou partes separadas por pontos no domínio do endereço de e-mail.
            //(\.\w{2,3})+ - Representa um grupo que corresponde a um ponto (.) seguido de dois ou três caracteres alfanuméricos. Isso representa a parte final do endereço de e-mail, como ".com" ou ".org". O sinal de mais (+) indica que esse grupo pode ocorrer uma ou mais vezes.
            //$ - Representa o final da string.
            = "^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$";

    public static boolean validarEmail(String email) throws Exception {
        Pattern pattern = Pattern.compile(EMAIL_REGEX, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        if (email.equalsIgnoreCase("")) {
            throw new Exception("O campo do email não pode estar vazio");
        }
        return matcher.matches();
    }

    //Método validar CPF
    public static boolean validarCPF(String cpf) {
        cpf = cpf.replaceAll("[^0-9]", ""); // Remove caracteres não numéricos

        if (cpf.length() != 11) {
            return false;
        }

        // Verifica se todos os dígitos são iguais (ex: 111.111.111-11)
        boolean todosDigitosIguais = true;
        for (int i = 1; i < 11; i++) {
            if (cpf.charAt(i) != cpf.charAt(0)) {
                todosDigitosIguais = false;
                break;
            }
        }
        if (todosDigitosIguais) {
            return false;
        }

        // Verifica o primeiro dígito verificador
        int soma = 0;
        for (int i = 0; i < 9; i++) {
            int digito = cpf.charAt(i) - '0';
            soma += digito * (10 - i);
        }
        int resto = soma % 11;
        int digitoVerificador1 = (resto < 2) ? 0 : 11 - resto;
        if (digitoVerificador1 != cpf.charAt(9) - '0') {
            return false;
        }

        // Verifica o segundo dígito verificador
        soma = 0;
        for (int i = 0; i < 10; i++) {
            int digito = cpf.charAt(i) - '0';
            soma += digito * (11 - i);
        }
        resto = soma % 11;
        int digitoVerificador2 = (resto < 2) ? 0 : 11 - resto;
        if (digitoVerificador2 != cpf.charAt(10) - '0') {
            return false;
        }

        return true;
    }

    //Validar Número da CNH se tem 11 dígitos
    public boolean validarNumeroCNH(String numeroCNH) throws Exception {

        // Validar Número
        //^ e $ são âncoras de início e fim, respectivamente, que garantem que a expressão corresponda a toda a cadeia de caracteres
        //[0-9] define uma classe de caracteres que representa qualquer dígito numérico de 0 a 9.
        //+ indica que o caractere anterior (no caso, a classe de caracteres [0-9]) pode ocorrer uma ou mais vezes.
        String regex = "^[0-9]+$";
        if (numeroCNH.length() != 11) {
            throw new Exception("Número de CNH deve ter 11 dígitos");
        }
        return numeroCNH.matches(regex);
    }

    //Validar DDI
    public static boolean validarDDI(String ddi) {

        // Expressão regular para validar DDI com o formato "+##"
        //"\" antes do "+" é usado para escapar o caractere especial "+", pois "+" tem um significado especial em expressões regulares e precisa ser tratado literalmente.
        //"\+" corresponde ao caractere "+" literal.
        //"\d{2}" corresponde a dois dígitos numéricos. O padrão "\d" representa qualquer dígito numérico de 0 a 9 e "{2}" indica que o padrão deve ocorrer exatamente duas vezes.
        //"$" é uma âncora de fim, que garante que a expressão corresponda ao final da cadeia de caracteres.
        String regex = "\\+\\d{2}$";

        // Verifica se a string do DDI corresponde ao regex
        boolean isValid = ddi.matches(regex);

        return isValid;
    }

    //Validar DDD
    private static final Set<String> codigosDDD;

    static {
        codigosDDD = new HashSet<>();
        codigosDDD.add("11");
        codigosDDD.add("12");
        codigosDDD.add("13");
        codigosDDD.add("14");
        codigosDDD.add("15");
        codigosDDD.add("16");
        codigosDDD.add("17");
        codigosDDD.add("18");
        codigosDDD.add("19");
        codigosDDD.add("21");
        codigosDDD.add("22");
        codigosDDD.add("24");
        codigosDDD.add("27");
        codigosDDD.add("28");
        codigosDDD.add("31");
        codigosDDD.add("32");
        codigosDDD.add("33");
        codigosDDD.add("34");
        codigosDDD.add("35");
        codigosDDD.add("37");
        codigosDDD.add("38");
        codigosDDD.add("41");
        codigosDDD.add("42");
        codigosDDD.add("43");
        codigosDDD.add("44");
        codigosDDD.add("45");
        codigosDDD.add("46");
        codigosDDD.add("47");
        codigosDDD.add("48");
        codigosDDD.add("49");
        codigosDDD.add("51");
        codigosDDD.add("53");
        codigosDDD.add("54");
        codigosDDD.add("55");
        codigosDDD.add("61");
        codigosDDD.add("62");
        codigosDDD.add("63");
        codigosDDD.add("64");
        codigosDDD.add("65");
        codigosDDD.add("66");
        codigosDDD.add("67");
        codigosDDD.add("68");
        codigosDDD.add("69");
        codigosDDD.add("71");
        codigosDDD.add("73");
        codigosDDD.add("74");
        codigosDDD.add("75");
        codigosDDD.add("77");
        codigosDDD.add("79");
        codigosDDD.add("81");
        codigosDDD.add("82");
        codigosDDD.add("83");
        codigosDDD.add("84");
        codigosDDD.add("85");
        codigosDDD.add("86");
        codigosDDD.add("87");
        codigosDDD.add("88");
        codigosDDD.add("89");
        codigosDDD.add("91");
        codigosDDD.add("92");
        codigosDDD.add("93");
        codigosDDD.add("94");
        codigosDDD.add("95");
        codigosDDD.add("96");
        codigosDDD.add("97");
        codigosDDD.add("98");
        codigosDDD.add("99");
    }

    public static boolean validarDDD(String ddd) {
        String regex = "\\((\\d{2})\\)";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(ddd);

        if (matcher.matches()) {
            String codigoDDD = matcher.group(1);
            return codigosDDD.contains(codigoDDD);
        }

        return false;
    }

    public boolean validarNumero(String numero) throws Exception {
        // Validar Número
        String regex = "^[0-9]+$";
        if (numero.length() != 9) {
            throw new Exception("Número deve ter 9 dígitos");
        }
        return numero.matches(regex);
    }

    @Override
    public void incluir(Proprietario objProprietario) throws Exception {
        //Regras de Negócios de incluir
        //Não pode haver CPF duplicado na agenda
        //Não pode permitir a inclusão de ninguém sem preencher todos os campos(cpf,nome,email)
        //Validar CPF para não colocar letras
        //Validar email para ter @ e .com

        //Validar Nome
        if (objProprietario.getNomeCompleto().equalsIgnoreCase("")) {
            throw new Exception("O campo do nome do Proprietário não pode estar vazio");
        }
        //validar Email como deve escrever no texto String email = "usuario@email.com";
        if (validarEmail(objProprietario.getEmail())) {
        } else {
            throw new Exception("Email Inválido");
        }
        //Validar email igual
        if (validarEmailIgual(objProprietario.getCpf(), objProprietario.getEmail()) != null) {
            throw new Exception("Esse email já está cadastrado!!!");
        }

        //Validando CPF
        boolean valido = validarCPF(objProprietario.getCpf());
        if (valido) {
        } else {
            throw new Exception("CPF Inválido");
        }
        if (ProprietarioPersistencia.buscar(objProprietario.getCpf()) != null) {
            throw new Exception("Este cpf já está cadastrado");
        }
        //Validar numero CNH
        if (validarNumeroCNH(objProprietario.getNumeroCNH())) {

        } else {
            throw new Exception("Número da CNH inválido");
        }
        //Validar número CNH igual
        if (validarNumeroCNHIgual(objProprietario.getCpf(), objProprietario.getNumeroCNH()) != null) {
            throw new Exception("Esse número de CNH já está cadastrado!!!");
        }

        //Validar DDI
        if (validarDDI(objProprietario.getTelefone().getDDI())) {
        } else {
            throw new Exception("DDI inválido");
        }

        //Validar DDD
        if (validarDDD(objProprietario.getTelefone().getDDD())) {
        } else {
            throw new Exception("DDD inválido");
        }

        //Validar numero
        if (validarNumero(objProprietario.getTelefone().getNumero())) {
        } else {
            throw new Exception("Número inválido");
        }
        //Validar Número de telefone igual
        if (validarNumeroTelefoneIgual(objProprietario.getCpf(), objProprietario.getTelefone().getDDD(),
                objProprietario.getTelefone().getNumero()) != null) {
            throw new Exception("Esse número de telefone já está cadastrado!!!");
        }
        //Validar se selecionou uma imagem para o proprietário

        if (objProprietario.getFotoProprietario().equalsIgnoreCase("")) {
            throw new Exception("Por favor selecione uma foto do proprietário");
        }

        //Validar se outra pessoa tem a mesma foto de proprietario cadastrado
        if (validarFotoProprietario(objProprietario.getCpf(), objProprietario.getFotoProprietario()) != null) {
            throw new Exception("Outra pessoa já está cadastrada com essa foto, por favor selecione outra foto de proprietário!");
        }

        //Validar se selecionou uma imagem para CNH
        if (objProprietario.getFotoCNH().equalsIgnoreCase("")) {
            throw new Exception("Por favor selecione uma foto da CNH");
        }

        //Validar se outra pessoa tem a mesma foto de proprietario cadastrado
        if (validarFotoCNH(objProprietario.getCpf(), objProprietario.getFotoCNH()) != null) {
            throw new Exception("Outra pessoa já está cadastrada com essa foto, por favor selecione outra foto de CNH!");
        }

        ProprietarioPersistencia.incluir(objProprietario);
    }

    @Override
    public Proprietario buscar(String cpf) throws Exception {
        try {
            //instancia com  referencia a contato e verifica passando parametro CPF

            if (cpf.equals("")) {
                throw new Exception("Digite um cpf para buscar");
            } else {

                ProprietarioDAO contatoDAO = new ProprietarioDAO();
                Proprietario proprietario = new Proprietario();
                proprietario = contatoDAO.buscar(cpf);

                //verifica se retorno do contato esta null, caso esteja null -> retorna contato inexistente;
                if (proprietario == null) {
                    throw new Exception("CPF INEXISTENTE!");
                }
                //se passar pela condição ele retorna o contato;
                return proprietario;
            }
        } catch (Exception erro) {
            String msg = erro.getMessage();
            throw new Exception(msg);
        }
    }

    @Override
    public ArrayList<Proprietario> obterListagem() throws Exception {
        //retorna arraylist de contatos
        return ProprietarioPersistencia.obterListagem();
    }

    @Override
    public void alterar(Proprietario objProprietario) throws Exception {

        //Validar Nome
        if (objProprietario.getNomeCompleto().equalsIgnoreCase("")) {
            throw new Exception("O campo do nome do Proprietário não pode estar vazio");
        }

        //Validar Email como deve escrever no texto String email = "usuario@email.com";
        if (validarEmail(objProprietario.getEmail())) {
        } else {
            throw new Exception("Email Inválido");
        }

        //Validar se outro proprietario tem o mesmo email
        if (validarEmailIgual(objProprietario.getCpf(), objProprietario.getEmail()) != null) {
            throw new Exception("Outra pessoa já cadastrou esse email, digite um email diferente");
        } else {

        }
        if (objProprietario.getCpf().equalsIgnoreCase("")) {
            throw new Exception("Digite algo no campo do CPF");
        } else {
            ProprietarioDAO ProprietarioDAO = new ProprietarioDAO();
            Proprietario proprietario = new Proprietario();
            proprietario = ProprietarioDAO.buscar(objProprietario.getCpf());

            if (proprietario == null) {
                throw new Exception("Proprietário Inexistente");
            } else {
                //Validar numero CNH
                if (validarNumeroCNH(objProprietario.getNumeroCNH())) {

                } else {
                    throw new Exception("Número da CNH inválido");
                }

                //Validar se outro proprietario tem o mesmo numero da cnh
                if (validarNumeroCNHIgual(objProprietario.getCpf(), objProprietario.getNumeroCNH()) != null) {
                    throw new Exception("Outra pessoa já cadastrou esse número de CNH, por favor digite um número diferente");
                } else {

                }

                //Validar DDI
                if (validarDDI(objProprietario.getTelefone().getDDI())) {
                } else {
                    throw new Exception("DDI inválido");
                }

                //Validar DDD
                if (validarDDD(objProprietario.getTelefone().getDDD())) {
                } else {
                    throw new Exception("DDD inválido");
                }

                //Validar Numero de telefone
                if (validarNumero(objProprietario.getTelefone().getNumero())) {
                } else {
                    throw new Exception("Número inválido");
                }

                //Validar se outro proprietario tem o mesmo número de telefone
                if (validarNumeroTelefoneIgual(objProprietario.getCpf(),
                        objProprietario.getTelefone().getDDD(), objProprietario.getTelefone().getNumero()) != null) {
                    throw new Exception("Outra pessoa já cadastrou esse número de Telefone, por favor digite um número diferentes");
                } else {

                }
                //Validar se outra pessoa tem a mesma foto de proprietario cadastrado
                if (validarFotoProprietario(objProprietario.getCpf(), objProprietario.getFotoProprietario()) != null) {
                    throw new Exception("Outra pessoa já está cadastrada com essa foto, por favor selecione outra foto de proprietário!");
                } else {

                }

                //Validar se outra pessoa tem a mesma foto de CNH cadastrado
                if (validarFotoCNH(objProprietario.getCpf(), objProprietario.getFotoCNH()) != null) {
                    throw new Exception("Outra pessoa já está cadastrada com essa foto, por favor selecione outra foto de CNH!");
                } else {

                }
            }

        }
        ProprietarioPersistencia.alterar(objProprietario);
    }

    @Override
    public void excluir(String cpf) throws Exception {
        ProprietarioPersistencia.excluir(cpf);
    }
}
