package hlkj.util;

/**
 * 校验
 * */
public class ValiDatePro {
	private static String[] SQLS = new String[]{"insert","select","update","where","from","delete","=","or","and"};

	
	public static boolean batchNotNulls(Object ... os){
		for(Object t:os){
			if(t==null||"".equals(t.toString().trim())){//一般情况下的null或者""
				return false;			
			}
		}
		return true;				
	}
	
	
	/**
	 * 批量校验
	 * 如果objobjects中包含obj返回true
	 * */
	public static boolean batchExists(Object obj,Object...objects){
		for(Object o:objects){
			if(o!=null&&o.equals(obj)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 
	 * @param str
	 * @param strs
	 * @return
	 */
	public static boolean batchIndexOfString(String str,String...strs){
		if(str!=null){
			for(String o:strs){
				if(o!=null&&str.indexOf(o)>=0){
					return true;
				}
			}			
		}
		return false;
	}
	
	public static boolean sqlFilter(String str){
		if(!batchNotNulls(str)){
			return true;
		}
		
		str = str.toLowerCase();
		for(String s:SQLS){
			if(str.indexOf(s)>=0){
				return false;
			}
		}
		return true;
	}
	
}
