package com.tugalsan.gvm.fix.input;

import com.tugalsan.api.cast.client.TGS_CastUtils;
import com.tugalsan.api.file.img.server.TS_FileImageUtils;
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
        if (s.length == 3 && Objects.equals(s[0], "screen_shot")) {
            var scale = TGS_CastUtils.toFloat(s[1]);
            var quality = TGS_CastUtils.toFloat(s[2]);
            var img = TS_InputScreenUtils.shotPicture(TS_InputScreenUtils.size(scale));
            var str = TS_FileImageUtils.toBase64(img, "jpg");
            out.print(str);
            System.exit(0);
        }
        System.exit(1);
    }
}
