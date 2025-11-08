package smgiaan;

import smgiaan.modelo.Atleta;
import smgiaan.modelo.Entrenamiento;
import smgiaan.modelo.Pago;
import smgiaan.servicio.AtletaServicio;
import smgiaan.servicio.EntrenamientoServicio;
import smgiaan.modelo.Pago;
import smgiaan.persistencia.PagoDAO;
import smgiaan.servicio.PagoServicio;

import java.time.LocalDate;
import java.util.Scanner;

public class MenuPrincipal {

    private static Scanner sc = new Scanner(System.in);
    private static AtletaServicio atletaServicio = new AtletaServicio();
    private static EntrenamientoServicio entrenamientoServicio = new EntrenamientoServicio();
    private static PagoServicio pagoServicio = new PagoServicio();
    private static PagoDAO pagoDAO = new PagoDAO();

    private static final String[] DEPARTAMENTOS = {
        "Alta Verapaz", "Baja Verapaz", "Chimaltenango", "Chiquimula", 
        "El Progreso", "Escuintla", "Guatemala", "Huehuetenango", 
        "Izabal", "Jalapa", "Jutiapa", "Peten", 
        "Quetzaltenango", "Quiche", "Retalhuleu", "Sacatepequez", 
        "San Marcos", "Santa Rosa", "Solola", "Suchitepequez", 
        "Totonicapan", "Zacapa"
    };

    private static final String[] TIPOS_ENTRENAMIENTO = {
        "Resistencia", "Tecnica", "Fuerza", "Velocidad", "Flexibilidad"
    };

    public static void main(String[] args) {
        int opcion;
        do {
            System.out.println("\n=== SISTEMA DE MONITOREO DE ATLETAS ===");
            System.out.println("1. Registrar Atleta");
            System.out.println("2. Listar Atletas");
            System.out.println("3. Registrar Entrenamiento");
            System.out.println("4. Consultar Estadisticas");
            System.out.println("6. Procesar Pago");
            System.out.println("7. Consultar Pagos");
            System.out.println("8. Guardar datos en JSON");
            System.out.println("0. Salir");
            System.out.print("Opcion: ");
            opcion = Integer.parseInt(sc.nextLine());

            switch (opcion) {
                case 1 -> registrarAtleta();
                case 2 -> listarAtletas();
                case 3 -> registrarEntrenamiento();
                case 4 -> consultarEstadisticas();
                case 5 -> guardarEnCSV();
                case 6 -> procesarPago();
                case 7 -> consultarPago();
                case 8 -> guardarEnJSON();
            }
        } while (opcion != 0);
    }

    private static void registrarAtleta() {
        try {
            System.out.print("Nombre Completo: ");
            String nombre = sc.nextLine();
            System.out.print("Edad: ");
            int edad = Integer.parseInt(sc.nextLine());

            System.out.println("Disciplina: ");
            System.out.println("1. Atletismo");
            System.out.println("2. Natación");
            System.out.println("3. Pesas");
            System.out.println("4. Ciclismo");
            int opDisc = Integer.parseInt(sc.nextLine());
            String disc = switch (opDisc) {
                case 1 -> "Atletismo";
                case 2 -> "Natacion";
                case 3 -> "Pesas";
                case 4 -> "Ciclismo";
                default -> "Otro";
            };

            System.out.println("\nSeleccione el Departamento:");
            for (int i = 0; i < DEPARTAMENTOS.length; i++) {
                System.out.printf("%2d. %s%n", (i + 1), DEPARTAMENTOS[i]);
            }
            System.out.print("Numero del departamento: ");
            int depIndex = Integer.parseInt(sc.nextLine()) - 1;
            String depto = (depIndex >= 0 && depIndex < DEPARTAMENTOS.length)
                            ? DEPARTAMENTOS[depIndex]
                            : "Guatemala";

            System.out.print("Nacionalidad: ");
            String nac = sc.nextLine();

            Atleta a = new Atleta();
            a.setNombreCompleto(nombre);
            a.setEdad(edad);
            a.setDisciplina(disc);
            a.setDepartamento(depto);
            a.setNacionalidad(nac);
            a.setFechaIngreso(LocalDate.now());

            atletaServicio.registrarAtleta(a);
            System.out.println("Atleta registrado con exito.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void listarAtletas() {
        try {
            System.out.println("\n=== LISTADO DE ATLETAS ===");
            System.out.printf("%-5s %-20s %-5s %-15s %-15s %-15s%n",
                    "ID","NombreCompleto","Edad","Disciplina","Departamento","Nacionalidad");
            for (Atleta a : atletaServicio.listarAtletas()) {
                System.out.printf("%-5d %-20s %-5d %-15s %-15s %-15s%n",
                        a.getId(), a.getNombreCompleto(), a.getEdad(),
                        a.getDisciplina(), a.getDepartamento(), a.getNacionalidad());
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void guardarEnJSON() {
        try {
            atletaServicio.guardarEnJSON("atletas.json");
            System.out.println("Datos guardados en JSON con éxito.");
        } catch (Exception e) {
            System.out.println("Error al guardar en JSON: " + e.getMessage());
        }
    }

    private static void guardarEnCSV() {
        try {
            atletaServicio.guardarEnCSV("atletas.csv");
            System.out.println("Datos guardados en CSV con éxito.");
        } catch (Exception e) {
            System.out.println("Error al guardar en CSV: " + e.getMessage());
        }
    }

    private static void registrarEntrenamiento() {
        try {
            System.out.print("ID del atleta: ");
            int atletaId = Integer.parseInt(sc.nextLine());
            Atleta atleta = atletaServicio.obtenerAtletaPorId(atletaId);
            if (atleta == null) {
                System.out.println("Atleta no encontrado.");
                return;
            }

            LocalDate fecha = LocalDate.now();

            System.out.println("\nSeleccione el tipo de entrenamiento:");
            for (int i = 0; i < TIPOS_ENTRENAMIENTO.length; i++) {
                System.out.printf("%2d. %s%n", (i + 1), TIPOS_ENTRENAMIENTO[i]);
            }
            System.out.print("Numero del tipo: ");
            int tipoIndex = Integer.parseInt(sc.nextLine()) - 1;
            String tipo = (tipoIndex >= 0 && tipoIndex < TIPOS_ENTRENAMIENTO.length)
                            ? TIPOS_ENTRENAMIENTO[tipoIndex]
                            : TIPOS_ENTRENAMIENTO[0];

            System.out.print("Valor de rendimiento (tiempo, peso, distancia, etc.): ");
            double valor = Double.parseDouble(sc.nextLine());

            System.out.print("Ubicacion (N para Nacional / I para Internacional): ");
            String ubicacionTipo = sc.nextLine().trim().toUpperCase();
            String ubicacion = "Nacional";
            if (ubicacionTipo.equals("I")) {
                System.out.print("Ingresa Pais Internacional: ");
                String pais = sc.nextLine();
                ubicacion = "Internacional - " + pais;
            }

            Entrenamiento e = new Entrenamiento();
            e.setAtletaId(atletaId);
            e.setFecha(fecha);
            e.setTipoEntrenamiento(tipo);
            e.setValorRendimiento(valor);
            e.setUbicacion(ubicacion);

            entrenamientoServicio.registrarEntrenamiento(e);
            System.out.println("Entrenamiento registrado con Exito.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void procesarPago() {
        try {
            System.out.print("ID del atleta: ");
            int atletaId = Integer.parseInt(sc.nextLine());
            Atleta atleta = atletaServicio.obtenerAtletaPorId(atletaId);
            if (atleta == null) {
                System.out.println("Atleta no encontrado.");
                return;
            }

            var lista = entrenamientoServicio.listarEntrenamientosPorAtleta(atletaId);
            int entrenamientos = lista.size();
            int entrenamientosExtranjero = 0;
            for (var e : lista) {
                if (e.getUbicacion().startsWith("Internacional")) {
                    entrenamientosExtranjero++;
                }
            }

            System.out.print("¿Superó su mejor marca este mes? (S/N): ");
            boolean superoMarca = sc.nextLine().trim().equalsIgnoreCase("S");

            double montoPago = pagoServicio.calcularPago(entrenamientos, entrenamientosExtranjero, superoMarca);

            Pago p = new Pago();
            p.setAtletaId(atletaId);
            p.setMes(LocalDate.now().getMonthValue());
            p.setPeriodo(String.format("%04d-%02d", LocalDate.now().getYear(), LocalDate.now().getMonthValue()));
            p.setMonto(montoPago);
            p.setFechaGenerado(LocalDate.now());

            pagoDAO.insertar(p);

            System.out.println("\n--- PAGO DEL ATLETA ---");
            System.out.println("ID Pago: " + p.getId());
            System.out.println("Atleta: " + atleta.getNombreCompleto());
            System.out.println("Entrenamientos totales: " + entrenamientos);
            System.out.println("Entrenamientos en el extranjero: " + entrenamientosExtranjero);
            System.out.println("Superó su mejor marca: " + (superoMarca ? "Sí" : "No"));
            System.out.printf("Pago total mensual: Q%.2f%n", montoPago);

        } catch (Exception e) {
            System.out.println("Error al procesar pago: " + e.getMessage());
        }
    }
    private static void consultarPago() {
    try {
        System.out.print("ID del atleta: ");
        int atletaId = Integer.parseInt(sc.nextLine());
        var pagos = pagoDAO.listarPorAtleta(atletaId);

        if (pagos.isEmpty()) {
            System.out.println("No hay pagos registrados para este atleta.");
            return;
        }

        System.out.println("\n--- HISTORIAL DE PAGOS ---");
        System.out.printf("%-5s %-10s %-15s %-10s %-15s%n",
                "ID", "Mes", "Periodo", "Monto", "Fecha");

        for (Pago p : pagos) {
            System.out.printf("%-5d %-10d %-15s Q%-10.2f %-15s%n",
                    p.getId(), p.getMes(), p.getPeriodo(), p.getMonto(), p.getFechaGenerado());
        }
    } catch (Exception e) {
        System.out.println("Error al consultar pagos: " + e.getMessage());
    }
}
    private static void generarReportePagosCSV() {
    try {
        pagoDAO.exportarPagosCSV("reporte_pagos.csv");
        System.out.println("Reporte CSV de pagos generado exitosamente.");
    } catch (Exception e) {
        System.out.println("Error al generar reporte CSV: " + e.getMessage());
    }
}



    private static void consultarEstadisticas() {
        try {
            System.out.print("\nID del atleta: ");
            int atletaId = Integer.parseInt(sc.nextLine());
            Atleta atleta = atletaServicio.obtenerAtletaPorId(atletaId);
            if (atleta == null) {
                System.out.println("Atleta no encontrado.");
                return;
            }

            var lista = entrenamientoServicio.listarEntrenamientosPorAtleta(atletaId);
            if (lista.isEmpty()) {
                System.out.println("No hay entrenamientos registrados para este atleta.");
                return;
            }

            System.out.println("\n=== HISTORIAL DE ENTRENAMIENTOS DE " + atleta.getNombreCompleto() + " ===");
            System.out.printf("%-12s %-15s %-15s %-20s%n",
                    "Fecha","Tipo","Valor","Ubicación");
            double suma = 0;
            double mejorMarca = Double.NEGATIVE_INFINITY;
            double sumaNacional=0; int countNac=0;
            double sumaInternacional=0; int countInt=0;

            for (Entrenamiento en : lista) {
                System.out.printf("%-12s %-15s %-15.2f %-20s%n",
                        en.getFecha(), en.getTipoEntrenamiento(),
                        en.getValorRendimiento(), en.getUbicacion());
                suma += en.getValorRendimiento();
                if (en.getValorRendimiento()>mejorMarca) mejorMarca=en.getValorRendimiento();
                if (en.getUbicacion().startsWith("Nacional")) {
                    sumaNacional+=en.getValorRendimiento(); countNac++;
                } else {
                    sumaInternacional+=en.getValorRendimiento(); countInt++;
                }
            }

            double promedio = suma / lista.size();
            double promNac = (countNac>0)? sumaNacional/countNac : 0;
            double promInt = (countInt>0)? sumaInternacional/countInt : 0;

            System.out.println("\n--- ESTADISTICAS ---");
            System.out.printf("Promedio de rendimiento: %.2f%n", promedio);
            System.out.printf("Mejor marca: %.2f%n", mejorMarca);
            System.out.printf("Promedio Nacional: %.2f%n", promNac);
            System.out.printf("Promedio Internacional: %.2f%n", promInt);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
