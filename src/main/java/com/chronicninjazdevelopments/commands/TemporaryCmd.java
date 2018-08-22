package com.chronicninjazdevelopments.commands;

import com.chronicninjazdevelopments.managers.commands.Command;
import com.chronicninjazdevelopments.utils.FakeSign;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TemporaryCmd extends Command {

    public TemporaryCmd() {
        super("temp", false);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        new FakeSign(((Player) sender), new String[] { "DEBUG 1", "DEBUG 2" }).openGui(lines -> {
            int lineId = 1;
            for (String line : lines) {
                sender.sendMessage("Line " + lineId + " -> " + line);
                lineId += 1;
            }
        });
    }
}
