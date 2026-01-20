package com.ifpe.clinica.domain;

import com.ifpe.clinica.enums.Especialidade;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "TB_MEDICO")
@DiscriminatorValue("MEDICO")
@PrimaryKeyJoinColumn(name = "ID_PESSOA", referencedColumnName = "ID_PESSOA")
public class Medico extends Pessoa {
    
    @Column(name = "TXT_CRM", nullable = false, length = 20, unique = true)
    private String crm;

    @OneToMany(mappedBy = "medico", fetch = FetchType.LAZY,
               cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Consulta> consultas = new ArrayList<>();
    
    @Column(name = "TXT_ESPECIALIDADE")
    @Enumerated(EnumType.STRING)
    private Especialidade especialidade; 

    public String getCrm() {
        return crm;
    }

    public void setCrm(String crm) {
        this.crm = crm;
    }

    public List<Consulta> getConsultas() {
        return consultas;
    }

    public void setConsultas(List<Consulta> consultas) {
        this.consultas = consultas;
    }
    
    public Especialidade getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(Especialidade especialidade) {
        this.especialidade = especialidade;
    }

    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.id);
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
        final Medico other = (Medico) obj;
        return Objects.equals(this.id, other.id);
    }

    
}
