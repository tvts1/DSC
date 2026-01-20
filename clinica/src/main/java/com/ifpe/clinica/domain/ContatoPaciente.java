package com.ifpe.clinica.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "TB_CONTATO_PACIENTE")
public class ContatoPaciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CONTATO")
    private Long id;

    @Embedded
    private Contato contato;

    @Column(name = "TXT_OBSERVACOES", length = 500)
    private String observacoes;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PACIENTE_ID")
    private Paciente paciente;

    public ContatoPaciente() {
    }

    public ContatoPaciente(Long id, Contato contato, String observacoes, Paciente paciente) {
        this.id = id;
        this.contato = contato;
        this.observacoes = observacoes;
        this.paciente = paciente;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Contato getContato() {
        return contato;
    }

    public void setContato(Contato contato) {
        this.contato = contato;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ContatoPaciente other = (ContatoPaciente) obj;
        return Objects.equals(this.id, other.id);
    }

    
}