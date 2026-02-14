package com.ifpe.clinica.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "TB_EXAME")
public class Exame {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_EXAME")
    private Long id;

    @NotBlank(message = "O nome do exame é obrigatório")
    @Column(name = "TXT_NOME", length = 255, nullable = false)
    private String nome;

    @ManyToMany(mappedBy = "exames", fetch = FetchType.LAZY)
    private List<Consulta> consultas = new ArrayList<>();

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
        name = "TB_EXAME_ANEXOS",
        joinColumns = @JoinColumn(name = "EXAME_ID", referencedColumnName = "ID_EXAME") 
    )
    @Column(name = "TXT_URL_ANEXO", length = 512, nullable = false)
    private List<String> urlsAnexos = new ArrayList<>();
    
    
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

    public List<Consulta> getConsultas() {
        return consultas;
    }

    public void setConsultas(List<Consulta> consultas) {
        this.consultas = consultas;
    }

    public List<String> getUrlsAnexos() {
        return urlsAnexos;
    }

    public void setUrlsAnexos(List<String> urlsAnexos) {
        this.urlsAnexos = urlsAnexos;
    }

    @Override
    public int hashCode() {
        int hash = 5;
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
        final Exame other = (Exame) obj;
        return Objects.equals(this.id, other.id);
    }

    
}

