package com.ifpe.clinica.domain;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.validator.constraints.br.CPF;

@Entity
@Table(name = "TB_PACIENTE")
@DiscriminatorValue("PACIENTE")
@PrimaryKeyJoinColumn(name = "ID_PESSOA", referencedColumnName = "ID_PESSOA")
public class Paciente extends Pessoa{
    
    @NotBlank(message = "{paciente.cpf.notblank}")
    @CPF(message = "{paciente.cpf.invalido}")
    @Column(name = "TXT_CPF", nullable = false, length = 11, unique = true)
    private String cpf;

    @Valid
    @NotNull(message = "{paciente.endereco.notnull}") // <-- ADICIONE ESTA LINHA AQUI!
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "ENDERECO_ID", nullable = false)
    private Endereco endereco;

    @OneToOne(mappedBy = "paciente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Prontuario prontuario;

    @OneToMany(mappedBy = "paciente", fetch = FetchType.LAZY,
               cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Consulta> consultas = new ArrayList<>();

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public Prontuario getProntuario() {
        return prontuario;
    }

    public void setProntuario(Prontuario prontuario) {
        this.prontuario = prontuario;
    }

    public List<Consulta> getConsultas() {
        return consultas;
    }

    public void setConsultas(List<Consulta> consultas) {
        this.consultas = consultas;
    }
}
