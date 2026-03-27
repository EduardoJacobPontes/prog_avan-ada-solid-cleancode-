package cleancode;

import java.util.List;

public class Pedido {
    int id;                  // atributo private
    Cliente cliente;
    List<Item> itens;
    double total;
    String status;

    public double calcularsubtotal(){
        double subTotal = 0;
        for(Item i : itens) {
            subTotal += i.subTotal();
        }
        return  subTotal;

    }

    public void imprimirPedidos() {
        System.out.println("Pedido " + id);
        System.out.println("Cliente " + cliente.getNome());
        for (int i = 0; i < itens.size(); i++) {
            System.out.println(itens.get(i).getNome());
        }
        System.out.println(total);
    }
}

