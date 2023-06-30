package modelos;

public class Veiculo {

    //Atributos
    private String id = "";
    private String nomeProprietario = "";
    private String marca = "";
    private String modelo = "";
    private String cor = "";
    private String placa = "";
    private String ano = "";
    private String tipoCombustivel = "";
    private String fotoMarca = "";
    private String fotoModelo = "";

    //Construtores
    public Veiculo() {
        id = "";
        nomeProprietario = "";
        marca = "";
        modelo = "";
        cor = "";
        placa = "";
        ano = "";
        tipoCombustivel = "";
        fotoMarca = "";
        fotoModelo = "";
    }

    public Veiculo(String id, String nomeProprietario, String marca, String modelo, String cor, String placa, String ano,
            String tipoCombustivel,String fotoMarca,String fotoModelo ) {
        this.id = id;
        this.nomeProprietario = nomeProprietario;
        this.marca = marca;
        this.modelo = modelo;
        this.cor = cor;
        this.placa = placa;
        this.ano = ano;
        this.tipoCombustivel = tipoCombustivel;
        this.fotoMarca = fotoMarca;
        this.fotoModelo = fotoModelo;

    }

    //Métodos
    @Override
    public String toString() {
        return id + ";" + nomeProprietario + ";" + marca + ";" + modelo + ";" + cor + ";" + placa + ";"
                + ano + ";" + tipoCombustivel + ";" + fotoMarca + ";" + fotoModelo + "\n";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNomeProprietario() {
        return nomeProprietario;
    }

    public void setNomeProprietario(String nomeProprietario) {
        this.nomeProprietario = nomeProprietario;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public String getTipoCombustivel() {
        return tipoCombustivel;
    }

    public void setTipoCombustivel(String tipoCombustivel) {
        this.tipoCombustivel = tipoCombustivel;
    }

    public String getFotoMarca() {
        return fotoMarca;
    }

    public void setFotoMarca(String fotoMarca) {
        this.fotoMarca = fotoMarca;
    }

    public String getFotoModelo() {
        return fotoModelo;
    }

    public void setFotoModelo(String fotoModelo) {
        this.fotoModelo = fotoModelo;
    }

}
