/**
 * SendHWWorker.java
 * cn.vko.zuoye.service.work
 * Copyright (c) 2014, 北京微课创景教育科技有限公司版权所有.
*/

package cn.vko.zuoye.service.work;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.vko.zuoye.entity.Homework;
import cn.vko.zuoye.keyvalue.HomeworkDistribution;
import cn.vko.zuoye.service.BaseService;
import cn.vko.zuoye.service.TeacherMakeHWService;
import cn.vko.zuoye.service.sso.RedisCommands;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * 收作业定时任务
 * <p>
 * @author   宋汝波
 * @Date	 2014-7-15 	 
 */
@Component
public class ReceiveHWWorker extends BaseService implements InitializingBean, DisposableBean {

	@Autowired
	private RedisCommands proxy;
	@Autowired
	private TeacherMakeHWService teacherMakeHWService;
	private boolean locked = false;
	private static final String LOCK_KEY = "vko:zuoye:worker_key_receive_lock_key";
	private static final String WORKER_KEY = "vko:zuoye:worker_key_receive_key";
	private ScheduledExecutorService service;

	@Override
	public void afterPropertiesSet() throws Exception {
		List<Homework> homeworks = homeworkMapper.selectDistributionHW(HomeworkDistribution.DAI_SHOU.getKey());
		String json = JSON.toJSONString(homeworks, SerializerFeature.WriteClassName);
		proxy.set(WORKER_KEY, json);
		service = Executors.newScheduledThreadPool(1);
		service.scheduleWithFixedDelay(new Task(), 20, 20, TimeUnit.SECONDS);
		//从数据库获取待发送作业的任务,放到redis,并开启定时任务
		logger.info("定时收作业任务开始运行");
	}

	public boolean lock(Long expireMsecs) throws InterruptedException {
		//超时时间5s
		int timeout = 5000;
		while (timeout >= 0) {
			long expires = System.currentTimeMillis() + expireMsecs + 1;
			String expiresStr = String.valueOf(expires);
			if (proxy.setnx(LOCK_KEY, expiresStr) == 1) {
				locked = true;
				return true;
			}

			String currentValueStr = proxy.get(LOCK_KEY);
			if (currentValueStr != null && Long.parseLong(currentValueStr) < System.currentTimeMillis()) {
				// lock is expired
				String oldValueStr = proxy.getSet(LOCK_KEY, expiresStr);
				if (oldValueStr != null && oldValueStr.equals(currentValueStr)) {
					// lock acquired
					locked = true;
					return true;
				}
			}
			timeout -= 100;
			Thread.sleep(100);
		}
		return false;
	}

	public void unlock() {
		if (locked) {
			proxy.del(LOCK_KEY);
			locked = false;
		}
	}

	public boolean refresh() throws Exception {
		//从数据库重新加载数据
		if (lock(1000L)) {
			List<Homework> homeworks = homeworkMapper.selectDistributionHW(HomeworkDistribution.DAI_SHOU.getKey());
			String json = JSON.toJSONString(homeworks, SerializerFeature.WriteClassName);
			proxy.set(WORKER_KEY, json);
		}
		unlock();
		return true;
	}

	class Task implements Runnable {
		@Override
		public void run() {
			try {
				List<Long> needDistributIds = new ArrayList<Long>();
				if (lock(1000L)) {
					//检测是否可以发布
					String json = proxy.get(WORKER_KEY);
					logger.info(json);
					if (json == null || json.length() == 0) {
						return;
					}
					List<Homework> homeworks = (List<Homework>) JSON.parse(json);
					if (homeworks == null) {
						return;
					}
					List<Homework> reserve = new ArrayList<Homework>();
					for (int i = 0, size = homeworks.size(); i < size; i++) {
						Homework homework = homeworks.get(i);
						if (homework.getEndTime() != null && homework.getEndTime().compareTo(new Date()) == -1) {
							needDistributIds.add(homework.getId());
						} else {
							reserve.add(homework);
						}
					}
					json = JSON.toJSONString(reserve, SerializerFeature.WriteClassName);
					proxy.set(WORKER_KEY, json);
				}
				unlock();
				if (needDistributIds.size() > 0) {
					//生成学生作业  needDistributIds
					teacherMakeHWService.saveReceiveHomework(needDistributIds);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public void destroy() throws Exception {
		if (service != null) {
			service.shutdownNow();
		}

	}

	public static void main(String[] args) {
		System.out.println(new Date(System.currentTimeMillis() - 100).compareTo(new Date()));
	}
}
