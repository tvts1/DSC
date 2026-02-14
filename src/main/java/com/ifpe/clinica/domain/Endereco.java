package com.ifpe.clinica.domain;

import com.ifpe.clinica.validation.EstadoValido;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.Objects;

@Entity
@Table(name = "TB_ENDERECO")
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ENDERECO")
    private Long id;

    @NotBlank(message = "A rua é obrigatória")
    @Column(name = "TXT_RUA", nullable = false, length = 255)
    private String rua;

    @NotBlank(message = "A cidade é obrigatória")
    @Column(name = "TXT_CIDADE", nullable = false, length = 100)
    private String cidade;

    @NotBlank(message = "O estado é obrigatório")
    @EstadoValido
    @Column(name = "TXT_ESTADO", nullable = false, length = 2)
    private String estado;

    @OneToOne(mappedBy = "endereco", fetch = FetchType.LAZY)
    private Paciente paciente;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.id);
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
        final Endereco other = (Endereco) obj;
        return Objects.equals(this.id, other.id);
    }
    
    
}
