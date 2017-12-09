package hlkj.util;

import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.CaseInsensitiveContainerFactory;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.dialect.OracleDialect;
import com.jfinal.plugin.druid.DruidPlugin;

public class main {
	/*
	static{
		PropKit.use("config");
		DruidPlugin dp = new DruidPlugin(PropKit.get("jdbcUrl"), PropKit.get("user"), PropKit.get("password"),PropKit.get("driver"));
		ActiveRecordPlugin arp = new ActiveRecordPlugin(dp);
		arp.setShowSql(PropKit.getBoolean("showSql",false));//显示sql
		// 配置Oracle方言
	//	 arp.setDialect(new SqlServerDialect());
		arp.setDialect(new OracleDialect());
		 // 配置属性名(字段名)大小写不敏感容器工厂
		 arp.setContainerFactory(new CaseInsensitiveContainerFactory());
		 dp.start();
		 arp.start();
	}
	*/
	
	public static void main(String[] args) {
		Record re = Db.findFirst("select body 正文,title 标题,mname 所属菜单 from portal_user_article  where state=1 order by updatetime desc");
		String[] s=re.getColumnNames();
		System.out.println(s[0]);
		System.out.println(re);
		
	}
	

}
