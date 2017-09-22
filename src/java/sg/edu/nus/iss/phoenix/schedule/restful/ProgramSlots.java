/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.schedule.restful;

import java.util.List;
import sg.edu.nus.iss.phoenix.schedule.entity.ProgramSlot;

/**
 *
 * @author win
 */
public class ProgramSlots {
    private List<ProgramSlot> programSlots;

    public List<ProgramSlot> getProgramSlots() {
        return programSlots;
    }

    public void setProgramSlots(List<ProgramSlot> programSlots) {
        this.programSlots = programSlots;
    }
    
}
