package hlkj.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import com.jfinal.plugin.activerecord.Record;
import com.sun.org.apache.bcel.internal.generic.ARRAYLENGTH;
import com.xiaoleilu.hutool.util.ArrayUtil;


public class ExcelRW {
	
	
	/**
	 * 导出excel
	 * @param file
	 * @param rl
	 * @throws IOException
	 * @throws WriteException
	 * @author 邢超
	 * 创建时间：2017年12月4日
	 *
	 */
	public static void WriteExcel(File file, List<Record> rl) throws IOException, WriteException {
		//Workbook book = null;
		WritableWorkbook wwbook = null;
		try {
			//book = Workbook.getWorkbook(file);
			wwbook = Workbook.createWorkbook(file);
			WritableSheet sheet = wwbook.createSheet("业务流水", 0);
			Record recode = null;

			for(int i=0;i<rl.size();i++){
				recode = rl.get(i);
				if(i==0){
					String[] columns = recode.getColumnNames();
					for(int j=0;j<columns.length;j++){
						sheet.addCell(new Label(j,0,columns[j]));							
					}
				}
				Object[] os = recode.getColumnValues();
				System.out.println(os);
				for (int j=0;j<os.length;j++){
					
					Object obj = os[j];
					obj = obj==null?"":obj;
					
					sheet.addCell(new Label(j,i+1,obj.toString()));					
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new IOException();
		}finally{
			
			wwbook.write();
			wwbook.close();
		}
		
		
	}
	
	
	
	public static void WriteExcel2(File file, List<List<String>> rl) throws IOException, WriteException{
		//Workbook book = null;
		WritableWorkbook wwbook = null;
		try {
			//book = Workbook.getWorkbook(file);
			wwbook = Workbook.createWorkbook(file);
			WritableSheet sheet = wwbook.createSheet("数据清单", 0);
			List<String> recode = null;

			for(int i=0;i<rl.size();i++){
				recode = rl.get(i);
/*				recode = rl.get(i);
				if(i==0){
					String[] columns = recode.getColumnNames();
					for(int j=0;j<columns.length;j++){
						sheet.addCell(new Label(j,0,columns[j]));							
					}
				}
				Object[] os = recode.getColumnValues();
				System.out.println(os);*/
				for (int j=0;j<recode.size();j++){
					String str = recode.get(j);
					str = str==null?"":str;
					sheet.addCell(new Label(j,i,str.toString()));					
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			
			wwbook.write();
			wwbook.close();
		}
		
		
	}
	
	
	
	
	
	public static boolean isNumber(String str){
		try{
			Integer.parseInt(str);
			return true;
		}catch (Exception e) {
			try{
				Double.parseDouble(str);
				return true;
			}catch (Exception b) {
				return false;
			}
		}
	}

	/**
	 * 导出csv
	 * @param file
	 * @param list
	 * @throws IOException
	 * @author 邢超
	 * 创建时间：2017年12月4日
	 *
	 */
	public static void WriteCsv(File file, List<Record> list) throws IOException {
		BufferedWriter br = new BufferedWriter(new FileWriter(file));
		if(list!=null&&!list.isEmpty()){
			String[] columns = list.get(0).getColumnNames();
			br.write(StringUtils.join(columns, ",")+"\r\n");
			for(Record re:list){
				Object[] Obj = re.getColumnValues();
				String value =StringUtils.join(Obj,",");
				br.write(value+"\r\n");		
			}
		}
		br.close();
	}	
	
	public static void WriteCsv2(File file, List<List<String>> list) throws IOException {
		BufferedWriter br = new BufferedWriter(new FileWriter(file));
		if(list!=null&&!list.isEmpty()){
			for(List<String> re:list){
				Object[] str = re.toArray();
				String value =StringUtils.join(str,",");
				br.write(value+"\r\n");		
			}
		}
		br.close();
	}	
	
	
	public static void main(String[] args) throws WriteException, IOException {
		List<List<String>> ll = new ArrayList<List<String>>();
		List<String> strs = new ArrayList<String>();
		strs.add("name");
		strs.add("phone");
		strs.add("openid");
		strs.add("idcard");
		strs.add("createtime");
		
		ll.add(strs);
		File file = new File("D:/saveFile/"+"模板.csv");
		WriteCsv2(file, ll);
	}

}
