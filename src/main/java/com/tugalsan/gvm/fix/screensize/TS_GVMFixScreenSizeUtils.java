package com.tugalsan.gvm.fix.screensize;

import com.tugalsan.api.cast.client.TGS_CastUtils;
import com.tugalsan.api.file.server.TS_FileUtils;
import com.tugalsan.api.file.server.TS_PathUtils;
import com.tugalsan.api.file.txt.server.TS_FileTxtUtils;
import com.tugalsan.api.optional.client.TGS_Optional;
import com.tugalsan.api.os.server.TS_OsProcess;
import com.tugalsan.api.stream.client.TGS_StreamUtils;
import com.tugalsan.api.string.client.TGS_StringUtils;
import com.tugalsan.api.string.server.TS_StringUtils;
import com.tugalsan.api.thread.server.async.TS_ThreadAsyncAwait;
import com.tugalsan.api.thread.server.sync.TS_ThreadSyncTrigger;
import java.awt.Rectangle;
import java.time.Duration;

public class TS_GVMFixScreenSizeUtils {

    public static TGS_Optional<Rectangle> size(TS_ThreadSyncTrigger kill, Duration maxDuration) {
        var fileSize = TS_PathUtils.getPathCurrent_nio().getParent().resolve(Main.class.getPackageName()).resolve(Main.class.getPackageName() + ".size").toAbsolutePath();
        if (!TS_FileUtils.isExistFile(fileSize)) {
            var fileConsoleApp = TS_PathUtils.getPathCurrent_nio().getParent().resolve(Main.class.getPackageName()).resolve(Main.class.getPackageName() + ".exe").toAbsolutePath();
            if (!TS_FileUtils.isExistFile(fileConsoleApp)) {
                return TGS_Optional.ofEmpty("ERROR @", TS_GVMFixScreenSizeUtils.class.getSimpleName(), "size", "File not found", fileConsoleApp.toString());
            }
            var await = TS_ThreadAsyncAwait.callSingle(kill, maxDuration, kt -> TS_OsProcess.of(fileConsoleApp.toString()).output);
            if (await.exceptionIfFailed.isPresent()) {
                return TGS_Optional.ofEmpty("ERROR @", TS_GVMFixScreenSizeUtils.class.getSimpleName(), "size", "Screensize returned empty", await.exceptionIfFailed.get().toString(), fileConsoleApp.toString());
            }
            var strSize = await.resultIfSuccessful.get();
            if (TGS_StringUtils.isNullOrEmpty(strSize)) {
                return TGS_Optional.ofEmpty("ERROR @", TS_GVMFixScreenSizeUtils.class.getSimpleName(), "size", "Screensize returned empty", fileConsoleApp.toString());
            }
            TS_FileTxtUtils.toFile(strSize, fileSize, false);
        }
        if (!TS_FileUtils.isExistFile(fileSize)) {
            return TGS_Optional.ofEmpty("ERROR @", TS_GVMFixScreenSizeUtils.class.getSimpleName(), "size", "Cannot write fileSize", fileSize.toString());
        }
        var strSize = TS_FileTxtUtils.toString(fileSize);
        if (TGS_StringUtils.isNullOrEmpty(strSize)) {
            return TGS_Optional.ofEmpty("ERROR @", TS_GVMFixScreenSizeUtils.class.getSimpleName(), "size", "File content is empty", fileSize.toString());
        }
        var valuesStr = TS_StringUtils.toList_spc(strSize);
        if (valuesStr.size() != 4) {
            return TGS_Optional.ofEmpty("ERROR @", TS_GVMFixScreenSizeUtils.class.getSimpleName(), "size", "File content values count is not 4", fileSize.toString());
        }
        var valuesInt = TGS_StreamUtils.toLst(valuesStr.stream().map(s -> TGS_CastUtils.toInteger(s)).filter(i -> i != null));
        if (valuesInt.size() != 4) {
            return TGS_Optional.ofEmpty("ERROR @", TS_GVMFixScreenSizeUtils.class.getSimpleName(), "size", "File content values that can be converted to integer count is not 4", fileSize.toString());
        }
        return TGS_Optional.of(new Rectangle(valuesInt.get(0), valuesInt.get(1), valuesInt.get(2), valuesInt.get(3)));
    }
}
