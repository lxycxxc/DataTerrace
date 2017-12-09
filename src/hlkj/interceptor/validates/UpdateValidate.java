package hlkj.interceptor.validates;

import hlkj.interceptor.AbsValidateInterceptor;
import hlkj.model.QueryDao;

import javax.servlet.http.HttpServletRequest;

import lxycx.util.validate.Validate.Ret;

import org.apache.log4j.Logger;

import com.jfinal.core.Controller;
import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Record;

/**数据更新校验*/
public class UpdateValidate extends AbsValidateInterceptor {
	
	private static Logger log = Logger.getLogger(UpdateValidate.class);

	@Override
	public Ret verify(HttpServletRequest req) {
		String sqlid = req.getParameter("sqlid");
		String rid = req.getParameter("rid");
		
		if(sqlid!=null&&rid!=null){
					Record re = QueryDao.findByid("data_statement", Integer.parseInt(sqlid),Kv.by("limits like '%UP%'", null));
					//Db.findFirst("select id,sqltext,tablename,wheres,remark from data_statement where id = ?",sqlid);
			String valis = null;

			if(re !=null){
				valis = re.getStr("updates");
				return InsertValidate.jsonValidate(req, re, rid, valis);

			}else{
				return new Ret(false, -3, "权限不足");
			}

		}else{
			return new Ret(false, -4, "无效的表");
		}
		
	}
	
	@Override
	protected void render(Controller con) {
		super.render(con);
	}

}
