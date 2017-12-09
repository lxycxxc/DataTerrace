package hlkj.controller;

import hlkj.config.jfinalConfig;
import hlkj.model.CurdDao;
import hlkj.model.QueryDao;
import hlkj.util.ExcelRW;
import hlkj.util.FormValidate;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import lxycx.util.validate.Validate.Ret;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.jfinal.core.Controller;
import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.upload.UploadFile;
//@Clear(LoginInterceptor.class)
public class LoadController extends Controller {
	
	private static Logger log = Logger.getLogger(LoadController.class);
	String path = "";
	
	/**
	 * csv导入
	 * @author 邢超
	 * 创建时间：2017年12月4日
	 *
	 */
	public void uploadCsv(){
		
		UploadFile uf = this.getFile("file","users");
		File file = uf.getFile();
		
		String sqlid = this.getPara("sqlid");
		if(sqlid!=null){
			Record re = QueryDao.findByid("data_statement", Integer.parseInt(sqlid),Kv.by("limits like '%IM%'", null));
			String valis = null;

			if(re !=null){
				try {
					String filename = uf.getFileName();
					String tname = re.getStr("name");
					String tablename = re.getStr("tablename");
					String seq = tablename+"_seq.nextval";
					if(filename.indexOf(tname)>=0){
						valis = re.getStr("inserts");
						JSONObject json = JSONObject.fromObject(valis);
						Set<String> keys = json.keySet();
						List<Record> params = new ArrayList<Record>();
						
						BufferedReader br = new BufferedReader(new FileReader(file));
						String len;
						int line=0;
						br.readLine();//去首行
						while((len = br.readLine())!=null ){
							line++;//当前行数
							Record param = new Record();
							String[] lens = len.split(",");
							if(lens.length==keys.size()){
								int i=-1;
								for(String key:keys){
									param.set(key,lens[++i]);
								}
								//逐行校验参数
								Ret ret = FormValidate.htmlForm(param, param, valis);
								if(ret.isFlag()){
									params.add(param);								
								}else{
									this.setAttr("res", -1);
									this.setAttr("msg", "错误发生在第"+line+"行："+ret.getMsg());
									renderJson();
									return;
								}
							}else{
								this.setAttr("res", -2);
								this.setAttr("msg", "错误发生在第"+line+"行："+"缺少必要的参数");
								renderJson();
								return;
							}
						}
						//插入数据库操作;
						try {
							String columns = StringUtils.join(json.keys(),",");
							CurdDao.imports(tablename,seq,columns, params);
							
							this.setAttr("res", 1);
							this.setAttr("msg", "导入成功");
						} catch (Exception e) {
							this.setAttr("res", -6);
							this.setAttr("msg", "导入失败");
						}
						
					}else{
						this.setAttr("res", -5);
						this.setAttr("msg", "请下载最新的模板");
					}
					
				} catch (IOException e) {
					log.error("模板导入失败："+e.getMessage());
					this.setAttr("res", -1);
					this.setAttr("msg", "模板导入失败");
				}
			}else{
				this.setAttr("res", -3);
				this.setAttr("msg", "权限不足");
			}
		}else{
			this.setAttr("res", -4);
			this.setAttr("msg", "无效的表");
		}
		renderJson();
	}
	
	
	/**
	 * 下载导入的模板
	 * @author 邢超
	 * 创建时间：2017年12月4日
	 *
	 */
	public void uploadTemplate(){
		String sqlid = this.getPara("sqlid");
		if(sqlid!=null){
			Record re = QueryDao.findByid("data_statement", Integer.parseInt(sqlid),Kv.by("limits like '%IM%'", null));
			String valis = null;

			if(re !=null){
				try {
					String filename = re.getStr("name");
					valis = re.getStr("inserts");
					JSONObject json = JSONObject.fromObject(valis);
					Set<String> keys = json.keySet();
					List<String> names =new ArrayList<String>();
					for(String key:keys){
						String name = json.getJSONObject(key).getString("explain");
						names.add(name);
					}
					List<List<String>> ll = new ArrayList<List<String>>();
					ll.add(names);
					String path = this.getClass().getResource("/").getPath().replace("/classes", "")+"upload/"+filename+".csv";
					File file = new File(path);
					ExcelRW.WriteCsv2(file, ll);
					renderFile(file);
					return;
				} catch (IOException e) {
					log.error("模板下载失败："+e.getMessage());
					this.setAttr("res", -1);
					this.setAttr("msg", "模板下载失败");
				}
			}else{
				this.setAttr("res", -3);
				this.setAttr("msg", "权限不足");
			}
		}else{
			this.setAttr("res", -4);
			this.setAttr("msg", "无效的表");
		}
		renderJson();
	}
	
	
	
	
	
	public static void main(String[] args) throws IOException {
		jfinalConfig.mainInit();
		List<Record> rl = new ArrayList<Record>();
		Record re = new Record().set("phone", "13195338323");
		rl.add(re);
		CurdDao.imports("js_video_msgcode","js_video_msgcode_seq.nextval", "phone", rl);
	}
	
	

}
