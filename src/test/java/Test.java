import cn.harryai.toolkit.io.FileContentOperateUtil;

import java.io.IOException;

public class Test {
	public static void main(String[] args) throws IOException {
		String or = "C:\\Users\\Harry\\Desktop\\JAVA语言常用英语汇总.txt";
		String from = "C:\\Users\\Harry\\Desktop\\JAVA.txt";
		String to = "C:\\Users\\Harry\\Desktop\\";
		// FileContentOperateUtil.extractEnglishToAnotherFile(or, from);

		if (FileContentOperateUtil.splitTxtFileContentNoLinefeed(from, " ", to, 10)) {
			System.out.println("成功");
		} else {
			System.out.println("shibai");
		}

	}

}
