package hlkj.interceptor;

import java.net.URLEncoder;

import org.apache.log4j.Logger;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;

/**
 * 登录拦截器抽象类，适用于普通登录或者微信登录等
 * @author 邢超
 * 时间：2017年9月15日
 */
public abstract class AbsLoginIntercept implements Interceptor {
	private static Logger log = Logger.getLogger(AbsLoginIntercept.class);
	
	@Override
	public void intercept(Invocation inv) {
		Controller con = inv.getController();
		
		String user = getUser(con);
		//String user_openid = UserUtil.getOpenid(con.getRequest());
		log.info("登录拦截器：user=="+user);
		if(user==null){//创建用户会话
			String url = con.getRequest().getRequestURI();
			url+="?"+con.getRequest().getQueryString();
			con.redirect(getRedirectUrl()+"?callbank="+URLEncoder.encode(url.toString()));
		}else{
			inv.invoke();
		}
	}

	
	/**
	 * 获取当前客户保存的登录状态，没有登录返回null
	 * @return
	 * @author 邢超
	 * 创建时间：2017年9月15日
	 *
	 */
	protected abstract String getUser(Controller con);
	
	/**
	 * 中断当前的正常请求,跳转到指定地址;
	 * 链接地址自动附加callbank参数存放被中断的请求地址；
	 * @return 需要跳转的链接，不允许携带参数
	 * @author 邢超
	 * 创建时间：2017年9月15日
	 *
	 */
	protected abstract String getRedirectUrl();

}