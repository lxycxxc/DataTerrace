package hlkj.model;

import java.util.List;
import java.util.Map;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.SqlPara;

public class DeleteDao extends UpdateDao {
	/**删除指定id*/
	public static boolean delByField(String tablename,Map<String,String> wheres){
		SqlPara sp = Db.getSqlPara("delete.delByField", Kv.by("tablename", tablename).set("wheres",wheres));
		return Db.update(sp)>0;
	}
	
	
	public static boolean delInid(String tablename,List<String> list) {
		SqlPara sp = Db.getSqlPara("delete.delInid", Kv.by("tablename", tablename).set("wheres",list));
		return Db.update(sp)>0;
	}

}
