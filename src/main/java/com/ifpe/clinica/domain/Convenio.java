package com.ifpe.clinica.domain;

import com.ifpe.clinica.enums.TipoConvenio;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "TB_CONVENIO")
public class Convenio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CONVENIO")
    private Long id;

    @NotBlank(message = "{convenio.nome.notblank}")
    @Size(max = 100, message = "{convenio.nome.size}")
    @Column(name = "TXT_NOME", nullable = false, length = 100)
    private String nome;

    @NotNull(message = "{convenio.tipo.notnull}")
    @Enumerated(EnumType.STRING)
    @Column(name = "TIPO_CONVENIO", nullable = false)
    private TipoConvenio tipo;

    @NotNull(message = "{convenio.carenciaDias.notnull}")
    @Min(value = 0, message = "{convenio.carenciaDias.min}")
    @Column(name = "NR_CARENCIA_DIAS", nullable = false)
    private Integer carenciaDias;

    public Convenio() {
    }

    public Convenio(Long id, String nome, TipoConvenio tipo, Integer carenciaDias) {
        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
        this.carenciaDias = carenciaDias;
    }
    

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

    public TipoConvenio getTipo() {
        return tipo;
    }

    public void setTipo(TipoConvenio tipo) {
        this.tipo = tipo;
    }

    public Integer getCarenciaDias() {
        return carenciaDias;
    }

    public void setCarenciaDias(Integer carenciaDias) {
        this.carenciaDias = carenciaDias;
    }

    
}

