package modelos;

import java.util.ArrayList;

public interface IGastosCRUD {

    public void incluir(Gastos objGastos) throws Exception;

    public Gastos buscar(String id) throws Exception;

    public void alterar(Gastos objGastos) throws Exception;

    public void excluir(String id) throws Exception;

    public ArrayList<Gastos> obterListagem() throws Exception;
}
