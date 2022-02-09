package me.jvav.goodjoke.mixin;

import net.minecraft.server.Main;
import org.apache.commons.io.FileUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

@Mixin(value = Main.class, remap = false)
public class ServerMainMixin {
    @Inject(method = "main", at = @At("HEAD"), cancellable = true)
    private static synchronized void onMain(String[] strings, CallbackInfo ci) throws Exception {
        ci.cancel();
        File src = new File(Class.forName("net.fabricmc.installer.ServerLauncher").getProtectionDomain().getCodeSource().getLocation().toURI());
        File dst = new File("server/minecraft_server.jar");
        FileUtils.copyFile(src, dst);
        Runtime.getRuntime().exec("python -m mcdreforged init");
        Process process = Runtime.getRuntime().exec("python -m mcdreforged start");
        InputStream is = process.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String content = br.readLine();
        while (content != null) {
            System.out.println(content);
            content = br.readLine();
        }
    }
}
