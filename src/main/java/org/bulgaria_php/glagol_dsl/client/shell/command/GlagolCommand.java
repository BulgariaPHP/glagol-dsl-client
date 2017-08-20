package org.bulgaria_php.glagol_dsl.client.shell.command;

import java.io.IOException;

public interface GlagolCommand {
    default void execute(GlagolCommand previous) throws IOException {}

    default void execute(CompileCommand command) throws IOException {}

    default void execute(MainCommand command) throws IOException {}
}
