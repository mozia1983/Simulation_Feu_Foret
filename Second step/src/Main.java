import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        SimulationParametersProvider parametersProvider;
        try {
            parametersProvider = new SimulationParametersProvider();
        } catch (IOException | JsonIOException | JsonSyntaxException e) {
            System.out.println(e.getMessage());
            return;
        }
        if (!parametersProvider.checkParametersValidity()) return;
        Grid grid = new Grid(parametersProvider.provideNbRows(), parametersProvider.provideNbColumns(), parametersProvider.provideIgnitionProbability());
        grid.initializePitCells(parametersProvider.providePitCellsIndices());
        grid.runSimulation();
    }
}