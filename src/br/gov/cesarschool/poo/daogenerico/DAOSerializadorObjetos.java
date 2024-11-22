package br.gov.cesarschool.poo.daogenerico;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DAOSerializadorObjetos {
    // Diretório onde os arquivos de entidades serão salvos
    private final String caminhoDiretorio;

    // Gera dinamicamente o caminho para o diretório baseado no nome da entidade.
    public DAOSerializadorObjetos(Class<?> tipoEntidade) {
        this.caminhoDiretorio = "." + File.separator + tipoEntidade.getSimpleName();
        criarDiretorio();
    }

    // Adiciona uma nova entidade ao repositório.
    public boolean incluir(Entidade entidade) {
        File arquivoEntidade = new File(obterCaminhoArquivo(String.valueOf(entidade.getIdUnico())));

        // Verifica se já existe uma entidade com o mesmo ID
        if (arquivoEntidade.exists()) {
            return false;
        }

        // Define a data de inclusão e salva
        entidade.setDataHoraInclusao(LocalDateTime.now());
        return salvarEntidade(entidade);
    }

    // Atualiza uma entidade existente.
    public boolean alterar(Entidade entidade) {
        File arquivoEntidade = new File(obterCaminhoArquivo((String) entidade.getIdUnico()));

        // Se não existir, retorna false (não há entidade para alterar).
        if (!arquivoEntidade.exists()) {
            return false;
        }

        // Define a data de alteração e salva
        entidade.setDataHoraUltimaAlteracao(LocalDateTime.now());
        return salvarEntidade(entidade);
    }

    // Remove uma entidade do repositório.
    public boolean excluir(String idUnico) {
        File arquivoEntidade = new File(obterCaminhoArquivo(idUnico));

        // Verifica se o arquivo existe e tenta excluí-lo
        return arquivoEntidade.exists() && arquivoEntidade.delete();
    }

    // Retorna uma entidade específica com base no idUnico.
    public Entidade buscar(String idUnico) {
        File arquivoEntidade = new File(obterCaminhoArquivo(idUnico));

        // Tenta carregar a entidade do arquivo correspondente
        if (arquivoEntidade.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivoEntidade))) {
                return (Entidade) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Erro ao carregar entidade: " + e.getMessage());
            }
        }

        return null;
    }

    // Retorna todas as entidades armazenadas no diretório.
    public Entidade[] buscarTodos() {
        File diretorio = new File(caminhoDiretorio);
        List<Entidade> entidades = new ArrayList<>();

        File[] arquivos = diretorio.listFiles();
        if (arquivos != null) {
            for (File arquivo : arquivos) {
                try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
                    entidades.add((Entidade) ois.readObject());
                } catch (IOException | ClassNotFoundException e) {
                    System.err.println("Erro ao carregar entidade: " + arquivo.getName() + " - " + e.getMessage());
                }
            }
        }

        return entidades.toArray(new Entidade[0]);
    }

    // Salva ou sobrescreve o arquivo de uma entidade.
    private boolean salvarEntidade(Entidade entidade) {
        File arquivoEntidade = new File(obterCaminhoArquivo(entidade.getIdUnico().toString()));

        // Tenta salvar a entidade no arquivo correspondente
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(arquivoEntidade))) {
            oos.writeObject(entidade);
            return true;
        } catch (IOException e) {
            System.err.println("Erro ao salvar entidade: " + e.getMessage());
            return false;
        }
    }

    // Carrega todas as entidades salvas no repositório.
    private String obterCaminhoArquivo(String idUnico) {
        return caminhoDiretorio + File.separator + idUnico;
    }

    // Método que cria o diretório.
    private void criarDiretorio() {
        File diretorio = new File(caminhoDiretorio);
        if (!diretorio.exists() && !diretorio.mkdirs()) {
            System.err.println("Erro ao criar diretório: " + caminhoDiretorio);
        }
    }
}
