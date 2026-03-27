package cleancode;

import javax.xml.namespace.QName;

public class Item {
    String nome;                      // atributo private
    double preco;
    int qtd;

    public double subTotal() {             // trocando o nome de "x" para "calcular faturamento" mais intuitivo e facil de localizar
        return preco * qtd;
    }

    public String getNome() {
        return getNome();
    }

    public double getQtd() {
        return getQtd();
    }
}


