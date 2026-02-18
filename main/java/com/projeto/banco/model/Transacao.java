package com.projeto.banco.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Transacao {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private LocalDateTime dataHora = LocalDateTime.now();
    private BigDecimal valor;
    private String tipo; // "CREDITO", "DEBITO", "TRANSFERENCIA"

    @ManyToOne
    @JoinColumn(name = "conta_id")
    @JsonBackReference // O Jackson vai ignorar este campo no JSON da Transação
    private Conta conta;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_transacao")
    private TipoTransacao tipoTransacao;

    @Column(name = "conta_destino_id")
    private Long contaDestinoId;

    public Transacao() {}

    public Transacao(BigDecimal valor, TipoTransacao tipo, Conta conta, Long contaDestinoId) {
        this.valor = valor;
        this.tipoTransacao = tipo;
        this.conta = conta;
        this.contaDestinoId = contaDestinoId; // Salva o ID da outra conta envolvida
        this.dataHora = LocalDateTime.now();
    }

    public enum TipoTransacao {

        TRANSFERENCIA_ENVIADA("Transferência Enviada"),
        TRANSFERENCIA_RECEBIDA("Transferência Recebida");

        private String descricao;

        TipoTransacao(String descricao) {
            this.descricao = descricao;
        }

        public String getDescricao() {
            return descricao;
        }
    }
}
