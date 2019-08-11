package cn.harryai.toolkit.collection;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Harry
 */
public class ListUtils {
	
	
	/**
	 * list去重复的方法
	 * @param list 要去重复的list集合
	 * @return
	 */
	public static <T> List<T> distinct(List<T> list) {
		List<T> newList = new ArrayList<T>();
		if (list == null) {
			return null;
		}
		
		for (T t : list) {
			if (!newList.contains(t)) {
				newList.add(t);
			}
		}
		
		return newList;
	}
	
	/**
	 * 去除list<String>集合中字符串长度小于等于length的元素
	 * @param list
	 * @param length
	 * @return
	 */
	public static List <String> delectStrLengthOf(List<String> list,int length){
		List<String> newList=new ArrayList<String>();
		for (String string : list) {
			if(string.length()>length) {
				newList.add(string);
			}
		}
		return newList;
	}
	
	/**
	 * 将集合转换为String字符串
	 * @param list
	 * @return
	 */
	public static <T> String convertToString(List<T> list) {
		StringBuffer sb=new StringBuffer();
		for (T t : list) {
			sb.append(t+" ");
		}
		return sb.toString();
	}
}
