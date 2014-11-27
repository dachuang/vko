package cn.vko.hessian.test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EntityUtil {

	private String type_char = "char";
	private String type_date = "date";
	private String type_timestamp = "timestamp";
	private String type_int = "int";
	private String type_bigint = "bigint";
	private String type_text = "text";
	private String type_bit = "bit";
	private String type_decimal = "decimal";

	private String bean_path = "d:/entity_bean";
	private String mapper_path = "d:/entity_mapper";

	private String bean_package = "cn.vko.zuoye.entity";
	private String mapper_package = "cn.vko.zuoye.mapper";
	private String mapper_extends = "SqlMapper";

	private String driverName = "com.mysql.jdbc.Driver";
	private String user = "delete7";
	private String password = "vko999999";
	private String url = "jdbc:mysql://192.168.1.246:3306/vko7?characterEncoding=utf8";

	private String tableName;
	private String beanName;
	private String mapperName;

	private Connection conn;

	private void init() throws ClassNotFoundException, SQLException {
		Class.forName(driverName);
		conn = DriverManager.getConnection(url, user, password);
	}

	private List<String> getTables() throws SQLException {
		List<String> tables = new ArrayList<String>();
		PreparedStatement pstate = conn.prepareStatement("show tables");
		ResultSet results = pstate.executeQuery();
		while (results.next()) {
			String tableName = results.getString(1);
			if (tableName.toLowerCase().startsWith("zy_")) {
				tables.add(tableName);
			}
		}
		return tables;
	}

	private void processTable(String table) {
		StringBuffer sb = new StringBuffer(table.length());
		table = table.toLowerCase();
		String[] tables = table.split("_");
		String temp = null;
		for (int i = 1; i < tables.length; i++) {
			temp = tables[i].trim();
			sb.append(temp.substring(0, 1).toUpperCase()).append(temp.substring(1));
		}
		beanName = sb.toString();
		mapperName = beanName + "Mapper";
	}

	private String processType(String type) {
		if (type.indexOf(type_char) > -1) {
			return "String";
		} else if (type.indexOf(type_bigint) > -1) {
			return "Long";
		} else if (type.indexOf(type_int) > -1) {
			return "Integer";
		} else if (type.indexOf(type_date) > -1) {
			return "java.util.Date";
		} else if (type.indexOf(type_text) > -1) {
			return "String";
		} else if (type.indexOf(type_timestamp) > -1) {
			return "java.util.Date";
		} else if (type.indexOf(type_bit) > -1) {
			return "Boolean";
		} else if (type.indexOf(type_decimal) > -1) {
			return "java.math.BigDecimal";
		}
		return null;
	}

	private String processField(String field) {
		StringBuffer sb = new StringBuffer(field.length());
		//field = field.toLowerCase();
		String[] fields = field.split("_");
		String temp = null;
		sb.append(fields[0]);
		for (int i = 1; i < fields.length; i++) {
			temp = fields[i].trim();
			sb.append(temp.substring(0, 1).toUpperCase()).append(temp.substring(1));
		}
		return sb.toString();
	}

	private String processResultMapId(String beanName) {
		return beanName.substring(0, 1).toLowerCase() + beanName.substring(1);
	}

	private void outputAuthor(BufferedWriter bw, String text) throws IOException {
		bw.newLine();
		bw.newLine();
		bw.write("/**");
		bw.newLine();
		bw.write("**/");
		bw.newLine();
		bw.newLine();
	}

	//	private void outputBaseBean() throws IOException {
	//		File folder = new File(bean_path);
	//		if (!folder.exists()) {
	//			folder.mkdir();
	//		}
	//		File beanFile = new File(bean_path, "BaseBean.java");
	//		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(beanFile)));
	//		bw.write("package " + bean_package + ";");
	//		bw.newLine();
	//		bw.write("import java.io.Serializable;");
	//		this.outputAuthor(bw, "排序基类");
	//		bw.newLine();
	//		bw.write("@SuppressWarnings(\"serial\")");
	//		bw.newLine();
	//		bw.write("public class BaseBean implements Serializable{");
	//		bw.newLine();
	//		bw.write("\t/**排序**/");
	//		bw.newLine();
	//		bw.write("\tprivate String orderSql;");
	//		bw.newLine();
	//		// 生成get 和 set方法
	//
	//		bw.write("\tpublic String getOrderSql(){");
	//		bw.newLine();
	//		bw.write("\t\treturn this.orderSql;");
	//		bw.newLine();
	//		bw.write("\t}");
	//		bw.newLine();
	//		bw.newLine();
	//
	//		bw.write("\tpublic void setOrderSql(String orderSql){");
	//		bw.newLine();
	//		bw.write("\t\tthis.orderSql=orderSql;");
	//		bw.newLine();
	//		bw.write("\t}");
	//		bw.newLine();
	//		bw.newLine();
	//
	//		bw.write("}");
	//		bw.newLine();
	//		bw.flush();
	//		bw.close();
	//	}

	private void outputBean(List<String> columns, List<String> types, List<String> comments) throws IOException {
		File folder = new File(bean_path);
		if (!folder.exists()) {
			folder.mkdir();
		}
		File beanFile = new File(bean_path, beanName + ".java");
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(beanFile)));
		bw.write("package " + bean_package + ";");
		//bw.newLine();
		//bw.write("import java.util.Date;");
		bw.newLine();
		bw.write("import lombok.Data;");
		//		bw.write("import javax.persistence.Entity;");
		this.outputAuthor(bw, beanName + "实体类");
		bw.newLine();
		//bw.write("@SuppressWarnings(\"serial\")");
		//bw.newLine();
		//		bw.write("@Entity");
		bw.write("@Data");
		bw.newLine();
		bw.write("public class " + beanName + " {");
		bw.newLine();
		int size = columns.size();
		for (int i = 0; i < size; i++) {
			bw.write("\t/**" + comments.get(i) + "**/");
			bw.newLine();
			bw.write("\tprivate " + this.processType(types.get(i)) + " " + this.processField(columns.get(i)) + ";");
			bw.newLine();
		}
		bw.newLine();
		// 生成get 和 set方法
		//		String tempField = null;
		//		String _tempField = null;
		//		String tempType = null;
		//		for (int i = 0; i < size; i++) {
		//			tempType = this.processType(types.get(i));
		//			_tempField = this.processField(columns.get(i));
		//			tempField = _tempField.substring(0, 1).toUpperCase() + _tempField.substring(1);
		//			bw.newLine();
		//			bw.write("\tpublic void set" + tempField + "(" + tempType + " _" + _tempField + "){");
		//			bw.newLine();
		//			bw.write("\t\tthis." + _tempField + "=_" + _tempField + ";");
		//			bw.newLine();
		//			bw.write("\t}");
		//			bw.newLine();
		//
		//			bw.write("\tpublic " + tempType + " get" + tempField + "(){");
		//			bw.newLine();
		//			bw.write("\t\treturn this." + _tempField + ";");
		//			bw.newLine();
		//			bw.write("\t}");
		//			bw.newLine();
		//		}
		bw.newLine();
		bw.write("}");
		bw.newLine();
		bw.flush();
		bw.close();
	}

	private void outputMapper() throws IOException {
		File folder = new File(mapper_path);
		if (!folder.exists()) {
			folder.mkdir();
		}
		File mapperFile = new File(mapper_path, mapperName + ".java");
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(mapperFile)));
		bw.write("package " + mapper_package + ";");
		bw.newLine();
		bw.write("import " + bean_package + "." + beanName + ";");
		this.outputAuthor(bw, mapperName + "数据库操作接口类");
		bw.newLine();
		bw.write("public interface " + mapperName + " extends " + mapper_extends + "<" + beanName + "> {");
		bw.newLine();
		bw.newLine();
		bw.write("}");
		bw.flush();
		bw.close();
	}

	private void outputMapperXml(List<String> columns, List<String> types, List<String> comments) throws IOException {
		File folder = new File(mapper_path);
		if (!folder.exists()) {
			folder.mkdir();
		}
		File mapperXmlFile = new File(mapper_path, mapperName + ".xml");
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(mapperXmlFile)));
		bw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		bw.newLine();
		bw.write("<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" ");
		bw.newLine();
		bw.write("    \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">");
		bw.newLine();
		bw.write("<mapper namespace=\"" + mapper_package + "." + mapperName + "\">");
		bw.newLine();
		bw.newLine();
		bw.write("\t<!--实体映射-->");
		bw.newLine();
		bw.write("\t<resultMap id=\"" + this.processResultMapId(beanName) + "ResultMap\" type=\"" + beanName + "\">");
		bw.newLine();
		bw.write("\t\t<!--" + comments.get(0) + "-->");
		bw.newLine();
		bw.write("\t\t<id property=\"" + this.processField(columns.get(0)) + "\" column=\"" + columns.get(0) + "\" />");
		bw.newLine();
		int size = columns.size();
		for (int i = 1; i < size; i++) {
			bw.write("\t\t<!--" + comments.get(i) + "-->");
			bw.newLine();
			bw.write("\t\t<result property=\"" + this.processField(columns.get(i)) + "\" column=\"" + columns.get(i)
					+ "\" />");
			bw.newLine();
		}
		bw.write("\t</resultMap>");

		bw.newLine();
		bw.newLine();

		bw.write("\t<!--分页类型映射-->");
		bw.newLine();
		bw.write("\t<parameterMap type=\"PageParam\" id=\"pageParamMap\">");
		bw.newLine();

		bw.write("\t\t<parameter property=\"t\" resultMap=\"" + this.processResultMapId(beanName) + "ResultMap\"/>");
		bw.newLine();

		bw.write("\t\t<parameter property=\"start\" javaType=\"int\"/>");
		bw.newLine();

		bw.write("\t\t<parameter property=\"num\" javaType=\"int\"/>");
		bw.newLine();

		bw.write("\t</parameterMap>");
		bw.newLine();
		bw.newLine();

		// 下面开始写SqlMapper中的方法
		this.outputSqlMapperMethod(bw, columns, types);

		bw.write("</mapper>");
		bw.flush();
		bw.close();
	}

	private void outputSqlMapperMethod(BufferedWriter bw, List<String> columns, List<String> types) throws IOException {
		int size = columns.size();
		// 写add - insert方法
		bw.write("\t<!--添加-->");
		bw.newLine();
		bw.write("\t<insert id=\"add\" parameterType=\"" + beanName + "\">");
		bw.newLine();
		bw.write("\t\tinsert into " + tableName);
		bw.newLine();
		bw.write(" \t\t(");
		for (int i = 1; i < size; i++) {
			bw.write(columns.get(i));
			if (i != size - 1) {
				bw.write(",");
			}
		}
		bw.write(") ");
		bw.newLine();
		bw.write("\t\tvalues ");
		bw.newLine();
		bw.write(" \t\t(");
		for (int i = 1; i < size; i++) {
			bw.write("#{" + this.processField(columns.get(i)) + "}");
			if (i != size - 1) {
				bw.write(",");
			}
		}
		bw.write(") ");
		bw.newLine();
		bw.write("\t</insert>");
		bw.newLine();
		bw.newLine();
		// add insert写入完毕

		// 写update 
		bw.write("\t<!--更新：只更新有值字段-->");
		bw.newLine();
		bw.write("\t<update id=\"update\" parameterType=\"" + beanName + "\">");
		bw.newLine();
		bw.write("\t\tupdate " + tableName + " set " + columns.get(0) + "=${" + this.processField(columns.get(0)) + "}");
		bw.newLine();
		String tempField = null;
		for (int i = 1; i < size; i++) {
			tempField = this.processField(columns.get(i));
			bw.write("\t\t<if test=\"" + tempField + "!=null\">");
			bw.newLine();
			bw.write("\t\t\t," + columns.get(i) + "=#{" + tempField + "}");
			bw.newLine();
			bw.write("\t\t</if>");
			bw.newLine();
		}
		bw.write("\t\twhere " + columns.get(0) + "=#{" + this.processField(columns.get(0)) + "}");
		bw.newLine();
		bw.write("\t</update>");
		bw.newLine();
		bw.newLine();
		// update写完

		// 写delete
		bw.write("\t<!--删除：根据主键编号删除-->");
		bw.newLine();
		bw.write("\t<delete id=\"delete\" parameterType=\"int\">");
		bw.newLine();
		bw.write("\t\tdelete from " + tableName);
		bw.newLine();
		bw.write("\t\twhere " + columns.get(0) + "=#{" + this.processField(columns.get(0)) + "}");
		bw.newLine();
		bw.write("\t</delete>");
		bw.newLine();
		bw.newLine();
		// delete写完

		// 写get
		bw.write("\t<!--查找：根据主键编号查找-->");
		bw.newLine();
		bw.write("\t<select id=\"get\" parameterType=\"int\" resultMap=\"" + this.processResultMapId(beanName)
				+ "ResultMap\">");
		bw.newLine();
		bw.write("\t\tselect * from " + tableName);
		bw.newLine();
		bw.write("\t\twhere " + columns.get(0) + "=#{" + this.processField(columns.get(0)) + "}");
		bw.newLine();
		bw.write("\t</select>");
		bw.newLine();
		bw.newLine();
		// get写完

		// 写findEqual
		bw.write("\t<!--==查找，匹配有值字段-->");
		bw.newLine();
		bw.write("\t<select id=\"findEqual\" parameterType=\"" + beanName + "\" resultMap=\""
				+ this.processResultMapId(beanName) + "ResultMap\">");
		bw.newLine();
		bw.write("\t\tselect * from " + tableName + "where 1=1 ");
		bw.newLine();
		tempField = null;
		for (int i = 0; i < size; i++) {
			tempField = this.processField(columns.get(i));
			bw.write("\t\t<if test=\"" + tempField + "!=null\">");
			bw.newLine();
			bw.write("\t\t\tand " + columns.get(i) + "=#{" + tempField + "}");
			bw.newLine();
			bw.write("\t\t</if>");
			bw.newLine();
		}
		bw.write("\t\t<if test=\"orderSql!=null\">");
		bw.newLine();
		bw.write("\t\t\t #{orderSql}");
		bw.newLine();
		bw.write("\t\t</if>");
		bw.newLine();
		bw.write("\t</select>");
		bw.newLine();
		bw.newLine();
		// findEqual写完

		// 写findLike
		bw.write("\t<!--like查找，匹配有值字段-->");
		bw.newLine();
		bw.write("\t<select id=\"findLike\" parameterType=\"" + beanName + "\" resultMap=\""
				+ this.processResultMapId(beanName) + "ResultMap\">");
		bw.newLine();
		bw.write("\t\tselect * from " + tableName + "where 1=1 ");
		bw.newLine();
		tempField = null;
		for (int i = 0; i < size; i++) {
			tempField = this.processField(columns.get(i));
			bw.write("\t\t<if test=\"" + tempField + "!=null\">");
			bw.newLine();
			bw.write("\t\t\tand " + columns.get(i) + " like %#{" + tempField + "}%");
			bw.newLine();
			bw.write("\t\t</if>");
			bw.newLine();
		}
		bw.write("\t\t<if test=\"orderSql!=null\">");
		bw.newLine();
		bw.write("\t\t\t #{orderSql}");
		bw.newLine();
		bw.write("\t\t</if>");
		bw.newLine();
		bw.write("\t</select>");
		bw.newLine();
		bw.newLine();
		// findLike写完

		// 写getCountLike
		bw.write("\t<!--查询条数，匹配有值字段-->");
		bw.newLine();
		bw.write("\t<select id=\"getCountLike\" parameterType=\"" + beanName + "\" resultType=\"int\">");
		bw.newLine();
		bw.write("\t\tselect count(*) from " + tableName + "where 1=1 ");
		bw.newLine();
		tempField = null;
		for (int i = 0; i < size; i++) {
			tempField = this.processField(columns.get(i));
			bw.write("\t\t<if test=\"" + tempField + "!=null\">");
			bw.newLine();
			bw.write("\t\t\tand " + columns.get(i) + " like %#{" + tempField + "}%");
			bw.newLine();
			bw.write("\t\t</if>");
			bw.newLine();
		}
		bw.write("\t</select>");
		bw.newLine();
		bw.newLine();
		// getCountLike写完

		// 写getCountEqual
		bw.write("\t<!--查询条数，匹配有值字段-->");
		bw.newLine();
		bw.write("\t<select id=\"getCountEqual\" parameterType=\"" + beanName + "\" resultType=\"int\">");
		bw.newLine();
		bw.write("\t\tselect count(*) from " + tableName + "where 1=1 ");
		bw.newLine();
		tempField = null;
		for (int i = 0; i < size; i++) {
			tempField = this.processField(columns.get(i));
			bw.write("\t\t<if test=\"" + tempField + "!=null\">");
			bw.newLine();
			bw.write("\t\t\tand " + columns.get(i) + "=#{" + tempField + "}");
			bw.newLine();
			bw.write("\t\t</if>");
			bw.newLine();
		}
		bw.write("\t</select>");
		bw.newLine();
		bw.newLine();
		// getCountEqual写完

		// 写listEqual
		bw.write("\t<!--==查找，匹配有值字段-->");
		bw.newLine();
		bw.write("\t<select id=\"listEqual\" parameterType=\"" + beanName + "\" resultMap=\""
				+ this.processResultMapId(beanName) + "ResultMap\">");
		bw.newLine();
		bw.write("\t\tselect * from " + tableName + "where 1=1 ");
		bw.newLine();
		tempField = null;
		for (int i = 0; i < size; i++) {
			tempField = this.processField(columns.get(i));
			bw.write("\t\t<if test=\"" + tempField + "!=null\">");
			bw.newLine();
			bw.write("\t\t\tand " + columns.get(i) + "=#{" + tempField + "}");
			bw.newLine();
			bw.write("\t\t</if>");
			bw.newLine();
		}
		bw.write("\t\t<if test=\"orderSql!=null\">");
		bw.newLine();
		bw.write("\t\t\t #{orderSql}");
		bw.newLine();
		bw.write("\t\t</if>");
		bw.newLine();
		bw.write("\t</select>");
		bw.newLine();
		bw.newLine();
		// listEqual写完

		// 写listLike
		bw.write("\t<!--like查找，匹配有值字段-->");
		bw.newLine();
		bw.write("\t<select id=\"listLike\" parameterType=\"" + beanName + "\" resultMap=\""
				+ this.processResultMapId(beanName) + "ResultMap\">");
		bw.newLine();
		bw.write("\t\tselect * from " + tableName + "where 1=1 ");
		bw.newLine();
		tempField = null;
		for (int i = 0; i < size; i++) {
			tempField = this.processField(columns.get(i));
			bw.write("\t\t<if test=\"" + tempField + "!=null\">");
			bw.newLine();
			bw.write("\t\t\tand " + columns.get(i) + " like %#{" + tempField + "}%");
			bw.newLine();
			bw.write("\t\t</if>");
			bw.newLine();
		}
		bw.write("\t\t<if test=\"orderSql!=null\">");
		bw.newLine();
		bw.write("\t\t\t #{orderSql}");
		bw.newLine();
		bw.write("\t\t</if>");
		bw.newLine();
		bw.write("\t</select>");
		bw.newLine();
		bw.newLine();
		// listLike写完

		// 写pageListEqual
		bw.write("\t<!--==分页查找，匹配有值字段-->");
		bw.newLine();
		bw.write("\t<select id=\"listEqual\" parameterType=\"" + beanName + "\" resultMap=\""
				+ this.processResultMapId(beanName) + "ResultMap\">");
		bw.newLine();
		bw.write("\t\tselect * from " + tableName + "where 1=1 ");
		bw.newLine();
		tempField = null;
		for (int i = 0; i < size; i++) {
			tempField = this.processField(columns.get(i));
			bw.write("\t\t<if test=\"" + tempField + "!=null\">");
			bw.newLine();
			bw.write("\t\t\tand " + columns.get(i) + "=#{" + tempField + "}");
			bw.newLine();
			bw.write("\t\t</if>");
			bw.newLine();
		}
		bw.write("\t\t<if test=\"orderSql!=null\">");
		bw.newLine();
		bw.write("\t\t\t #{orderSql}");
		bw.newLine();
		bw.write("\t\t</if>");
		bw.newLine();
		bw.write("\t\t\tlimit #{start},#{num}");
		bw.newLine();
		bw.write("\t</select>");

		bw.newLine();
		bw.newLine();
		// pageListEqual写完

		// 写pageListLike
		bw.write("\t<!--like分页查找，匹配有值字段-->");
		bw.newLine();
		bw.write("\t<select id=\"listLike\" parameterType=\"" + beanName + "\" resultMap=\""
				+ this.processResultMapId(beanName) + "ResultMap\">");
		bw.newLine();
		bw.write("\t\tselect * from " + tableName + "where 1=1 ");
		bw.newLine();
		tempField = null;
		for (int i = 0; i < size; i++) {
			tempField = this.processField(columns.get(i));
			bw.write("\t\t<if test=\"" + tempField + "!=null\">");
			bw.newLine();
			bw.write("\t\t\tand " + columns.get(i) + " like %#{" + tempField + "}%");
			bw.newLine();
			bw.write("\t\t</if>");
			bw.newLine();
		}
		bw.write("\t\t<if test=\"orderSql!=null\">");
		bw.newLine();
		bw.write("\t\t\t #{orderSql}");
		bw.newLine();
		bw.write("\t\t</if>");
		bw.newLine();
		bw.write("\t\t\tlimit #{start},#{num}");
		bw.newLine();
		bw.write("\t</select>");
		bw.newLine();
		bw.newLine();
		// pageListLike写完

		//bw.write("");
		bw.newLine();
	}

	public void generate() throws ClassNotFoundException, SQLException, IOException {
		this.init();
		String prefix = "show full fields from ";
		List<String> columns = null;
		List<String> types = null;
		List<String> comments = null;
		PreparedStatement pstate = null;
		List<String> tables = this.getTables();
		for (String table : tables) {
			columns = new ArrayList<String>();
			types = new ArrayList<String>();
			comments = new ArrayList<String>();
			pstate = conn.prepareStatement(prefix + table);
			ResultSet results = pstate.executeQuery();
			while (results.next()) {
				columns.add(results.getString("FIELD"));
				types.add(results.getString("TYPE"));
				comments.add(results.getString("COMMENT"));
			}
			tableName = table;
			this.processTable(table);
			//			this.outputBaseBean();
			this.outputBean(columns, types, comments);
			this.outputMapper();
			this.outputMapperXml(columns, types, comments);
		}
		conn.close();
	}

	public static void main(String[] args) {
		try {
			new EntityUtil().generate();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
