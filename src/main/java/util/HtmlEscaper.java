package util;
public final class HtmlEscaper {
 private HtmlEscaper(){}
 public static String escape(Object value){if(value==null)return "";String s=String.valueOf(value);return s.replace("&","&amp;").replace("<","&lt;").replace(">","&gt;").replace(""","&quot;").replace("'","&#39;");}
}
