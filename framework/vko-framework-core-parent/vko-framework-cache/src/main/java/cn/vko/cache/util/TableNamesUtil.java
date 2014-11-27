package cn.vko.cache.util;

import java.io.StringReader;
import java.util.Collections;
import java.util.List;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.replace.Replace;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.update.Update;

/**
 * @author songrubo
 * @version 2013年11月1日 下午1:59:03
 */
public class TableNamesUtil {
	private static CCJSqlParserManager pm = new CCJSqlParserManager();

	public static List<String> getTableNames(String sql) {
		try {
			Statement statement = pm.parse(new StringReader(sql));
			CacheTablesNamesFinder finder = new CacheTablesNamesFinder();
			if (!finder.isCanCache()) {
				return Collections.emptyList();
			}
			if (statement instanceof Select) {
				return finder.getTableList((Select) statement);
			} else if (statement instanceof Update) {
				return finder.getTableList((Update) statement);
			} else if (statement instanceof Insert) {
				return finder.getTableList((Insert) statement);
			} else if (statement instanceof Delete) {
				return finder.getTableList((Delete) statement);
			} else if (statement instanceof Replace) {
				return finder.getTableList((Replace) statement);
			}
		} catch (JSQLParserException e) {
			e.printStackTrace();
		}
		return Collections.emptyList();
	}

	public static String getCacheKey(String sql) {
		List<String> tableNames = getTableNames(sql);
		return getCacheKey(tableNames);
	}

	public static String getCacheKey(List<String> tableNames) {
		if (tableNames == null) {
			return null;
		}
		StringBuilder builder = new StringBuilder();
		for (String name : tableNames) {
			String tempNmae = name.toUpperCase();
			// 视图
			if (tempNmae.startsWith("V_")) {
				return null;
			}
			builder.append(tempNmae);
			builder.append("-");
		}
		return builder.append(":").toString();
	}
}
