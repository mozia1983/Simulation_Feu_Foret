import com.google.gson.*;

import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

public class SimulationParametersProvider {

    private final JsonObject parameters;

    SimulationParametersProvider() throws IOException, JsonIOException, JsonSyntaxException {
        FileReader fileReader = new FileReader("src/simulation_parameters.json");
        parameters = (JsonObject) JsonParser.parseReader(Objects.requireNonNull(fileReader));
        fileReader.close();
    }

    public boolean checkParametersValidity() {
        if (parameters == null) {
            System.out.println("Parameters for grid initialization not found");
            return false;
        }
        if (!parameters.has("nbRows")) {
            System.out.println("Rows field missing");
            return false;
        }
        int nbRows = parameters.getAsJsonPrimitive("nbRows").getAsInt();
        if (nbRows < 0) {
            System.out.println("Rows field value is incorrect");
            return false;
        }

        if (!parameters.has("nbColumns")) {
            System.out.println("Columns field missing");
            return false;
        }
        int nbColumns = parameters.getAsJsonPrimitive("nbColumns").getAsInt();
        if (nbColumns < 0) {
            System.out.println("Columns field value is incorrect");
            return false;
        }

        if (!parameters.has("ignitionProbability")) {
            System.out.println("Ignition Probability field missing");
            return false;
        }
        double ignitionProbability = parameters.getAsJsonPrimitive("ignitionProbability").getAsDouble();
        if (ignitionProbability < 0. || ignitionProbability > 1.) {
            System.out.println("Ignition Probability field value is incorrect");
            return false;
        }

        if (parameters.has("pitCellsIndices")) {
            JsonArray pitsIndices = parameters.get("pitCellsIndices").getAsJsonArray();
            for (int i = 0; i < pitsIndices.size(); i++) {
                JsonObject pit = pitsIndices.get(i).getAsJsonObject();
                if (pit.has("rowIndex") && pit.has("columnIndex")) {
                    int rowIndex = pit.getAsJsonPrimitive("rowIndex").getAsInt();
                    int columnIndex = pit.getAsJsonPrimitive("columnIndex").getAsInt();
                    if (rowIndex < 0 || rowIndex > (nbRows - 1)) {
                        System.out.println("Row index in field " + i + " of pitsIndices is out of bounds");
                        return false;
                    }
                    if (columnIndex < 0 || columnIndex > (nbColumns - 1)) {
                        System.out.println("Column index in field " + i + " of pitsIndices is out of bounds");
                        return false;
                    }
                } else {
                    System.out.println("field " + i + " of pitsIndices is corrupted");
                    return false;
                }
            }
        }
        return true;
    }

    public int provideNbRows() {
        return parameters.getAsJsonPrimitive("nbRows").getAsInt();
    }

    public int provideNbColumns() {
        return parameters.getAsJsonPrimitive("nbColumns").getAsInt();
    }

    public double provideIgnitionProbability() {
        return parameters.getAsJsonPrimitive("ignitionProbability").getAsDouble();
    }



    public int[][] providePitCellsIndices() {
        JsonArray pitCellsIndices = parameters.get("pitCellsIndices").getAsJsonArray();
        int[][] indices = new int[pitCellsIndices.size()][2];
        for (int i = 0; i < pitCellsIndices.size(); i++) {
            JsonObject pitCellIndices = pitCellsIndices.get(i).getAsJsonObject();
            indices[i][0] = pitCellIndices.getAsJsonPrimitive("rowIndex").getAsInt();
            indices[i][1] = pitCellIndices.getAsJsonPrimitive("columnIndex").getAsInt();
        }
        return indices;
    }

}
