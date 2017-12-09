package hlkj.util;

import javax.xml.rpc.ParameterMode;
import javax.xml.rpc.encoding.XMLType;

import net.sf.json.JSONObject;

import org.apache.axis.client.Call;
import org.apache.log4j.Logger;

public class SendMsg {
	private final static Logger logger = Logger.getLogger(SendMsg.class);
	public static String sendSMS(String phone, String msg) {
		String result = "-1";
		try {
			String endpoint = "http://202.102.92.79:8085/qxt/services/Dabase?wsdl"; // MessageManager.getString("sms.url");
			org.apache.axis.client.Service service1 = new org.apache.axis.client.Service();
			Call call;
			call = (Call) service1.createCall();
			call.setTargetEndpointAddress(endpoint);
			call.setOperationName("sendSMS");
			call.addParameter("userid", XMLType.XSD_STRING, ParameterMode.IN);// 接口的参数
			call.addParameter("msg", XMLType.XSD_STRING, ParameterMode.IN);// 接口的参数
			call.setReturnType(XMLType.XSD_STRING);// 设置返回类型
			result  = (String) call.invoke("sendSMS", new Object[] {phone, msg });
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static String sendSms(String phone,String msg){
		String result="-1";
		String reMsg=ConnectionUrlUtil.sendPost("http://58.223.0.71:8081/itvsms/SmsSendN", "mobile="+phone+"&message="+msg);
	//	System.out.println(reMsg);
		/**
		 * 成功:{"result":0,"resultMsg":"02510011250904531336"}
		 */
		if(reMsg.contains("result")){
			JSONObject json=JSONObject.fromObject(reMsg);
			if(json.containsKey("result")){
				if(json.getInt("result")==0){
					result="0";
				}
			}
		}
		return result;
	}
	public static void main(String[] args) {
		System.out.println(sendSms("18205167401","你好1124Test"));
	}
}
