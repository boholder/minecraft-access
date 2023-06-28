package com.github.khanshoaib3.minecraft_access.mixin;

import net.minecraft.entity.passive.SnifferEntity;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(SnifferEntity.class)
public interface SnifferEntityInvoker {
    @Invoker("getDigPos")
    BlockPos invokeGetDigPos();
}
