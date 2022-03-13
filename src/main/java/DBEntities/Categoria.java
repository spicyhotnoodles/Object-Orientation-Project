package DBEntities;

import java.util.ArrayList;
import java.util.List;

public class Categoria {

    private String codice;
    private String nome;
    private String padre;

    private Categoria() {}

    public static class Builder {
        private String codice;
        private String nome;
        private String padre;

        public Builder() {
            this.codice = "";
            this.nome = "";
            this.padre = "";
        }

        public Builder setCodice(String codice) {
            this.codice = codice;
            return this;
        }

        public Builder setNome(String nome) {
            this.nome = nome;
            return this;
        }

        public Builder setPadre(String padre) {
            this.padre = padre;
            return this;
        }

        public Categoria build() {
            Categoria categoria = new Categoria();
            categoria.codice = this.codice;
            categoria.nome = this.nome;
            categoria.padre = this.padre;
            return categoria;
        }

    }

    public String getCodice() {
        return codice;
    }

    public String getNome() {
        return nome;
    }

    public String getPadre() {
        return padre;
    }

    public void setCodice(String codice) {
        this.codice = codice;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return "Categoria{" +
                "codice='" + codice + '\'' +
                ", nome='" + nome + '\'' +
                ", padre='" + padre + '\'' +
                '}';
    }
}
