/**
 * SmsUtil.java
 * cn.vko.sms.util
 * Copyright (c) 2012, 北京微课创景教育科技有限公司版权所有.
*/

package cn.vko.zuoye.service.sms;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.vko.core.common.util.Util;

/**
 * 短信发送接收工具类
 * <p>
 * 基于http://www.zucp.net的webservice开发
 * @author   彭文杰
 * @author   赵立伟
 * @Date	 2012-5-9 	
 * @version  3.1.0 
 */
public class SmsUtil {
	private static final MsgClient MSG_CLIENT = new MsgClient("SDK-BBX-010-12592", "795592");
	private static final Map<String, String> MSG_ERROR = new HashMap<String, String>();
	static {
		MSG_ERROR.put("-2", "帐号/密码不正确  1.序列号未注册2.密码加密不正确3.密码被修改");
		MSG_ERROR.put("-4", "余额不足 ");
		MSG_ERROR.put("-5", "数据格式错误   ");
		MSG_ERROR.put("-6", "参数有误 ,请调试程序查看各参数 ");
		MSG_ERROR.put("-7", "权限受限 ,该序列号是否已经开通了调用该方法的权限 ");
		MSG_ERROR.put("-8", "流量控制错误  ");
		MSG_ERROR.put("-9", "扩展码权限错误 ,该序列号是否已经开通了扩展子号的权限 ");
		MSG_ERROR.put("-10", "短信内容过长 ");
		MSG_ERROR.put("-11", "内部数据库错误   ");
		MSG_ERROR.put("-12", "序列号状态错误,序列号是否被禁用 ");
		MSG_ERROR.put("-13", "没有提交增值内容   ");
		MSG_ERROR.put("-14", "服务器写文件失败   ");
		MSG_ERROR.put("-15", "文件内容 base64 编码错误 ");
		MSG_ERROR.put("-16", "返回报告库参数错误   ");
		MSG_ERROR.put("-17", "没有权限   ");
		MSG_ERROR.put("-18", "上次提交没有等待返回不能继续提交 ");
		MSG_ERROR.put("-19", "禁止同时使用多个接口地址 每个序列号提交只能使用一个接口地址 ");
		MSG_ERROR.put("-20", "禁止一个手机号相同内容多次提交 ");
		MSG_ERROR.put("-22", "使用非法 ip地址提交 ");
	}

	/**
	 * 发送消息给一个或者多个手机
	 * <p>
	 * 注意禁止相同的信息，单独给每个手机发送，尽量一次发送
	 * @param content 发送内容
	 * @param mobiles 发送的手机号
	 * @return 返回结果详见错误说明
	 */
	public static String send(final String content, final String... mobiles) {
		String result = null;
		if (Util.isEmpty(mobiles)) {
			return "";
		}
		result = MSG_CLIENT.mt(concat(",", mobiles).toString(), content, "", "", "");

		result2Ex(result);
		return result;
	}

	public static <T> StringBuilder concat(Object c, T[] objs) {
		StringBuilder sb = new StringBuilder();
		if (null == objs || 0 == objs.length)
			return sb;

		sb.append(objs[0]);
		for (int i = 1; i < objs.length; i++)
			sb.append(c).append(objs[i]);

		return sb;
	}

	/**
	 * 发送个性信息给每个手机
	 * <p>
	 * 信息条数需要和手机号数一致
	 *
	 * @param contents 发送的内容
	 * @param mobiles 手机号码
	 * @return 发送结果
	 */
	public static String send(final List<String> contents, final List<String> mobiles) {
		if (Util.isEmpty(contents) || Util.isEmpty(mobiles)) {
			return "";
		}
		if (contents.size() != mobiles.size()) {
			throw new RuntimeException("发送个性消息时，消息条数和手机号数不一致！");
		}
		String result = MSG_CLIENT.gxmt(concat(",", mobiles.toArray()).toString(),
				concat(",", encodeGbk(contents).toArray()).toString(), "", "", "");
		return result2Ex(result);
	}

	/**
	 * 将返回的错误码转为错误信息
	 * <p>
	 * 错误信息以业务异常的形式抛出
	 *
	 * @param result 返回结果
	 * @return 如果不是错误，则直接返回结果
	 */
	private static String result2Ex(final String result) {
		if (Util.isEmpty(result)) {
			return result;
		}
		if (result.startsWith("-")) {
			if (MSG_ERROR.containsKey(result)) {
				throw new RuntimeException(MSG_ERROR.get(result));
			}
			throw new RuntimeException("短信发送或接受时出现未知错误！");
		}
		return result;
	}

	/**
	 * 将信息进行gbk编码
	 * <p>
	 * 发送个性消息时，需要将消息进行gb2312编码
	 *
	 * @param contents 消息内容
	 * @return 编码后的内容
	 */
	public static List<String> encodeGbk(final List<String> contents) {
		List<String> result = new ArrayList<String>();
		if (Util.isEmpty(contents)) {
			return result;
		}
		for (String content : contents) {
			try {
				result.add(URLEncoder.encode(content, "gb2312"));
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException("短信进行编码时出现错误!", e);
			}
		}
		return result;
	}

	/**
	 * 从短线系统接受短信
	 * <p>
	 * 只能接受一次，所以接受后需要及时保存
	 *
	 * @return 转换后的短信对象
	 */
	//	public static List<RecMessage> recieve() {
	//		String result = MSG_CLIENT.mo();
	//		result2Ex(result);
	//		List<RecMessage> msgs = list();
	//		if ("1".equals(result) || vkoIsEmpty(result)) {
	//			return msgs;
	//		}
	//		String[] msgStrs = result.split("\r\n");
	//		for (String msgStr : msgStrs) {
	//			String[] elements = msgStr.split(",");
	//			if (vkoIsEmpty(elements) || elements.length < 5) {
	//				continue;
	//			}
	//			RecMessage msg = new RecMessage();
	//			msg.setSmsNo(elements[0]);
	//			msg.setExtNo(elements[1]);
	//			msg.setMobile(elements[2]);
	//			try {
	//				msg.setContent(URLDecoder.decode(elements[3], "gb2312"));
	//			} catch (UnsupportedEncodingException e) {
	//				msg.setContent(elements[3]);
	//			}
	//			msg.setCTime(elements[4]);
	//			msgs.add(msg);
	//		}
	//		return msgs;
	//	}

	public static String getBalance() {
		return MSG_CLIENT.getBalance();
	}

	/**
	 * 从数据库的sms_log中查找当天的记录进行发送
	 * 每条内容相同时进行处理
	 * @return 发送结果
	 */
	//	public static String sendFromDbSame(final String date, final String content) {
	//		int pageSize = 1000;
	//		Pager pager = new Pager();
	//		pager.setPageSize(pageSize);
	//		int i = 1;
	//		pager.setPageNumber(i);
	//		List<Record> records = null;
	//
	//		do {
	//			records = DaoUtil.query("select mobile from sms_log where sendDate = '" + date + "'", null, pager);
	//			if (Util.vkoIsEmpty(records)) {
	//				return "";
	//			}
	//			List<String> mobiles = new ArrayList<String>();
	//			for (Record record : records) {
	//				mobiles.add(record.getString("mobile"));
	//			}
	//			System.out.println("发送结果：" + send(content, CollectionUtil.collection2array(mobiles)));
	//			System.out.println("第" + i + "页已经发送本页 " + records.size() + "：手机号从" + mobiles.get(0) + "到"
	//					+ mobiles.get(mobiles.size() - 1));
	//			pager.setPageNumber(i++);
	//			pager.setPageSize(pageSize);
	//
	//		} while (records.size() == pageSize);
	//		return "";
	//	}

	public static void main(String[] args) {
		String content = "大家好这是测试的短信【微课网】";
		System.out.println(send(content, "15011302483,13051278175"));
		//281315271002203759
		//28131539156129896
	}
}
