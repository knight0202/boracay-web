package com.mashup.boracay.login.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordEncoder implements org.springframework.security.crypto.password.PasswordEncoder {

	@Override
	public String encode(CharSequence rawPassword) {
		if (rawPassword == null) {
			throw new NullPointerException();
		}

		MessageDigest md;
		byte[] mb = null;

		try {
			md = MessageDigest.getInstance("SHA-512");

			md.update(rawPassword.toString().getBytes());
			mb = md.digest();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String out = "";

		for (int i = 0; i < mb.length; i++) {
			byte temp = mb[i];
			String s = Integer.toHexString(new Byte(temp));
			while (s.length() < 2) {
				s = "0" + s;
			}
			s = s.substring(s.length() - 2);
			out += s;
		}

		return out;
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		if (encodedPassword == null || rawPassword == null) {
			return false;
		}

		if (!encodedPassword.equals(encode(rawPassword))) {
			return false;
		}

		return true;
	}

}
