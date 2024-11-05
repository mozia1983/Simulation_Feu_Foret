

function propagate_to_neighbor(x, y, active_fire_cells, future_fire_cells, ash_cells) {
    var found_as_ash = ash_cells.some(ash_cell => (ash_cell.X === x && ash_cell.Y === y));
    var found_as_fire = active_fire_cells.some(fire_cell => (fire_cell.X === x && fire_cell.Y === y));
    if (!found_as_ash && !found_as_fire)
        future_fire_cells.push({
            "X": x,
            "Y": y
        });
}

function propagate(x, y, active_fire_cells, future_fire_cells, ash_cells) {
    // in case of one row grid the x === 0 and x === rows - 1 are the same thats why we use else if instead of another if
    if (x === 0) {
        propagate_to_neighbor(x + 1, y, active_fire_cells, future_fire_cells, ash_cells);
        if (y !== 0)
            propagate_to_neighbor(x, y - 1, active_fire_cells, future_fire_cells, ash_cells);
        if (y !== columns - 1)
            propagate_to_neighbor(x, y + 1, active_fire_cells, future_fire_cells, ash_cells);
    } else if (x === rows - 1) {
        propagate_to_neighbor(x - 1, y, active_fire_cells, future_fire_cells, ash_cells);
        if (y !== 0)
            propagate_to_neighbor(x, y - 1, active_fire_cells, future_fire_cells, ash_cells);
        if (y !== columns - 1)
            propagate_to_neighbor(x, y + 1, active_fire_cells, future_fire_cells, ash_cells);
    } else {
        propagate_to_neighbor(x - 1, y, active_fire_cells, future_fire_cells, ash_cells);
        propagate_to_neighbor(x + 1, y, active_fire_cells, future_fire_cells, ash_cells);
        if (y !== 0)
            propagate_to_neighbor(x, y - 1, active_fire_cells, future_fire_cells, ash_cells);
        if (y !== columns - 1)
            propagate_to_neighbor(x, y + 1, active_fire_cells, future_fire_cells, ash_cells);
    }
    return;
}


const rows = 4; // Grid rows
const columns = 4; // Grid columns
let active_fire_cells = [{
    "X": 1,
    "Y": 1
}]; // The initial fire pits coordinates on grid

let future_fire_cells = []; // The new fire pits coordinates on grid after one step propagation
let ash_cells = []; // The fire pits that have propagated and became ash prior to the current step of propagation
let new_born_ash_cells = []; // The fire pits coordinates on grid
let counter = 0; // The initial fire pits coordinates on grid

// At each step active fire cells propagate to create future fire cells and turn into ash cells
while (active_fire_cells.length) {
    let grid = document.createElement('div');
    grid.className = 'grid';    
    grid.style.gridTemplateRows = `repeat(${rows}, 1fr)`;
    grid.style.gridTemplateColumns = `repeat(${columns}, 1fr)`;
    for (let i = 0; i < rows; i++) {
        for (let j = 0; j < columns; j++) {
            const fire_node = active_fire_cells.some(pit => (pit.X === i && pit.Y === j));
            const ash_cell = ash_cells.some(cell => (cell.X === i && cell.Y === j));
            if (fire_node) {
                const cell = document.createElement('div');
                cell.classList.add('fireNode');
                grid.appendChild(cell);
                new_born_ash_cells.push({
                    "X": i,
                    "Y": j
                });
                propagate(i, j, active_fire_cells, future_fire_cells, ash_cells);
            }
            else if (ash_cell) {
                const cell = document.createElement('div');
                cell.classList.add('ashNode');
                grid.appendChild(cell);
            }
            else {
                const cell = document.createElement('div');
                cell.classList.add('treeNode');
                grid.appendChild(cell);
            }
        }
    }

    // Fire cells that have propagated are added to ash cells as they have become one
    for (let index = 0; index < new_born_ash_cells.length; index++) {
        ash_cells.push(new_born_ash_cells[index]);
    }
    new_born_ash_cells = [];

    // Future Fire cells that have been created at current step become the active fire cells for the next step
    active_fire_cells = [];
    for (let index = 0; index < future_fire_cells.length; index++) {
        const found = active_fire_cells.some(pit => (pit.X === future_fire_cells[index] && pit.Y === future_fire_cells[index]));
        if (!found) {
            active_fire_cells.push(future_fire_cells[index]);
        }
    }
    future_fire_cells = [];

    document.body.appendChild(grid);
    let title = document.createElement('h2');
    title.innerText ="Step " + counter;
    document.body.appendChild(title);
    ++counter;
}

// Display the grid when all cells turned into ash cells
let grid = document.createElement('div');
grid.className = 'grid';
grid.style.gridTemplateRows = `repeat(${rows}, 1fr)`;
grid.style.gridTemplateColumns = `repeat(${columns}, 1fr)`;
for (let i = 0; i < rows; i++) {
    for (let j = 0; j < columns; j++) {

            const cell = document.createElement('div');
            cell.classList.add('ashNode');
            grid.appendChild(cell);
    }
}
document.body.appendChild(grid);
let title = document.createElement('h2');
title.innerText ="Step " + counter;
document.body.appendChild(title);