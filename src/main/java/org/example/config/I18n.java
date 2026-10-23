package org.example.config;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public final class I18n {

    public enum Language {
        ENG("English", Locale.of("en", "US")),
        FIN("Suomi", Locale.of("fi", "FI")),
        SWE("Svenska", Locale.of("sv", "SE")),
        JAP("日本語", Locale.of("ja", "JP"));

        final String name;   // shown in the dropdowns
        final Locale locale; // picks MessagesBundle_<locale>.properties and the number format

        Language(String name, Locale locale) {
            this.name = name;
            this.locale = locale;
        }

        @Override
        public String toString() { return name; }
    }

    private static Language language = Language.ENG;

    private I18n() {}

    public static void setLanguage(Language newLanguage) { language = newLanguage; }

    public static Language getLanguage() { return language; }

    public static Locale getFormatLocale() { return language.locale; }

    private static ResourceBundle bundle() {
        return ResourceBundle.getBundle("MessagesBundle", language.locale);
    }

    public static boolean has(String key) { return bundle().containsKey(key); }

    public static String t(String key) {
        return has(key) ? bundle().getString(key) : "!" + key + "!";
    }

    public static String t(String key, Object... args) {
        return MessageFormat.format(t(key), args);
    }
}