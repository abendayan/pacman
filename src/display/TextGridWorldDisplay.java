package display;

import agents.ValueEstimationAgent;
import game.Directions;
import grid.Grid;
import mdp.Gridworld;
import utils.Counter;
import utils.Pair;
import utils.Tuple;

import java.util.*;

public class TextGridWorldDisplay implements GridWorldDisplay {
    Gridworld gridworld;
    TextGridWorldDisplay(Gridworld gridworld) {
        this.gridworld = gridworld;
    }

    @Override
    public void pause() {}

    @Override
    public void start() {}

    @Override
    public void displayValues(ValueEstimationAgent agent, Tuple currentState, String message) {
        if(!message.equals("")) {
            System.out.println(message);
        }
        Counter<Tuple> values = new Counter<>();
        Map<Tuple, Directions> policy = new HashMap<>();
        ArrayList<Tuple> states = this.gridworld.getStates();
        for(Tuple state : states) {
            values.addNumberTime(state, agent.getValue(state));
            policy.put(state, agent.getPolicy(state));
        }
        prettyPrintValues(values, policy, currentState);
    }

    @Override
    public void displayNullValues(Tuple state, String message) {
        if(!message.equals("")) {
            System.out.println(message);
        }
        prettyPrintNullValues(state);
    }

    @Override
    public void displayQValues(ValueEstimationAgent agent, Tuple currentState, String message) {
        if(!message.equals("")) {
            System.out.println(message);
        }
        Counter<Pair<Tuple, Directions>> qvalues = new Counter<>();
        ArrayList<Tuple> states = this.gridworld.getStates();
        for(Tuple state: states) {
            for(Directions action : this.gridworld.getPossibleActions(state)) {
                qvalues.addNumberTime(new Pair<>(state, action), agent.getQValue(state, action));
            }
        }
        prettyPrintQValues(qvalues, currentState, "");
    }

    private void prettyPrintValues(Counter<Tuple> values, Map<Tuple, Directions> policy, Tuple currentState) {
        Grid grid = this.gridworld.grid;
        int maxLen = 11;
        ArrayList<ArrayList<String>> newRows = new ArrayList<>();
        for(int y = 0; y < grid.height; y++) {
            ArrayList<String> newRow = new ArrayList<>();
            for(int x = 0; x < grid.width; x++) {
                Tuple state = new Tuple(x, y);
                float value = values.count(state);
                Directions action = Directions.STOP;
                if(policy != null && policy.containsKey(state)) {
                    action = policy.get(state);
                }
                ArrayList<Directions> actions = this.gridworld.getPossibleActions(state);
                if(!actions.contains(action) && actions.contains(Directions.EXIT)) {
                    action = Directions.EXIT;
                }
                ArrayList<String> valString = new ArrayList<>();
                if(action.equals(Directions.EXIT)) {
                    valString.add(border(Float.toString(value)));
                }
                else {
                    valString.add("");
                    valString.add("");
                    valString.add(Float.toString(value));
                    valString.add("");
                    valString.add("");
                    StringBuilder temp = new StringBuilder();
                    for(int i = 0; i < maxLen; i++) {
                        temp.append(" ");
                    }
                    valString.add(temp.toString());
                }
                if(grid.data[x][y].equals("S")) {
                    valString = new ArrayList<>();
                    valString.add("");
                    valString.add("");
                    valString.add("S: "+Float.toString(value));
                    valString.add("");
                    valString.add("");
                    StringBuilder temp = new StringBuilder();
                    for(int i = 0; i < maxLen; i++) {
                        temp.append(" ");
                    }
                    valString.add(temp.toString());
                }
                if(grid.data[x][y].equals("#")) {
                    valString = new ArrayList<>();
                    valString.add("");
                    valString.add("#####");
                    valString.add("");
                    valString.add("#####");
                    valString.add("");
                    valString.add("#####");
                    valString.add("");
                    StringBuilder temp = new StringBuilder();
                    for(int i = 0; i < maxLen; i++) {
                        temp.append(" ");
                    }
                    valString.add(temp.toString());
                }
                if(currentState.equals(state)) {
                    int l = valString.get(1).length();
                    if(l == 0) {
                        valString.set(1, "*");
                    }
                    else {
                        StringBuilder temp = new StringBuilder("|");
                        for(int i = 0; i < ((l-1)/2) -1; i++) {
                            temp.append(" ");
                        }
                        temp.append("*");
                        for(int i = 0; i < ((l)/2) -1; i++) {
                            temp.append(" ");
                        }
                        temp.append("|");
                        valString.set(1, temp.toString());
                    }
                }
                StringBuilder temp = new StringBuilder();
                for(int i = 0; i < maxLen/2; i++) {
                    temp.append(" ");
                }
                switch (action) {
                    case EAST:
                        valString.set(2, "  " + valString.get(2) + " >");
                        break;
                    case WEST:
                        valString.set(2, "< " + valString.get(2) + "  ");
                        break;
                    case NORTH:
                        valString.set(0, temp.toString() + "^" + temp.toString());
                        break;
                    case SOUTH:
                        valString.set(0, temp.toString() + "v" + temp.toString());
                        break;
                }
                String newCell = "";
                for(String val : valString) {
                    newCell += val + "\n";
                }
                newRow.add(newCell);
            }
            newRows.add(newRow);
        }
        int numCols = grid.width;
        for(int i = 0; i < newRows.size(); i++) {
            newRows.get(i).add(0, "\n\n" + Integer.toString(i));
        }
        Collections.reverse(newRows);
        ArrayList<String> colLabels = new ArrayList<>();
        for(int colNum = 0; colNum < numCols; colNum++) {
            colLabels.add(Integer.toString(colNum));
        }
        colLabels.add(0, " ");
        ArrayList<ArrayList<String>> finalRows = new ArrayList<ArrayList<String>>();
        finalRows.add(colLabels);
        finalRows.addAll(newRows);
        indent(finalRows, true, "-", "|", "center", true, "|", "|");
    }

    private void indent(ArrayList<ArrayList<String>> rows, boolean hasHeader, String headerChar, String delim,
                        String justify, boolean seperateRows, String prefix, String postfix) {
        // break each logical row into one or more physical ones
        ArrayList<ArrayList<ArrayList<String>>> logicalRows = new ArrayList<>();
        ArrayList<ArrayList<String>> arrayRows = new ArrayList<>();
        for(ArrayList<String> row : rows) {
            arrayRows.addAll(rowWrapper(row));
        }
        // columns of physical rows
        ArrayList<ArrayList<String>> columnLogical = new ArrayList<>();
        int maxLen = 0;
        for(ArrayList<ArrayList<String>> aRow : logicalRows) {
            if(aRow.size() > maxLen) {
                maxLen = aRow.size();
            }
        }
        for(int i = 0; i < maxLen; i++) {
            for(ArrayList<ArrayList<String>> aRow : logicalRows) {
                columnLogical.get(i).add(aRow.get(0).get(i));
            }
        }
    }

    private ArrayList<ArrayList<String>> rowWrapper(ArrayList<String> row) {
        ArrayList<ArrayList<String>> newRows = new ArrayList<>();
        for(String cell : row) {
            ArrayList<String> cellList = new ArrayList<>();
            Collections.addAll(cellList, cell.split("\n"));
            newRows.add(cellList);
        }
        ArrayList<ArrayList<String>> result = new ArrayList<>();
        int maxLen = 0;
        for(ArrayList<String> aRow : newRows) {
            if(aRow.size() > maxLen) {
                maxLen = aRow.size();
            }
        }
        for(int i = 0; i < maxLen; i++) {
            for(ArrayList<String> aRow : newRows) {
                if(aRow.size() < i) {
                    result.get(i).add(aRow.get(i));
                }
                else {
                    result.get(i).add("");
                }
            }
        }
        return result;
    }

    private void prettyPrintNullValues(Tuple currentState) {

    }

    private void prettyPrintQValues(Counter<Pair<Tuple, Directions>> qvalues, Tuple currentState, String message) {

    }

    private String border(String value) {
        int length = value.length();
        StringBuilder pieces = new StringBuilder();
        for(int i = 0; i < length + 2; i++) {
            pieces.append("-");
        }
        pieces.append("\n");
        for(int i = 0; i < length + 2; i++) {
            pieces.append(" ");
        }
        pieces.append("|\n | ");
        pieces.append(value);
        pieces.append(" | \n|");
        for(int i = 0; i < length + 2; i++) {
            pieces.append(" ");
        }
        pieces.append("|\n");
        for(int i = 0; i < length + 2; i++) {
            pieces.append("-");
        }
        return pieces.toString();
    }
}
