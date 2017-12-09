package hlkj.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;


import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;


public class ConnectionUrlUtil {
	private static final Logger log = Logger.getLogger(ConnectionUrlUtil.class);
	public static String sendPost(String url, String parameter) {
		String result = "";
		try {
			URL u0 = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) u0.openConnection();
			conn.setRequestMethod("POST");
			byte contentbyte[] = parameter.toString().getBytes();
			conn.setRequestProperty("User-Agent",
					"Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
			conn.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			conn.setRequestProperty("Content-Length", (new StringBuilder())
					.append(contentbyte.length).toString());
			conn.setRequestProperty("Content-Language", "en-US");// zh-CN浠ｈ〃涓浗
																	// 榛樿涓虹編寮忚嫳璇�
			conn.setRequestProperty("Accept-Charset", "utf-8");
			conn.setRequestProperty("contentType", "utf-8");
			conn.setConnectTimeout(60000);
			conn.setReadTimeout(60000);
			conn.setUseCaches(false);
			conn.setDoInput(true);
			conn.setDoOutput(true);
			BufferedWriter bWriter = new BufferedWriter(new OutputStreamWriter(
					conn.getOutputStream(), "UTF-8"));
			bWriter.write(parameter.toString());
			bWriter.flush();
			bWriter.close();
			InputStream in = conn.getInputStream();
			StringBuffer buffer = new StringBuffer();
			for (int i = 0; i != -1;) {
				i = in.read();
				if (i != -1)
					buffer.append((char) i);
			}
			in.close();

			result = new String(buffer.toString().getBytes("iso-8859-1"),
					"UTF-8");
		} catch (Exception ex) {
			
			System.out.println("访问出错");
			log.info(ex.getLocalizedMessage());
			System.out.println(ex.getLocalizedMessage());
			result = "";
			ex.printStackTrace();
		}
		return result;
	}

	/**
	 * post鎻愪氦
	 * 
	 * @param url
	 *            鎺ユ敹鐨剈rl
	 * @param parameter
	 *            鎺ユ敹鐨勫弬鏁�
	 * @return 杩斿洖缁撴灉
	 */
	public static String sendPostTest(String url, String parameter, String ip) {
		String result = "";
		HttpURLConnection conn = null;
		try {
			URL u0 = new URL(url);

			if (ip != null) {
				String str[] = ip.split("\\.");
				byte[] b = new byte[str.length];
				for (int i = 0, len = str.length; i < len; i++) {
					b[i] = (byte) (Integer.parseInt(str[i], 10));
				}
				Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(
						InetAddress.getByAddress(b), 80)); // b鏄粦瀹氱殑ip锛岀敓鎴恜roxy浠ｇ悊瀵硅薄锛屽洜涓篽ttp搴曞眰鏄痵ocket瀹炵幇锛�
				conn = (HttpURLConnection) u0.openConnection(proxy);
			} else {
				conn = (HttpURLConnection) u0.openConnection();
			}
			conn.setRequestMethod("POST");
			byte contentbyte[] = parameter.toString().getBytes();
			conn.setRequestProperty("User-Agent",
					"Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
			// 璁剧疆璇锋眰绫诲瀷
			conn.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			// 璁剧疆琛ㄥ崟闀垮害
			conn.setRequestProperty("Content-Length", (new StringBuilder())
					.append(contentbyte.length).toString());
			// 璁剧疆榛樿璇█
			conn.setRequestProperty("Content-Language", "en-US");// zh-CN浠ｈ〃涓浗
																	// 榛樿涓虹編寮忚嫳璇�
			conn.setRequestProperty("Accept-Charset", "utf-8");
			conn.setRequestProperty("contentType", "utf-8");

			// 杩炴帴涓绘満鐨勮秴鏃舵椂闂达紙鍗曚綅锛氭绉掞級
			conn.setConnectTimeout(60000);
			// 浠庝富鏈鸿鍙栨暟鎹殑瓒呮椂鏃堕棿锛堝崟浣嶏細姣)
			conn.setReadTimeout(60000);
			// Post 璇锋眰涓嶈兘浣跨敤缂撳瓨
			conn.setUseCaches(false);
			// 璁剧疆鏄惁浠巋ttpUrlConnection璇诲叆锛岄粯璁ゆ儏鍐典笅鏄痶rue;
			conn.setDoInput(true);
			// 璁剧疆鏄惁鍚慼ttpUrlConnection杈撳嚭锛屽洜涓鸿繖涓槸post璇锋眰锛屽弬鏁拌鏀惧湪 2
			// http姝ｆ枃鍐咃紝鍥犳闇�璁句负true, 榛樿鎯呭喌涓嬫槸false;
			conn.setDoOutput(true);
			BufferedWriter bWriter = new BufferedWriter(new OutputStreamWriter(
					conn.getOutputStream()));
			bWriter.write(parameter.toString());
			bWriter.flush();
			bWriter.close();
			// 璋冪敤HttpURLConnection杩炴帴瀵硅薄鐨刧etInputStream()鍑芥暟,
			// 灏嗗唴瀛樼紦鍐插尯涓皝瑁呭ソ鐨勫畬鏁寸殑HTTP璇锋眰鐢垫枃鍙戦�鍒版湇鍔＄銆�
			InputStream in = conn.getInputStream();
			StringBuffer buffer = new StringBuffer();
			for (int i = 0; i != -1;) {
				i = in.read();
				if (i != -1)
					buffer.append((char) i);
			}
			in.close();

			// 姝ゆ柟娉曟槸鐢≧eader璇诲彇BufferedReader reader = new BufferedReader(new
			// InputStreamReader( connection.getInputStream()));
			result = new String(buffer.toString().getBytes("iso-8859-1"),
					"UTF-8");
		} catch (Exception ex) {
			result = "";
		}
		return result;
	}

	public static String rest(String serviceUrl, String parameter,
			String restMethod) {
		StringBuffer sb = new StringBuffer();

		try {
			URL url = new URL(serviceUrl);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();

			con.setConnectTimeout(60 * 1000);
			con.setRequestMethod(restMethod);

			if (!"GET".equals(restMethod)) {
				con.setDoOutput(true);
				if (!"DELETE".equals(restMethod)) {

					OutputStream os = con.getOutputStream();
					os.write(parameter.getBytes("utf-8"));
					os.close();
					InputStream in = con.getInputStream();
					BufferedReader br = new BufferedReader(
							new InputStreamReader(in, "utf-8"));
					String line = "";
					while ((line = br.readLine()) != null) {
						sb.append(line);
					}
				}
			} else {
				InputStream in = con.getInputStream();
				BufferedReader br = new BufferedReader(new InputStreamReader(
						in, "utf-8"));
				String line = "";
				while ((line = br.readLine()) != null) {
					sb.append(line);
				}
			}
			System.out.println(con.getResponseCode() + ":"
					+ con.getResponseMessage());

			return sb.toString();

		} catch (Exception e) {
			return "-1";
		}

	}
	public static String sendGet(String url, String param) {
		String result = "";
		BufferedReader in = null;
		try {
			String urlNameString = url + "?" + param;
			URL realUrl = new URL(urlNameString);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 建立实际的连接
			connection.connect();
			// 获取所有响应头字段
			Map<String, List<String>> map = connection.getHeaderFields();
			// 遍历所有的响应头字段
			for (String key : map.keySet()) {
				System.out.println(key + "--->" + map.get(key));
			}
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送GET请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}
	public static void main(String[] args) {
//		System.out.println(sendPost(
//				"http://www.hbyxclub.com/hb_dial/guan/guanzhu",
//				"openid=ofzjbjreuEG9_hyMrJljM4K2bkEM&eventKey=qrscene_127885350"));
		
		System.out.println(sendPost(
				"http://localhost:8080/hb_dial/init",
				"openId=ocajgojfadkabbbdlahkcpakjkimlilhidkifeniapoffmohhbpijfmoijmmenip"));
	}
}
