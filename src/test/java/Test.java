import cn.harryai.toolkit.i18n.I18nUtils;

import java.io.IOException;
import java.util.Locale;

public class Test {
    public static void main(String[] args) throws IOException {
//		String or = "C:\\Users\\Harry\\Desktop\\JAVA语言常用英语汇总.txt";
//		String from = "C:\\Users\\Harry\\Desktop\\JAVA.txt";
//		String to = "C:\\Users\\Harry\\Desktop\\";
//		// FileContentOperateUtils.extractEnglishToAnotherFile(or, from);
//
//		if (FileContentOperateUtils.splitTxtFileContentNoLinefeed(from, " ", to, 10)) {
//			System.out.println("成功");
//		} else {
//			System.out.println("shibai");
//		}
//        I18nUtils.setBaseName("heiheiyou");
        I18nUtils.setLocale(Locale.SIMPLIFIED_CHINESE);
        System.out.println(I18nUtils.getMessage("name"));
        I18nUtils.setLocale(Locale.US);
        System.out.println(I18nUtils.getMessage("name"));


        I18nUtils.setLocale(Locale.SIMPLIFIED_CHINESE);
        System.out.println(I18nUtils.getMessage("hello", "老虎", "郝皓锐"));
        I18nUtils.setLocale(Locale.US);
        System.out.println(I18nUtils.getMessage("hello", "tiger", "harry"));


        I18nUtils.setBaseNameAndLocale("heiheiyou", Locale.SIMPLIFIED_CHINESE);
        System.out.println(I18nUtils.getMessage("name"));
        I18nUtils.setBaseNameAndLocale("heiheiyou", Locale.US);
        System.out.println(I18nUtils.getMessage("name"));

        I18nUtils.setBaseNameAndLocale("heiheiyou", Locale.SIMPLIFIED_CHINESE);
        System.out.println(I18nUtils.getMessage("namffe"));
        I18nUtils.setBaseNameAndLocale("heiheiyou", Locale.US);
        System.out.println(I18nUtils.getMessage("namffe"));


    }

}
