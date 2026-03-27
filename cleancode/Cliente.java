package cleancode;

public class Cliente {
    // trocando de public pra private
    int id;
    String nome;
    String email;
    int tipo;

    //  substituindo if/else por switch case e trocando o nome do metodo get tipo para melhor entendimento
    public String getTipoCliente() {
        return switch (this.tipo) {
            case 1 -> "comum";
            case 2 -> "premium";
            case 3 -> "vip";
            default -> "outro";
        };
    }

    // Getters básicos para acessar os dados
    public int getId() { return id; }
    public String getNome() { return nome; }
    public String getEmail() { return email; }
    public int getTipo() { return tipo; }
}





