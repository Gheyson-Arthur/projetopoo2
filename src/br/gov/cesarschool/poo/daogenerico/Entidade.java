package br.gov.cesarschool.poo.daogenerico;

import java.io.Serializable;
import java.time.LocalDateTime;

/*
 * Esta classe representa uma superclasse de todas as entidades.
 *
 * Deve ter os seguintes atributos (com respectivos set e get):
 *	LocalDateTime dataHoraInclusao,
 *  LocalDateTime dataHoraUltimaAlteracao,
 *	String usuarioInclusao e
 *	String usuarioUltimaAlteracao
 *
 * Deve ter um único construtor sem parâmetros.
 *
 * Deve ser abstrata.
 *
 * Deve ter um método abstrato getIdUnico().
 *
 * Deve implementar a interface Serializable do JAVA
 */

// Deve ser abstrata.
// Deve implementar a interface Serializable do JAVA
public abstract class Entidade implements Serializable {

    // Deve ter os seguintes atributos (com respectivos set e get):
    private LocalDateTime dataHoraInclusao;
    private LocalDateTime dataHoraUltimaAlteracao;
    private String usuarioInclusao;
    private String usuarioUltimaAlteracao;

    // Deve ter um único construtor sem parâmetros.
    public Entidade() {
        this.dataHoraInclusao = LocalDateTime.now();
        this.dataHoraUltimaAlteracao = LocalDateTime.now();
    }

    // Getters e Setters
    public LocalDateTime getDataHoraInclusao() {
        return dataHoraInclusao;
    }
    public void setDataHoraInclusao(LocalDateTime dataHoraInclusao) {
        this.dataHoraInclusao = dataHoraInclusao;
    }

    public LocalDateTime getDataHoraUltimaAlteracao() {
        return dataHoraUltimaAlteracao;
    }
    public void setDataHoraUltimaAlteracao(LocalDateTime dataHoraUltimaAlteracao) {
        this.dataHoraUltimaAlteracao = dataHoraUltimaAlteracao;
    }

    public String getUsuarioInclusao() {
        return usuarioInclusao;
    }
    public void setUsuarioInclusao(String usuarioInclusao) {
        this.usuarioInclusao = usuarioInclusao;
    }

    public String getUsuarioUltimaAlteracao() {
        return usuarioUltimaAlteracao;
    }
    public void setUsuarioUltimaAlteracao(String usuarioUltimaAlteracao) {
        this.usuarioUltimaAlteracao = usuarioUltimaAlteracao;
    }

    // Deve ter um método abstrato getIdUnico().
    public abstract Object getIdUnico();
}