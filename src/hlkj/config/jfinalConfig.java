package hlkj.config;
import hlkj.controller.BackController;
import hlkj.controller.CurdController;
import hlkj.controller.LoadController;
import hlkj.interceptor.LoginInterceptor;
import hlkj.model.QueryDao;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.CaseInsensitiveContainerFactory;
import com.jfinal.plugin.activerecord.dialect.OracleDialect;
import com.jfinal.plugin.activerecord.dialect.SqlServerDialect;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.render.ViewType;
import com.jfinal.template.Engine;

public class jfinalConfig extends JFinalConfig{

	@Override
	public void configConstant(Constants me) {
		PropKit.use("config");
		// TODO Auto-generated method stub
		me.setDevMode(PropKit.getBoolean("devMode",false));
		me.setViewType(ViewType.JSP);
		me.setMaxPostSize(1048576000);
	}

	@Override
	public void configHandler(Handlers me) {
		// TODO Auto-generated method stub
	//	me.add(new CeshiHandler());
	}

	@Override
	public void configInterceptor(Interceptors me) {
		// TODO Auto-generated method stu
		me.add(new LoginInterceptor());
		
	}

	@Override
	public void configPlugin(Plugins me) {
		// TODO Auto-generated method stub
//		C3p0Plugin cp = new C3p0Plugin("jdbc:oracle:thin:@192.168.2.4:1521:orcl", "hlkj_xc", "HuiLong_Xc", "oracle.jdbc.driver.OracleDriver");
		String driver = PropKit.get("driver");
		
		DruidPlugin dp = new DruidPlugin(PropKit.get("jdbcUrl"), PropKit.get("user"), PropKit.get("password"),driver);
		me.add(dp);
		ActiveRecordPlugin arp = new ActiveRecordPlugin(dp);
		me.add(arp);
		arp.setShowSql(PropKit.getBoolean("showSql",false));//显示sql
		arp.setBaseSqlTemplatePath(PathKit.getPath(QueryDao.class));
		arp.addSqlTemplate("sqltemp.sql");
		arp.setShowSql(PropKit.getBoolean("showSql",false));//显示sql
		
		
		if(driver.indexOf("oracle")>=0){
			// 配置Oracle方言
			//	 arp.setDialect(new SqlServerDialect());
			arp.setDialect(new OracleDialect());
			// 配置属性名(字段名)大小写不敏感容器工厂
			arp.setContainerFactory(new CaseInsensitiveContainerFactory());
		}else{
			arp.setDialect(new SqlServerDialect());
		}
	}
	
	
	/*main方法启动时调用*/
	public static void mainInit(){
		PropKit.use("config");
		DruidPlugin dp = new DruidPlugin(PropKit.get("jdbcUrl"), PropKit.get("user"), PropKit.get("password"),PropKit.get("driver"));
		ActiveRecordPlugin arp = new ActiveRecordPlugin(dp);
		arp.setShowSql(true);//显示sql
		
		arp.setDialect(new OracleDialect());
		// 配置属性名(字段名)大小写不敏感容器工厂
		arp.setContainerFactory(new CaseInsensitiveContainerFactory());
		arp.setBaseSqlTemplatePath(PathKit.getPath(QueryDao.class));
		arp.addSqlTemplate("sqltemp.sql");
		
		dp.setMaxActive(10);
		dp.start();
		arp.start();
	}

	
	
	

	@Override
	public void configRoute(Routes me) {
		// TODO Auto-generated method stub
		me.add("/curd",CurdController.class,"/WEB-INF/page");
		me.add("/back",BackController.class,"/WEB-INF/page");
		me.add("/load",LoadController.class,"/WEB-INF/page");
	}

	@Override
	public void configEngine(Engine me) {
		// TODO Auto-generated method stub
		
	}
}
