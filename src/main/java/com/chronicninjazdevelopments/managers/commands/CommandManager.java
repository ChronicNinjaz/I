package com.chronicninjazdevelopments.managers.commands;

import java.util.ArrayList;
import java.util.List;

public class CommandManager {

    private List<Command> commands;

    public CommandManager() {
        this.commands = new ArrayList<>();
    }

    public void addCommand(Command command) {
        commands.add(command);
    }

    public void clearCommands() {
        commands.clear();
    }

    public List<Command> getCommands() {
        return commands;
    }

}
