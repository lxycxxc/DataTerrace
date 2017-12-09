package hlkj.interceptor;

import lxycx.util.validate.ServletValidate;
import lxycx.util.validate.Validate.Ret;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;


public abstract class AbsValidateInterceptor implements Interceptor,ServletValidate {

	@Override
	public final void intercept(Invocation inv) {
		Controller con = inv.getController();
		
		Ret ret = this.verify(con.getRequest());
		if(ret.isFlag()){
			inv.invoke();
		}else{
			con.setAttr("res", ret.getState());
			con.setAttr("msg", ret.getMsg());
			render(con);
		}

	}
	/**被拦截后的返回操作，默认返回json,子类可覆写*/
	protected void render(Controller con){
		con.renderJson();
	}
}
