package hlkj.util;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.imageio.ImageIO;


/**
 * 
 * 随机增强�?
 * */
public class RandomPro {
	private static Random r = new Random(System.currentTimeMillis()*3);
	private static RandomPro rp = new RandomPro();
	private static int imgWidth = 12;
	private static int imgHeight =25;
	private static String utf8s = "\u7684\u4e00\u4e86\u662f\u6211\u4e0d\u5728\u4eba\u4eec\u6709\u6765\u4ed6\u8fd9\u4e0a\u7740\u4e2a\u5730\u5230\u5927\u91cc\u8bf4\u5c31\u53bb\u5b50\u5f97\u4e5f\u548c\u90a3\u8981\u4e0b\u770b\u5929\u65f6\u8fc7\u51fa\u5c0f\u4e48\u8d77\u4f60\u90fd\u628a\u597d\u8fd8\u591a\u6ca1\u4e3a\u53c8\u53ef\u5bb6\u5b66\u53ea\u4ee5\u4e3b\u4f1a\u6837\u5e74\u60f3\u751f\u540c\u8001\u4e2d\u5341\u4ece\u81ea\u9762\u524d\u5934\u9053\u5b83\u540e\u7136\u8d70\u5f88\u50cf\u89c1\u4e24\u7528\u5979\u56fd\u52a8\u8fdb\u6210\u56de\u4ec0\u8fb9\u4f5c\u5bf9\u5f00\u800c\u5df1\u4e9b\u73b0\u5c71\u6c11\u5019\u7ecf\u53d1\u5de5\u5411\u4e8b\u547d\u7ed9\u957f\u6c34\u51e0\u4e49\u4e09\u58f0\u4e8e\u9ad8\u624b\u77e5\u7406\u773c\u5fd7\u70b9\u5fc3\u6218\u4e8c\u95ee\u4f46\u8eab\u65b9\u5b9e\u5403\u505a\u53eb\u5f53\u4f4f\u542c\u9769\u6253\u5462\u771f\u5168\u624d\u56db\u5df2\u6240\u654c\u4e4b\u6700\u5149\u4ea7\u60c5\u8def\u5206\u603b\u6761\u767d\u8bdd\u4e1c\u5e2d\u6b21\u4eb2\u5982\u88ab\u82b1\u53e3\u653e\u513f\u5e38\u6c14\u4e94\u7b2c\u4f7f\u5199\u519b\u5427\u6587\u8fd0\u518d\u679c\u600e\u5b9a\u8bb8\u5feb\u660e\u884c\u56e0\u522b\u98de\u5916\u6811\u7269\u6d3b\u90e8\u95e8\u65e0\u5f80\u8239\u671b\u65b0\u5e26\u961f\u5148\u529b\u5b8c\u5374\u7ad9\u4ee3\u5458\u673a\u66f4\u4e5d\u60a8\u6bcf\u98ce\u7ea7\u8ddf\u7b11\u554a\u5b69\u4e07\u5c11\u76f4\u610f\u591c\u6bd4\u9636\u8fde\u8f66\u91cd\u4fbf\u6597\u9a6c\u54ea\u5316\u592a\u6307\u53d8\u793e\u4f3c\u58eb\u8005\u5e72\u77f3\u6ee1\u65e5\u51b3\u767e\u539f\u62ff\u7fa4\u7a76\u5404\u516d\u672c\u601d\u89e3\u7acb\u6cb3\u6751\u516b\u96be\u65e9\u8bba\u5417\u6839\u5171\u8ba9\u76f8\u7814\u4eca\u5176\u4e66\u5750\u63a5\u5e94\u5173\u4fe1\u89c9\u6b65\u53cd\u5904\u8bb0\u5c06\u5343\u627e\u4e89\u9886\u6216\u5e08\u7ed3\u5757\u8dd1\u8c01\u8349\u8d8a\u5b57\u52a0\u811a\u7d27\u7231\u7b49\u4e60\u9635\u6015\u6708\u9752\u534a\u706b\u6cd5\u9898\u5efa\u8d76\u4f4d\u5531\u6d77\u4e03\u5973\u4efb\u4ef6\u611f\u51c6\u5f20\u56e2\u5c4b\u79bb\u8272\u8138\u7247\u79d1\u5012\u775b\u5229\u4e16\u521a\u4e14\u7531\u9001\u5207\u661f\u5bfc\u665a\u8868\u591f\u6574\u8ba4\u54cd\u96ea\u6d41\u672a\u573a\u8be5\u5e76\u5e95\u6df1\u523b\u5e73\u4f1f\u5fd9\u63d0\u786e\u8fd1\u4eae\u8f7b\u8bb2\u519c\u53e4\u9ed1\u544a\u754c\u62c9\u540d\u5440\u571f\u6e05\u9633\u7167\u529e\u53f2\u6539\u5386\u8f6c\u753b\u9020\u5634\u6b64\u6cbb\u5317\u5fc5\u670d\u96e8\u7a7f\u5185\u8bc6\u9a8c\u4f20\u4e1a\u83dc\u722c\u7761\u5174\u5f62\u91cf\u54b1\u89c2\u82e6\u4f53\u4f17\u901a\u51b2\u5408\u7834\u53cb\u5ea6\u672f\u996d\u516c\u65c1\u623f\u6781\u5357\u67aa\u8bfb\u6c99\u5c81\u7ebf\u91ce\u575a\u7a7a\u6536\u7b97\u81f3\u653f\u57ce\u52b3\u843d\u94b1\u7279\u56f4\u5f1f\u80dc\u6559\u70ed\u5c55\u5305\u6b4c\u7c7b\u6e10\u5f3a\u6570\u4e61\u547c\u6027\u97f3\u7b54\u54e5\u9645\u65e7\u795e\u5ea7\u7ae0\u5e2e\u5566\u53d7\u7cfb\u4ee4\u8df3\u975e\u4f55\u725b\u53d6\u5165\u5cb8\u6562\u6389\u5ffd\u79cd\u88c5\u9876\u6025\u6797\u505c\u606f\u53e5\u533a\u8863\u822c\u62a5\u53f6\u538b\u6162\u53d4\u80cc\u7ec6"; 

	private RandomPro(){}
	
	
	public static RandomPro getRp() {
		return rp;
	}
	
	public static Set<String> ranCode(int size,int length){
		Random r = new Random(System.currentTimeMillis());
		String[] nums = new String[]{"1","2","3","4","5","6","7","8","9"};
		String[] strs = new String[]{"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z","a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};
		Set<String> set = new HashSet<String>();
		while(set.size()<size){
			StringBuilder sb = new StringBuilder();
			for(int i=1;i<=length;i++){
				if(i%2==0){
					sb.append(nums[r.nextInt(9)]);
				}else{
					sb.append(strs[r.nextInt(52)]);
				}
			}			
			set.add(sb.toString());
		}
		
		
		return set;
	}
	
	
	/**
	 * 返回指定位数随机字符串
	 * */
	public static String getRandomString(int size){
		String str = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		return getRandomString(str, size);
	}
	/**
	 * 返回指定位数随机字符串
	 * @param str 指定字符集
	 * @param size 返回的字符串的长度
	 * */
	public static String getRandomString(String str, int size){
		StringBuilder sb = new StringBuilder();
		char[] chars = str.toCharArray();
		int length = chars.length;
		
		for(int i=0;i<size;i++){
		sb.append(chars[r.nextInt(length)]);
		}
		return sb.toString();
	}
	
	/**
	 * 返回指定数量的utf-8汉字
	 * @param size
	 * @return
	 * @author 邢超
	 * 创建时间：2017-5-8
	 *
	 */
	public static String getUtf8Hz(int size){
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<size;i++){
			char s = utf8s.charAt(r.nextInt(utf8s.length()-1));
			sb.append(s);
		}
		
		return sb.toString();
	}
	
	
	/**
	 * 返回指定数量的GBK汉字
	 * @return
	 * @throws UnsupportedEncodingException
	 * @author 邢超
	 * 创建时间：2017-5-8
	 *
	 */
	public static String getGbkHz(int size) throws UnsupportedEncodingException{
		 StringBuilder sb = new StringBuilder();
		 for(int i=0;i<size;i++){
			 int hightPos, lowPos; // 定义高低位
			 hightPos = (176 + Math.abs(r.nextInt(39))); //获取高位值
			 lowPos = (161 + Math.abs(r.nextInt(93))); //获取低位值
			 byte[] b = new byte[2];
			 b[0] = (new Integer(hightPos).byteValue());
			 b[1] = (new Integer(lowPos).byteValue());
			 sb.append(new String(b, "GBK")); //转成中文
		 }
		return sb.toString();
	}
	
	
	
	

	
	/**
	 * 返回Size以内gl个随机数的set集�?
	 * */
	public static Set<Integer> getSet(Integer gl,Integer size){
		Set<Integer> rans = new HashSet<Integer>();
		while(rans.size()<gl){
			rans.add(r.nextInt(size));
		}
		return rans;
	}
	
	/**
	 * 返回Size以内gl个随机数的list集�?
	 * */
	public static List<Integer> getList(Integer gl,Integer size){
		List<Integer> rans = new ArrayList<Integer>();
		while(rans.size()<gl){
			rans.add(r.nextInt(size));
		}
		return rans;
	}
	
	
	/**
	 * 数字验证码
	 * */
	public static String getVC(int len){
		return getRandomString("0123456789", len);
	}
	
	/**
	 * 当前时间的毫秒数13位+指定位随机数
	 * @return 13+len
	 */
	public static String getOrderId(int len){
		return System.currentTimeMillis()+getVC(len);
	}
	
	
	/**
	 * 生成一个验证码，返回一个对应的字符串
	 * @param out 验证码输出源
	 * @param size 验证码长度
	 * */
	public static String imageYzm(OutputStream out,int size){
		try {
			StringBuffer sb = new StringBuffer();
			// 在内存中创建图象
			BufferedImage image = new BufferedImage(imgWidth*size, imgHeight,BufferedImage.TYPE_INT_RGB);
			// 获取图形上下文
			Graphics2D g = image.createGraphics();
			// 设定背景色
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, imgWidth*size, imgHeight);
			// 设定字体
			g.setFont(new Font("Arial", Font.PLAIN + Font.ITALIC, 28));
			// 画边框
			//	g.setColor(new Color(255, 255, 255));
			//	g.drawRect(0, 0, imgWidth - 1, imgHeight - 1);
			
			// 随机产生155条干扰线，使图象中的认证码不易被其它程序探测到
			g.setColor(new Color(203, 165, 162));
			for (int i = 0; i < 60; i++) {
			int x = r.nextInt(imgWidth*size);
			int y = r.nextInt(imgHeight);
			int xl = r.nextInt(12);
			int yl = r.nextInt(12);
			g.drawLine(x, y, x + xl, y + yl);
			}
			// 取随机产生的认证码(4位数字)
			String sRand = "";
			int red = 0, green = 0, blue = 0;
			for (int i = 0; i < size; i++) {
				red = r.nextInt(255);
				green = r.nextInt(255);
				blue = r.nextInt(255);
				int wordType = r.nextInt(3);
				//sRand += String.valueOf(retWord);
				g.setColor(new Color(red, green, blue));
				sRand = RandomPro.getRandomString(1);
				g.drawString(sRand, (i) * 10, 20);
				sb.append(sRand);
			}
			// 将认证码存入SESSION

			// 图象生效
			g.dispose();
			//OutputStream os = new FileOutputStream(new File("D://123.jpg"));
			// 输出图象到页面
			ImageIO.write(image, "JPEG", out);

			// 以下关闭输入流！
			out.flush();
			out.close();
			return sb.toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	
	
	public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {

System.out.println(utf8s);
	}

}
