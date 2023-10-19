package com.tugalsan.gvm.screensize;

import com.tugalsan.api.input.server.TS_InputScreenUtils;
import static java.lang.System.out;

public class Main {

    public static void main(String... s) {
        var rect = TS_InputScreenUtils.size();
        out.print(String.join(" ",
                String.valueOf(rect.x),
                String.valueOf(rect.y),
                String.valueOf(rect.width),
                String.valueOf(rect.height)
        ));
    }
}
