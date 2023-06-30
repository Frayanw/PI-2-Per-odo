package modelos;

import java.util.ArrayList;

public interface ICategoriaGastosCRUD {

    public void incluir(CategoriaGastos objCategoriaGastos) throws Exception;

    public void alterar(CategoriaGastos objCategoriaGastos) throws Exception;

    public void excluir(String id) throws Exception;

    public ArrayList<CategoriaGastos> obterListagem() throws Exception;
}
