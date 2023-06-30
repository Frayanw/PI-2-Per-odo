package modelos;

import java.util.ArrayList;

public interface IMarcaCRUD {

    public void incluir(Marca objMarca) throws Exception;

    public void alterar(Marca objMarca) throws Exception;

    public void excluir(String id) throws Exception;

    public ArrayList<Marca> obterListagem() throws Exception;
    
}
