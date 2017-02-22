package com.milepost.system.util;

import java.io.File;

/**
 * 操作文件工具类
 * @author HRF
 */
public class FileUtil {
	
	public static boolean deleteFile(String sPath) {
		Boolean flag = Boolean.valueOf(false);
		File file = new File(sPath);

		if ((file.isFile()) && (file.exists())) {
			file.delete();
			flag = Boolean.valueOf(true);
		}
		return flag.booleanValue();
	}

	public static boolean deleteDirectory(String sPath) {
		if (!sPath.endsWith(File.separator)) {
			sPath = sPath + File.separator;
		}
		File dirFile = new File(sPath);

		if ((!dirFile.exists()) || (!dirFile.isDirectory())) {
			return false;
		}
		Boolean flag = Boolean.valueOf(true);

		File[] files = dirFile.listFiles();
		for (int i = 0; i < files.length; i++) {
			if (files[i].isFile()) {
				flag = Boolean.valueOf(deleteFile(files[i].getAbsolutePath()));
				if (!flag.booleanValue())
					break;
			} else {
				flag = Boolean.valueOf(deleteDirectory(files[i].getAbsolutePath()));
				if (!flag.booleanValue())
					break;
			}
		}
		if (!flag.booleanValue()) {
			return false;
		}

		return dirFile.delete();
	}
}