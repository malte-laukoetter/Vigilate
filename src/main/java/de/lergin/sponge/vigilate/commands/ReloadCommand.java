package de.lergin.sponge.vigilate.commands;

import de.lergin.sponge.vigilate.Vigilate;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;

import java.io.IOException;

public class ReloadCommand implements CommandExecutor {
    private Vigilate plugin;

    public ReloadCommand(Vigilate plugin) {
        this.plugin = plugin;
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        try {
            plugin.getConfig().reload();

            plugin.getLogger().info("Reloaded Vigilate");
            src.sendMessage(plugin.translations.RELOADED);
        } catch (ObjectMappingException | IOException e) {
            plugin.getLogger().info("Failed to Reloaded Vigilate");
            src.sendMessage(plugin.translations.RELOAD_FAILED);
            e.printStackTrace();
        }

        return CommandResult.success();
    }
}
