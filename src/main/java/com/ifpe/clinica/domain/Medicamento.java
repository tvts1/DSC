package com.ifpe.clinica.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.Objects;

@Entity
@Table(name = "TB_MEDICAMENTO")
public class Medicamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_MEDICAMENTO")
    private Long id;

    @NotBlank(message = "O nome do medicamento é obrigatório")
    @Column(name = "TXT_NOME", length = 255, nullable = false)
    private String nome;

    @NotBlank(message = "A dose é obrigatória")
    @Column(name = "TXT_DOSE", length = 50, nullable = false)
    private String dose;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRONTUARIO_ID")
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
