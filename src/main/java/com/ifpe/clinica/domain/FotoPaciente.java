package com.ifpe.clinica.domain;

import com.ifpe.clinica.enums.CategoriaFoto;
import com.ifpe.clinica.enums.TipoImagem;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "TB_FOTO_PACIENTE")
public class FotoPaciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_FOTO")
    private Long id;

    @NotBlank(message = "{fotoPaciente.titulo.notblank}")
    @Size(max = 100, message = "{fotoPaciente.titulo.size}")
    @Column(name = "TXT_TITULO", length = 100, nullable = false)
    private String titulo;

    @Size(max = 500, message = "{fotoPaciente.descricao.size}")
    @Column(name = "TXT_DESCRICAO", length = 500)
    private String descricao;

    @NotNull(message = "{fotoPaciente.imagem.notnull}")
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "BIN_IMAGEM", nullable = false)
    private byte[] imagem;

    @NotNull(message = "{fotoPaciente.tipoImagem.notnull}")
    @Enumerated(EnumType.STRING)
    @Column(name = "TXT_TIPO_IMAGEM", length = 50, nullable = false)
    private TipoImagem tipoImagem;

    @NotNull(message = "{fotoPaciente.tamanho.notnull}")
    @Min(value = 1, message = "{fotoPaciente.tamanho.min}")
    @Column(name = "TXT_TAMANHO", nullable = false)
    private Long tamanho;

    @NotNull(message = "{fotoPaciente.dataUpload.notnull}")
    @PastOrPresent(message = "{fotoPaciente.dataUpload.past}")
    @Column(name = "DT_UPLOAD", nullable = false)
    private LocalDateTime dataUpload = LocalDateTime.now();

    @NotNull(message = "{fotoPaciente.categoria.notnull}")
    @Enumerated(EnumType.STRING)
    @Column(name = "TXT_CATEGORIA", length = 30, nullable = false)
    private CategoriaFoto categoria;

    @NotNull(message = "{fotoPaciente.paciente.notnull}")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PACIENTE_ID", nullable = false)
    private Paciente paciente;

    public FotoPaciente() {
    }

    public FotoPaciente(Long id, String titulo, String descricao, byte[] imagem,
            TipoImagem tipoImagem, Long tamanho, CategoriaFoto categoria, Paciente paciente) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.imagem = imagem;
        this.tipoImagem = tipoImagem;
        this.tamanho = tamanho;
        this.categoria = categoria;
        this.paciente = paciente;
    }

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

    public TipoImagem getTipoImagem() {
        return tipoImagem;
    }

    public void setTipoImagem(TipoImagem tipoImagem) {
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
