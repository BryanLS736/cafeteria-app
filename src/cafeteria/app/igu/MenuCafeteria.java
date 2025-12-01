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
    public static void moverDerecha (int largo, String palabra) {
        System.out.println(" ".repeat(Math.max(largo - palabra.length(), 0)) + palabra);
    }
    
    
    // Método que muestra el menú principal del proyecto
    public static String mostrarMenuPrincipal(Scanner lector, int ancho, String simbolo) {
        Pattern patronOpcion = Pattern.compile("^(1|2|3)$");
        String opcion;
        Matcher matchOpcion;
        
        // Do-while para asegurar que se ingrese una opcion valida
        do {
            separador(simbolo, ancho);
            centrar(ancho, "MENU DE PEDIDOS");
            System.out.println("1.- Registrar pedido");
            System.out.println("2.- Historial de pedidos");
            System.out.println("3.- Reporte General");
            System.out.println("4.- Salir");
            separador(simbolo, ancho);

            System.out.print("Escriba su opcion (1, 2 o 3): ");
            opcion = lector.nextLine();
            matchOpcion = patronOpcion.matcher(opcion);

            // El if se muestra si no se cumple con el patron
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
        
        // Do-while para asegurarse de que se ingrese una mesa valida
        do {
            separador(simbolo, ancho);
            centrar(ancho, "ELECCION DE MESA");
            
            // Bucle for para mostrar las mesas disponibles del local
            for (int i = 1; i <= 5; i++) {
                System.out.println(i + ".- Mesa numero " + i);
            }
            separador(simbolo, ancho);
            
            // Try-catch para evitar que se ingrese un dato tipo cadena (String)
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
    
    
    // Método para inicializar los productos que tiene la tienda, el precio y el stock disponible
    public static HashMap<Integer, String[]> inicializarProductos() {
        HashMap<Integer, String[]> productos = new HashMap<>();
        productos.put(1, new String[]{"Cappuchino", "10.0", "5"});
        productos.put(2, new String[]{"Te negro", "6.0", "5"});
        productos.put(3, new String[]{"Galletas", "8.0", "5"});
        productos.put(4, new String[]{"Croissants", "3.0", "5"});
        productos.put(5, new String[]{"Sandwich", "6.0", "5"});
        return productos;
    }
    
    
    // Se pone afuera para que no se reinicie a cada momento
    static HashMap<Integer, String[]> productos = inicializarProductos(); // HashMap de los productos disponibles
    
    
    // Método para registrar el pedido
    public static Map<String, Object> registrarPedido(Scanner lector, HashMap<Integer, String[]> productos, int ancho, String simbolo) {
        List<List<String>> productosGuardados = new ArrayList<>();
        String continuar = "s";
        double total = 0;
        
        // Do-while para que se siga comprando si elige si, si elige no se termina el bucle
        while (continuar.equalsIgnoreCase("s")) {
            List<String> productoGuardado = new ArrayList<>();
            int opcionProducto = -1;
            boolean entradaValida = false;
            
            // While para validar si la entrada por teclado cumple
            while (!entradaValida) {
                separador(simbolo, ancho);
                centrar(ancho, "MENU DE PRODUCTOS");

                // For-each que muestra cada producto del menu y su información
                for (Map.Entry<Integer, String[]> entrada : productos.entrySet()) {
                    int id = entrada.getKey();
                    String nombre = entrada.getValue()[0];
                    double precio = Double.parseDouble(entrada.getValue()[1]);
                    String cantidad = entrada.getValue()[2];

                    System.out.println(id + ".- " + nombre + "  -  S/." + precio + "  -  " + ((cantidad.equalsIgnoreCase("Agotado")) ? cantidad : cantidad + " unidades"));
                }
                
                separador(simbolo, ancho);
                
                System.out.print("Escriba el ID del producto desea comprar: ");
                
                // Condicional if-else, si se cumple es porque es de tipo entero,
                if (lector.hasNextInt()) {
                    opcionProducto = lector.nextInt();
                    lector.nextLine();
                    separador(simbolo, ancho);

                    // Condicional si el entero escrito es uno de los ID disponibles
                    if (!productos.keySet().contains(opcionProducto)) {
                        System.out.println("El ID no existe!");
                        
                    // Else-if, si en caso el ID elegido está agotado
                    } else if (productos.get(opcionProducto)[2].equalsIgnoreCase("agotado")) {
                        System.out.println("El producto esta agotado!");
                        
                    // Else, si en caso si existe el ID. El centinela se vuelve true y termina el bucle while
                    } else {
                        entradaValida = true;
                    }
                    
                // Else, si en caso no es un id de tipo entero
                } else {
                    System.out.println("Opcion no valida. Ingrese un ID valido.");
                    lector.nextLine();
                }
            }
            
            // Codigo que se ejecuta si el ID es valido y aun hay stock
            String[] datosCompra = productos.get(opcionProducto);
            System.out.println("El siguiente producto se ha agregado a su carrito!");
            System.out.println("ID: " + opcionProducto);
            System.out.println("Nombre: " + datosCompra[0]);
            System.out.println("Precio: " + datosCompra[1]);

            total += Double.parseDouble(datosCompra[1]);

            System.out.println("Total momentaneo: " + total);
            
            productos.get(opcionProducto)[2] = String.valueOf(Integer.parseInt(productos.get(opcionProducto)[2]) - 1);

            boolean existe = false;
            
            for (List<String> producto : productosGuardados) {
                if (producto.get(0).equals(String.valueOf(opcionProducto))) {
                    int cantidad = Integer.parseInt(producto.get(3));
                    double totalPrecio = Double.parseDouble(producto.get(2));
                    
                    producto.set(3, String.valueOf(cantidad + 1));
                    producto.set(2, String.valueOf(totalPrecio + Double.parseDouble(datosCompra[1])));
                    existe = true;
                    break;
                }
            }
            
            if (!existe) {
                productoGuardado.add(String.valueOf(opcionProducto)); // Indice 0 - ID
                productoGuardado.add(datosCompra[0]); // Indice 1 - Nombre
                productoGuardado.add(datosCompra[1]); // Indice 2 - Total de ese producto (Precio unitario del producto x cantidad)
                productoGuardado.add("1"); // Indice 3 - Cantidad
                productosGuardados.add(productoGuardado);
            }
            

            // If que si el stock del producto llego a 0, cambia a "agotado"
            if (Integer.parseInt(productos.get(opcionProducto)[2]) == 0) {
                productos.get(opcionProducto)[2] = "Agotado";
            }
            
            separador(simbolo, ancho);
            System.out.print("Desea realizar otra compra? (S/N): ");
            continuar = lector.nextLine();
            
            String respuestaEliminar;

            do {
                System.out.println("Desea eliminar algún producto? (S/N)");
                respuestaEliminar = lector.nextLine();
                
                if (respuestaEliminar.equalsIgnoreCase("s")) {
                    total = eliminarProductosCarrito(simbolo, ancho, productosGuardados, productos, total);
                }
            } while (respuestaEliminar.equalsIgnoreCase("s"));
            
                
        }
        
        // Se guarda los productos elegidos y el total en un HashMap resultado
        Map<String, Object> resultado = new HashMap<>();
        resultado.put("productosGuardados", productosGuardados);
        resultado.put("total", total);
        
        return resultado;
    }
    
    
    public static double eliminarProductosCarrito(String simbolo, int ancho,List<List<String>> productosGuardados, Map<Integer, String[]> productos, double total) {
        Scanner lector = new Scanner(System.in);
        
        if (productosGuardados.isEmpty()) {
            System.out.println("El carrito está vacio, no hay nada para eliminar!");
        } else {
            separador(simbolo, ancho);
            centrar(ancho, "PRODUCTOS EN EL CARRITO");
            separador(simbolo, ancho);
            
            for (int i = 0; i < productosGuardados.size(); i++) {
                List<String> producto = productosGuardados.get(i);
                System.out.printf("%d) %s  x  %s  -  S/ %s\n", (i + 1), producto.get(1), producto.get(3), producto.get(2));
            }
            
            separador(simbolo, ancho);
            
            System.out.println("Ingrese el numero del producto a eliminar: ");
            int opcion = lector.nextInt();
            separador(simbolo, ancho);
            
            if (opcion < 1 || opcion > productosGuardados.size()) {
                System.out.println("Opcion invalida.");
                separador(simbolo, ancho);
            } else {
                List<String> productoElegido = productosGuardados.get(opcion - 1);
                int id = Integer.parseInt(productoElegido.get(0));
                int cantidad = Integer.parseInt(productoElegido.get(3));
                double precio = Double.parseDouble(productoElegido.get(2));
                
                productos.get(id)[2] = String.valueOf(Integer.parseInt(productos.get(id)[2]) + cantidad);
                productosGuardados.remove(opcion - 1);
                total -= precio;
                System.out.println("Producto eliminado del carrito!");
            }
            
        }
        return total;
    }
    
    
    // Método que imprime la boleta
    public static void imprimirBoleta(int mesa, List<List<String>> productosGuardados, double total) {
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm:ss");
        Date today = new Date();
        String horaStr = formatoHora.format(today);
        String fechaStr = formatoFecha.format(today);
        int anchoBoleta = 40;
        String simboloBoleta = "=";
        String simboloSeparación = "-";
        String mesero = "Bryan Luque";
        double igv = 0.18 * total;

        System.out.println("");
        separador(simboloBoleta, anchoBoleta);
        centrar(anchoBoleta, "CAFETERIA CENTRAL");
        centrar(anchoBoleta, "Jr. Los Pinos 123 - Lima, Peru");
        centrar(anchoBoleta, "RUC: 234567891011  Tel:987-654-321");
        separador(simboloBoleta, anchoBoleta);
        
        System.out.println("");
        System.out.printf("%s%23s\n", ("Fecha: " + fechaStr), ("Hora: " + horaStr));
        System.out.printf("%s\n", ("Numero de mesa: " + mesa));
        System.out.printf("%s\n", ("Cajero: " + mesero));
        System.out.println("");
        
        separador(simboloSeparación, anchoBoleta);
        String mensajeEncabezado = String.format("%-6s%-24s%-6s", "Cant.", "Descripcion", "Precio");
        System.out.println(mensajeEncabezado);
        separador(simboloSeparación, anchoBoleta);

        // Recorre cada producto de los productos guardados
        for (List<String> producto : productosGuardados) {
            String mensaje = String.format("%-6s%-24s%-6s", producto.get(3), producto.get(1), ("S/ " + producto.get(2)));
            System.out.println(mensaje);
        }
        
        separador(simboloSeparación, anchoBoleta);
        System.out.printf("SUBTOTAL:%-21s%-11s\n", " ", ("S/ " + total));
        System.out.printf("IGV (18%%):%-20sS/ %-8.2f\n", " ", igv);
        
        separador(simboloSeparación, anchoBoleta);
        System.out.printf("TOTAL A PAGAR:%-16sS/ %-8.2f\n", " ", (total + igv));
        
        separador(simboloBoleta, anchoBoleta);
        System.out.println("");
        centrar(anchoBoleta, "GRACIAS POR SU COMPRA, VUELVA PRONTO!");
        separador(simboloBoleta, anchoBoleta);
        System.out.println("");
    }
    
    
    // Método para mostrar el historial de pedidos
    public static void mostrarHistorialPedidos() {
        // Condicion si está vacio
        if (historialPedidos.isEmpty()) {
            System.out.println("No hay pedidos en el historial todavia.");
        // Condición si tiene al menos 1 registro
        } else {
            System.out.println("\n========= HISTORIAL DE PEDIDOS =========");
            int contador = 1;
            int ancho = 40;
            String sep1 = "=";
            String sep2 = "-";
            
            // Recorre la lista de historial pedidos
            for (Map<String, Object> pedido : historialPedidos) {
                separador(sep1, ancho);
                System.out.println("Pedido N. " + contador);
                separador(sep1, ancho);
                
                System.out.println("Mesa: " + pedido.get("mesa"));
                
                List<List<String>> productosGuardados = (List<List<String>>) pedido.get("productosGuardados");
                double total = (double) pedido.get("total");
                double igv = total * 0.18;
                
                separador(sep2, ancho);
                System.out.printf("%-6s%-24s%-6s\n", "Cant.", "Descripcion", "Precio");
                separador(sep2, ancho);
                
                // Recorre la lista productos que contiene una lista de los productos guardados de cada pedido
                for (List<String> prod : productosGuardados) {
                    System.out.printf("%-6s%-24s%-6s\n", prod.get(3), prod.get(1), "S/ " + prod.get(2));
                }
                
                separador(sep2, ancho);
                System.out.printf("SUBTOTAL:%-21s%-11s\n", " ", ("S/ " + total));
                System.out.printf("IGV (18%%):%-20sS/ %-8.2f\n", " ", igv);

                separador(sep2, ancho);
                System.out.printf("TOTAL A PAGAR:%-16sS/ %-8.2f\n", " ", (total + igv));

                separador(sep1, ancho);
                System.out.println("");
                contador++;
            }
            
            System.out.println("============FIN DEL HISTORIAL===========");
            System.out.println("");
        }
    }
    
    
    // Lista global del proyecto, que podemos acceder a ella
    static List<Map<String, Object>> historialPedidos = new ArrayList<>();
    
    
    //Método para generar el reporte
    public static void mostrarReporteGeneral(int ancho, String simbolo) {

    if (historialPedidos.isEmpty()) {
        System.out.println("No hay pedidos registrados aun.");
        return;
    }

    int totalPedidos = historialPedidos.size();
    int totalProductosVendidos = 0;
    double totalGeneral = 0;

    HashMap<String, Integer> contadorProductos = new HashMap<>();

    // Recorrer todos los pedidos
    for (Map<String, Object> pedido : historialPedidos) {

        double total = (double) pedido.get("total");
        totalGeneral += total;

        List<List<String>> productos = (List<List<String>>) pedido.get("productosGuardados");

        for (List<String> prod : productos) {
            int cantidad = Integer.parseInt(prod.get(3));
            totalProductosVendidos += cantidad;

            String nombre = prod.get(1);
            contadorProductos.put(nombre,
                contadorProductos.getOrDefault(nombre, 0) + cantidad
            );
        }
    }

    // Buscar producto más vendido
    String productoMasVendido = "Ninguno";
    int maxCant = 0;

    for (String nombre : contadorProductos.keySet()) {
        int cant = contadorProductos.get(nombre);
        if (cant > maxCant) {
            maxCant = cant;
            productoMasVendido = nombre;
        }
    }

    // ==============================
    //   IMPRESIÓN DEL REPORTE
    // ==============================
    separador("=", ancho);
    centrar(ancho, "REPORTE GENERAL");
    separador("=", ancho);

    System.out.println("Total de pedidos realizados: " + totalPedidos);
    System.out.println("Total de productos vendidos: " + totalProductosVendidos);

    separador("-", ancho);
    centrar(ancho, "DINERO RECAUDADO");
    separador("-", ancho);
    System.out.printf("TOTAL GENERAL: S/ %.2f\n", totalGeneral);

    separador("-", ancho);
    centrar(ancho, "PRODUCTO MAS VENDIDO");
    separador("-", ancho);
    System.out.println(productoMasVendido + " - " + maxCant + " unidades vendidas");
}
    
    
    // Método main que ejecuta todo el proyecto
    public static void main(String[] args) {
        Scanner lector = new Scanner(System.in);
        int ancho = 50;
        String simbolo = "-";
        String opcion;
        
        // Inicio del do-while para seguir haciendo las ordenes hasta darle a 3.- Salir
        do {
            opcion = mostrarMenuPrincipal(lector, ancho, simbolo);

            switch (opcion) {
                // Registrar pedidos
                case "1" -> {
                    int mesa = elegirMesa(lector, ancho, simbolo); // Retorna el numero de mesa
                    Map<String, Object> resultado = registrarPedido(lector, productos, ancho, simbolo); // Agrega los pedidos a resultado
                    
                    List<List<String>> carrito = (List<List<String>>) resultado.get("productosGuardados");
                    double total = 0;
                    
                    for (List<String> prod : carrito) {
                        total += Double.parseDouble(prod.get(2));
                    }
                    
                    // Agrega la mesa a resultado
                    resultado.put("total", total);
                    resultado.put("mesa", mesa);
                    
                    // Agrega resultado al historial de pedidos (resultados contiene la información importante de cada pedido)
                    historialPedidos.add(resultado);
                    
                    // Retorna la boleta
                    imprimirBoleta(mesa, carrito, total);
                }

                // Historial de pedidos
                case "2" -> {
                    mostrarHistorialPedidos();
                }

                // Opcion para salir
                case "3" -> {
                    mostrarReporteGeneral(ancho, simbolo);

                }
                case "4" -> {
                    System.out.println("Saliendo del programa...");
                    System.out.println("Usted ha salido del programa");
                }
            }

        } while (!opcion.equals("3")); // Fin del do-while 
    
    }
}