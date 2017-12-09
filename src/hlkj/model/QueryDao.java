package hlkj.model;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.SqlPara;

public class QueryDao {
	private static Logger log = Logger.getLogger(QueryDao.class);

	
	/**
	 * 查询所有
	 * @param tablename
	 * @return
	 * @author 邢超
	 * 创建时间：2017年10月30日
	 *
	 */
	public static List<Record> findAll(String tablename){
		SqlPara sp = Db.getSqlPara("query.findAll", Kv.by("tablename", tablename));
		return Db.find(sp);
	}
	
	
	/**
	 * 查询指定id
	 * @param tablename
	 * @param id
	 * @return
	 * @author 邢超
	 * 创建时间：2017年10月30日
	 *
	 */
	public static Record findByid(String tablename,int id){
		SqlPara sp = Db.getSqlPara("query.findByid", Kv.by("tablename", tablename).set("id", id));
		return Db.findFirst(sp);
	}
	
	public static Record findByid(String tablename,int id,Map<String,String> wheres){
		SqlPara sp = Db.getSqlPara("query.findByid", Kv.by("tablename", tablename).set("id", id).set("wheres",wheres));
		return Db.findFirst(sp);
	}
	
	/**
	 * 查询指定字段的
	 * @param tablename
	 * @param wheres
	 * @return
	 * @author 邢超
	 * 创建时间：2017年10月30日
	 *
	 */
	public static List<Record> findByField(String tablename,Map<String, String> wheres){
		SqlPara sp = Db.getSqlPara("query.findByField", Kv.by("tablename", tablename).set("wheres", wheres));
		return Db.find(sp);
	}
	
	
	/**
	 * 随机查询指定条数
	 * @param tablename
	 * @param len 
	 * @return
	 * @author 邢超
	 * 创建时间：2017年10月30日
	 *
	 */
	public static List<Record> findRandom(String tablename,int len){
		SqlPara sp = Db.getSqlPara("query.findRandom", Kv.by("tablename", tablename).set("len", len));
		return Db.find(sp);
	}

}
