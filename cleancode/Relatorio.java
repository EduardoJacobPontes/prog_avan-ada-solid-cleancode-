package cleancode;

import java.util.List;

public class Relatorio {

    public void gerar(List<Pedido> pedidos) { // Nomes claros: troquei 'ps' por 'pedidos'
        System.out.println("======= RELATORIO =======");

        int qtd = 0;
        double valorTotal = 0; // trocando nome soma por valor total
        int cancelados = 0;
        int comuns = 0;
        int premiums = 0;
        int vips = 0;

        // Clean Code : retirando índice 'i'
        for (Pedido pedido : pedidos) {
            qtd++;
            valorTotal += pedido.total;

            if ("CANCELADO".equals(pedido.status)) {
                cancelados++;
            }

            // O switch usando a variável pedido ao ineves do if else
            switch (pedido.cliente.tipo) {
                case 1 -> comuns++;
                case 2 -> premiums++;
                case 3 -> vips++;
            }

            System.out.println("Pedido " + pedido.id + " - " + pedido.cliente.nome + " - " + pedido.total + " - " + pedido.status);

            // Foreach para os itens também, eliminando o índice 'j'
            for (Item item : pedido.itens) {
                System.out.println("   item: " + item.nome + " qtd:" + item.qtd + " preco:" + item.preco);
            }
        }

        System.out.println("--------------------");
        System.out.println("qtd pedidos: " + qtd);
        System.out.println("valor total: " + valorTotal);   // melhorando saidas do sout
        System.out.println("cancelados: " + cancelados);
        System.out.println("clientes comuns: " + comuns);
        System.out.println("clientes premium: " + premiums);
        System.out.println("clientes vip: " + vips);

        System.out.println("resultado: " + avaliarDesempenho(valorTotal));
    }

    //  tirando os if else
    private String avaliarDesempenho(double somaTotal) {
        if (somaTotal > 1000) return "resultado muito bom";
        if (somaTotal > 500) return "resultado ok";
        return "resultado fraco";
    }
}