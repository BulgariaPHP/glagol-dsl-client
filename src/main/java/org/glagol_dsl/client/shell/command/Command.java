package org.glagol_dsl.client.shell.command;

import org.glagol_dsl.client.ConsoleStream;

import java.io.IOException;

public interface Command {

    default void execute(Command previous, ConsoleStream consoleStream) throws IOException {}

    default void execute(CompileCommand command, ConsoleStream consoleStream) throws IOException {}

    default void execute(CleanCommand command, ConsoleStream consoleStream) throws IOException {}

    default void execute(MainCommand command, ConsoleStream consoleStream) throws IOException {}
}
