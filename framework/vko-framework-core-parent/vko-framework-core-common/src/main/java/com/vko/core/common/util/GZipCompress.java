package com.vko.core.common.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class GZipCompress {

	public static byte[] doCompress(byte[] inFileName) {
		try {
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			GZIPOutputStream out = new GZIPOutputStream(outputStream);
			out.write(inFileName);
			out.finish();
			out.close();
			return outputStream.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new byte[0];
	}

	public static byte[] doUncompress(byte[] inFileName) {

		try {
			ByteArrayInputStream inputStream = new ByteArrayInputStream(inFileName);
			GZIPInputStream in = new GZIPInputStream(inputStream);

			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			byte[] buf = new byte[1024];
			int len = 0;
			while ((len = in.read(buf)) > 0) {
				outputStream.write(buf, 0, len);
			}
			in.close();
			outputStream.close();
			return outputStream.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new byte[0];
	}

}
