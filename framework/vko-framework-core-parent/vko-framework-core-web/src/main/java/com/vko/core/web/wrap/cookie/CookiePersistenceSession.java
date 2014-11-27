package com.vko.core.web.wrap.cookie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.util.StringUtils;

import com.vko.core.web.wrap.PersistenceSession;

import com.vko.core.common.util.CompressEncodeing;
import com.vko.core.common.util.RandomGUID;

/**
 * @author bo
 * 
 *         可以将sessionid和时间放到一个cookie中
 */
public class CookiePersistenceSession implements PersistenceSession {
	protected HttpServletRequest request;

	protected HttpServletResponse response;

	protected CookieConfig cookieConfig;

	protected String sessionId = null;

	protected String sessionTime = null;

	protected boolean isInvalidate = false;
	protected HttpSessionAttributeListener attributeListener;
	protected HttpSessionListener sessionListener;
	protected HttpSession session;
	protected HashMap<String, Object> map = new HashMap<String, Object>();

	public CookiePersistenceSession(HttpServletRequest request, HttpServletResponse response, CookieConfig cookieConfig) {
		this.request = request;
		this.response = response;
		this.cookieConfig = cookieConfig;

	}

	@Override
	public void init() {
		// 删除jsessionid
		Cookie[] cookie = request.getCookies();
		if (cookie == null || cookie.length == 0) {
			sessionCreated();
		} else {
			for (int i = 0; i < cookie.length; i++) {
				if (KEY_ID.equals(cookie[i].getName())) {
					sessionId = cookie[i].getValue();
					continue;
				} else if (KEY_TIME.equals(cookie[i].getName())) {
					sessionTime = cookie[i].getValue();
					continue;
				}
				if (cookie[i].getName().startsWith(KEY_COOKIE_NAME_PREFIX)) {
					String value = cookie[i].getValue();
					if (value.length() > 0) {
						map.putAll(CookieSerializeImpl.me().decode(value));
					}
				}
			}
		}
		if (sessionId == null) {
			sessionCreated();
		}
		setAccessTime();
	}

	protected void sessionCreated() {
		RandomGUID myGUID = new RandomGUID();
		sessionId = myGUID.toString();
		Cookie cookieObj = new Cookie(KEY_ID, sessionId);
		getBaseCookie(cookieObj);
		response.addCookie(cookieObj);
		if (sessionListener != null) {
			HttpSessionEvent event = new HttpSessionEvent(session);
			sessionListener.sessionCreated(event);
		}
	}

	protected void sessionDestroyed() {
		isInvalidate = true;
		map.clear();
		clearSession();
		if (sessionListener != null) {
			HttpSessionEvent event = new HttpSessionEvent(session);
			sessionListener.sessionDestroyed(event);
		}
		sessionCreated();
	}

	private void clearSession() {
		Cookie[] cookies = request.getCookies();
		for (int i = 0; i < cookies.length; i++) {
			if (cookies[i].getName().startsWith(KEY_COOKIE_NAME_PREFIX)) {
				Cookie cookie = new Cookie(cookies[i].getName(), "");
				cookie.setMaxAge(0);
				response.addCookie(cookie);
			}
		}
	}

	protected void getBaseCookie(Cookie cookieObj) {
		cookieObj.setPath(cookieConfig.getPath());
		cookieObj.setMaxAge(cookieConfig.getMaxAge());
		if (cookieConfig.getDomain() != null) {
			cookieObj.setDomain(cookieConfig.getDomain());
		}
		cookieObj.setComment(cookieConfig.getComment());
		cookieObj.setSecure(cookieConfig.isSecure());
		cookieObj.setVersion(cookieConfig.getVersion());
	}

	@Override
	public Object getAttribute(String key) {
		return map.get(key);
	}

	@Override
	public void removeArribute(String key) {
		map.remove(key);
		storeSessionMap(map);
		if (attributeListener != null) {
			HttpSessionBindingEvent event = new HttpSessionBindingEvent(session, key);
			attributeListener.attributeRemoved(event);
		}
	}

	@Override
	public void setAttribute(String key, Object value) {
		// 检测是否允许set
		String[] sessionNames = cookieConfig.getAllowSessionNames();
		if (sessionNames != null) {
			boolean search = false;
			for (int i = 0; i < sessionNames.length; i++) {
				if (key.equals(sessionNames[i])) {
					search = true;
					break;
				}
			}
			if (!search) {
				throw new RuntimeException(key + "设定为不能向session中添加");
			}
		}
		boolean isAdded = true;
		if (map.containsKey(key)) {
			isAdded = false;
		}
		map.put(key, value);
		storeSessionMap(map);
		if (attributeListener != null) {
			HttpSessionBindingEvent event = new HttpSessionBindingEvent(session, key, value);
			if (isAdded) {
				attributeListener.attributeAdded(event);
			} else {
				attributeListener.attributeReplaced(event);
			}
		}
	}

	@Override
	public String getSessionId() {
		return sessionId;
	}

	@Override
	public Map<String, Object> getSessionMap() {
		return map;
	}

	@Override
	public void invalidate() {
		sessionDestroyed();
	}

	/*
	 * 4000字节限制,自动新建cookie字段,字段不能超过30,每个域
	 */
	public void storeSessionMap(Map<String, Object> map) {
		if (map.isEmpty()) {
			// 清空
			clearSession();
			return;
		}
		List<String> allCookieValue = new ArrayList<String>(10);
		getSplitedMap(map, allCookieValue);
		for (int i = 0, size = allCookieValue.size(); i < size; i++) {
			Cookie cookie = new Cookie(KEY_COOKIE_NAME_PREFIX + i, allCookieValue.get(i));
			getBaseCookie(cookie);
			response.addCookie(cookie);
		}
	}

	protected void getSplitedMap(Map<String, Object> map, List<String> allCookieValue) {
		int size = map.size();
		String str = CookieSerializeImpl.me().encode(map);
		if (str.length() > cookieConfig.getSizeToSplit() && size > 1) {
			int i = 0, half = size / 2;
			Map<String, Object> odd = new HashMap<String, Object>(half);
			Map<String, Object> even = new HashMap<String, Object>(half);
			for (Iterator<String> iterator = map.keySet().iterator(); iterator.hasNext();) {
				String key = iterator.next();
				if (i % 2 == 0) {
					even.put(key, map.get(key));
				} else {
					odd.put(key, map.get(key));
				}
				i++;
			}
			getSplitedMap(odd, allCookieValue);
			getSplitedMap(even, allCookieValue);
		} else {
			allCookieValue.add(str);
		}

	}

	/*
	 * 访问时间-过期时间
	 */
	@Override
	public long getLastAccessedTime() {
		if (sessionTime == null) {
			return 0L;
		}
		String[] timeString = StringUtils.split(sessionTime, "-");
		if (timeString != null) {
			return CompressEncodeing.unCompressNumber(timeString[0]);
		}
		return CompressEncodeing.unCompressNumber(sessionTime);
	}

	@Override
	public int getMaxInactiveInterval() {
		return cookieConfig.getSessionExpireTime();
	}

	/*
	 * 默认半个小时过期
	 */
	@Override
	public void setMaxInactiveInterval(int second) {
		throw new RuntimeException("not support");

	}

	protected void setAccessTime() {
		// 检测session过期
		int second = cookieConfig.getSessionExpireTime();
		long lastAccess = getLastAccessedTime();
		long time = System.currentTimeMillis();
		if (lastAccess > 0 && (time - lastAccess) > (second * 1000)) {
			sessionDestroyed();
		}
		String currentTime = CompressEncodeing.compressNumber(time);
		String expire = CompressEncodeing.compressNumber(second);
		Cookie cookie = new Cookie(KEY_TIME, currentTime + "-" + expire);
		getBaseCookie(cookie);
		response.addCookie(cookie);
	}

	@Override
	public long getCreationTime() {
		if (sessionId != null) {
			return RandomGUID.fromString(sessionId).getTimeField();
		}
		return 0L;
	}

	@Override
	public void setAttributeListener(HttpSessionAttributeListener attributeListener) {
		this.attributeListener = attributeListener;
	}

	@Override
	public void setSessionListener(HttpSessionListener sessionListener) {
		this.sessionListener = sessionListener;
	}

	@Override
	public boolean isInvalidate() {
		return isInvalidate;
	}

	@Override
	public void setWrappedSession(HttpSession session) {
		this.session = session;

	}
}
