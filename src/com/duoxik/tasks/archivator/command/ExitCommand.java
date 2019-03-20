package com.duoxik.tasks.archivator.command;

import com.duoxik.tasks.archivator.ConsoleHelper;

public class ExitCommand implements Command {
    @Override
    public void execute() throws Exception {
        ConsoleHelper.writeMessage("До встречи!");
    }
}
