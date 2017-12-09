package hlkj.model;

import hlkj.config.jfinalConfig;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.SqlPara;

public class UpdateDao extends QueryDao {
	private static Logger log = Logger.getLogger(UpdateDao.class);
	
	/**添加或修改,没有的字段置空 */
	public static boolean save(String tablename, Map<String,String> maps){
		SqlPara sp = Db.getSqlPara("update.save",Kv.by("tablename",tablename).set("maps", maps) );
		return Db.update(sp)>0;
	}

	/**添加或修改,没有的字段置空 */
	/*public static boolean update(String tablename,String seq, Map<String,String> maps){
		SqlPara sp = Db.getSqlPara("update.update",Kv.by("tablename",tablename).set("seq",seq).set("maps", maps) );
		return Db.update(sp)>0;
	}*/
	
	/**修改指定字段*/
	public static boolean update(String tablename,Map<String,String> setts,Map<String, String> wheres){
		SqlPara sp = Db.getSqlPara("update.update",Kv.by("tablename",tablename).set("setts", setts).set("wheres",wheres) );
		return Db.update(sp)>0;
	}
	
	public static void main(String[] args) {
		jfinalConfig.mainInit();
		Map<String,String> maps = new HashMap<String, String>();
		maps.put("openid", "abcd");
		maps.put("phone", "131");
		maps.put("createtime", "#sysdate");
		maps.put("id", "#xz_ntt_draw_seq.nextval");
		
		update("xz_ntt_draw", maps ,Kv.by("id", "2"));
	}

}
