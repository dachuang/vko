package com.vko.core.web.html;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class ResponseHtmlWrapper extends HttpServletResponseWrapper implements ResponseHtml {
	private PrintWriter writer;
	private ServletOutputStream stream;
	private StringWriter htmlWriter;

	public ResponseHtmlWrapper(HttpServletResponse response) {
		super(response);
	}

	@Override
	public ServletOutputStream getOutputStream() throws IOException {
		if (stream != null) {
			return stream;
		}
		if (writer != null) {
			throw new IOException("can not getOutputStream after getWriter");
		}
		stream = super.getOutputStream();
		return stream;
	}

	@Override
	public PrintWriter getWriter() throws IOException {
		if (writer != null) {
			return writer;
		}
		if (stream != null) {
			if (writer != null) {
				throw new IOException("can not getWriter after getOutputStream");
			}
		}
		// 判断是否需要进行静态生成
		if (HtmlFlag.getFlag().isCache()) {
			htmlWriter = new StringWriter(1000);
			writer = new PrintWriterWrapper(super.getWriter(), htmlWriter);
		} else {
			writer = super.getWriter();
		}
		return writer;
	}

	@Override
	public String getHtml() {
		if (htmlWriter != null) {
			return htmlWriter.toString();
		}
		return null;
	}
}
