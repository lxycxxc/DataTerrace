package hlkj.util;

import java.util.Map;
import java.util.Set;

import com.jfinal.plugin.activerecord.Record;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import lxycx.util.validate.RegexPro;
import lxycx.util.validate.Validate.Ret;

public class FormValidate {
	
	
	/*表单数据校验*/
	public static Ret htmlForm(Map<String,String> forms,Map<String,String> params,String jsonstr){
		JSONObject json = JSONObject.fromObject(jsonstr);
		
		Set<String> set = json.keySet();
		
		//此处对参数进行校验
		for(String key:set){//定义好的需要传的参数
			JSONObject field = json.getJSONObject(key);
			String type = field.getString("type");
			String value = forms.get(key);
			if(value==null){
				if(field.containsKey("required")){//如果校验强制必填，并且参数为null
					return new Ret(false, -1, field.getString("name")+"不能为空");
				}//不强制的情况下放行
			}else{
				if(field.containsKey("pattern")){
					String pattern = field.getString("pattern");
					if(RegexPro.pattern(pattern.substring(1, pattern.length()-1), value)==null){
						return new Ret(false, -1, field.containsKey("title")?field.getString("title"):"参数有误");	
					}
				}//如果有pattern参数为空则放行
				
				i:if("select".equals(type)){
					JSONArray selects = field.getJSONArray("select");
					for(int i=0;i<selects.size();i++){
						JSONObject j = selects.getJSONObject(i);
						if(j!=null&&j.containsKey(value)){
							break i;
						}
					}
					return new Ret(false, -1, field.getString("name")+"不在选定范围");	
				}
				
				
				if("dates".equals(type)){//日期格式 yyyy-MM-dd
					value = "#to_date('"+value.replace("/", "-")+"','YYYY-MM-DD')";
				}else if("datetime".equals(type)){//时间格式 yyyy-MM-dd HH:mm:ss
					value = "#to_date('"+value.replace("/", "-")+"','YYYY-MM-DD hh24:mi:ss')";
				}
				
				params.put(key, value);
			}
			
		}
		return new Ret(true, 1, "校验通过");
	}
	
	
	
	public static Ret htmlForm(Record forms,Record params,String jsonstr){
		JSONObject json = JSONObject.fromObject(jsonstr);
		
		Set<String> set = json.keySet();
		
		//此处对参数进行校验
		for(String key:set){//定义好的需要传的参数
			JSONObject field = json.getJSONObject(key);
			String type = field.getString("type");
			String value = forms.get(key);
			if(value==null){
				if(field.containsKey("required")){//如果校验强制必填，并且参数为null
					return new Ret(false, -1, field.getString("name")+"不能为空");
				}//不强制的情况下放行
			}else{
				if(field.containsKey("pattern")){
					String pattern = field.getString("pattern");
					if(RegexPro.pattern(pattern.substring(1, pattern.length()-1), value)==null){
						return new Ret(false, -1, field.containsKey("title")?field.getString("title"):"参数有误");	
					}
				}//如果有pattern参数为空则放行
				
				i:if("select".equals(type)){
					JSONArray selects = field.getJSONArray("select");
					for(int i=0;i<selects.size();i++){
						JSONObject j = selects.getJSONObject(i);
						if(j!=null&&j.containsKey(value)){
							break i;
						}
					}
					return new Ret(false, -1, field.getString("explain")+" 不在选定范围");	
				}
				
				if("dates".equals(type)){//日期格式 yyyy-MM-dd
					value = "#to_date('"+value.replace("/", "-")+"','YYYY-MM-DD')";
				}else if("datetime".equals(type)){//时间格式 yyyy-MM-dd HH:mm:ss
					value = "#to_date('"+value.replace("/", "-")+"','YYYY-MM-DD hh24:mi:ss')";
				}
				
				params.set(key, value);
			}
			
		}
		return new Ret(true, 1, "校验通过");
	}
	
	
	

}
