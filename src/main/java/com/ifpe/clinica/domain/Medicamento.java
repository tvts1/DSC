package com.ifpe.clinica.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Objects;

@Entity
@Table(name = "TB_MEDICAMENTO")
public class Medicamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_MEDICAMENTO")
    private Long id;

    @NotBlank(message = "{medicamento.nome.notblank}")
    @Size(max = 255, message = "{medicamento.nome.size}")
    @Column(name = "TXT_NOME", length = 255, nullable = false)
    private String nome;

    @NotBlank(message = "{medicamento.dose.notblank}")
    @Size(max = 50, message = "{medicamento.dose.size}")
    @Column(name = "TXT_DOSE", length = 50, nullable = false)
    private String dose;

    @NotNull(message = "{medicamento.prontuario.notnull}")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRONTUARIO_ID", nullable = false)
    private Prontuario prontuario;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDose() {
        return dose;
    }

    public void setDose(String dose) {
        this.dose = dose;
    }

    public Prontuario getProntuario() {
        return prontuario;
    }

    public void setProntuario(Prontuario prontuario) {
        this.prontuario = prontuario;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.id);
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
        final Medicamento other = (Medicamento) obj;
        return Objects.equals(this.id, other.id);
    }

}
