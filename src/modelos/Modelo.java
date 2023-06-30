package modelos;

/**
 *
 * @author Frayan
 */
public class Modelo {

    //Atributos
    private String id = "";
    private String descricao = "";
    private String marca = "";
    private String categoria = "";
    private String modelo = "";

    //Construtores
    public Modelo() {
        id = "";
        descricao = "";
        marca = "";
        categoria = "";
        modelo = "";

    }

    public Modelo(String id, String descricao, String marca, String categoria, String modelo) {
        this.id = id;
        this.descricao = descricao;
        this.marca = marca;
        this.categoria = categoria;
        this.modelo = modelo;

    }

    //Métodos
    @Override
    public String toString() {
        return id + ";" + descricao + ";" + marca + ";" + categoria + ";" + modelo + "\n";
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

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

}
