package modelos;

public class Gastos {

    //Atributos
    private String id = "";
    private String proprietario = "";
    private String veiculo = "";
    private String categoriaGastos = "";
    private String data = "";
    private float valor = 0;
    private String iconeCategoria = "";

    //Construtores
    public Gastos() {
        id = "";
        proprietario = "";
        veiculo = "";
        categoriaGastos = "";
        data = "";
        valor = 0;
        iconeCategoria = "";
    }

    public Gastos(String id, String proprietario, String veiculo, String categoriaGastos, String data, float valor,
            String iconeCategoria) {
        this.id = id;
        this.proprietario = proprietario;
        this.veiculo = veiculo;
        this.categoriaGastos = categoriaGastos;
        this.data = data;
        this.valor = valor;
        this.iconeCategoria = iconeCategoria;
    }

    @Override
    public String toString() {
        return id + ";" + proprietario + ";" + veiculo + ";" + categoriaGastos + ";" + data + ";" + valor + ";" + iconeCategoria +"\n";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProprietario() {
        return proprietario;
    }

    public void setProprietario(String proprietario) {
        this.proprietario = proprietario;
    }

    public String getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(String veiculo) {
        this.veiculo = veiculo;
    }

    public String getCategoriaGastos() {
        return categoriaGastos;
    }

    public void setCategoriaGastos(String categoriaGastos) {
        this.categoriaGastos = categoriaGastos;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public String getIconeCategoria() {
        return iconeCategoria;
    }

    public void setIconeCategoria(String iconeCategoria) {
        this.iconeCategoria = iconeCategoria;
    }

}
