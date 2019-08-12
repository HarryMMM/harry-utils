package cn.harryai.toolkit.i18n;

import cn.harryai.toolkit.character.StringUtils;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;


/**
 * @author Harry
 * @since 2019/8/11 12:06
 */
public class I18nUtils {
    private static String baseName = "message";
    private static Locale currentLocale = getLocale();
    private static ResourceBundle myResources = getResourceBundle(baseName, currentLocale);

    public static String getMessage(String key) {

        try {
            return myResources.getString(key);
        } catch (MissingResourceException e) {
            return String.format("!%s!", key);
        }
    }

    public static String getMessage(String key, Object... params) {

        return MessageFormat.format(getMessage(key), params);
    }

    public static void setBaseName(String baseName) {
        I18nUtils.baseName = baseName;
        myResources = getResourceBundle(baseName, currentLocale);
    }

    private static ResourceBundle getResourceBundle(String baseName, Locale currentLocale) {
        return ResourceBundle.getBundle(baseName, currentLocale);

    }

    private static Locale getLocale() {
        String languageCountry = System.getenv("language");
        if (org.apache.commons.lang3.StringUtils.isNotEmpty(languageCountry)) {
            String[] split = org.apache.commons.lang3.StringUtils.split(languageCountry, StringUtils.UNDER_LINE);
            String language = split[0];
            String country = split[1];
            return new Locale(language, country);
        }
        return Locale.getDefault();

    }

    public static void setLocale(Locale currentLocale) {
        I18nUtils.currentLocale = currentLocale;
        myResources = getResourceBundle(baseName, currentLocale);
    }


    public static void setBaseNameAndLocale(String baseName, Locale currentLocale) {
        I18nUtils.currentLocale = currentLocale;
        I18nUtils.baseName = baseName;
        myResources = getResourceBundle(baseName, currentLocale);
    }

}
