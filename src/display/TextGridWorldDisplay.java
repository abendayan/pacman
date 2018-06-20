package display;

import agents.ValueEstimationAgent;
import game.Directions;
import grid.Grid;
import mdp.Gridworld;
import utils.Counter;
import utils.Function;
import utils.Pair;
import utils.Tuple;

import java.util.*;

public class TextGridWorldDisplay implements GridWorldDisplay {
    Gridworld gridworld;
    public TextGridWorldDisplay(Gridworld gridworld) {
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
//        for(int x = 0; x < grid.width; x++) {
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
                    String[] borderString =  border(Float.toString(value)).split("\n");
                    for(String bord : borderString) {
                        valString.add(bord);
                    }
                }
                else {
                    valString.add("");
                    valString.add("");
                    valString.add(Float.toString(value));
                    valString.add("");
//                    valString.add("");
                    StringBuilder temp = new StringBuilder();
                    for(int i = 0; i < maxLen; i++) {
                        temp.append(" ");
                    }
                    valString.add(temp.toString());
                }
                if(grid.data[y][x].equals("S")) {
                    valString = new ArrayList<>();
                    valString.add("");
                    valString.add("");
                    valString.add("S: "+Float.toString(value));
                    valString.add("");
//                    valString.add("");
                    StringBuilder temp = new StringBuilder();
                    for(int i = 0; i < maxLen; i++) {
                        temp.append(" ");
                    }
                    valString.add(temp.toString());
                }
                if(grid.data[y][x].equals("#")) {
                    valString = new ArrayList<>();
                    valString.add("");
                    valString.add("#####");
                    valString.add("#####");
                    valString.add("#####");
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
        System.out.println(indent(finalRows, true, "-", "|", "center", true, "|", "|"));
    }

    private String indent(ArrayList<ArrayList<String>> rows, boolean hasHeader, String headerChar, String delim,
                        String justify, boolean seperateRows, String prefix, String postfix) {
        // break each logical row into one or more physical ones
        ArrayList<ArrayList<ArrayList<String>>> logicalRows = new ArrayList<>();
//        ArrayList<ArrayList<String>> arrayRows = new ArrayList<>();
        int p = 0;
        for(ArrayList<String> row : rows) {
            logicalRows.add(rowWrapper(row));
            p+=1;
        }
        // columns of physical rows
        ArrayList<ArrayList<String>> columns = new ArrayList<>();
        int maxLen = logicalRows.get(0).get(0).size();
        for(int i = 0; i < maxLen; i++) {
            columns.add(new ArrayList<>());
            for(ArrayList<ArrayList<String>> aRow : logicalRows) {
                for(ArrayList<String> inRow : aRow) {
                    columns.get(i).add(inRow.get(i));
                }
            }
        }
        ArrayList<Integer> maxWidth = new ArrayList<>();
        int totalMaxWidth = 0;
        for(ArrayList<String> column: columns) {
            int currentMax = 0;
            for(String item : column) {
                if(item.length() > currentMax) {
                    currentMax = item.length();
                }
            }
            maxWidth.add(currentMax);
            totalMaxWidth += currentMax;
        }
        String rowSeparator = "";
        for(int i = 0; i < (prefix.length() + postfix.length() + totalMaxWidth + (delim.length()*(maxWidth.size()-1))); i++) {
            rowSeparator += headerChar;
        }
        String indentResult = "";
        if(seperateRows) {
            indentResult += rowSeparator;
            indentResult += "\n";
        }
        int i;
        for(ArrayList<ArrayList<String>> physicalRows : logicalRows) {

            for(ArrayList<String> row : physicalRows) {
                indentResult += prefix;
                i = 0;
                for(String item : row) {
                    switch (justify) {
                        case "center":
                            if(item.equals("")) {
                                indentResult += Function.centerString(maxWidth.get(i), item + " ");
                            }
                            else {
                                indentResult += Function.centerString(maxWidth.get(i), item);
                            }
                            break;
                        case "left":
                            indentResult += Function.padLeft(maxWidth.get(i), item);
                            break;
                        case "right":
                            indentResult += Function.padRight(maxWidth.get(i), item);
                            break;
                    }
                    if(i < row.size() - 1) {
                        indentResult += delim;
                    }
                    i += 1;
                }
                indentResult += postfix;
                indentResult += "\n";
            }
            if(seperateRows || hasHeader) {
                hasHeader = false;
                indentResult += rowSeparator;
                indentResult += "\n";
            }
        }
        return indentResult;
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
            if(result.size() <= i) {
                result.add(new ArrayList<>());
            }
            for(ArrayList<String> aRow : newRows) {
                if(aRow.size() > i) {
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
        Grid grid = this.gridworld.grid;
        int maxLen = 11;
        ArrayList<ArrayList<String>> newRows = new ArrayList<>();
        for(int y = 0; y < grid.height; y++) {
            ArrayList<String> newRow = new ArrayList<>();
            for(int x = 0; x < grid.width; x++) {
                Tuple state = new Tuple(x, y);
                Directions action = null;
                ArrayList<Directions> actions = this.gridworld.getPossibleActions(state);
                if(actions.contains(Directions.EXIT)) {
                    action = Directions.EXIT;
                }
                ArrayList<String> valString = new ArrayList<>();
                if(grid.data[x][y].equals("S")) {
                    valString.add("\n\nS\n\n");
                    for(int i = 0; i < maxLen; i++) {
                        valString.add(" ");
                    }
                }
                else if(grid.data[x][y].equals("#")) {
                    valString.add("\n#####\n#####\n#####\n");
                    for(int i = 0; i < maxLen; i++) {
                        valString.add(" ");
                    }
                }
                else if(Function.isInteger(grid.data[x][y]) || Function.isFloat(grid.data[x][y])) {
                    valString.add(border(grid.data[x][y]));
                }
                else {
                    valString.add(border("  "));
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
        System.out.println(indent(finalRows, true, "-", "|", "center", true, "|", "|"));

    }

    private void prettyPrintQValues(Counter<Pair<Tuple, Directions>> qvalues, Tuple currentState, String message) {
        Grid grid = this.gridworld.grid;
        int maxLen = 11;
        ArrayList<ArrayList<String>> newRows = new ArrayList<>();
        for(int y = 0; y < grid.height; y++) {
            ArrayList<String> newRow = new ArrayList<>();
            for(int x = 0; x < grid.width; x++) {
                Tuple state = new Tuple(x, y);
                ArrayList<Directions> actions = this.gridworld.getPossibleActions(state);
                if(actions.size() == 0) {
                    actions = new ArrayList<>();
                    actions.add(null);
                }
                Float bestQ = 0f;
                for(Directions action : actions) {
                    if(qvalues.count(new Pair<>(state, action)) > bestQ) {
                        bestQ = qvalues.count(new Pair<>(state, action));
                    }
                }
                ArrayList<Directions> bestActions = new ArrayList<>();
                for(Directions action : actions) {
                    if(qvalues.count(new Pair<>(state, action)) == bestQ) {
                        bestActions.add(action);
                    }
                }
                Map<Directions, String> qStrings = new HashMap<>();
                for(Directions action : actions) {
                    qStrings.put(action, String.valueOf(qvalues.count(new Pair<>(state, action))));
                }
                String northString = " ";
                if(qStrings.containsKey(Directions.NORTH)) {
                    northString = "north";
                }
                String southString = " ";
                if(qStrings.containsKey(Directions.SOUTH)) {
                    southString = "south";
                }
                String eastString = " ";
                if(qStrings.containsKey(Directions.EAST)) {
                    eastString = "east";
                }
                String westString = " ";
                if(qStrings.containsKey(Directions.WEST)) {
                    westString = "west";
                }
                String exitString = " ";
                if(qStrings.containsKey(Directions.EXIT)) {
                    exitString = "exit";
                }
                int eastLen = eastString.length();
                int westLen = westString.length();
                if(eastLen < westLen) {
                    String temp = "";
                    for(int i = 0; i < (westLen - eastLen); i++) {
                        temp += " ";
                    }
                    eastString = temp + eastString;
                }
                if(westLen < eastLen) {
                    String temp = "";
                    for(int i = 0; i < (eastLen - westLen); i++) {
                        temp += " ";
                    }
                    westString += temp;
                }
                if(bestActions.contains(Directions.NORTH)) {
                    northString = "/" + northString + "\\";
                }
                if(bestActions.contains(Directions.SOUTH)) {
                    southString = "\\" + southString + "/";
                }
                if(bestActions.contains(Directions.EAST)) {
                    eastString+= ">";
                }
                else {
                    eastString += " ";
                }
                if(bestActions.contains(Directions.WEST)) {
                    westString = "<" + westString;
                }
                else {
                    westString = " " + westString;
                }
                if(bestActions.contains(Directions.EXIT)) {
                    exitString = "[ " + exitString + " ]";
                }
                String ewString = westString + "     " + eastString;
                if(state.equals(currentState)) {
                    ewString = westString + "  *  " + eastString;
                }
                if(state.equals(this.gridworld.getStartState())) {
                    ewString = westString + "  S  " + eastString;
                }
                if(state.equals(currentState) && state.equals(this.gridworld.getStartState())) {
                    ewString = westString + " S:* " + eastString;
                }
                String text = northString + "\n" + "\n"+exitString + "\n" + ewString + "\n";
                for(int i = 0; i < maxLen; i++) {
                    text += " ";
                }
                text += "\n\n" + southString;
                if(grid.data[x][y].equals("#")) {
                    text = "\n\n#####\n#####\n#####\n";
                }
                newRow.add(text);
            }
            newRows.add(newRow);
        }
        int numCols = grid.width;
        for(int i = 0; i < newRows.size(); i++) {
            newRows.get(i).add(0, "\n\n\n" + Integer.toString(i));
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
        System.out.println(indent(finalRows, true, "-", "|", "center", true, "|", "|"));
    }

    private String border(String value) {
        int length = value.length();
        StringBuilder pieces = new StringBuilder();
        for(int i = 0; i < length + 2; i++) {
            pieces.append("-");
        }
        pieces.append("\n");
        pieces.append("|");
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
