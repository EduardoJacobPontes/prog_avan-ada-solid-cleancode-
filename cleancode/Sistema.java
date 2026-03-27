package cleancode;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Sistema {

    Scanner sc = new Scanner(System.in);
    List<Pedido> pedidos = new ArrayList<>(); // armazenar pedidos em um db ( corrigir)
    Db db = new Db();

    public void executar() {
        int opcaoSelecionada = -1;

        while (opcaoSelecionada != 0) {
            System.out.println("==== SISTEMA ====");
            System.out.println("1 - Novo pedido");
            System.out.println("2 - Listar pedidos");
            System.out.println("3 - Buscar pedido por id");
            System.out.println("4 - Relatorio");
            System.out.println("5 - Cancelar pedido");
            System.out.println("0 - Sair");
            System.out.print("Opcao: ");

            try {
                opcaoSelecionada = Integer.parseInt(sc.nextLine()); // opcaoSelecionada = opcao
            } catch (Exception e) {
                System.out.println("erro");
                opcaoSelecionada = -1;
            }

            switch (opcaoSelecionada){
                case 1:
                    novoPedido();
                    break;
                case 2:
                    listar();
                    break;
                case 3:
                    buscar();
                    break;
                case 4:
                    relatorio();
                    break;
                case 5:
                    cancelar();
                    break;
                case 0:
                    System.out.printf("Fim");
                default:
                    System.out.println("Opção Invalida");
                    break;
            }
        }
        sc.close();
    }

    public void novoPedido() {
        System.out.println("Nome cliente:");
        String nomecliente = sc.nextLine(); //nomecliente = nome

        System.out.println("Tipo cliente (1 comum, 2 premium, 3 vip):");
        int tipocliente = 0; // tipocliente = tipo cliente
        try {
            tipocliente = Integer.parseInt(sc.nextLine());
        } catch (Exception e) {
            System.out.println("tipo errado, vai comum");
            tipocliente = 1;
        }

        Cliente novocliente = new Cliente();
        novocliente.id = pedidos.size() + 1;
        novocliente.nome = nomecliente;
        novocliente.tipo = tipocliente;
        novocliente.email = nomecliente.replace(" ", "").toLowerCase() + "@email.com";

        Pedido novopedido = new Pedido();
        novopedido.id = pedidos.size() + 1;
        novopedido.cliente = novocliente;
        novopedido.status = "NOVO";
        novopedido.itens = new ArrayList<>();

        String continua = "s";
        while (continua.equalsIgnoreCase("s")) {
            System.out.println("Nome item:");
            String nomeItem = sc.nextLine();

            System.out.println("Preco item:");
            double precoItem = 0;
            try {
                precoItem = Double.parseDouble(sc.nextLine());
            } catch (Exception e) {
                precoItem = 0;
            }

            System.out.println("Qtd:");
            int quantidade = 0;
            try {
                quantidade = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                quantidade = 1;
            }

            Item item = new Item();
            item.nome = nomeItem;
            item.preco = precoItem;
            item.qtd = quantidade;
            novopedido.itens.add(item);

            System.out.println("Adicionar mais item? s/n");
            continua = sc.nextLine();
        }



        double total = novopedido.calcularsubtotal();

        // regra de desconto
        double desconto = 0;
        switch(novocliente.tipo){
            case 1:
                if(total > 300) desconto = 0.05;
                break;
            case 2:
                desconto = (total > 200) ? 0.10 : 0.03;
                break;
            case 3:
                desconto = 0.15;
                break;
        }
    total *= (1 - desconto);
        // frete
        if (total < 100) {
            total += 25;
        } else if (total < 300) {
            total += 15;
        }

        novopedido.total = total;

        pedidos.add(novopedido);
        db.save(novopedido);

        System.out.println("Pedido criado com sucesso");
        System.out.println("Id: " + novopedido.id);
        System.out.println("Cliente: " + novopedido.cliente.nome);
        System.out.println("Total: " + novopedido.total);

        if (novopedido.total > 500) {
            System.out.println("Pedido importante!!!");
        }
    }

    public void listar() {
        if (pedidos.size() == 0) {
            System.out.println("sem pedidos");
        } else {
            for (int i = 0; i < pedidos.size(); i++) {
                Pedido p = pedidos.get(i);
                System.out.println("---------------");
                System.out.println("id: " + p.id);
                System.out.println("cliente: " + p.cliente.nome);
                System.out.println("email: " + p.cliente.email);             // tem como melhorar esse sot
                System.out.println("tipo: " + p.cliente.tipo);
                System.out.println("status: " + p.status);
                System.out.println("total: " + p.total);
                System.out.println("itens:");
                for (int j = 0; j < p.itens.size(); j++) {
                    Item it = p.itens.get(j);
                    System.out.println(it.nome + " - " + it.qtd + " - " + it.preco);
                }
            }
        }
    }

    public void buscar() {
        System.out.println("Digite o id:");
        int id = Integer.parseInt(sc.nextLine());
        boolean achou = false;

        for (int i = 0; i < pedidos.size(); i++) {
            Pedido pedido = pedidos.get(i);
            if (pedido.id == id) {
                achou = true;
                System.out.println("Pedido encontrado");
                System.out.println("id: " + pedido.id);
                System.out.println("cliente: " + pedido.cliente.nome);
                System.out.println("status: " + pedido.status);
                System.out.println("total: " + pedido.total);

                double subtotal = 0;
                for (int j = 0; j < pedido.itens.size(); j++) {
                    subtotal = subtotal + (pedido.itens.get(j).preco * pedido.itens.get(j).qtd);
                }
                System.out.println("subtotal calculado novamente: " + subtotal);

                if (pedido.cliente.tipo == 1) {
                    System.out.println("cliente comum");
                } else if (pedido.cliente.tipo == 2) {
                    System.out.println("cliente premium");
                } else if (pedido.cliente.tipo == 3) {
                    System.out.println("cliente vip");
                } else {
                    System.out.println("cliente desconhecido");
                }

                for (int j = 0; j < pedido.itens.size(); j++) {
                    Item it = pedido.itens.get(j);
                    System.out.println("item " + (j + 1) + ": " + it.nome + " / " + it.qtd + " / " + it.preco);
                }
            }
        }

        if (achou == false) {
            System.out.println("nao achou");
        }
    }

    public void relatorio() {
        Relatorio relatorio = new Relatorio();
        relatorio.gerar(pedidos);
    }

    public void cancelar() {
        System.out.println("Digite id do pedido");
        int id = Integer.parseInt(sc.nextLine());

        for (int i = 0; i < pedidos.size(); i++) {
            if (pedidos.get(i).id == id) {
                if (pedidos.get(i).status.equals("CANCELADO")) {
                    System.out.println("ja cancelado");
                } else {
                    pedidos.get(i).status = "CANCELADO";
                    System.out.println("cancelado");
                }
                return;
            }
        }

        System.out.println("pedido nao existe");
    }
}

