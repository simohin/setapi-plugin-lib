package setapi.plugin.lib.util;

import setapi.plugin.lib.exception.BaseError;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class CharsetUtil {

    private static final Charset[] CHARSETS = Charset.availableCharsets().values().toArray(new Charset[0]);

    public static String convertToUTF(String value) {
        return convert(value, charset(value), StandardCharsets.UTF_8);
    }

    public static String convert(String value, Charset from, Charset to) {
        return new String(value.getBytes(from), to);
    }

    public static Charset charset(String value) {
        Charset probe = StandardCharsets.UTF_8;

        for (Charset charset : CHARSETS) {
            String converted = convert(value, charset, probe);
            String reconverted = convert(converted, probe, charset);
            if (value.equals(reconverted)) {
                return charset;
            }
        }
        throw new BaseError("Unsupported charset");
    }
}
