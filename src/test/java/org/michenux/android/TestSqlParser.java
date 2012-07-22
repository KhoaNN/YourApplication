package org.michenux.android;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.michenux.android.db.utils.SqlParser;

public class TestSqlParser {

	public static void testParser() throws Exception {
		InputStream is = new FileInputStream(new File("assets/sql/upgrade-102.sql"));
		try {
			for( String line: SqlParser.parseSqlFile(is)) {
				System.out.println(line);
			}
		} finally {
			is.close();
		}
	}
	
	public static void main(String[] args) {
		try {
			testParser();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
