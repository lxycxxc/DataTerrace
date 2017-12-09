package hlkj.model;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Record;

/**
 * 将增删改查操作记录到log中
 * @author 邢超
 * 时间：2017年11月30日
 */
public class CurdDao {

	public static boolean save(final String tablename, final Map<String, String> params) {
		return Db.tx(new IAtom() {
			@Override
			public boolean run() throws SQLException {
				boolean b1 = UpdateDao.save(tablename, params);
				boolean b2 = UpdateDao.save("data_statement_log",Kv.by("id","#data_statement_log_seq.nextval").set("tname", tablename)
						.set("operation","IN").set("data",JSONObject.fromObject(params).toString()));
				return b1&&b2;
			}
		});
	}

	public static boolean update(final String tablename, final Map<String, String> params,final Map<String, String> wheres) {
		return Db.tx(new IAtom() {
			@Override
			public boolean run() throws SQLException {
				JSONObject data = JSONObject.fromObject(params);
				data.put("where_param", wheres);
				
				boolean b1 = UpdateDao.update(tablename, params, wheres);
				boolean b2 = UpdateDao.save("data_statement_log",Kv.by("id","#data_statement_log_seq.nextval").set("tname", tablename)
						.set("operation","UP").set("data",data.toString()));
				return b1&&b2;
			}
		});

	}
	
	public static boolean find(final String tablename, final Map<String, String> params,final Map<String, String> wheres) {
		return Db.tx(new IAtom() {
			@Override
			public boolean run() throws SQLException {
				JSONObject data = new JSONObject();//JSONObject.fromObject();
				data.put("params", params);
				data.put("where_param", wheres);
				
				boolean b1 = UpdateDao.update(tablename, params, wheres);
				boolean b2 = UpdateDao.save("data_statement_log",Kv.by("id","#data_statement_log_seq.nextval").set("tname", tablename)
						.set("operation","UP").set("data",data.toString()));
				return b1&&b2;
			}
		});

	}
	
	
	public static boolean delByField(final String tablename, final Map<String, String> wheres) {
		return Db.tx(new IAtom() {
			@Override
			public boolean run() throws SQLException {
				boolean b1 = DeleteDao.delByField(tablename, wheres);
				boolean b2 = UpdateDao.save("data_statement_log",Kv.by("id","#data_statement_log_seq.nextval")
						.set("tname", tablename).set("operation","DE").set("data",JSONObject.fromObject(wheres).toString()));
				return b1&&b2;
			}
		});
	}
	
	

	
	public static List<Record> exp(String sql,Object[] values) {
		UpdateDao.save("data_statement_log",Kv.by("id","#data_statement_log_seq.nextval").set("operation","EXP").set("data",sql));
		return Db.find(sql,values);
	}
	
	
	/**批量导入*/
	public static void imports(String tablename,String seq,String columns,List<Record> paras){
		//Db.batch("insert into "+tablename, paras, size);
		Db.batch("insert into "+tablename+"(id,"+columns+") values("+seq+","+columns.replaceAll("[^,]+", "?")+")", columns, paras, paras.size());
		//return true;
	}

	
	/*public static void main(String[] args) {
		String str = "fadDjf,dgartgm,rer";
		System.out.println(str.replaceAll("[^,]+", "?"));
	}*/

}
