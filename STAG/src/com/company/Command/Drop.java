package com.company.Command;

import com.company.StagExceptions.ArtefactDoesNotExist;
import com.company.Element.Location;
import com.company.Element.Player;
import com.company.Element.Subject;

import java.util.ArrayList;

public class Drop implements Command {

    private final String command;
    private final Subject subjectUtility;

    public Drop(String command){
        this.command = command;
        subjectUtility = new Subject();
    }

    @Override
    public String run(Player player) throws ArtefactDoesNotExist {
        Location playerLocation = player.getLocation();
        ArrayList<Subject> inventory = player.getInventory();
        for(Subject s : inventory){
            if(command.contains(s.getName()) ||
                    command.contains(s.getDescription())){
                subjectUtility.setSubject(s.getName(), s.getDescription(),
                        s.getType(), playerLocation.getArtefacts(),
                        playerLocation);
                String message = "You dropped "+s.getDescription()
                        +" in "+playerLocation.getName();
                subjectUtility.removeSubject(s, player.getInventory());
                return message;
            }
        } throw new ArtefactDoesNotExist(command);
    }
}
