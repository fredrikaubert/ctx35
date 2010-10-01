package no.parasit.x10.ctx35;

import java.io.UnsupportedEncodingException;

public class Ctx35CheckSumCalculator {

	public String calculate(String string) {
		try {
			int checksum = 0;
			byte[] bytes = string.getBytes("ASCII");
			for (byte b : bytes) {
				checksum += b;
			}
			checksum &= 0xff;
			String value = Integer.toHexString(checksum).toUpperCase();
			if (value.length() == 1) {
				value = "0" + value;
			}
			return value;
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}
}
