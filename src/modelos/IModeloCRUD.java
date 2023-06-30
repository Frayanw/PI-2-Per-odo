
package modelos;

import java.util.ArrayList;

public interface IModeloCRUD {

    public void incluir(Modelo objModelo) throws Exception;

    public void alterar(Modelo objModelo) throws Exception;

    public void excluir(String id) throws Exception;

    public ArrayList<Modelo> obterListagem() throws Exception;
    
}
