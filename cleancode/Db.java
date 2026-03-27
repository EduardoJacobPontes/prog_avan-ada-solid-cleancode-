package cleancode;

import java.util.ArrayList;
import java.util.List;

public class Db {

    public static List<Pedido> banco = new ArrayList<>();

    public void save(Pedido p) {
        try {
            banco.add(p);
            System.out.println("salvou no banco");
        } catch (Exception e) {
            System.out.println("erro ao salvar");
        }
    }

    public List<Pedido> getAll() {
        return banco;
    }

    public Pedido getById(int id) {
        for (int i = 0; i < banco.size(); i++) {
            if (banco.get(i).id == id) {
                return banco.get(i);
            }
        }
        return null;
    }
}

