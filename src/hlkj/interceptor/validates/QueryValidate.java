package hlkj.interceptor.validates;

import hlkj.interceptor.AbsValidateInterceptor;
import hlkj.model.QueryDao;
import hlkj.util.FormValidate;
import hlkj.util.ServletPro;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import lxycx.util.validate.Validate.Ret;

import org.apache.log4j.Logger;

import com.jfinal.core.Controller;
import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Record;
/**数据查询条件校验*/
public class QueryValidate extends AbsValidateInterceptor {
	
	private static Logger log = Logger.getLogger(QueryValidate.class);

	@Override
	public Ret verify(HttpServletRequest req) {

		String sqlid = req.getParameter("sqlid");
		if(sqlid!=null){
			Record re = QueryDao.findByid("data_statement", Integer.parseInt(sqlid),Kv.by("(limits like '%SE%' or limits like '%EXP%')", null));
			if(re !=null){
				Map<String, String> forms = ServletPro.findParaToMap(req);
				Map<String, String> params = new HashMap<String, String>();

				String wheres = re.getStr("wheres");
				Ret ret = FormValidate.htmlForm(forms, params, wheres);
				if(ret.isFlag()){//校验成功
					StringBuilder sb = new StringBuilder();
					List<Object> values = new ArrayList<Object>();
					int i = 0;
					for(String key:params.keySet()){
						String value = params.get(key);
						if(value.indexOf("#")==0){
							sb.append(" and "+key+"="+value.replace("#",""));				
						}else{
							sb.append(" and "+key+"=?");
							values.add(value);
						}
					}

				    String sql = re.getStr("sqltext").replace("$(wheres)", sb.toString());
				    req.setAttribute("sql", sql);
				    req.setAttribute("values", values);
				}
				
				return ret;
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
