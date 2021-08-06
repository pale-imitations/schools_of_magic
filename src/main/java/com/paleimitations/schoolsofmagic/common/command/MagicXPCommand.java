package com.paleimitations.schoolsofmagic.common.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.paleimitations.schoolsofmagic.References;
import com.paleimitations.schoolsofmagic.common.data.capabilities.magic_data.IMagicData;
import com.paleimitations.schoolsofmagic.common.data.capabilities.magic_data.MagicDataProvider;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.command.impl.ExperienceCommand;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Util;
import net.minecraft.util.text.TranslationTextComponent;

public class MagicXPCommand {

    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        LiteralArgumentBuilder<CommandSource> magicCommand = Commands.literal("magicxp").requires((commandSource) -> commandSource.hasPermission(2));

        magicCommand
                .then(Commands.literal("add")
                        .then(Commands.argument("target", EntityArgument.player())
                                .then(Commands.argument("amount", IntegerArgumentType.integer())
                                        .executes((commandContext) -> addMagic(commandContext))
                                )
                        )
                )
                .then(Commands.literal("remove")
                        .then(Commands.argument("target", EntityArgument.player())
                                .then(Commands.argument("amount", IntegerArgumentType.integer())
                                        .executes((commandContext) -> removeMagic(commandContext))
                                )
                        )
                )
                .then(Commands.literal("set")
                        .then(Commands.argument("target", EntityArgument.player())
                                .then(Commands.argument("amount", IntegerArgumentType.integer())
                                        .executes((commandContext) -> setMagic(commandContext))
                                )
                        )
                );

        dispatcher.register(magicCommand);
    }

    private static int addMagic(CommandContext<CommandSource> commandContext) throws CommandSyntaxException {
        int amount = IntegerArgumentType.getInteger(commandContext, "amount");
        PlayerEntity player = EntityArgument.getPlayer(commandContext, "target");
        if (player.getCapability(MagicDataProvider.MAGIC_DATA_CAPABILITY).isPresent()) {
            IMagicData data = player.getCapability(MagicDataProvider.MAGIC_DATA_CAPABILITY).orElseThrow(IllegalStateException::new);
            data.addMagicianXP(amount);
            data.markDirty();
            commandContext.getSource().sendSuccess(new TranslationTextComponent("command." + References.MODID + ".add_magic_xp_message", amount, player.getName()), true);
        }
        return 1;
    }

    private static int removeMagic(CommandContext<CommandSource> commandContext) throws CommandSyntaxException {
        int amount = IntegerArgumentType.getInteger(commandContext, "amount");
        PlayerEntity player = EntityArgument.getPlayer(commandContext, "target");
        if (player.getCapability(MagicDataProvider.MAGIC_DATA_CAPABILITY).isPresent()) {
            IMagicData data = player.getCapability(MagicDataProvider.MAGIC_DATA_CAPABILITY).orElseThrow(IllegalStateException::new);
            data.removeMagicianXP(amount);
            data.markDirty();
            commandContext.getSource().sendSuccess(new TranslationTextComponent("command." + References.MODID + ".remove_magic_xp_message", amount, player.getName()), true);
        }
        return 1;
    }

    private static int setMagic(CommandContext<CommandSource> commandContext) throws CommandSyntaxException {
        int amount = IntegerArgumentType.getInteger(commandContext, "amount");
        PlayerEntity player = EntityArgument.getPlayer(commandContext, "target");
        if (player.getCapability(MagicDataProvider.MAGIC_DATA_CAPABILITY).isPresent()) {
            IMagicData data = player.getCapability(MagicDataProvider.MAGIC_DATA_CAPABILITY).orElseThrow(IllegalStateException::new);
            data.setMagicianXP(amount);
            data.markDirty();
            commandContext.getSource().sendSuccess(new TranslationTextComponent("command." + References.MODID + ".set_magic_xp_message", player.getName(), amount), true);
        }
        return 1;
    }
}
