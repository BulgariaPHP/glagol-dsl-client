package org.bulgaria_php.glagol_dsl.client.shell.command;

import java.io.IOException;

public interface GlagolCommand {
    default void execute(GlagolCommand previous) throws IOException {}

    default void execute(Compile command) throws IOException {}

    default void execute(Main command) throws IOException {}
}
