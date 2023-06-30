package modelos;

import java.util.ArrayList;

public interface IProprietarioCRUD {

    public void incluir(Proprietario objProprietario) throws Exception;

    public Proprietario buscar(String CPF) throws Exception;

    public void alterar(Proprietario objProprietario) throws Exception;

    public void excluir(String cpf) throws Exception;

    public ArrayList<Proprietario> obterListagem() throws Exception;
}
