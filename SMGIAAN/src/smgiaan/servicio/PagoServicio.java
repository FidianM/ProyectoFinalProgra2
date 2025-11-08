/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package smgiaan.servicio;

public class PagoServicio {

    private static final double SUELDO_BASE = 7000.0;  // sueldo mensual base
    private static final double BONO_ENTRENAMIENTO = 200.0; // por cada entrenamiento
    private static final double BONO_ENTR_EXTRANJERO = 50.0; // por entrenamiento internacional
    private static final double BONO_MEJOR_MARCA = 250.0; // si supera su mejor marca

    public double calcularPago(int entrenamientos, int entrenamientosExtranjero, boolean superoMarca) {
        double pago = SUELDO_BASE;
        pago += entrenamientos * BONO_ENTRENAMIENTO;
        pago += entrenamientosExtranjero * BONO_ENTR_EXTRANJERO;
        if (superoMarca) {
            pago += BONO_MEJOR_MARCA;
        }
        return pago;
    }
}

