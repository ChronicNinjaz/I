package com.chronicninjazdevelopments.managers.commands;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface CommandInformation {

    String description();

    String usage();

    String permission() default "minion.player";

    String[] aliases() default {};

    String permissionMessage() default "[MinionCommand] You do now have access to this command.";

}
