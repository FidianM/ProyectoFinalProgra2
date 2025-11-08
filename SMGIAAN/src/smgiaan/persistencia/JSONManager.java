/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package smgiaan.persistencia;

import com.google.gson.*;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Arrays;
import smgiaan.modelo.Atleta;

public class JSONManager {

    // Adaptador para LocalDate
    private static class LocalDateAdapter implements JsonSerializer<LocalDate>, JsonDeserializer<LocalDate> {
        private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;

        @Override
        public JsonElement serialize(LocalDate date, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(date.format(formatter));
        }

        @Override
        public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            return LocalDate.parse(json.getAsString(), formatter);
        }
    }

    // Crear Gson configurado
    private static Gson buildGson() {
        return new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .setPrettyPrinting()
                .create();
    }

    // Guardar lista de atletas en JSON
    public static void guardarListaEnJSON(List<Atleta> lista, String fileName) throws IOException {
        Gson gson = buildGson();
        try (FileWriter writer = new FileWriter(fileName)) {
            gson.toJson(lista, writer);
        }
    }

    // Leer lista de atletas desde JSON
    public static List<Atleta> leerListaDesdeJSON(String fileName) throws IOException {
        Gson gson = buildGson();
        try (FileReader reader = new FileReader(fileName)) {
            Atleta[] atletas = gson.fromJson(reader, Atleta[].class);
            return Arrays.asList(atletas);
        }
    }
}

