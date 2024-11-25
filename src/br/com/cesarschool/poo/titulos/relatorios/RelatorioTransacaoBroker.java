package br.com.cesarschool.poo.titulos.relatorios;

import br.com.cesarschool.poo.titulos.entidades.Transacao;
import br.com.cesarschool.poo.titulos.repositorios.RepositorioTransacao;
import br.com.cesarschool.poo.titulos.utils.ComparadorPorDataHora;
import br.com.cesarschool.poo.titulos.utils.ComparadorTransacaoPorNomeCredora;
import br.com.cesarschool.poo.titulos.utils.Ordenador;
import br.gov.cesarschool.poo.daogenerico.Entidade;

public class RelatorioTransacaoBroker {

    private final RepositorioTransacao repositorioTransacao;

    public RelatorioTransacaoBroker() {
        this.repositorioTransacao = new RepositorioTransacao();
    }

    public RelatorioTransacaoBroker(RepositorioTransacao repositorioTransacao) {
        this.repositorioTransacao = repositorioTransacao;
    }

    public Transacao[] relatorioTransacaoPorNomeEntidadeCredora() {
        Transacao[] transacoes = obterTransacoesDoRepositorio();
        Ordenador.ordenar(transacoes, new ComparadorTransacaoPorNomeCredora());
        return transacoes;
    }

    public Transacao[] relatorioTransacaoPorDataHora() {
        Transacao[] transacoes = obterTransacoesDoRepositorio();
        ComparadorPorDataHora ctpdh = new ComparadorPorDataHora();
        for (int i = 1; i < transacoes.length; i++) {
            Transacao chave = transacoes[i];
            int j = i - 1;
            while (j >= 0 && ctpdh.comparar(transacoes[j], chave) < 0) {
                transacoes[j + 1] = transacoes[j];
                j--;
            }
            transacoes[j + 1] = chave;
        }
        return transacoes;
    }

    private Transacao[] obterTransacoesDoRepositorio() {
        return converterParaTransacoes(repositorioTransacao.buscarTodos());
    }

    private Transacao[] converterParaTransacoes(Entidade[] entidades) {
        return java.util.Arrays.stream(entidades)
                .filter(Transacao.class::isInstance)
                .map(Transacao.class::cast)
                .toArray(Transacao[]::new);
    }
}