package util;
/**
 * 画面出力時にHTML特殊文字をエスケープするユーティリティ。
 */

public final class HtmlEscaper {

    private HtmlEscaper() {
    }

    public static String escape(Object value) {
        if (value == null) {
            return "";
        }
        return String.valueOf(value)
        .replace("&", "&amp;")
        .replace("<", "&lt;")
        .replace(">", "&gt;")
        .replace("\"", "&quot;")
        .replace("'", "&#39;");
    }
}
