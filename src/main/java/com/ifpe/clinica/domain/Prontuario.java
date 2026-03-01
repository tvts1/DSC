package com.ifpe.clinica.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "TB_PRONTUARIO")
public class Prontuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PRONTUARIO")
    private Long id;

    @NotBlank(message = "{prontuario.descricao.notblank}")
    @Size(max = 500, message = "{prontuario.descricao.size}")
    @Column(name = "TXT_DESCRICAO", length = 500, nullable = false)
    private String descricao;

    @NotNull(message = "{prontuario.paciente.notnull}")
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PACIENTE_ID", nullable = false)
    private Paciente paciente;

    @OneToMany(mappedBy = "prontuario", fetch = FetchType.LAZY,
               cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Medicamento> medicamentos = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public List<Medicamento> getMedicamentos() {
        return medicamentos;
    }

    public void setMedicamentos(List<Medicamento> medicamentos) {
        this.medicamentos = medicamentos;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + Objects.hashCode(this.id);
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
        final Prontuario other = (Prontuario) obj;
        return Objects.equals(this.id, other.id);
    }
}