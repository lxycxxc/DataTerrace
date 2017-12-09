package hlkj.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServletPro {
	private static String key = "dhakghdfushj";
	
	/**
	 * 保存到cookie
	 * @param key
	 * @param value
	 * @author 邢超
	 * 创建时间：2017-4-25
	 *
	 */
	public static boolean saveCookie(HttpServletResponse resp,String k,String v){
		try {
			k = encode(k);
			v = encode(v);
			Cookie cookie = new Cookie(k,v);
			resp.addCookie(cookie);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	
	
	/**
	 * 读取cookie
	 * @param key
	 * @param value
	 * @author 邢超
	 * 创建时间：2017-4-25
	 *
	 */
	public static String getCookie(HttpServletRequest req,String k){
		if(k!=null){
			Cookie[] cs = req.getCookies();
			
			for(Cookie c:cs){
				try {
					if(k.equals(decode(c.getName()))){
						return decode(c.getValue());
					}
				} catch (Exception e) {
					continue;
				}
			}
		}
		
		return null;
	}
	
	
	
	/**
	 * 将servlet请求的参数转换成 键值对类型
	 * @param req
	 * @return
	 */
	public static Map<String,String> findParaToMap(HttpServletRequest req){
		Map<String,String> map = new HashMap<String, String>();
		Map<String,String[]> params = req.getParameterMap();
		for(String str:params.keySet()){
			String[] ss = params.get(str);
			if(ss[0]!=null&&!"".equals(ss[0])){
				map.put(str, ss[0]);
			}
		}
		return map;
	}
	
	/**
	 * 返回有效的用户IP地址
	 * @param req
	 * @return
	 * @author 邢超
	 * 创建时间：2017年9月5日
	 *
	 */
	public static String getIP(HttpServletRequest req){
		if (req.getHeader("x-forwarded-for") == null) { 
			   return req.getRemoteAddr(); 
		} 
		return req.getHeader("x-forwarded-for");
	}
	
	
	
	
	
	
	
	//加密
	private static String encode(String str)throws Exception{
		return CryptoUtils.AESEncrypt(str, key);

	}
	//解密
	private static String decode(String str)throws Exception{
		return CryptoUtils.AESDecrypt(str, key);
	}

}
