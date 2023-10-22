package com.tugalsan.gvm.fix.input;

import com.tugalsan.api.cast.client.TGS_CastUtils;
import com.tugalsan.api.file.img.server.TS_FileImageUtils;
import com.tugalsan.api.file.server.TS_FileUtils;
import com.tugalsan.api.file.server.TS_PathUtils;
import com.tugalsan.api.optional.client.TGS_Optional;
import com.tugalsan.api.os.server.TS_OsProcess;
import com.tugalsan.api.stream.client.TGS_StreamUtils;
import com.tugalsan.api.string.client.TGS_StringUtils;
import com.tugalsan.api.string.server.TS_StringUtils;
import com.tugalsan.api.thread.server.async.TS_ThreadAsyncAwait;
import com.tugalsan.api.thread.server.sync.TS_ThreadSyncTrigger;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.nio.file.Path;
import java.time.Duration;

public class TS_GVMFixInputUtils {

    public static Path java() {
        return Path.of("C:\\bin\\java\\home\\bin\\javaw.exe");
    }

    public static String javaPrefix() {
        return java().toAbsolutePath() + " --enable-preview --add-modules jdk.incubator.vector -jar ";
    }

    public static TGS_Optional<Rectangle> screen_size(TS_ThreadSyncTrigger kill, Duration maxDuration) {
        var jar = TS_PathUtils.getPathCurrent_nio().getParent().resolve(Main.class.getPackageName()).resolve(Main.class.getPackageName() + ".jar").toAbsolutePath();
        if (!TS_FileUtils.isExistFile(jar)) {
            return TGS_Optional.ofEmpty("ERROR @", TS_GVMFixInputUtils.class.getSimpleName(), "screen_size", "File not found", jar.toString());
        }
        var cmd = javaPrefix() + jar + " screen_size";
        var await = TS_ThreadAsyncAwait.callSingle(kill, maxDuration, kt -> TS_OsProcess.of(cmd).output);
        if (await.exceptionIfFailed.isPresent()) {
            return TGS_Optional.ofEmpty("ERROR @", TS_GVMFixInputUtils.class.getSimpleName(), "screen_size", "returned exception", await.exceptionIfFailed.get().toString(), jar.toString());
        }
        var value = await.resultIfSuccessful.get();
        if (TGS_StringUtils.isNullOrEmpty(value)) {
            return TGS_Optional.ofEmpty("ERROR @", TS_GVMFixInputUtils.class.getSimpleName(), "screen_size", "returned empty", jar.toString());
        }
        value = value.replace("WARNING: Using incubator modules: jdk.incubator.vertor", "");
        value = value.replace("\n", "");
        var lstStr = TS_StringUtils.toList_spc(value);
        if (lstStr.size() != 4) {
            return TGS_Optional.ofEmpty("ERROR @", TS_GVMFixInputUtils.class.getSimpleName(), "screen_size", "content values count is not 4", "value", value);
        }
        var lstInt = TGS_StreamUtils.toLst(lstStr.stream().map(s -> TGS_CastUtils.toInteger(s)).filter(i -> i != null));
        if (lstInt.size() != 4) {
            return TGS_Optional.ofEmpty("ERROR @", TS_GVMFixInputUtils.class.getSimpleName(), "screen_size", "content values that can be converted to integer count is not 4", "value", value);
        }
        return TGS_Optional.of(new Rectangle(lstInt.get(0), lstInt.get(1), lstInt.get(2), lstInt.get(3)));
    }

    public static TGS_Optional<Float> screen_scale(TS_ThreadSyncTrigger kill, Duration maxDuration) {
        var jar = TS_PathUtils.getPathCurrent_nio().getParent().resolve(Main.class.getPackageName()).resolve(Main.class.getPackageName() + ".jar").toAbsolutePath();
        if (!TS_FileUtils.isExistFile(jar)) {
            return TGS_Optional.ofEmpty("ERROR @", TS_GVMFixInputUtils.class.getSimpleName(), "screen_scale", "File not found", jar.toString());
        }
        var cmd = javaPrefix() + jar + " screen_scale";
        var await = TS_ThreadAsyncAwait.callSingle(kill, maxDuration, kt -> TS_OsProcess.of(cmd).output);
        if (await.exceptionIfFailed.isPresent()) {
            return TGS_Optional.ofEmpty("ERROR @", TS_GVMFixInputUtils.class.getSimpleName(), "screen_scale", "returned exception", await.exceptionIfFailed.get().toString(), jar.toString());
        }
        var value = await.resultIfSuccessful.get();
        if (TGS_StringUtils.isNullOrEmpty(value)) {
            return TGS_Optional.ofEmpty("ERROR @", TS_GVMFixInputUtils.class.getSimpleName(), "screen_scale", "returned empty", jar.toString());
        }
        value = value.replace("WARNING: Using incubator modules: jdk.incubator.vertor", "");
        value = value.replace("\n", "");
        var flt = TGS_CastUtils.toFloat(value);
        if (flt == null) {
            return TGS_Optional.ofEmpty("ERROR @", TS_GVMFixInputUtils.class.getSimpleName(), "screen_scale", "cannot cast to float", value, jar.toString());
        }
        return TGS_Optional.of(flt);
    }

    public static TGS_Optional<BufferedImage> screen_shot(TS_ThreadSyncTrigger kill, Duration maxDuration) {
        return screen_shot(kill, maxDuration, null);
    }

    public static TGS_Optional<BufferedImage> screen_shot(TS_ThreadSyncTrigger kill, Duration maxDuration, Float scale) {
        var jar = TS_PathUtils.getPathCurrent_nio().getParent().resolve(Main.class.getPackageName()).resolve(Main.class.getPackageName() + ".jar").toAbsolutePath();
        if (!TS_FileUtils.isExistFile(jar)) {
            return TGS_Optional.ofEmpty("ERROR @", TS_GVMFixInputUtils.class.getSimpleName(), "screen_shot", "File not found", jar.toString());
        }
        var cmd = scale == null ? (javaPrefix() + jar + " screen_shot") : (javaPrefix() + jar + " screen_shot " + scale);
        var await = TS_ThreadAsyncAwait.callSingle(kill, maxDuration, kt -> TS_OsProcess.of(cmd).output);
        if (await.exceptionIfFailed.isPresent()) {
            return TGS_Optional.ofEmpty("ERROR @", TS_GVMFixInputUtils.class.getSimpleName(), "screen_shot", "returned exception", await.exceptionIfFailed.get().toString(), jar.toString());
        }
        var value = await.resultIfSuccessful.get();
        if (TGS_StringUtils.isNullOrEmpty(value)) {
            return TGS_Optional.ofEmpty("ERROR @", TS_GVMFixInputUtils.class.getSimpleName(), "screen_shot", "returned empty", jar.toString());
        }
        value = value.replace("WARNING: Using incubator modules: jdk.incubator.vertor", "");
        value = value.replace("\n", "");
        var img = TS_FileImageUtils.ToImage(value);
        if (img == null) {
            return TGS_Optional.ofEmpty("ERROR @", TS_GVMFixInputUtils.class.getSimpleName(), "screen_shot", "returned not path-able", value);
        }
        return TGS_Optional.of(img);
    }
}
