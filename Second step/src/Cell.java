public class Cell {

    private final int rowIndex;
    private final int columnIndex;
    private CellState actualState = CellState.GREEN;
    private CellState futureState = CellState.GREEN;

    Cell(int rowIndex, int columnIndex) {
        this.rowIndex = rowIndex;
        this.columnIndex = columnIndex;
    }

    public int getRowIndex() {
        return this.rowIndex;
    }

    public int getColumnIndex() {
        return this.columnIndex;
    }

    public void setActualState(CellState state) {
        this.actualState = state;
    }

    public CellState getActualState() {
        return actualState;
    }

    public void setFutureState(CellState state) {
        this.futureState = state;
    }

    public CellState getFutureState() {
        return futureState;
    }
}
