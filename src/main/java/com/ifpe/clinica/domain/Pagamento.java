package com.ifpe.clinica.domain;

import com.ifpe.clinica.enums.FormaPagamento;
import com.ifpe.clinica.enums.StatusPagamento;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "TB_PAGAMENTO")
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PAGAMENTO")
    private Long id;

    @NotNull(message = "{pagamento.consulta.notnull}")
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "CONSULTA_ID", nullable = false, unique = true)
    private Consulta consulta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CONVENIO_ID")
    private Convenio convenio;

    @NotNull(message = "{pagamento.formaPagamento.notnull}")
    @Enumerated(EnumType.STRING)
    @Column(name = "FORMA_PAGAMENTO", nullable = false)
    private FormaPagamento formaPagamento;

    @NotNull(message = "{pagamento.status.notnull}")
    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS_PAGAMENTO", nullable = false)
    private StatusPagamento status;

    @NotNull(message = "{pagamento.valorTotal.notnull}")
    @Positive(message = "{pagamento.valorTotal.positive}")
    @Column(name = "VALOR_TOTAL", nullable = false, precision = 10, scale = 2)
    private BigDecimal valorTotal;

    @PositiveOrZero(message = "{pagamento.valorPago.positiveOrZero}")
    @Column(name = "VALOR_PAGO", precision = 10, scale = 2)
    private BigDecimal valorPago;

    @PositiveOrZero(message = "{pagamento.desconto.positiveOrZero}")
    @Column(name = "VALOR_DESCONTO", precision = 10, scale = 2)
    private BigDecimal desconto;

    @PositiveOrZero(message = "{pagamento.acrescimo.positiveOrZero}")
    @Column(name = "VALOR_ACRESCIMO", precision = 10, scale = 2)
    private BigDecimal acrescimo;

    @NotNull(message = "{pagamento.dataGeracao.notnull}")
    @PastOrPresent(message = "{pagamento.dataGeracao.past}")
    @Column(name = "DT_GERACAO", nullable = false)
    private LocalDateTime dataGeracao = LocalDateTime.now();

    @PastOrPresent(message = "{pagamento.dataPagamento.past}")
    @Column(name = "DT_PAGAMENTO")
    private LocalDateTime dataPagamento;

    @Size(max = 100, message = "{pagamento.codigoTransacao.size}")
    @Column(name = "TXT_CODIGO_TRANSACAO", length = 100)
    private String codigoTransacao;

    @Size(max = 500, message = "{pagamento.observacoes.size}")
    @Column(name = "TXT_OBSERVACOES", length = 500)
    private String observacoes;

    public Pagamento() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Consulta getConsulta() {
        return consulta;
    }

    public void setConsulta(Consulta consulta) {
        this.consulta = consulta;
    }

    public Convenio getConvenio() {
        return convenio;
    }

    public void setConvenio(Convenio convenio) {
        this.convenio = convenio;
    }

    public FormaPagamento getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(FormaPagamento formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public StatusPagamento getStatus() {
        return status;
    }

    public void setStatus(StatusPagamento status) {
        this.status = status;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public BigDecimal getValorPago() {
        return valorPago;
    }

    public void setValorPago(BigDecimal valorPago) {
        this.valorPago = valorPago;
    }

    public BigDecimal getDesconto() {
        return desconto;
    }

    public void setDesconto(BigDecimal desconto) {
        this.desconto = desconto;
    }

    public BigDecimal getAcrescimo() {
        return acrescimo;
    }

    public void setAcrescimo(BigDecimal acrescimo) {
        this.acrescimo = acrescimo;
    }

    public LocalDateTime getDataGeracao() {
        return dataGeracao;
    }

    public void setDataGeracao(LocalDateTime dataGeracao) {
        this.dataGeracao = dataGeracao;
    }

    public LocalDateTime getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(LocalDateTime dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public String getCodigoTransacao() {
        return codigoTransacao;
    }

    public void setCodigoTransacao(String codigoTransacao) {
        this.codigoTransacao = codigoTransacao;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.id);
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
        Pagamento other = (Pagamento) obj;
        return Objects.equals(this.id, other.id);
    }
}
