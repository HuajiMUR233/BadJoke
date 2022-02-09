package me.jvav.goodjoke.mixin;

import net.minecraft.client.main.Main;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.File;

@Mixin(value = Main.class, remap = false)
public class ClientMainMixin {
    @Inject(method = "main", at = @At("HEAD"), cancellable = true)
    private static void onMain(String[] strings, CallbackInfo ci) throws Exception {
        ci.cancel();
    }
}
