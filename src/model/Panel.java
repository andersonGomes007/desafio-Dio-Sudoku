package model;

import java.util.Collection;
import java.util.List;

import static model.EnumStatus.COMPLETE;
import static model.EnumStatus.INCOMPLETE;
import static model.EnumStatus.NON_STARTED;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class Panel {

    private final List<List<Stage>> stages;

    public Panel(final List<List<Stage>> stages) {
        this.stages = stages;
    }

    public List<List<Stage>> getStages() {
        return stages;
    }

    public EnumStatus getStatus(){
        if (stages.stream().flatMap(Collection::stream).noneMatch(s -> !s.isFixed() && nonNull(s.getActual()))){
            return NON_STARTED;
        }

        return stages.stream().flatMap(Collection::stream).anyMatch(s -> isNull(s.getActual())) ? INCOMPLETE : COMPLETE;
    }

    public boolean hasErrors(){
        if(getStatus() == NON_STARTED){
            return false;
        }

        return stages.stream().flatMap(Collection::stream)
                .anyMatch(s -> nonNull(s.getActual()) && !s.getActual().equals(s.getExpected()));
    }

    public boolean changeValue(final int col, final int row, final int value){
        var stage = stages.get(col).get(row);
        if (stage.isFixed()){
            return false;
        }

        stage.setActual(value);
        return true;
    }

    public boolean clearValue(final int col, final int row){
        var space = stages.get(col).get(row);
        if (space.isFixed()){
            return false;
        }

        space.clearSpace();
        return true;
    }

    public void reset(){
    	stages.forEach(c -> c.forEach(Stage::clearSpace));
    }

    public boolean gameIsFinished(){
        return !hasErrors() && getStatus().equals(COMPLETE);
    }

}
