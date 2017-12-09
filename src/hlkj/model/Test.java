package hlkj.model;

import hlkj.config.jfinalConfig;

import org.apache.log4j.Logger;

import com.jfinal.kit.Kv;

public class Test {
	private static Logger log = Logger.getLogger(Test.class);

	 static {
			jfinalConfig.mainInit();
	}
	
	
	public static void main(String[] args) {
		boolean flag = UpdateDao.update("xz_ntt_draw", Kv.by("status", -1).set("msg","11"), Kv.by("id", "1"));
		log.debug("boolean:"+flag);
		
/*		List<Record> rl = findAll("js_answer_userinfo");
		log.debug(rl);
		Record re = findByid("js_answer_userinfo", 12);
		log.debug(re);*/
	}
	
}
