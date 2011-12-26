package edu.kit.pse.ass.gui.layout;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

//taken from: http://blog.tuxychandru.com/2009/12/simple-layouts-with-jsp-in-spring-mvc.html
public class LayoutInterceptor extends HandlerInterceptorAdapter {
	private static final String NO_LAYOUT = "noLayout:";

	private String layoutView;

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		super.postHandle(request, response, handler, modelAndView);

		String originalView = modelAndView.getViewName();

		if (originalView != null && !originalView.startsWith("redirect:")) {
			includeLayout(modelAndView, originalView);
		}
	}

	private void includeLayout(ModelAndView modelAndView, String originalView) {
		boolean noLayout = originalView.startsWith(NO_LAYOUT);

		String realViewName = (noLayout) ? originalView.substring(NO_LAYOUT
				.length()) : originalView;

		if (noLayout) {
			modelAndView.setViewName(realViewName);
		} else {
			modelAndView.addObject("view", realViewName);
			modelAndView.setViewName(layoutView);
		}
	}

	public void setLayoutView(String layoutView) {
		this.layoutView = layoutView;
	}
}