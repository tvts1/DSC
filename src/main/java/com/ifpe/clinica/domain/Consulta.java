package com.ifpe.clinica.domain;

import com.ifpe.clinica.validation.DiaUtil;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "TB_CONSULTA")
public class Consulta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CONSULTA")
    private Long id;
    
    @NotNull(message = "A data da consulta é obrigatória")
    @Future(message = "A consulta deve ser agendada para o futuro")
    @DiaUtil(message = "Consultas apenas em dias úteis")
    @Column(name = "DT_CONSULTA", nullable = false)
    private LocalDateTime data;

    @NotNull(message = "O médico é obrigatório")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEDICO_ID")
    private Medico medico;

    @NotNull(message = "O paciente é obrigatório")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PACIENTE_ID")
    private Paciente paciente;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "TB_CONSULTA_EXAME",
        joinColumns = @JoinColumn(name = "CONSULTA_ID"),
        inverseJoinColumns = @JoinColumn(name = "EXAME_ID")
    )
    private List<Exame> exames = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public List<Exame> getExames() {
        return exames;
    }

    public void setExames(List<Exame> exames) {
        this.exames = exames;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.id);
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
        final Consulta other = (Consulta) obj;
        return Objects.equals(this.id, other.id);
    }
}
