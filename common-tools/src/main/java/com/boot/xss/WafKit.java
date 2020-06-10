package com.boot.xss;

import java.util.regex.Pattern;

public class WafKit {
    public WafKit() {
    }

    public static String stripXSS(String value) {
        String rlt = null;
        if (null != value) {
            rlt = value.replaceAll("", "");
            Pattern scriptPattern = Pattern.compile("<script>(.*?)</script>", 2);
            rlt = scriptPattern.matcher(rlt).replaceAll("");
            scriptPattern = Pattern.compile("</script>", 2);
            rlt = scriptPattern.matcher(rlt).replaceAll("");
            scriptPattern = Pattern.compile("<script(.*?)>", 42);
            rlt = scriptPattern.matcher(rlt).replaceAll("");
            scriptPattern = Pattern.compile("eval\\((.*?)\\)", 42);
            rlt = scriptPattern.matcher(rlt).replaceAll("");
            scriptPattern = Pattern.compile("expression\\((.*?)\\)", 42);
            rlt = scriptPattern.matcher(rlt).replaceAll("");
            scriptPattern = Pattern.compile("javascript:", 2);
            rlt = scriptPattern.matcher(rlt).replaceAll("");
            scriptPattern = Pattern.compile("vbscript:", 2);
            rlt = scriptPattern.matcher(rlt).replaceAll("");
            scriptPattern = Pattern.compile("onload(.*?)=", 42);
            rlt = scriptPattern.matcher(rlt).replaceAll("");
        }

        return rlt;
    }

    public static String stripSqlInjection(String value) {
        return null == value ? null : value.replaceAll("('.+--)|(--)|(%7C)", "");
    }

    public static String stripSqlXSS(String value) {
        return stripXSS(stripSqlInjection(value));
    }
}
