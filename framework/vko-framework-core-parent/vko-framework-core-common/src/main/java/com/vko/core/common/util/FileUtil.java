package com.vko.core.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.oreilly.servlet.MultipartRequest;

/**
 * 文件工具类
 * 
 * @author pomoer
 *
 */
public class FileUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);

	// 拷贝块大小
	protected static final int BLKSIZ = 2048;

	public static void removeFile(File file) {
		int maxTry = 3;
		while (maxTry > 0) {
			maxTry--;
			if (file.isFile()) {
				if (file.delete())
					return;
				else
					continue;
			} else {
				return;
			}
		}
	}

	/**  
	 * 删除文件  
	 *   
	 * @param filePathAndName 删除文件完整路径:d:/filedir/filename  
	 * @return  
	 */
	public static boolean delFile(String filePathAndName) {
		boolean ret = false;
		try {
			new File(filePathAndName.toString()).delete();
			ret = true;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	/**  
	 * 删除目录下的文件  
	 *   
	 * @param dir 文件目录 d:/filedir/  
	 * @return  
	 */
	public static boolean delFiles(String dir) {
		boolean ret = false;
		try {
			File filePath = new File(dir);// 查询路径   
			String[] files = filePath.list();// 存放所有查询结果   
			int i = 0;
			for (i = 0; i < files.length; i++) {
				new java.io.File(dir + File.separator + files[i]).delete();
			}
			ret = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	/**  
	 * 判断文件是否存在  
	 *   
	 * @param filename  
	 * @return  
	 */
	public static boolean isExist(String filename) {
		boolean ret = false;
		try {
			File file = new File(filename.toString());
			if (file.exists()) {
				ret = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	/**  
	 * 上传文件  
	 *   
	 * @param file 上传文件实体  
	 * @param filename 上传文件新命名  
	 * @param dir  上传文件目录  
	 * @return  
	 */
	public static boolean uploadFile(File file, String dir, String filename) {
		boolean ret = false;
		try {
			if (file != null) {
				java.io.File filePath = new java.io.File(dir);
				if (!filePath.exists()) {
					filePath.mkdir();
				}
				String target = dir + filename;
				FileOutputStream outputStream = new FileOutputStream(target);
				FileInputStream fileIn = new FileInputStream(file);
				byte[] buffer = new byte[1024];
				int len;
				while ((len = fileIn.read(buffer)) > 0) {
					outputStream.write(buffer, 0, len);
				}
				fileIn.close();
				outputStream.close();
				ret = true;
			}
		} catch (Exception ex) {
			// log.info("上传文件无法处理，请确认上传文件！");   
		}
		return ret;
	}

	/**
	 * 保存文件 
	 * 
	 * @param stream
	 * @param path
	 * @param filename
	 * @throws IOException
	 */
	public static void SaveFileFromInputStream(InputStream stream, String path, String filename) throws IOException {
		FileOutputStream fs = new FileOutputStream(path + File.separator + filename);
		byte[] buffer = new byte[1024 * 1024];
		int bytesum = 0;
		int byteread = 0;
		while ((byteread = stream.read(buffer)) != -1) {
			bytesum += byteread;
			fs.write(buffer, 0, byteread);
			fs.flush();
		}
		fs.close();
		stream.close();
	}

	public static void readFile(File file, OutputStream output) throws IOException {
		FileInputStream input = null;
		FileChannel fc = null;
		try {
			input = new FileInputStream(file);
			fc = input.getChannel();
			ByteBuffer buffer = ByteBuffer.allocate(4096);
			for (;;) {
				buffer.clear();
				int n = fc.read(buffer);
				if (n == (-1))
					break;
				output.write(buffer.array(), 0, buffer.position());
			}
		} finally {
			if (fc != null) {
				try {
					fc.close();
				} catch (IOException e) {
				}
			}
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
				}
			}
		}
	}

	public static void writeFile(File file, byte[] data) throws IOException {
		final int MAX_BUFFER_SIZE = 4096;
		FileOutputStream output = null;
		FileChannel fc = null;
		try {
			output = new FileOutputStream(file);
			fc = output.getChannel();
			ByteBuffer buffer = ByteBuffer.allocate(MAX_BUFFER_SIZE);
			int offset = 0;
			while (offset < data.length) {
				buffer.clear();
				int len = data.length - offset;
				if (len > MAX_BUFFER_SIZE)
					len = MAX_BUFFER_SIZE;
				buffer.put(data, offset, len);
				offset += len;
				buffer.flip();
				fc.write(buffer);
			}
		} finally {
			if (fc != null) {
				try {
					fc.close();
				} catch (IOException e) {
				}
			}
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
				}
			}
		}
	}

	public static String getExtension(String fileName) {
		int index = fileName.lastIndexOf(".");
		if (index != -1)
			return fileName.substring(index + 1, fileName.length());
		else
			return fileName;
	}

	public static String getFileNameNotExtension(String fileName) {
		int index = fileName.lastIndexOf(".");
		if (index != -1)
			return fileName.substring(0, index);
		else
			return fileName;
	}

	/**
	 * 生成随机文件名：日期+四位随机数
	 * 
	 * @return
	 */
	public static String getFileNameRandom() {
		String name;
		String format = "yyyyMMddHHmmss";
		Random r = new Random();

		//生成随机文件名：日期+四位随机数   
		int rannum = (int) (r.nextDouble() * (9999 - 1000 + 1)) + 1000;
		name = new SimpleDateFormat(format).format(new Date());
		name = name + rannum;
		return name;
	}

	/**
	 * 上传文件 
	 * 
	 * @param request
	 * @param filePath 上传的文件保存在服务器的路径
	 * @param fileMaxSize 允许上传的文件最大的大小 5M = 5 * 1024 * 1024
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static String upload(HttpServletRequest request, String filePath, int fileMaxSize) throws Exception {
		//存绝对路径  
		//String filePath = "C://upload";  
		//存相对路径  
		File uploadPath = new File(filePath);
		//检查文件夹是否存在 不存在 创建一个  
		if (!uploadPath.exists()) {
			uploadPath.mkdirs();
		}

		//文件名  
		String fileName = null;
		//上传文件数  
		int fileCount = 0;
		//重命名策略  
		RandomFileRenamePolicy policy = new RandomFileRenamePolicy();
		//上传文件  
		MultipartRequest mulit = new MultipartRequest(request, filePath, fileMaxSize, "UTF-8", policy);

		Enumeration filesname = mulit.getFileNames();
		while (filesname.hasMoreElements()) {
			String name = (String) filesname.nextElement();
			fileName = mulit.getFilesystemName(name);
			String contentType = mulit.getContentType(name);

			if (fileName != null) {
				fileCount++;
			}
			if (logger.isInfoEnabled()) {
				logger.info("文件名：" + fileName);
				logger.info("文件类型： " + contentType);
			}

		}
		if (logger.isInfoEnabled()) {
			logger.info("共上传" + fileCount + "个文件!");
		}
		return fileName;
	}

	/**
	 * 文件复制
	 * 
	 * @param src 源文件路径
	 * @param dest 目标文件路径
	 * 
	 * @throws FileNotFoundException 
	 */
	public static void cp(String src, String dest) {
		if (src == null || "".equals(src) || dest == null || "".equals(dest)) {
			System.out.println("源文件或目标文件路径空，未复制");
			return;
		}
		if (src.equals(dest)) {
			System.out.println("源文件和目标文件是相同，未复制");
			return;
		}
		File infile = new File(src);
		if (!infile.exists()) {
			System.out.println((String.format("源文件不存在：%s", src)));
			return;
		}
		File outfile = new File(dest);
		if (outfile.exists()) {
			outfile.delete();
		}

		cp(infile, outfile);
	}

	/**
	 * 文件复制
	 * 
	 * @param src 源文件
	 * @param dest 目标文件
	 */
	public static void cp(File src, File dest) {
		if (!src.exists()) {
			System.out.println((String.format("源文件不存在：%s", src)));
			return;
		}
		if (src.getAbsolutePath().equals(dest.getAbsolutePath())) {
			System.out.println("源文件和目标文件是相同，未复制");
			return;
		}
		BufferedInputStream in = null;
		BufferedOutputStream out = null;
		try {
			in = new BufferedInputStream(new FileInputStream(src));
			out = new BufferedOutputStream(new FileOutputStream(dest));

			cp(in, out);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
				out.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	/**
	 * 基于文件流
	 * 
	 * @param src 源文件流
	 * @param dest 目标文件流
	 * 
	 * @throws IOException
	 */
	public static void cp(InputStream src, OutputStream dest) throws IOException {
		synchronized (src) {
			synchronized (dest) {
				byte[] buffer = new byte[BLKSIZ];
				while (true) {
					int bytesRead = src.read(buffer);
					if (bytesRead == -1)
						break;
					dest.write(buffer, 0, bytesRead);
				}
			}
		}
	}
}
