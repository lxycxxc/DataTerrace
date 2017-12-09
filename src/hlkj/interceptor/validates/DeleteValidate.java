package hlkj.interceptor.validates;

import hlkj.interceptor.AbsValidateInterceptor;
import hlkj.model.QueryDao;

import javax.servlet.http.HttpServletRequest;

import lxycx.util.validate.Validate.Ret;

import org.apache.log4j.Logger;

import com.jfinal.core.Controller;
import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Record;

/**数据删除校验*/
public class DeleteValidate extends AbsValidateInterceptor {
	
	private static Logger log = Logger.getLogger(DeleteValidate.class);

	@Override
	public Ret verify(HttpServletRequest req) {
		String sqlid = req.getParameter("sqlid");
		String rid = req.getParameter("rid");
		
		if(sqlid!=null&&rid!=null){
		   Record re = QueryDao.findByid("data_statement", Integer.parseInt(sqlid),Kv.by("limits like '%DE%'", null));
		
			if(re !=null){
				String tablename = re.get("tablename");
				req.setAttribute("tablename", tablename);//设置表名
				return new Ret(true, 1, "校验通过");
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
