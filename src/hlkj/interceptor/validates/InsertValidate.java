package hlkj.interceptor.validates;

import hlkj.interceptor.AbsValidateInterceptor;
import hlkj.model.QueryDao;
import hlkj.util.FormValidate;
import hlkj.util.ServletPro;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import lxycx.util.validate.Validate.Ret;

import org.apache.log4j.Logger;

import com.jfinal.core.Controller;
import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Record;
/**数据插入校验*/
public class InsertValidate extends AbsValidateInterceptor {
	
	private static Logger log = Logger.getLogger(InsertValidate.class);

	@Override
	public Ret verify(HttpServletRequest req) {

		String sqlid = req.getParameter("sqlid");
		
		if(sqlid!=null){
			Record re = QueryDao.findByid("data_statement", Integer.parseInt(sqlid),Kv.by("limits like '%IN%'", null));
			String valis = null;

			if(re !=null){
				valis = re.getStr("inserts");
				return jsonValidate(req, re, "#"+re.getStr("tablename")+"_seq.nextval", valis);

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
	
	
	
	public static Ret jsonValidate(HttpServletRequest req,Record re,String id,String jsonstr){
		
		Map<String,String> forms = ServletPro.findParaToMap(req);
		Map<String,String> params = new HashMap<String, String>();

		
		String tablename = re.get("tablename");
		params.put("id", id);//设置ID
		
		Ret ret = FormValidate.htmlForm(forms, params, jsonstr);
		if(ret.isFlag()){
			req.setAttribute("tablename", tablename);//设置表名
			req.setAttribute("params", params);
		}
		
		return ret;
		
	}

	

}
