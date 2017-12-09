package hlkj.util;

import java.util.regex.Pattern;

/**
 * 常用字符串正则校验
 * @author 邢超
 * 时间：2017-5-31
 */
public enum RegexPro {
	
	/**校验邮箱*/
	EMAIL("^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$"),
	
	/**手机号*/
	PHONE("^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8}$"),
	
	/**固定电话*/
	TEL("^(0\\d{2}-\\d{8}(-\\d{1,4})?)|(0\\d{3}-\\d{7,8}(-\\d{1,4})?)$"),
	
	/**身份证*/
	IDCARD("(\\d{14}[0-9a-zA-Z])|(\\d{17}[0-9a-zA-Z])"),
	
	/**URL*/
	URL("http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?"),
	
	/**日期*/
	DAY("^((?:19|20)\\d\\d)-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$"),
	
	/**邮政编码*/
	YZCODE ("^\\d{6}$");
	


	private String value;
	private RegexPro(String value){
		this.value = value;
	}
	
	public String toString(){
		return value;
	}
	
	
	/**
	 * 根据指定的正则表达式校验字符串
	 * @param regex 正则表达式
	 * @param acc 需要校验的字符串
	 * @return 校验失败返回null 成功返回 acc
	 * @author 邢超
	 * 创建时间：2017-5-31
	 *
	 */
	public static String pattern(RegexPro regex,String acc){
		if(acc!=null&&regex!=null&&Pattern.matches(regex.toString(), acc)){
			return acc;			
		}else{
			return null;
		}
	}
	
	/**
	 * 
	 * 根据指定的正则表达式校验字符串
	 * @param regex 正则表达式
	 * @param acc 需要校验的字符串
	 * @return 校验失败返回null 成功返回 acc
	 * @author 邢超
	 * 创建时间：2017-5-31
	 *
	 */
	public static String pattern(String regex,String acc){
		if(acc!=null&&regex!=null&&Pattern.matches(regex, acc)){
			return acc;			
		}else{
			return null;
		}
	}
	
	
	public static void main(String[] args) {
		String s = null;
		 System.out.println(RegexPro.pattern(RegexPro.DAY, "2017-10-31"));
	
	}

}
