package com.vko.core.common.util;

import java.io.File;
import java.util.Date;

import com.oreilly.servlet.multipart.FileRenamePolicy;

/**
 * 上传文件命名策略
 * 
 * @author longyin E-mail:darkwing.zhu@gmail.com
 * @version 创建时间：2012-4-19 下午10:29:47
 *
 */

public class RandomFileRenamePolicy implements FileRenamePolicy {
	public File rename(File file) {
		String body = "";
		String ext = "";
		Date date = new Date();
		int pot = file.getName().lastIndexOf(".");
		if (pot != -1) {
			body = date.getTime() + "";
			ext = file.getName().substring(pot);
		} else {
			body = (new Date()).getTime() + "";
			ext = "";
		}
		String newName = body + ext;
		file = new File(file.getParent(), newName);
		return file;

	}
}