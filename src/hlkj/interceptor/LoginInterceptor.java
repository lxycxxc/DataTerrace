package hlkj.interceptor;
import hlkj.util.ServletPro;

import com.jfinal.aop.Interceptor;
import com.jfinal.core.Controller;


/**
 * 微信登录拦截器
 * @author Administrator
 *
 */
public class LoginInterceptor extends AbsLoginIntercept implements Interceptor {

	@Override
	protected String getUser(Controller con) {
		
		String login_user = con.getSessionAttr("user");
		//System.out.println("useropenid = "+user_openid);
		return 	login_user;
	}

	@Override
	protected String getRedirectUrl() {
		// TODO Auto-generated method stub
		return "/login";
	}

}
