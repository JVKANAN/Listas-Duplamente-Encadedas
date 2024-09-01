import java.util.ListIterator;
import java.util.NoSuchElementException;

public class Deque <Item> implements Iterable<Item>{

    private int n; 
    private No sentinela; 

    public Deque() {
        n = 0;
        sentinela = new No();
        sentinela.prox = sentinela;
        sentinela.ant = sentinela;
    }

    private class No { // Classe Nó
        private Item dado;
        private No prox;
        private No ant;
    }

    public void push_front(Item item) {
        No tmp = new No();
        tmp.dado = item;
        tmp.ant = sentinela;
        tmp.prox = sentinela.prox;
        sentinela.prox = tmp;
        tmp.prox.ant = tmp;
        ++n;
    }

    public void push_back(Item item) {
        No tmp = new No();
        tmp.dado = item;
        tmp.ant = sentinela.ant;
        tmp.prox = sentinela;
        sentinela.ant = tmp;
        tmp.ant.prox = tmp;
        n++;
    }

    public Item pop_front() {
        No tmp = sentinela.prox;
        Item meuDado = tmp.dado; 
        tmp.ant.prox = tmp.prox;
        tmp.prox.ant = tmp.ant;
        --n;
        return meuDado;
    }

    public Item pop_back() {
        No tmp = sentinela.ant;
        Item meuDado = tmp.dado;
        tmp.ant.prox = tmp.prox;
        tmp.prox.ant = tmp.ant;
        --n;
        return meuDado;
    }

    public No first() {
        if (sentinela == sentinela.prox) return null;
        return sentinela.prox;
    }

    public boolean isEmpty() {
        return n == 0;
    }

    public int size() {
        return n;
    }

    public ListIterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements ListIterator<Item> {
        private No atual = sentinela.prox;
        private int indice = 0;
        private No acessadoUltimo = null;

        public boolean hasNext() {
            return indice < n;
        }

        public boolean hasPrevious() {
            return indice > 0;
        }

        public int previousIndex() {
            return indice - 1;
        }

        public int nextIndex() {
            return indice;
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item meuDado = atual.dado;
            acessadoUltimo = atual;
            atual = atual.prox;
            indice++;
            return meuDado;
        }

        public Item previous() {
            if (!hasPrevious()) throw new NoSuchElementException();
            atual = atual.ant;
            Item meuDado = atual.dado;
            acessadoUltimo = atual;
            indice--;
            return meuDado;
        }

        public void remove() {
            if (acessadoUltimo == null) throw new IllegalStateException();
            acessadoUltimo.ant.prox = acessadoUltimo.prox;
            acessadoUltimo.prox.ant = acessadoUltimo.ant;
            n--;
            if (atual == acessadoUltimo)
                atual = acessadoUltimo.prox;
            else
                indice--;
            acessadoUltimo = null;
        }

        public void set(Item x) {
            if (acessadoUltimo == null) throw new IllegalStateException();
            acessadoUltimo.dado = x;
        }

        public void add(Item x) {
            No tmp = new No();
            tmp.dado = x;
            tmp.prox = atual.prox;
            tmp.ant = atual;
            
            tmp.prox.ant = tmp;
            atual.prox = tmp;
            n++;
        }
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Item item : this)
            s.append(item + " ");
        return s.toString();
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        Deque<Integer> list = new Deque<Integer>();
        for (int i = 0; i < n; i++)
            list.push_front(i);
        while (!list.isEmpty()) {
            StdOut.println(list.pop_front());
        }
        for (int i = 0; i < n; i++)
            list.push_back(i);
        ListIterator<Integer> it = list.iterator();
        while (it.hasNext()) {
            int x = it.next();
            it.set(x + 1);
            StdOut.println(list);
        }
        while (it.hasPrevious()) {
            int x = it.previous();
            it.set(x + x + x);
            StdOut.println(list);
        }
        it = list.iterator();
        while (it.hasNext()) {
            int x = it.next();
            if (x % 2 == 0) it.remove();
        }
        StdOut.println(list);
        while (it.hasPrevious()) {
            int x = it.previous();
            it.add(x + x);
        }
        StdOut.println(list);
        StdOut.println();
    }
}