/**
 * WaterMarker.java
 * com.vko.imports.watermark
 * Copyright (c) 2014, 北京微课创景教育科技有限公司版权所有.
*/
package com.vko.core.common.watermark;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

/**
 * 图片加水印 
 * 
 * @author	丁辰叶
 * @date	2014-7-26
 */
public class WaterMarker {

	/**
	 * 0.0 -> 1.0  透明 -> 不透明
	 */
	private float alpha = 0.25f;// 透明度
	private int border = 2;// 边距
	/**
	 * 147-左上角，258-居中，369-右下角
	 */
	private int place = 1;// 水印位置

	// 默认水印图
	private File markFile = new File("/home/static/logo.png");
	private Image markImg = null;
	private int wMark = 0;
	private int hMark = 0;

	public WaterMarker() {
		initMark();
	}

	public WaterMarker(File markFile) {
		this.markFile = markFile;
		initMark();
	}

	private void initMark() {
		try {
			markImg = ImageIO.read(markFile);
			wMark = markImg.getWidth(null);
			hMark = markImg.getHeight(null);
		} catch (Exception e) {
		}
	}

	/**
	 * 给文件加水印，如果指定的生成水印路径已存在会删除
	 * 
	 * @param srcPath 源文件路径
	 * @param tarPath 生成水印文件路径，【传空则直接在源文件上加水印】
	 */
	public void making(String srcPath, String tarPath) {
		if (srcPath == null || "".equals(srcPath)) {
			System.out.println("原图地址空");
			return;
		}
		if (tarPath == null || "".equals(tarPath)) {
			tarPath = srcPath;
		}
		File srcFile = new File(srcPath);
		File tarFile = new File(tarPath);
		if (!srcFile.exists()) {
			System.out.println(String.format("原图文件不存在：%s", srcPath));
			return;
		}
		if (!tarPath.equals(srcPath) && tarFile.exists()) {
			tarFile.delete();
		}
		making(srcFile, tarFile);
	}

	/**
	 * 给文件加水印，gif加白底
	 * 
	 * @param srcFile 源文件
	 * @param tarFile 生成水印文件
	 */
	public void making(File srcFile, File tarFile) {
		if (!srcFile.exists()) {
			System.out.println(String.format("原图文件不存在：%s", srcFile.getAbsoluteFile()));
			return;
		}
		if (!markFile.exists()) {
			System.out.println(String.format("水印文件不存在：%s", markFile.getAbsoluteFile()));
			return;
		}
		PicFmtDetection pfd = new PicFmtDetection(srcFile);
		try {
			Image srcImg = ImageIO.read(srcFile);
			int wSrc = srcImg.getWidth(null);
			int hSrc = srcImg.getHeight(null);

			if ((wSrc >= (wMark * 3) && hSrc >= (hMark + (border * 2)))
					|| (hSrc >= (hMark * 3) && wSrc >= (wMark + (border * 2)))) {
				BufferedImage image = new BufferedImage(wSrc, hSrc, pfd.getImageType());
				Graphics2D g = image.createGraphics();

				if (pfd.getFormatName().equals("gif")) {
					// gif图片需要画一个白底再打水印
					g.setColor(Color.WHITE);
					g.fillRect(0, 0, wSrc, hSrc);
				}

				// 画原图
				g.drawImage(srcImg, 0, 0, null);

				// 画水印且设置透明度
				g.setComposite(AlphaComposite.getInstance(3, alpha));
				g.drawImage(markImg, markXPlace(wSrc, wMark, place, border), markYPlace(hSrc, hMark, place, border),
						null);
				g.dispose();

				ImageIO.write(image, pfd.getFormatName(), tarFile);
				System.out.println("水印添加成功！");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 计算X轴位置
	 * 
	 * @param pictWidth 原图宽度
	 * @param markWidth 水印图宽度
	 * @param place 位置
	 * @param border 边宽
	 * @return
	 */
	private int markXPlace(int pictWidth, int markWidth, int place, int border) {
		if (((pictWidth - markWidth)) / 2 > border) {
			switch (place) {
			case 1:
				return (border);
			case 2:
				return ((pictWidth - markWidth) / 2);
			case 3:
				return (pictWidth - markWidth - border);
			case 4:
				return (border);
			case 5:
				return ((pictWidth - markWidth) / 2);
			case 6:
				return (pictWidth - markWidth - border);
			case 7:
				return (border);
			case 8:
				return ((pictWidth - markWidth) / 2);
			case 9:
				return (pictWidth - markWidth - border);
			default:
				return ((pictWidth - markWidth) / 2);
			}
		} else {
			return (pictWidth - markWidth) / 2;
		}
	}

	/**
	 * 计算Y轴位置
	 * 
	 * @param pictHeight 原图高度
	 * @param markHeight 水印图高度
	 * @param place 位置
	 * @param border 边宽
	 * @return
	 */
	private int markYPlace(int pictHeight, int markHeight, int place, int border) {
		if (((pictHeight - markHeight)) / 2 > border) {
			switch (place) {
			case 1:
				return (border);
			case 2:
				return ((pictHeight - markHeight) / 2);
			case 3:
				return (pictHeight - markHeight - border);
			case 4:
				return (border);
			case 5:
				return ((pictHeight - markHeight) / 2);
			case 6:
				return (pictHeight - markHeight - border);
			case 7:
				return (border);
			case 8:
				return ((pictHeight - markHeight) / 2);
			case 9:
				return (pictHeight - markHeight - border);
			default:
				return ((pictHeight - markHeight) / 2);
			}
		} else {
			return (pictHeight - markHeight) / 2;
		}
	}

	/**
	 * 像素监测，小于水印图，不处理
	 * 
	 * @param path 待检测图片路径
	 * @return
	 */
	public boolean pixCheck(String path) {
		try {
			Image iSrc = ImageIO.read(new File(path));
			int wSrc = iSrc.getWidth(null);
			int hSrc = iSrc.getHeight(null);
			if ((hSrc >= (this.hMark * 3) && wSrc >= (this.wMark * 2))
					|| (wSrc >= (this.wMark * 3) && hSrc >= (this.hMark * 2))) {
				return true;
			} else {
				System.out.println(String.format("--忽略像素低：%s，%dx%d", path, wSrc, hSrc));
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 通过文件的绝对路径提取文件目录信息
	 * 
	 * @param file 绝对路径
	 * @return
	 */
	public String pickPath(String file) {
		int i = file.lastIndexOf("/") + 1;
		return file.substring(0, i);
	}

	/**
	 * 通过文件绝对路径提取文件名信息
	 * 
	 * @param file 绝对路径
	 * @return
	 */
	public String pickName(String file) {
		int i = file.lastIndexOf("/") + 1;
		return file.substring(i);
	}

	/**
	 * 图片类型判断
	 * 
	 * @author	丁辰叶
	 * @date	2014-7-31
	 */
	static class PicFmtDetection {
		int imageType;// 画底布时的类型
		String formatName; // 输出格式

		public PicFmtDetection(File file) {
			boolean isGif = file.getName().endsWith("gif");
			boolean isJpg = file.getName().endsWith("jpg") || file.getName().endsWith("jpeg")
					|| file.getName().endsWith("bmp");
			boolean isPng = file.getName().endsWith("png");
			if (isGif) {
				this.imageType = 1;
				this.formatName = "gif";
			} else if (isJpg) {
				this.imageType = 1;
				this.formatName = "jpg";
			} else if (isPng) {
				this.imageType = 6;
				this.formatName = "png";
			}
		}

		public int getImageType() {
			return imageType;
		}

		public String getFormatName() {
			return formatName;
		}

	}

}
