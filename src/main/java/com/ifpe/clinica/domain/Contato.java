package com.ifpe.clinica.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.io.Serializable;

@Embeddable
public class Contato implements Serializable {

    @Size(min = 10, max = 11, message = "{contato.telefone.size}")
    @Pattern(regexp = "^\\d{10,11}$", message = "{contato.telefone.pattern}")
    @Column(name = "TXT_TELEFONE", length = 11)
    private String telefone;

    @NotBlank(message = "{contato.celular.notblank}")
    @Size(min = 11, max = 11, message = "{contato.celular.size}")
    @Pattern(regexp = "^\\d{11}$", message = "{contato.celular.pattern}")
    @Column(name = "TXT_CELULAR", length = 11) 
    private String celular;

    @NotBlank(message = "{contato.email.notblank}")
    @Email(message = "{contato.email.email}")
    @Size(max = 100, message = "{contato.email.size}")
    @Column(name = "TXT_EMAIL", length = 100)
    private String email;

    @Size(min = 10, max = 11, message = "{contato.telefone.size}")
    @Pattern(regexp = "^\\d{10,11}$", message = "{contato.telefone.pattern}")
    @Column(name = "TXT_TELEFONE_EMERGENCIA", length = 11)
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
