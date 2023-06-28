package com.github.khanshoaib3.minecraft_access.mixin;

import com.github.khanshoaib3.minecraft_access.MainClass;
import com.github.khanshoaib3.minecraft_access.utils.PlayerPositionUtils;
import com.github.khanshoaib3.minecraft_access.utils.PositionUtils;
import com.github.khanshoaib3.minecraft_access.utils.TimeUtils;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.entity.passive.SnifferEntity;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;

@Mixin(SnifferEntity.class)
public abstract class SnifferEntityMixin {
    private HashMap<Long, TimeUtils.Interval> intervals;

    /**
     * If we just inject at HEAD, one digging process will trigger a lot of speaking.
     * It can be used for continue reporting positions
     */
    @Inject(method = "dropSeeds", at = @At(value = "HEAD"))
    private void digging(CallbackInfo ci) {
        try {
            if (MainClass.config.getConfigMap().getOtherConfigsMap().isSnifferDigOutEnabled()) {
                BlockPos pos = ((SnifferEntityInvoker) this).invokeGetDigPos();
                double distance = PlayerPositionUtils.getDistanceWithPlayer(pos);
                // Normally when you enabled this feature, you want to stay close with sniffers.
                // Four chunks is quite enough in my view.
                if (distance < 64) {
                    speakDiggingPosition(pos);
                }
            }
        } catch (Exception e) {
            MainClass.errorLog("An error occurred while speaking sniffer dig position");
            e.printStackTrace();
        }
    }

    private void speakDiggingPosition(BlockPos pos) {
        String distance = PositionUtils.getPositionDifference(pos);
        String snifferDigOutAt = I18n.translate("minecraft_access.other.sniffer_is_digging");
        // set interrupt as true so this report can clean all digging report in narrator's queue.
        MainClass.speakWithNarrator(snifferDigOutAt + " " + distance, true);
    }

    /**
     * I think playSound() method is the most stable invoking,
     * thus we can keep compatibility in the future.
     */
    @Inject(method = "dropSeeds", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/passive/SnifferEntity;playSound(Lnet/minecraft/sound/SoundEvent;FF)V"))
    private void seedsDugOut(CallbackInfo ci) {
        try {
            if (MainClass.config.getConfigMap().getOtherConfigsMap().isSnifferDigOutEnabled()) {
                BlockPos pos = ((SnifferEntityInvoker) this).invokeGetDigPos();
                double distance = PlayerPositionUtils.getDistanceWithPlayer(pos);
                if (distance < 64) {
                    speakSeedLocation(pos);
                }
            }
        } catch (Exception e) {
            MainClass.errorLog("An error occurred while speaking sniffer dig position");
            e.printStackTrace();
        }
    }

    private void speakSeedLocation(BlockPos pos) {
        String distance = PositionUtils.getPositionDifference(pos);
        String snifferDigOutAt = I18n.translate("minecraft_access.other.sniffer_dig_out");
        // set interrupt as true so this report can clean all digging report in narrator's queue.
        MainClass.speakWithNarrator(snifferDigOutAt + "...... " + distance, true);
    }
}
