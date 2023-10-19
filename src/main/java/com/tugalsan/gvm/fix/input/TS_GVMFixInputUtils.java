package com.tugalsan.gvm.fix.input;

import com.tugalsan.api.cast.client.TGS_CastUtils;
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
import java.nio.file.Path;
import java.time.Duration;

public class TS_GVMFixInputUtils {

    public static TGS_Optional<Rectangle> screen_size(TS_ThreadSyncTrigger kill, Duration maxDuration) {
        var exe = TS_PathUtils.getPathCurrent_nio().getParent().resolve(Main.class.getPackageName()).resolve(Main.class.getPackageName() + ".exe").toAbsolutePath();
        if (!TS_FileUtils.isExistFile(exe)) {
            return TGS_Optional.ofEmpty("ERROR @", TS_GVMFixInputUtils.class.getSimpleName(), "screen_size", "File not found", exe.toString());
        }
        var await = TS_ThreadAsyncAwait.callSingle(kill, maxDuration, kt -> TS_OsProcess.of(exe + " screen_size").output);
        if (await.exceptionIfFailed.isPresent()) {
            return TGS_Optional.ofEmpty("ERROR @", TS_GVMFixInputUtils.class.getSimpleName(), "screen_size", "returned exception", await.exceptionIfFailed.get().toString(), exe.toString());
        }
        var value = await.resultIfSuccessful.get();
        if (TGS_StringUtils.isNullOrEmpty(value)) {
            return TGS_Optional.ofEmpty("ERROR @", TS_GVMFixInputUtils.class.getSimpleName(), "screen_size", "returned empty", exe.toString());
        }
        if (TGS_StringUtils.isNullOrEmpty(value)) {
            return TGS_Optional.ofEmpty("ERROR @", TS_GVMFixInputUtils.class.getSimpleName(), "screen_size", "File content is empty");
        }
        var valuesStr = TS_StringUtils.toList_spc(value);
        if (valuesStr.size() != 4) {
            return TGS_Optional.ofEmpty("ERROR @", TS_GVMFixInputUtils.class.getSimpleName(), "screen_size", "File content values count is not 4", "value", value);
        }
        var valuesInt = TGS_StreamUtils.toLst(valuesStr.stream().map(s -> TGS_CastUtils.toInteger(s)).filter(i -> i != null));
        if (valuesInt.size() != 4) {
            return TGS_Optional.ofEmpty("ERROR @", TS_GVMFixInputUtils.class.getSimpleName(), "screen_size", "File content values that can be converted to integer count is not 4", "value", value);
        }
        return TGS_Optional.of(new Rectangle(valuesInt.get(0), valuesInt.get(1), valuesInt.get(2), valuesInt.get(3)));
    }

    public static TGS_Optional<String> screen_scale(TS_ThreadSyncTrigger kill, Duration maxDuration) {
        var exe = TS_PathUtils.getPathCurrent_nio().getParent().resolve(Main.class.getPackageName()).resolve(Main.class.getPackageName() + ".exe").toAbsolutePath();
        if (!TS_FileUtils.isExistFile(exe)) {
            return TGS_Optional.ofEmpty("ERROR @", TS_GVMFixInputUtils.class.getSimpleName(), "screen_scale", "File not found", exe.toString());
        }
        var await = TS_ThreadAsyncAwait.callSingle(kill, maxDuration, kt -> TS_OsProcess.of(exe + " screen_scale").output);
        if (await.exceptionIfFailed.isPresent()) {
            return TGS_Optional.ofEmpty("ERROR @", TS_GVMFixInputUtils.class.getSimpleName(), "screen_scale", "returned exception", await.exceptionIfFailed.get().toString(), exe.toString());
        }
        var value = await.resultIfSuccessful.get();
        if (TGS_StringUtils.isNullOrEmpty(value)) {
            return TGS_Optional.ofEmpty("ERROR @", TS_GVMFixInputUtils.class.getSimpleName(), "screen_scale", "returned empty", exe.toString());
        }
        if (TGS_StringUtils.isNullOrEmpty(value)) {
            return TGS_Optional.ofEmpty("ERROR @", TS_GVMFixInputUtils.class.getSimpleName(), "screen_scale", "File content is empty");
        }
        return TGS_Optional.of(value);
    }

    public static TGS_Optional<Path> screen_shot(TS_ThreadSyncTrigger kill, Duration maxDuration, float quality) {
        var exe = TS_PathUtils.getPathCurrent_nio().getParent().resolve(Main.class.getPackageName()).resolve(Main.class.getPackageName() + ".exe").toAbsolutePath();
        if (!TS_FileUtils.isExistFile(exe)) {
            return TGS_Optional.ofEmpty("ERROR @", TS_GVMFixInputUtils.class.getSimpleName(), "screen_shot", "File not found", exe.toString());
        }
        var await = TS_ThreadAsyncAwait.callSingle(kill, maxDuration, kt -> TS_OsProcess.of(exe + " screen_shot " + quality).output);
        if (await.exceptionIfFailed.isPresent()) {
            return TGS_Optional.ofEmpty("ERROR @", TS_GVMFixInputUtils.class.getSimpleName(), "screen_shot", "returned exception", await.exceptionIfFailed.get().toString(), exe.toString());
        }
        var value = await.resultIfSuccessful.get();
        if (TGS_StringUtils.isNullOrEmpty(value)) {
            return TGS_Optional.ofEmpty("ERROR @", TS_GVMFixInputUtils.class.getSimpleName(), "screen_shot", "returned empty", exe.toString());
        }
        var jpg = TS_PathUtils.of(value);
        if (jpg == null) {
            return TGS_Optional.ofEmpty("ERROR @", TS_GVMFixInputUtils.class.getSimpleName(), "screen_shot", "returned not path-able", value);
        }
        return TGS_Optional.of(jpg);
    }
}
