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
    
    
    public static void centrarDerecha (int largo, String palabra) {
        System.out.println(" ".repeat(Math.max(largo - palabra.length(), 0)) + palabra);
    }
    
    // Ejecucion del programa
    public static void main(String[] args) {
        // Declaración y asignación para los métodos centrar y separador
        String simbolo = "-";
        int ancho = 50;
        
        // Declaracion de variables para el menu
        String opcion;
        Pattern patronOpcion = Pattern.compile("^(1|2|3)$");
        boolean condicionOpcion;
        Scanner lector = new Scanner(System.in);
        
        // Menu principal de la cafeteria
        do {
            MenuCafeteria.separador(simbolo, ancho);
            
            MenuCafeteria.centrar(ancho, "MENU DE PEDIDOS");
            System.out.println("1.- Registrar pedido");
            System.out.println("2.- Historial de pedidos");
            System.out.println("3.- Salir");
            
            MenuCafeteria.separador(simbolo, ancho);
            
            System.out.print("Escriba su opcion (1, 2 o 3): ");
            opcion = lector.nextLine();
            
            Matcher matchOpcion = patronOpcion.matcher(opcion);
            condicionOpcion = matchOpcion.matches();
            
            if (!condicionOpcion) {
                MenuCafeteria.separador(simbolo, ancho);
                
                System.out.println("Opcion no valida");
                System.out.println("Ingrese valor 1, 2 o 3");
            }
            
        } while (!condicionOpcion);
        
        
        // Switch para las distintas opciones del menu
        switch (opcion) {
            
            // Registrar pedido logica
            case "1" -> {
                // Elección de mesa
                int mesa;
                
                do {
                    MenuCafeteria.separador(simbolo, ancho);
                    
                    MenuCafeteria.centrar(ancho, "ELECCION DE MESA");
                    System.out.println("1.- Mesa numero 1");
                    System.out.println("2.- Mesa numero 2");
                    System.out.println("3.- Mesa numero 3");
                    System.out.println("4.- Mesa numero 4");
                    System.out.println("5.- Mesa numero 5");
                    
                    MenuCafeteria.separador(simbolo, ancho);
                    
                    System.out.print("Escriba su mesa: ");
                    mesa = lector.nextInt();
                    lector.nextLine();
                    
                    if (mesa <= 0 || mesa > 5) {
                        MenuCafeteria.separador(simbolo, ancho);
                        System.out.println("Mesa no encontrada!");
                    } else {
                        MenuCafeteria.separador(simbolo, ancho);
                        System.out.println("Numero de mesa escogido: " + mesa);
                    }
                    
                } while (mesa <= 0 || mesa > 5);
                
                
                // Elección de producto a comprar
                int opcionProducto;
                
                
                HashMap<Integer, String[]> productos = new HashMap<>();
                
                // Guarda los productos en un HashMap (id_producto, información(nombre, precio, cantidad))
                productos.put(1, new String[] {"Cappuchino", "10.0", "5"});
                productos.put(2, new String[] {"Te negro", "6.0", "3"});
                productos.put(3, new String[] {"Galletas", "8.0", "7"});
                productos.put(4, new String[] {"Croissands", "3.0", "5"});
                productos.put(5, new String[] {"Sandwich", "6.0", "4"});
                
                // Centinela del do-while
                String continuar;
                double total = 0;
                
                
                // Productos guardados
                List<List<String>> productosGuardados = new ArrayList<>();
                
                
                // Bucle para definir si seguir comprando productos
                do {
                    // Se guarda los datos de un producto en este arrayList
                    List<String> productoGuardado = new ArrayList<>();
                    
                    
                    // Bucle para mostrar el menu de productos disponibles
                    do {
                        MenuCafeteria.separador(simbolo, ancho);

                        MenuCafeteria.centrar(ancho, "MENU DE PRODUCTOS");


                        for (Map.Entry<Integer, String[]> entrada : productos.entrySet()) {
                            int id = entrada.getKey();
                            String nombre = entrada.getValue()[0];
                            double precio = Double.parseDouble(entrada.getValue()[1]);
                            String cantidad = entrada.getValue()[2];
                            
                            System.out.println(id + ".- " + nombre + "  -  S/." + precio + "  -  " + ((cantidad.equalsIgnoreCase("Agotado")) ? cantidad : cantidad + " unidades"));
                        }


                        MenuCafeteria.separador(simbolo, ancho);

                        System.out.print("Escriba el ID del producto desea comprar: ");
                        opcionProducto = lector.nextInt();
                        lector.nextLine();

                        MenuCafeteria.separador(simbolo, ancho);

                        if (!productos.keySet().contains(opcionProducto)) {
                            System.out.println("El ID no existe!");
                        } else if (productos.get(opcionProducto)[2].equalsIgnoreCase("agotado")) {
                            System.out.println("El producto está agotado!");
                        } else {
                            String[] datosCompra = productos.get(opcionProducto);
                            
                            System.out.println("El siguiente producto se ha agregado a su carrito!");
                            System.out.println("ID: " + opcionProducto);
                            System.out.println("Nombre: " + datosCompra[0]);
                            System.out.println("Precio: " + datosCompra[1]);
                            
                            total += Double.parseDouble(datosCompra[1]);
                            
                            System.out.println("Total momentaneo: " + total);
                            
                            productos.get(opcionProducto)[2] = String.valueOf(Integer.parseInt(productos.get(opcionProducto)[2]) - 1);
                            
                            // Se guarda el producto en un arraylist, y luego se guarda ese arraylist de producto en otro arraylist de productos
                            productoGuardado.add(datosCompra[0]);
                            productoGuardado.add(datosCompra[1]);
                            productosGuardados.add(productoGuardado);
                            
                            if (Integer.parseInt(productos.get(opcionProducto)[2]) == 0) {
                                productos.get(opcionProducto)[2] = "Agotado";
                            }
                        }
                    
                    } while (!productos.keySet().contains(opcionProducto));
                    
                    MenuCafeteria.separador(simbolo, ancho);
                    System.out.print("Desea realizar otra compra? (S/N): ");
                    continuar = lector.nextLine();

                } while (continuar.equalsIgnoreCase("s"));
                
                SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
                SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm:ss");
                Date today = new Date();
                String horaStr = formatoHora.format(today);
                String fechaStr = formatoFecha.format(today);
                
                // Boleta
                int anchoBoleta = 40;
                String simboloBoleta = "#";
                
                System.out.println("");
                MenuCafeteria.separador(simboloBoleta, anchoBoleta);
                MenuCafeteria.centrar(anchoBoleta, "CAFETERIA CENTRAL");
                MenuCafeteria.centrar(anchoBoleta, "Fecha: " + fechaStr + " ".repeat(5) + "Hora: " + horaStr);
                MenuCafeteria.separador(simboloBoleta, anchoBoleta);
                MenuCafeteria.centrar(anchoBoleta, "N. MESA: " + mesa);
                System.out.println("");
                
                for (List<String> producto : productosGuardados) {
                    String mensaje = producto.get(0) + " - S/." + producto.get(1);
                    MenuCafeteria.centrar(anchoBoleta, mensaje);
                }
                
                MenuCafeteria.separador(simboloBoleta, anchoBoleta);
                MenuCafeteria.centrarDerecha(anchoBoleta, "TOTAL: S/." + total);
                MenuCafeteria.separador(simboloBoleta, anchoBoleta);
                MenuCafeteria.centrar(anchoBoleta, "GRACIAS POR SU COMPRA, VUELVA PRONTO!");
                MenuCafeteria.separador(simboloBoleta, anchoBoleta);
                System.out.println("");
                
            }
            
            // Historial de pedidos (FALTA TERMINAR)
            case "2" -> {
                System.out.println("En progreso...");
            }
            
            // Salir
            case "3" -> {
                System.out.println("Saliendo del programa...");
                System.out.println("Usted ha salido del programa");
            }
        }
        
    }
    
}
