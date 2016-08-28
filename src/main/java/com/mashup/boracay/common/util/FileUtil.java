package com.mashup.boracay.common.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

public class FileUtil {
	private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);

	private static String imagePath = "";
	private static final String seperate = "-";
	private static File imageFolder = null;
	static {
		
		imagePath = System.getProperty("user.dir") + File.separator +"src" + File.separator + "main" + File.separator + "webapp" + File.separator + "resources" + File.separator;
		imageFolder = new File(imagePath);
	}

	public static String saveFile(String type, String companyId,
			MultipartFile file) throws IOException {
		if (!existCompanyFolder(type, companyId)) {
			makeCompanyFolder(type, companyId);
		}
		
		
		File convFile = new File(imagePath + type + File.separator + companyId + File.separator + file.getName() + seperate + UUID.randomUUID().toString() + "." + getFileExtension(file.getOriginalFilename()));
		file.transferTo(convFile);
		
		return convFile.getPath().substring(imagePath.length());
	}

	private static boolean existCompanyFolder(String type, String companyId) {
		File file = makeFile(imageFolder.getPath(), type, companyId);
		return file.exists() && file.isDirectory();
	}

	private static File makeFile(String path, String type, String key) {
		return new File(path + File.separator + type + File.separator + key);
	}
	
	private static File makeCompanyFolder(String type, String companyId)
			throws IOException {
		File file = makeFile(imageFolder.getPath(), type, companyId);
		file.mkdirs();
		return file;
	}
	
	private static String getFileExtension(String fileName) {
		int pos = fileName.lastIndexOf(".");
		return fileName.substring(pos + 1);
	}
}
