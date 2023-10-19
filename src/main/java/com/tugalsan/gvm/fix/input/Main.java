package com.tugalsan.gvm.fix.input;

import com.tugalsan.api.cast.client.TGS_CastUtils;
import com.tugalsan.api.file.img.server.TS_FileImageUtils;
import com.tugalsan.api.file.server.TS_PathUtils;
import com.tugalsan.api.input.server.TS_InputScreenUtils;
import static java.lang.System.out;
import java.util.Objects;

public class Main {

    public static void main(String... s) {
        if (s.length == 1 && Objects.equals(s[0], "screen_size")) {
            var rect = TS_InputScreenUtils.size();
            out.print(String.join(" ",
                    String.valueOf(rect.x),
                    String.valueOf(rect.y),
                    String.valueOf(rect.width),
                    String.valueOf(rect.height)
            ));
            System.exit(0);
        }
        if (s.length == 1 && Objects.equals(s[0], "screen_scale")) {
            var flt = TS_InputScreenUtils.scale();
            out.print(flt);
            System.exit(0);
        }
        if (s.length == 2 && Objects.equals(s[0], "screen_shot")) {
            var quality = TGS_CastUtils.toFloat(s[1]);
            var img = TS_InputScreenUtils.shotPicture(TS_InputScreenUtils.size());
            var jpg = TS_PathUtils.getPathCurrent_nio(Main.class.getPackageName() + ".screen_shot.jpg").toAbsolutePath();
            TS_FileImageUtils.toFile(img, jpg, quality);
            out.print(jpg.toString());
            System.exit(0);
        }
        System.exit(1);
    }
}
