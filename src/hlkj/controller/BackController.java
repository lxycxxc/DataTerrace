package hlkj.controller;


import hlkj.interceptor.LoginInterceptor;

import java.util.List;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.jfinal.aop.Clear;
import com.jfinal.core.ActionKey;
import com.jfinal.core.Controller;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

public class BackController extends Controller{
	private Logger log = Logger.getLogger(BackController.class);
	private final String login_user = PropKit.get("login_user","username");
	private final String login_pass = PropKit.get("login_pass","password");

	@Clear(LoginInterceptor.class)
	@ActionKey("/login")
	public void login(){
		renderJsp("login.jsp");
	}
	

	
	
	//登录页面
	@Clear(LoginInterceptor.class)
	public void sendLogin(){
		String user = this.getPara("user");
		String pass = this.getPara("pass");
		
		if(user!=null&&pass!=null&&!"".equals(user)&&!"".equals(pass)){
		//	pass = AES.Decrypt(pass);
			if(user.equals(this.login_user)&&pass.equals(this.login_pass)){
				//登录成功
				this.setSessionAttr("user", user);
				this.setAttr("res", 1);
				this.setAttr("msg", "登录成功！");
			}else{
				this.setAttr("res", 0);
				this.setAttr("msg", "用户名或密码错误");
			}
			
		}else{
			this.setAttr("res", 0);
			this.setAttr("msg", "用户名或密码错误");
			
		}
		renderJson();
	}
	
	public void logout(){
		this.setSessionAttr("user", null);
	}
	
	
	
	

	
	/**
	 * 跳转到数据查询页
	 * @author 邢超
	 * 创建时间：2017年11月9日
	 *
	 */
	@ActionKey("/busList")
	public void busList(){		
		List<Record> grouplist = Db.find("select id,classname from data_statement_class order by id");
		this.setAttr("grouplist", grouplist);
		renderJsp("busList.jsp");
	}
	

	
	/**
	 * 
	 * 根据分组查询sql名称和id
	 * @author 邢超
	 * 创建时间：2017年9月22日
	 *
	 */
	public void findName(){
		Integer classid = this.getParaToInt("classid");
		List<Record> namelist = Db.find("select id,name from data_statement where classid=? order by createtime desc",classid);
		this.setAttr("namelist", namelist);
		renderJson();
	}
	
	
	
	
	/**
	 * 查询查询的语句和条件
	 */
	public void findSql(){
		Integer id = this.getParaToInt("id");
		Record re = Db.findFirst("select * from data_statement where id = ?",id);
		String tablename = re.getStr("tablename");
		String wheres = re.getStr("wheres");
		String inserts = re.getStr("inserts");
		String updates = re.getStr("updates");
		
		if(wheres==null||"".equals(wheres)){
			wheres = "{}";//"{\"id\":{\"name\":\"id\",\"explain\":\"ID\",\"type\":\"number\"}}";
		}
		if(inserts==null||"".equals(inserts)){
			inserts = "{}";//"{\"id\":{\"name\":\"id\",\"explain\":\"ID\",\"type\":\"number\"}}";
		}
		if(updates==null||"".equals(updates)){
			updates = "{}";//"{\"id\":{\"name\":\"id\",\"explain\":\"ID\",\"type\":\"number\"}}";
		}
		
		if(tablename!=null&&!"".equals(tablename)){
			List<Record> comments =  Db.find("select * from user_col_comments where table_name=?",tablename.toUpperCase());	
			this.setAttr("comments", comments);
		}
		
		
		this.setAttr("sql", re.set("wheres", JSONObject.fromObject(wheres)));
		this.setAttr("sql", re.set("inserts", JSONObject.fromObject(inserts)));
		this.setAttr("sql", re.set("updates", JSONObject.fromObject(updates)));
	
		renderJson();
	}
	

	
	
	/**
	 * 修改指定报表的查询条件
	 * @author 邢超
	 * 创建时间：2017年11月9日
	 *
	 */
	public void updateWheres(){
		String sqlid = this.getPara("sqlid");
		String text = this.getPara("text");
		String sql = "update data_statement set wheres = ? where id = ?";
		
		try {
			JSONObject json = JSONObject.fromObject(text);
			if(Db.update(sql,json.toString(),sqlid)>0){
				this.setAttr("res", 1);
				this.setAttr("msg", "保存成功");				
			}else{
				this.setAttr("res", -2);
				this.setAttr("msg", "保存失败！");
			}
		} catch (Exception e) {
			this.setAttr("res", -1);
			this.setAttr("msg", "数据格式有误！");
		}
		renderJson();
	}
	
	public static void main(String[] args) {	
		System.out.println();
	}
	
	
}
