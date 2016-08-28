package com.mashup.boracay.common.util;

import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.math.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommonUtil {

	protected static Logger logger = LoggerFactory.getLogger(CommonUtil.class);


	public static String messageToCryp(String message) throws Exception {

		if (message == null || message.trim().equals("")) {
			return "";
		}

		MessageDigest md;
		byte[] mb = null;

		try {
			md = MessageDigest.getInstance("SHA-512");

			md.update(message.getBytes());
			mb = md.digest();

		} catch (NoSuchAlgorithmException nsae) {
			throw nsae;
		} catch (Exception e) {
			throw e;
		}

		String out = "";

		try {
			for (int i = 0; i < mb.length; i++) {
				byte temp = mb[i];
				String s = Integer.toHexString(new Byte(temp));
				while (s.length() < 2) {
					s = "0" + s;
				}
				s = s.substring(s.length() - 2);
				out += s;
			}
		} catch (Exception e) {
			throw e;
		}

		return out;
	}
	
	
	  public static String getContentDispositionString(HttpServletRequest request, String filename) {
	        String disposition = "";
	        {
	            try {
	                if (request.getHeader("User-Agent").contains("MSIE")) {
	                    disposition = "attachment; filename=" + URLEncoder.encode(filename, "UTF-8").replaceAll("\\+", "%20") + ";";
	                } else {
	                    disposition = "attachment; filename=\"" + new String(filename.getBytes("UTF-8"), "ISO-8859-1") + "\"";
	                }
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }
	        return disposition;
	    }

	  public static String generatorNumber() {
		  
		  Random random = new Random();
		  StringBuffer buffer = new StringBuffer();
		  
		  for(int i = 0; i < 10; i++) {
			  if(i == 2 || i == 5) {
				  buffer.append("-");
			  } else {
				  buffer.append(String.valueOf(RandomUtils.nextInt(random, 10)));
			  }
		  }
		  return buffer.toString();
	  }
}
