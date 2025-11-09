package cafeteria.app.igu;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MenuCafeteria {
    
    // Para centrar las palabras
    public static void centrar (int largo, String palabra) {
        System.out.println(" ".repeat(Math.max((largo - palabra.length()) / 2, 0)) + palabra);
    }
    
    // Para imprimir el separador
    public static void separador (String simbolo, int largo) {
        System.out.println(simbolo.repeat(Math.max(largo, 0)));
    }
    
    // Para imprimir a la derecha del todo
    public static void centrarDerecha (int largo, String palabra) {
        System.out.println(" ".repeat(Math.max(largo - palabra.length(), 0)) + palabra);
    }
    
    // Método que muestra el menú principal del proyecto
    public static String mostrarMenuPrincipal(Scanner lector, int ancho, String simbolo) {
        Pattern patronOpcion = Pattern.compile("^(1|2|3)$");
        String opcion;
        Matcher matchOpcion;
    
        do {
            separador(simbolo, ancho);
            centrar(ancho, "MENU DE PEDIDOS");
            System.out.println("1.- Registrar pedido");
            System.out.println("2.- Historial de pedidos");
            System.out.println("3.- Salir");
            separador(simbolo, ancho);

            System.out.print("Escriba su opcion (1, 2 o 3): ");
            opcion = lector.nextLine();
            matchOpcion = patronOpcion.matcher(opcion);

            if (!matchOpcion.matches()) {
                separador(simbolo, ancho);
                System.out.println("Opcion no valida, ingrese 1, 2 o 3.");
            }
        } while (!matchOpcion.matches());

        return opcion;
    }

    // Método para elegir la mesa
    public static int elegirMesa(Scanner lector, int ancho, String simbolo) {
        int mesa = -1;
        do {
            separador(simbolo, ancho);
            centrar(ancho, "ELECCION DE MESA");
            for (int i = 1; i <= 5; i++) {
                System.out.println(i + ".- Mesa numero " + i);
            }
            separador(simbolo, ancho);
            
            try {
                System.out.print("Escriba su mesa: ");
                mesa = lector.nextInt();
                lector.nextLine();

                if (mesa <= 0 || mesa > 5) {
                    separador(simbolo, ancho);
                    System.out.println("Mesa no encontrada!");
                } else {
                    separador(simbolo, ancho);
                    System.out.println("Numero de mesa escogido: " + mesa);
                }
            } catch (Exception e) {
                System.out.println("Opcion no valida! Ingrese un numero!");
                lector.nextLine();
            }
            
        } while (mesa <= 0 || mesa > 5);
        
        return mesa;
    }
    
    public static HashMap<Integer, String[]> inicializarProductos() {
        HashMap<Integer, String[]> productos = new HashMap<>();
        productos.put(1, new String[]{"Cappuchino", "10.0", "5"});
        productos.put(2, new String[]{"Te negro", "6.0", "3"});
        productos.put(3, new String[]{"Galletas", "8.0", "7"});
        productos.put(4, new String[]{"Croissants", "3.0", "5"});
        productos.put(5, new String[]{"Sandwich", "6.0", "4"});
        return productos;
    }
    
    public static Map<String, Object> registrarPedido(Scanner lector, HashMap<Integer, String[]> productos, int ancho, String simbolo) {
        List<List<String>> productosGuardados = new ArrayList<>();
        String continuar;
        double total = 0;
        
        do {
            List<String> productoGuardado = new ArrayList<>();
            int opcionProducto = -1;
            boolean entradaValida = false;
            
            while (!entradaValida) {
                separador(simbolo, ancho);
                centrar(ancho, "MENU DE PRODUCTOS");

                for (Map.Entry<Integer, String[]> entrada : productos.entrySet()) {
                    int id = entrada.getKey();
                    String nombre = entrada.getValue()[0];
                    double precio = Double.parseDouble(entrada.getValue()[1]);
                    String cantidad = entrada.getValue()[2];

                    System.out.println(id + ".- " + nombre + "  -  S/." + precio + "  -  " + ((cantidad.equalsIgnoreCase("Agotado")) ? cantidad : cantidad + " unidades"));
                }
                
                separador(simbolo, ancho);
                
                System.out.print("Escriba el ID del producto desea comprar: ");
                
                if (lector.hasNextInt()) {
                    opcionProducto = lector.nextInt();
                    lector.nextLine();
                    separador(simbolo, ancho);

                    if (!productos.keySet().contains(opcionProducto)) {
                        System.out.println("El ID no existe!");
                    } else if (productos.get(opcionProducto)[2].equalsIgnoreCase("agotado")) {
                        System.out.println("El producto esta agotado!");
                    } else {
                        entradaValida = true;
                    }
                } else {
                    System.out.println("Opcion no valida. Ingrese un ID valido.");
                    lector.nextLine();
                }
            }
            
            String[] datosCompra = productos.get(opcionProducto);
            System.out.println("El siguiente producto se ha agregado a su carrito!");
            System.out.println("ID: " + opcionProducto);
            System.out.println("Nombre: " + datosCompra[0]);
            System.out.println("Precio: " + datosCompra[1]);

            total += Double.parseDouble(datosCompra[1]);

            System.out.println("Total momentaneo: " + total);

            
            productos.get(opcionProducto)[2] = String.valueOf(Integer.parseInt(productos.get(opcionProducto)[2]) - 1);

            productoGuardado.add(datosCompra[0]);
            productoGuardado.add(datosCompra[1]);
            productosGuardados.add(productoGuardado);

            if (Integer.parseInt(productos.get(opcionProducto)[2]) == 0) {
                productos.get(opcionProducto)[2] = "Agotado";
            }
            
            separador(simbolo, ancho);
            System.out.print("Desea realizar otra compra? (S/N): ");
            continuar = lector.nextLine();
                
        } while (continuar.equalsIgnoreCase("s"));
        
        Map<String, Object> resultado = new HashMap<>();
        resultado.put("productosGuardados", productosGuardados);
        resultado.put("total", total);
        
        return resultado;
    }
    
    public static void imprimirBoleta(int mesa, List<List<String>> productosGuardados, double total) {
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm:ss");
        Date today = new Date();
        String horaStr = formatoHora.format(today);
        String fechaStr = formatoFecha.format(today);
        int anchoBoleta = 40;
        String simboloBoleta = "#";

        System.out.println("");
        separador(simboloBoleta, anchoBoleta);
        centrar(anchoBoleta, "CAFETERIA CENTRAL");
        centrar(anchoBoleta, "Fecha: " + fechaStr + " ".repeat(5) + "Hora: " + horaStr);
        separador(simboloBoleta, anchoBoleta);
        centrar(anchoBoleta, "N. MESA: " + mesa);
        System.out.println("");

        for (List<String> producto : productosGuardados) {
            String mensaje = producto.get(0) + " - S/." + producto.get(1);
            centrar(anchoBoleta, mensaje);
        }
        
        separador(simboloBoleta, anchoBoleta);
        centrarDerecha(anchoBoleta, "TOTAL: S/." + total);
        separador(simboloBoleta, anchoBoleta);
        centrar(anchoBoleta, "GRACIAS POR SU COMPRA, VUELVA PRONTO!");
        separador(simboloBoleta, anchoBoleta);
        System.out.println("");
    }
    
    
    // Ejecucion del programa
    public static void main(String[] args) {
        Scanner lector = new Scanner(System.in);
        int ancho = 50;
        String simbolo = "-";
        
        String opcion = mostrarMenuPrincipal(lector, ancho, simbolo);
        
        switch (opcion) {
            case "1" -> {
                int mesa = elegirMesa(lector, ancho, simbolo);
                                
                HashMap<Integer, String[]> productos = inicializarProductos();
                
                Map<String, Object> resultado = registrarPedido(lector, productos, ancho, simbolo);
                
                imprimirBoleta(mesa, (List<List<String>>) resultado.get("productosGuardados"), (double) resultado.get("total"));
            }
            
            // Historial de pedidos
            case "2" -> {
                /*
                byte opcionHistorial = 0;
                do {
                    
                } while (opcionHistorial > 0 && opcionHistorial < );
                */
            }
            
            // Salir
            case "3" -> {
                System.out.println("Saliendo del programa...");
                System.out.println("Usted ha salido del programa");
            }
        }
        
    }
    
}
