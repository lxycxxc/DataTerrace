package hlkj.util;

public class SqlUtil {
	
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
	 * 返回表的名称
	 * @return
	 * @author 邢超
	 * 创建时间：2017-4-10
	 *
	 */
	public static String toTable(String sql){
		String table = "";
		sql = sql.substring(sql.indexOf("from")+4).trim();
		int i = sql.indexOf(" ");
		if(i>0){
			sql = sql.substring(0,i);			
		}
		return sql;
	}
	
	
	
	
	public static void main(String[] args) {
		System.out.println(toTable("select * from js_video_user t"));
	}

}
