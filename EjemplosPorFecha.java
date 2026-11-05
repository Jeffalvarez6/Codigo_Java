import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;

public class EjemplosPorFecha {

    private static final DateTimeFormatter FORMATO =
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
                         .withZone(ZoneId.of("UTC"));

    static Evento ev(int id, String fecha, String descripcion, int prioridad) {
        return new Evento(id, Instant.parse(fecha), descripcion, prioridad);
    }

    static void imprimir(String titulo, Evento[] eventos) {
        System.out.println("  " + titulo);
        for (Evento e : eventos)
            System.out.printf("    %s | id=%-4d | p%d | %s%n",
                FORMATO.format(e.getFechaCreacion()), e.getId(),
                e.getPrioridad(), e.getDescripcion());
    }

    static boolean ordenadoPorFecha(Evento[] a) {
        for (int i = 1; i < a.length; i++)
            if (a[i - 1].getFechaCreacion().isAfter(a[i].getFechaCreacion()))
                return false;
        return true;
    }

    static void ejemplo1MergeSort() {
        System.out.println("EJEMPLO 1 - Tickets de soporte (Merge Sort por fecha)");
        Evento[] tickets = {
            ev(104, "2026-03-15T14:20:00Z", "Impresora sin conexion", 4),
            ev(101, "2026-03-15T08:05:00Z", "Olvido de contrasena", 2),
            ev(107, "2026-03-16T09:40:00Z", "Pantalla azul en PC", 1),
            ev(102, "2026-03-15T10:30:00Z", "Correo no sincroniza", 3),
            ev(110, "2026-03-14T17:55:00Z", "Solicitud de mouse", 8),
            ev(105, "2026-03-15T16:00:00Z", "VPN no conecta", 1)
        };
        imprimir("Antes:", tickets);
        Ordenamientos.merge(tickets, Ordenamientos.POR_FECHA);
        imprimir("Despues (del mas antiguo al mas reciente):", tickets);
        System.out.println("  Ordenado correctamente: " + ordenadoPorFecha(tickets));
        System.out.println();
    }

    static void ejemplo2InsertionSort() {
        System.out.println("EJEMPLO 2 - Pedidos de tienda en linea (Insertion Sort por fecha)");
        Evento[] pedidos = {
            ev(5003, "2026-11-01T12:15:00Z", "Pedido audifonos", 5),
            ev(5001, "2026-10-31T22:45:00Z", "Pedido teclado", 6),
            ev(5006, "2026-11-02T07:30:00Z", "Pedido monitor", 3),
            ev(5002, "2026-11-01T09:00:00Z", "Pedido cargador", 7),
            ev(5005, "2026-11-01T18:20:00Z", "Pedido portatil", 2)
        };
        imprimir("Antes:", pedidos);
        Ordenamientos.insertion(pedidos, Ordenamientos.POR_FECHA);
        imprimir("Despues (orden de llegada para despachar):", pedidos);
        System.out.println("  Ordenado correctamente: " + ordenadoPorFecha(pedidos));
        System.out.println();
    }

    static void ejemplo3QuickSortDescendente() {
        System.out.println("EJEMPLO 3 - Historial de citas medicas (Quick Sort, mas reciente primero)");
        Evento[] citas = {
            ev(31, "2026-05-10T15:00:00Z", "Control general", 6),
            ev(32, "2026-08-22T10:30:00Z", "Examen de sangre", 4),
            ev(33, "2026-01-18T08:00:00Z", "Vacunacion", 5),
            ev(34, "2026-10-05T11:45:00Z", "Urgencias", 0),
            ev(35, "2026-07-01T09:15:00Z", "Odontologia", 7),
            ev(36, "2026-03-27T16:30:00Z", "Optometria", 8)
        };
        imprimir("Antes:", citas);
        Comparator<Evento> masRecientePrimero = Ordenamientos.POR_FECHA.reversed();
        Ordenamientos.quick(citas, masRecientePrimero);
        imprimir("Despues (de la mas reciente a la mas antigua):", citas);
        boolean ok = true;
        for (int i = 1; i < citas.length; i++)
            if (masRecientePrimero.compare(citas[i - 1], citas[i]) > 0) ok = false;
        System.out.println("  Ordenado correctamente: " + ok);
        System.out.println();
    }

    public static void main(String[] args) {
        ejemplo1MergeSort();
        ejemplo2InsertionSort();
        ejemplo3QuickSortDescendente();
    }
}