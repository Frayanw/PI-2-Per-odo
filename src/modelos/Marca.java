package modelos;

/**
 *
 * @author Frayan
 */
public class Marca {
    //Atributos
    private String id = "";
    private String descricao = "";
    private String logo = "";
    
    //Construtores
    public Marca(){
        id = "";
        descricao = "";
        logo = "";
    }
   public Marca(String id, String descricao, String logo){
       this.id = id;
       this.descricao = descricao;
       this.logo = logo;
   }
   
   //Métodos

    @Override
    public String toString() {
        return id + ";" + descricao + ";" + logo + "\n";
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

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
    
}
