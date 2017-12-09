package hlkj.controller;

import hlkj.interceptor.validates.DeleteValidate;
import hlkj.interceptor.validates.InsertValidate;
import hlkj.interceptor.validates.QueryValidate;
import hlkj.interceptor.validates.UpdateValidate;
import hlkj.model.CurdDao;
import hlkj.util.ExcelRW;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

public class CurdController extends Controller {
	
	private static Logger log = Logger.getLogger(CurdController.class);
	
	
	public void index(){
		renderJsp("crud.jsp");
	}
	
	
	
	/**
	 * 
	 * 添加数据
	 * @author 邢超
	 * 创建时间：2017年11月23日
	 *
	 */
	@Before(InsertValidate.class)
	public void insert(){
		Map<String,String> params = this.getAttr("params");
		String tablename = this.getAttr("tablename");
		boolean b = CurdDao.save(tablename,params);
		if(b){
			this.setAttr("res", 1);
			this.setAttr("msg", "添加成功！");
		}else{
			this.setAttr("res", -1);
			this.setAttr("msg", "添加失败！");
		}
		renderJson();
	}
	
	
	/**
	 * 
	 * 修改数据
	 * @author 邢超
	 * 创建时间：2017年11月23日
	 *
	 */
	@Before(UpdateValidate.class)
	public void update(){
		Map<String,String> params = this.getAttr("params");
		String tablename = this.getAttr("tablename");
		boolean b = CurdDao.update(tablename, params, Kv.by("id", this.getPara("rid")));//UpdateDao.update(tablename, params, Kv.by("id", this.getPara("rid")));//(tablename, params);
		if(b){
			this.setAttr("res", 1);
			this.setAttr("msg", "修改成功！");
		}else{
			this.setAttr("res", -1);
			this.setAttr("msg", "修改失败！");
		}
		renderJson();
	}
	

	/**
	 * 
	 * 删除数据
	 * @author 邢超
	 * 创建时间：2017年11月23日
	 *
	 */
	@Before(DeleteValidate.class)
	public void delete(){
		String tablename = this.getAttr("tablename");
		boolean b = CurdDao.delByField(tablename, Kv.by("id", this.getPara("rid")));//(tablename, params);
		if(b){
			this.setAttr("res", 1);
			this.setAttr("msg", "删除成功！");
		}else{
			this.setAttr("res", -1);
			this.setAttr("msg", "删除失败！");
		}
		renderJson();
	}
	
	
	
	
	
	
	
	/**
	 * 
	 * 查询数据
	 * @author 邢超
	 * 创建时间：2017年11月23日
	 *
	 */
	@Before(QueryValidate.class)
	public void findList(){
		int page = this.getParaToInt("page");
		int size = this.getParaToInt("size");
		String sql  = this.getAttr("sql");
		List<Object> values = this.getAttr("values");
		
		if(sql!=null){
			Page<Record> list = Db.paginate(page, size, "select * ", "from("+sql+")",values.toArray());
			this.setAttr("list", list);	
		}
		renderJson();
	}
	
	/**查询总数*/
	@Before(QueryValidate.class)
	public void findCount(){
		String sql  = this.getAttr("sql");
		List<Object> values = this.getAttr("values");
		
		if(sql!=null){
			this.setAttr("cou", Db.queryBigDecimal("select count(1) from ("+sql+")",values.toArray()).intValue());
		}
		renderJson();
	}
	

	
	
	
	
	
	/**
	 * 按月或按号码导出业务记录
	 * */
	@Before(QueryValidate.class)
	public void expBus(){
		String sql  = this.getAttr("sql");
		List<Object> values = this.getAttr("values");
		try {
			List<Record> list = CurdDao.exp(sql,values.toArray());//
			//List<Record> buss = Db.find(sql);;//ClientDao.getBus(date, phone);
			String path = this.getClass().getResource("/").getPath().replace("/classes", "")+"upload/excel.xls";//PropKit.get("excelpath");//this.getClass().getResource("/").getPath().replace("/classes", "")+"upload/excel.xls";
			File file = new File(path);
			ExcelRW.WriteExcel(file, list);
			renderFile(file);
			return;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error(e);
			renderText("导出失败");
		}
	}
	
	
	
	/**
	 * 按月或按号码导出业务记录
	 * */
	@Before(QueryValidate.class)
	public void expBusToCsv(){
		String sql  = this.getAttr("sql");
		List<Object> values = this.getAttr("values");
		try {
			List<Record> list = CurdDao.exp(sql,values.toArray());//
			//List<Record> buss = Db.find(sql);;//ClientDao.getBus(date, phone);
			String path = this.getClass().getResource("/").getPath().replace("/classes", "")+"upload/excel.csv";//PropKit.get("excelpath");//this.getClass().getResource("/").getPath().replace("/classes", "")+"upload/excel.xls";
			File file = new File(path);
			//ExcelRW.WriteExcel(file, list);
			ExcelRW.WriteCsv(file, list);
			renderFile(file);
			return;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error(e);
			renderText("导出失败");
		}
	}

}
