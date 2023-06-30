package modelos;

public class Telefone {

    //Atributos
    private String DDI = "";
    private String DDD = "";
    private String numero = "";

    //Construtores
    public Telefone() {
        DDI = "";
        DDD = "";
        numero = "";
    }

    public Telefone(String DDI, String DDD, String numero) {
        this.DDI = DDI;
        this.DDD = DDD;
        this.numero = numero;
    }

    //Métodos
    @Override
    public String toString() {
        return DDI + ";" + DDD + ";" + numero;
    }

    public String getDDI() {
        return DDI;
    }

    public void setDDI(String DDI) {
        this.DDI = DDI;
    }

    public String getDDD() {
        return DDD;
    }

    public void setDDD(String DDD) {
        this.DDD = DDD;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

}
