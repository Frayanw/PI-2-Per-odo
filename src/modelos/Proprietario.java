/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos;

import java.awt.Image;

/**
 *
 * @author Frayan
 */
public class Proprietario {

    //Atributos
    private String nomeCompleto = "";
    private String email = "";
    private String cpf = "";
    private String numeroCNH = "";
    private String categoriaCNH = "";
    private Telefone telefone = null;
    private String fotoProprietario = "";
    private String fotoCNH = "";

    //Construtores
    public Proprietario() {
        nomeCompleto = "";
        email = "";
        cpf = "";
        numeroCNH = "";
        categoriaCNH = "";
        telefone = null;
        fotoProprietario = "";
        fotoCNH = "";
    }

    public Proprietario(String nomeCompleto, String email, String cpf, String numeroCNH, String categoriaCNH, Telefone telefone,
            String foto, String fotoCNH) {
        this.nomeCompleto = nomeCompleto;
        this.email = email;
        this.cpf = cpf;
        this.numeroCNH = numeroCNH;
        this.categoriaCNH = categoriaCNH;
        this.telefone = telefone;
        this.fotoProprietario = foto;
        this.fotoCNH = fotoCNH;
    }

    //Métodos
    @Override
    public String toString() {
        return nomeCompleto + ";" + email + ";" + cpf + ";" + numeroCNH + ";" + categoriaCNH + ";" +telefone.toString() + ";" + fotoProprietario + ";" + fotoCNH + "\n";
    }
    
    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public Telefone getTelefone() {
        return telefone;
    }

    public void setTelefone(Telefone telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumeroCNH() {
        return numeroCNH;
    }

    public void setNumeroCNH(String numeroCNH) {
        this.numeroCNH = numeroCNH;
    }

    public String getCategoriaCNH() {
        return categoriaCNH;
    }

    public void setCategoriaCNH(String categoriaCNH) {
        this.categoriaCNH = categoriaCNH;
    }

    public String getFotoProprietario() {
        return fotoProprietario;
    }

    public void setFotoProprietario(String fotoProprietario) {
        this.fotoProprietario = fotoProprietario;
    }

    public String getFotoCNH() {
        return fotoCNH;
    }

    public void setFotoCNH(String fotoCNH) {
        this.fotoCNH = fotoCNH;
    }

}
