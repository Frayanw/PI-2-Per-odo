package modelos;

import java.util.ArrayList;

public interface IVeiculoCRUD {

    public void incluir(Veiculo objVeiculo) throws Exception;

    public Veiculo buscar(String id) throws Exception;

    public void alterar(Veiculo objVeiculo) throws Exception;

    public void excluir(String id) throws Exception;

    public ArrayList<Veiculo> obterListagem() throws Exception;
}
