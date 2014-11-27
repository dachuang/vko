/**
 * TeacherMakeHomework.java
 * cn.vko.zuoye.web.teacher
 * Copyright (c) 2014, 北京微课创景教育科技有限公司版权所有.
*/

package cn.vko.zuoye.web.teacher;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.vko.cache.dao.mybatis.Page;
import cn.vko.zuoye.service.TeacherMakeHWService;
import cn.vko.zuoye.service.hessian.input.IPhotoService;
import cn.vko.zuoye.service.sso.SsoFetchUser;
import cn.vko.zuoye.web.BaseController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.vko.core.common.sign.Base64;
import com.vko.core.common.util.CookieUtil;
import com.vko.core.common.util.SerializeUtil;
import com.vko.core.common.util.SmartMap;

/**
 * 教师发作业控制器
 * <p>
 * 两种类型,引用题库和引用照片
 * @author   宋汝波
 * @Date	 2014-7-14 	 
 */
@Controller
@RequestMapping("make")
public class TeacherMakeHWController extends BaseController {

	@Autowired
	TeacherMakeHWService teacherMakeHWService;
	@Autowired
	IPhotoService photoService;

	/*
	 * 以下是根据题库发作业
	 */
	@RequestMapping("tiku/{k1:[0-9-]+}_{k2:[0-9-]+}_{k3:[0-9-]+}_{type:[0-9-]+}_{sort:[0-9-]+}")
	public String referenceTiku(Model model, @PathVariable("type") String type, @PathVariable("k1") String k1,
			@PathVariable("k2") String k2, @PathVariable("k3") String k3, @PathVariable("sort") int sort) {
		Map<String, Integer> result = teacherMakeHWService.getTeacherInfo();
		Page page = teacherMakeHWService.getSearchExam(this.getPage(), result.get("xueDuanId"),
				result.get("subjectId"), k1, k2, k3, type, sort);
		Map<String, Object> options = teacherMakeHWService.getSearchOptions(result.get("xueDuanId"),
				result.get("subjectId"), k1, k2);
		model.addAllAttributes(options);
		model.addAttribute("k1", k1);
		model.addAttribute("k2", k2);
		model.addAttribute("k3", k3);
		model.addAttribute("type", type);
		model.addAttribute("sort", sort);
		model.addAttribute("page", page);
		model.addAttribute("select", getFromCookie());
		return "teacher/makehw/referenceTiku";
	}

	/**
	 * 发作业首页
	 * <p>
	 *
	 * @param model
	*/
	@RequestMapping("tiku")
	public String referenceTikuIndex(Model model) {
		Map<String, Integer> result = teacherMakeHWService.getTeacherInfo();
		String k1 = "-";
		String k2 = "-";
		String k3 = "-";
		String type = "-";
		int sort = 0;
		Page page = teacherMakeHWService.getSearchExam(this.getPage(), result.get("xueDuanId"),
				result.get("subjectId"), k1, k2, k3, type, sort);
		Map<String, Object> options = teacherMakeHWService.getSearchOptions(result.get("xueDuanId"),
				result.get("subjectId"), k1, k2);
		model.addAllAttributes(options);
		model.addAttribute("k1", k1);
		model.addAttribute("k2", k2);
		model.addAttribute("k3", k3);
		model.addAttribute("type", type);
		model.addAttribute("sort", sort);
		model.addAttribute("page", page);
		model.addAttribute("select", getFromCookie());
		return "teacher/makehw/referenceTiku";
	}

	/**
	 * 获取cookie中的试题id
	 * <p>
	 *
	*/
	private List<String> getFromCookie() {
		Cookie cookie = CookieUtil.getCookie(inv.getRequest(), "vko_assemble_zy");
		if (cookie != null) {
			String value = CookieUtil.decode(cookie.getValue());
			Collection<Object> colen = JSON.parseObject(value).values();
			List<String> all = new ArrayList<String>();
			for (Object object : colen) {
				all.addAll((List) object);
			}
			return all;
		}
		return Collections.emptyList();
	}

	@RequestMapping("ptiku")
	public String preView(Model model) {
		List<Long> idsLong = new ArrayList<Long>();
		List<String> idsString = getFromCookie();
		for (int i = 0; i < idsString.size(); i++) {
			idsLong.add(new Long(idsString.get(i)));
		}
		List<SmartMap<String, Object>> examList = teacherMakeHWService.getAssembleExamContent(idsLong);
		model.addAttribute("examList", examList);
		SmartMap<String, Object> smartMap = this.getParameterMap();
		model.addAllAttributes(smartMap);
		return "teacher/makehw/previewTiku";
	}

	/**
	 * 保存题库作业
	 * <p>
	 *
	 * @param model
	 * @return
	*/
	@RequestMapping("stiku")
	public String saveHomework(Model model) throws Exception {
		List<Long> idsLong = new ArrayList<Long>();
		List<String> idsString = getFromCookie();
		for (int i = 0; i < idsString.size(); i++) {
			idsLong.add(new Long(idsString.get(i)));
		}
		SmartMap<String, Object> smartMap = this.getParameterMap();
		teacherMakeHWService.saveTikuHomework(idsLong, smartMap);
		model.addAllAttributes(smartMap);
		return "teacher/makehw/success";
	}

	@RequestMapping("ntiku")
	public String nextHomework(Model model) {
		//班级群列表
		Map<Long, String> groupMap = teacherMakeHWService.getGroupsByTeacherId(SsoFetchUser.getUserIdWithEx());
		model.addAttribute("groupMap", groupMap);
		//根据选择的试题显示试题列表
		List<Long> idsLong = new ArrayList<Long>();
		List<String> idsString = getFromCookie();
		for (int i = 0; i < idsString.size(); i++) {
			idsLong.add(new Long(idsString.get(i)));
		}
		if (idsLong.size() == 0) {
			return "redirect:/make/tiku.html";
		}
		List<SmartMap<String, Object>> examList = teacherMakeHWService.getExamsContent(idsLong);
		model.addAttribute("examList", examList);
		model.addAttribute("select", getFromCookie());
		return "teacher/makehw/nextTiku";
	}

	/*
	 * 以下根据上传图片发作业
	 */
	/**
	 * 直接进入图片发作业页面
	 * <p>
	 *
	 * @param model
	*/
	@RequestMapping("mobile")
	public String referenceShouji(Model model) {
		SsoFetchUser.getUserIdWithEx();
		//SmartMap<String, Object> smartMap = this.getParameterMap();
		//model.addAllAttributes(smartMap);
		String base64Data = inv.getParameter("base64Data");
		if (base64Data != null) {
			SmartMap<String, Object> data = SerializeUtil.fastDeserialize(Base64.decode(base64Data));
			model.addAttribute("answerList", JSON.parse(data.getString("answer")));
			model.addAttribute("imagesList", JSON.parse(data.getString("jsonImages")));
			model.addAllAttributes(data);
		}
		//model.addAttribute("albumId", photoService.getMobileAlbumId(userId));
		return "teacher/makehw/referenceShouji";
	}

	@RequestMapping("imageurl")
	@ResponseBody
	public String imageurl() {
		long userId = SsoFetchUser.getUserIdWithEx();
		SmartMap<String, Object> smartMap = this.getParameterMap();
		//model.addAllAttributes(smartMap);
		//model.addAttribute("imageUrlList", photoService.getAllAvatarByUserId(userId));
		//{"pager":{"pageNumber":1,"pageSize":5,"pageCount":2,"recordCount":10},"list":["<li><img width='99' height='128' alt='undefined' src='undefined!mid' real='undefined' /></li>","<li><img width='99' height='128' alt='undefined' src='undefined!mid' real='undefined' /></li>","<li><img width='99' height='128' alt='undefined' src='undefined!mid' real='undefined' /></li>","<li><img width='99' height='128' alt='undefined' src='undefined!mid' real='undefined' /></li>","<li><img width='99' height='128' alt='undefined' src='undefined!mid' real='undefined' /></li>","<li><img width='99' height='128' alt='undefined' src='undefined!mid' real='undefined' /></li>","<li><img width='99' height='128' alt='undefined' src='undefined!mid' real='undefined' /></li>","<li><img width='99' height='128' alt='undefined' src='undefined!mid' real='undefined' /></li>","<li><img width='99' height='128' alt='undefined' src='undefined!mid' real='undefined' /></li>","<li><img width='99' height='128' alt='undefined' src='undefined!mid' real='undefined' /></li>","<li><img width='99' height='128' alt='undefined' src='undefined!mid' real='undefined' /></li>","<li><img width='99' height='128' alt='undefined' src='undefined!mid' real='undefined' /></li>","<li><img width='99' height='128' alt='undefined' src='undefined!mid' real='undefined' /></li>"],"queryForm":{"subject":0,"grade":0}}
		String json = photoService.getPagingMobileByUserId(smartMap.getint("pageNumber"), smartMap.getint("pageSize"),
				userId);
		JSONObject obj = JSON.parseObject(json);
		String html = "<li><img width='99' height='128' alt='%s' src='%s!mid' real='%s' /></li>";
		List array = (List) obj.get("list");
		for (int i = 0; i < array.size(); i++) {
			Map m = (Map) array.get(i);
			String photourl = (String) m.get("photourl");
			String name = (String) m.get("name");

			array.set(i, String.format(html, name, photourl, photourl));
		}
		return JSON.toJSONString(obj);
	}

	/**
	 *进入下一个页面
	 * <p>
	 *
	 * @param model
	 * @param jsonAnswer
	 * @param remark
	*/
	@RequestMapping("nmobile")
	public String saveAnswerCard(Model model, @RequestParam("answer") String jsonAnswer,
			@RequestParam("remark") String remark, @RequestParam("images") String jsonImages) {
		//先不向数据库中写入数据,保存成map然后写入下一个页面
		SmartMap<String, Object> data = new SmartMap<String, Object>();
		data.put("answer", jsonAnswer);
		data.put("remark", remark);
		data.put("jsonImages", jsonImages);
		//图片数据 
		byte[] serial = SerializeUtil.fastSerialize(data);
		String base64Data = Base64.encode(serial);
		model.addAttribute("base64Data", base64Data);
		Map<Long, String> groupMap = teacherMakeHWService.getGroupsByTeacherId(SsoFetchUser.getUserIdWithEx());
		model.addAttribute("groupMap", groupMap);
		return "teacher/makehw/nextShouji";
	}

	/**
	 * 保存
	 * <p>
	 *
	 * @param model
	 * @return
	*/
	@RequestMapping("smobile")
	public String saveImageHomework(Model model) throws Exception {
		SmartMap<String, Object> smartMap = this.getParameterMap();
		teacherMakeHWService.saveImageHomework(smartMap);
		model.addAllAttributes(smartMap);
		return "teacher/makehw/success";
	}
}
