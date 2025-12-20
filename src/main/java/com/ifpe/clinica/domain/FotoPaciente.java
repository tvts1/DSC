package com.ifpe.clinica.domain;

import com.ifpe.clinica.enums.CategoriaFoto;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "TB_FOTO_PACIENTE")
public class FotoPaciente {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_FOTO")
    private Long id;
    
    @Column(name = "TXT_TITULO", length = 100)
    private String titulo;
    
    @Column(name = "TXT_DESCRICAO", length = 500)
    private String descricao;
    
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "BIN_IMAGEM")
    private byte[] imagem;
    
    @Column(name = "TXT_TIPO_IMAGEM", length = 50)
    private String tipoImagem; // "JPG", "PNG", "JPEG"
    
    @Column(name = "TXT_TAMANHO")
    private Long tamanho; // em bytes
    
    @Column(name = "DT_UPLOAD")
    private LocalDateTime dataUpload = LocalDateTime.now();
    
    @Enumerated(EnumType.STRING)
    @Column(name = "TXT_CATEGORIA", length = 30)
    private CategoriaFoto categoria;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PACIENTE_ID", nullable = false)
    private Paciente paciente;

    public FotoPaciente() {
    }

    public FotoPaciente(Long id, String titulo, String descricao, byte[] imagem, 
                       String tipoImagem, Long tamanho, CategoriaFoto categoria, Paciente paciente) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.imagem = imagem;
        this.tipoImagem = tipoImagem;
        this.tamanho = tamanho;
        this.categoria = categoria;
        this.paciente = paciente;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public byte[] getImagem() {
        return imagem;
    }

    public void setImagem(byte[] imagem) {
        this.imagem = imagem;
    }

    public String getTipoImagem() {
        return tipoImagem;
    }

    public void setTipoImagem(String tipoImagem) {
        this.tipoImagem = tipoImagem;
    }

    public Long getTamanho() {
        return tamanho;
    }

    public void setTamanho(Long tamanho) {
        this.tamanho = tamanho;
    }

    public LocalDateTime getDataUpload() {
        return dataUpload;
    }

    public void setDataUpload(LocalDateTime dataUpload) {
        this.dataUpload = dataUpload;
    }

    public CategoriaFoto getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaFoto categoria) {
        this.categoria = categoria;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }
}