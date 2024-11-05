public class Grid {
    private final int nbRows;
    private final int nbColumns;
    private final Cell[][] cells;
    private boolean fireIsActive = false;
    private final double ignitionProbability;

    Grid(int nbRows, int nbColumns, double ignitionProbability) {
        this.nbRows = nbRows;
        this.nbColumns = nbColumns;
        this.ignitionProbability = ignitionProbability;
        cells = new Cell[nbRows][nbColumns];
        for (int i = 0; i < nbRows; i++) {
            for (int j = 0; j < nbColumns; j++) {
                cells[i][j] = new Cell(i, j);
            }
        }
    }

    public void initializePitCells (int[][] indices) {
        if(indices.length >0) fireIsActive = true;

        for (int[] pitCellIndices : indices)
        {
            igniteCell(pitCellIndices[0],pitCellIndices[1]);
        }
    }

    private Cell getCellFromIndices(int rowIndex, int columnIndex) {
        return cells[rowIndex][columnIndex];
    }

    private void igniteCell(int rowIndex, int columnIndex) {
        Cell cell = getCellFromIndices(rowIndex, columnIndex);
        CellState futureState = cell.getFutureState();
        if (futureState == CellState.GREEN) cell.setFutureState(CellState.RED);
    }

    private void igniteRightNeighborOfCell(Cell pitCell) {
            igniteCell(pitCell.getRowIndex() + 1, pitCell.getColumnIndex());
    }

    private void igniteLeftNeighborOfCell(Cell pitCell) {
            igniteCell(pitCell.getRowIndex() - 1, pitCell.getColumnIndex());
    }

    private void igniteTopNeighborOfCell(Cell pitCell) {
            igniteCell(pitCell.getRowIndex(), pitCell.getColumnIndex() - 1);
    }

    private void igniteBottomNeighborOfCell(Cell pitCell) {
            igniteCell(pitCell.getRowIndex(), pitCell.getColumnIndex() + 1);
    }

    private boolean ignitionIsPossible(){
        double randomNumber = Math.random();
        return (randomNumber <= ignitionProbability);
    }

    private void propagateFromCellToNeighbors(Cell pitCell) {
        // Before igniting neighbor cell we check for border cell cases and for possibility of propagation
        if ((pitCell.getRowIndex() < (nbRows - 1)) && ignitionIsPossible())
            igniteRightNeighborOfCell(pitCell);
        if ((pitCell.getRowIndex() > 0) && ignitionIsPossible())
            igniteLeftNeighborOfCell(pitCell);
        if ((pitCell.getColumnIndex() > 0) && ignitionIsPossible())
            igniteTopNeighborOfCell(pitCell);
        if ((pitCell.getColumnIndex() < (nbColumns - 1)) && ignitionIsPossible())
            igniteBottomNeighborOfCell(pitCell);
    }

    private void preparePitCellsForPropagation() {
        for (int rowIndex = 0; rowIndex < nbRows; rowIndex++) {
            for (int columnIndex = 0; columnIndex < nbColumns; columnIndex++) {
                Cell currentCell = cells[rowIndex][columnIndex];
                if (currentCell.getFutureState() == CellState.RED) {
                    currentCell.setActualState(CellState.RED);
                    currentCell.setFutureState(CellState.GREY);
                }
            }
        }
    }

    private void displayGrid(int counter) {
        if (nbColumns < 1 || nbRows < 1) {
            System.out.println("Grid cannot be displayed : dimensions are corrupt");
            return;
        }
        char[][] output = new char[(2 * nbRows) - 1][(2 * nbColumns) - 1];

        for (int rowIndex = 0; rowIndex < (nbRows - 1); rowIndex++) {
            Cell currentCell = cells[rowIndex][0];
            Cell nextVerticalCell = cells[rowIndex + 1][0];
            switch (currentCell.getActualState()) {
                case RED : output[2 * rowIndex][0] = 'R'; break;
                case GREEN : output[2 * rowIndex][0] = 'G'; break;
                case GREY : output[2 * rowIndex][0] = 'A'; break;
            }
            if ((currentCell.getActualState() == CellState.GREEN && nextVerticalCell.getActualState() == CellState.RED)
                    || (currentCell.getActualState() == CellState.RED && nextVerticalCell.getActualState() == CellState.GREEN)) {
                output[(2 * rowIndex) + 1][0] = '|';
            } else {
                output[(2 * rowIndex) + 1][0] = ' ';
            }
        }
        Cell lastVerticalCell = cells[nbRows - 1][0];
        switch (lastVerticalCell.getActualState()) {
            case RED : output[2 * (nbRows - 1)][0] = 'R'; break;
            case GREEN : output[2 * (nbRows - 1)][0] = 'G'; break;
            case GREY : output[2 * (nbRows - 1)][0] = 'A'; break;
        }
        for (int columnIndex = 0; columnIndex < (nbColumns - 1); columnIndex++) {
            Cell currentCell = cells[0][columnIndex];
            Cell nextHorizontalCell = cells[0][columnIndex + 1];
            switch (currentCell.getActualState()) {
                case RED : output[0][2 * columnIndex] = 'R'; break;
                case GREEN : output[0][2 * columnIndex] = 'G'; break;
                case GREY : output[0][2 * columnIndex] = 'A'; break;
            }
            if ((currentCell.getActualState() == CellState.GREEN && nextHorizontalCell.getActualState() == CellState.RED)
                    || (currentCell.getActualState() == CellState.RED && nextHorizontalCell.getActualState() == CellState.GREEN)) {
                output[0][(2 * columnIndex) + 1] = '-';
            } else {
                output[0][(2 * columnIndex) + 1] = ' ';
            }
        }
        Cell lastHorizontalCell = cells[0][nbColumns - 1];
        switch (lastHorizontalCell.getActualState()) {
            case RED : output[0][2 * (nbColumns - 1)] = 'R'; break;
            case GREEN : output[0][2 * (nbColumns - 1)] = 'G'; break;
            case GREY : output[0][2 * (nbColumns - 1)] = 'A'; break;
        }
        System.out.println(String.valueOf(output[0]));


        for (int rowIndex = 1; rowIndex < nbRows; rowIndex++) {
            for (int columnIndex = 1; columnIndex < nbColumns; columnIndex++) {
                Cell currentCell = cells[rowIndex][columnIndex];
                Cell previousHorizontalCell = cells[rowIndex][columnIndex - 1];
                Cell previousVerticalCell = cells[rowIndex - 1][columnIndex];

                switch (currentCell.getActualState()) {
                    case RED : output[2 * rowIndex][2 * columnIndex] = 'R'; break;
                    case GREEN : output[2 * rowIndex][2 * columnIndex] = 'G'; break;
                    case GREY : output[2 * rowIndex][2 * columnIndex] = 'A'; break;
                }
                if ((currentCell.getActualState() == CellState.GREEN && previousHorizontalCell.getActualState() == CellState.RED)
                        || (currentCell.getActualState() == CellState.RED && previousHorizontalCell.getActualState() == CellState.GREEN)) {
                    output[2 * rowIndex][2 * columnIndex - 1] = '-';
                } else {
                    output[2 * rowIndex][2 * columnIndex - 1] = ' ';
                }

                if ((currentCell.getActualState() == CellState.GREEN && previousVerticalCell.getActualState() == CellState.RED)
                        || (currentCell.getActualState() == CellState.RED && previousVerticalCell.getActualState() == CellState.GREEN)) {
                    output[2 * rowIndex - 1][2 * columnIndex] = '|';
                } else {
                    output[2 * rowIndex - 1][2 * columnIndex] = ' ';
                }
                output[2 * rowIndex - 1][2 * columnIndex - 1] = ' ';
            }
            System.out.println(String.valueOf(output[(2 * rowIndex) - 1]));
            System.out.println(String.valueOf(output[2 * rowIndex]));
        }
        if (counter != 0)
            System.out.println("\n" + "--- Step "+ counter + " ---" + "\n");
        else
            System.out.println("\n" + "Initial grid" + "\n");

    }

    private void runOneStep() {
        for (int rowIndex = 0; rowIndex < nbRows; rowIndex++) {
            for (int columnIndex = 0; columnIndex < nbColumns; columnIndex++) {
                Cell currentCell = cells[rowIndex][columnIndex];
                if (currentCell.getActualState() == CellState.RED) {
                    propagateFromCellToNeighbors(currentCell);
                    currentCell.setActualState(CellState.GREY);
                }
            }
        }
    }

    private void updateFireActivity() {
        fireIsActive = false;
        for (int rowIndex = 0; rowIndex < nbRows; rowIndex++) {
            for (int columnIndex = 0; columnIndex < nbColumns; columnIndex++) {
                Cell currentCell = cells[rowIndex][columnIndex];
                if (currentCell.getFutureState() == CellState.RED) {
                    fireIsActive = true;
                    return;
                }
            }
        }
    }

    /*
     * The fire simulation run on a 2D grid composed of cells,
     * Cells can be GREEN(can catch fire), RED(on fire), GREY(ash)
     * At each step The red cells propagate fire to their green adjacent neighbours with certain probability
     * Cells have 2 states :
     *  The actual state represent the state of the cell for the actual step of propagation
     *  The future state hold the consequence of the propagation when a cell is set on fire
     * At each step, before the propagation, the grid update the actual state of cells that were set on fire
     * These cells become the pit cells that will propagate
     * Simulation ends when there is no cells on fire
    * */
    public void runSimulation() {
        int counter = 0;
        while (fireIsActive) {
            preparePitCellsForPropagation();
            displayGrid(counter++);
            runOneStep();
            updateFireActivity();
        }
        displayGrid(counter);
    }
}
