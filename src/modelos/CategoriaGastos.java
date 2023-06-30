
package modelos;

public class CategoriaGastos {
      //Atributos
    private String id = "";
    private String descricao = "";
    private String icone = "";
    
    //Construtores
    public CategoriaGastos(){
        id = "";
        descricao = "";
        icone = "";
    }
   public CategoriaGastos(String id, String descricao, String icone){
       this.id = id;
       this.descricao = descricao;
       this.icone = icone;
   }
   
   //Métodos

    @Override
    public String toString() {
        return id + ";" + descricao + ";" + icone + "\n";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }
    
}

