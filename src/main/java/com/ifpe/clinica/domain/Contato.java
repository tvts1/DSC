package com.ifpe.clinica.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import java.io.Serializable;

@Embeddable
public class Contato implements Serializable {

    @Pattern(regexp = "\\d{8,20}", message = "Telefone deve conter apenas números")
    @Column(name = "TXT_TELEFONE", length = 20)
    private String telefone;

    @Column(name = "TXT_CELULAR", length = 20)
    private String celular;

    @Column(name = "TXT_EMAIL", length = 100)
    private String email;

    @Column(name = "TXT_TELEFONE_EMERGENCIA", length = 20)
    private String telefoneEmergencia;

    public Contato() {
    }

    public Contato(String telefone, String celular, String email, String telefoneEmergencia) {
        this.telefone = telefone;
        this.celular = celular;
        this.email = email;
        this.telefoneEmergencia = telefoneEmergencia;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefoneEmergencia() {
        return telefoneEmergencia;
    }

    public void setTelefoneEmergencia(String telefoneEmergencia) {
        this.telefoneEmergencia = telefoneEmergencia;
    }
}
