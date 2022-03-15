package DBEntities;

public class Categoria {

    private String codice;
    private String nome;
    private String supercategoria;

    public Categoria() {
        this.codice = "";
        this.supercategoria = "";
        this.nome = "";
    }

    public static class Builder {
        private String codice;
        private String nome;
        private String supercategoria;

        public Builder() {
            this.codice = "";
            this.nome = "";
            this.supercategoria = "";
        }

        public Builder setCodice(String codice) {
            this.codice = codice;
            return this;
        }

        public Builder setNome(String nome) {
            this.nome = nome;
            return this;
        }

        public Builder setSupercategoria(String supercategoria) {
            this.supercategoria = supercategoria;
            return this;
        }

        public Categoria build() {
            Categoria categoria = new Categoria();
            categoria.codice = this.codice;
            categoria.nome = this.nome;
            categoria.supercategoria = this.supercategoria;
            return categoria;
        }

    }

    public String getCodice() {
        return codice;
    }

    public String getNome() {
        return nome;
    }

    public String getSupercategoria() {
        return supercategoria;
    }

    public void setCodice(String codice) {
        this.codice = codice;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setSupercategoria(String supercategoria) { this.supercategoria = supercategoria; }

    @Override
    public String toString() {
        return "Categoria{" +
                "codice='" + codice + '\'' +
                ", nome='" + nome + '\'' +
                ", supercategoria='" + supercategoria + '\'' +
                '}';
    }
}
