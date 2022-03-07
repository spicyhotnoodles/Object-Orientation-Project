package DBEntities;

import java.util.List;

public class Categoria {

    private String codice;
    private String nome;
    private List<Categoria> sottoCategorie;

    public Categoria(String nome, List<Categoria> sottoCategorie) {
        this.nome = nome;
        this.sottoCategorie = sottoCategorie;
    }

    public Categoria(String nome) {
        this.nome = nome;
    }

    public String getCodice() {
        return codice;
    }

    public String getNome() {
        return nome;
    }

    public List<Categoria> getSottoCategorie() {
        return sottoCategorie;
    }

    public void setCodice(String codice) {
        this.codice = codice;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setSottoCategorie(List<Categoria> sottoCategorie) {
        this.sottoCategorie = sottoCategorie;
    }
}
