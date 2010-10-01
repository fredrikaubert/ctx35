package no.parasit.x10.ctx35;

import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * @author fredrik
 */

public class Ctx35SerialTransmitter {
	private static final char LAST_CHARACTER_IN_A_TRANSMISSION = '#';
	private static final String ASCII_ENCODING = "ASCII";

	private CommPortIdentifier commPortIdentifier;
	private int baudRate = 19200;
	private SerialPort serialPort;

	private InputStream inputStream;
	private OutputStream outputStream;

	public String transmit(String transmission) {
		System.out.println(new Date() + " - Transmitting:" + transmission);
		transmitString(transmission);
		return readResponse();

	}

	private void transmitString(String transmission) {
		try {
			transmitBytes(transmission.getBytes(ASCII_ENCODING));
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	private void transmitBytes(byte... bytes) {
		for (int b : bytes) {
			writeToOutputStream(b);
		}
	}

	private void writeToOutputStream(int b) {
		try {
			outputStream.write(b);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private String readResponse() {
		String response = "";
		int read = 0;
		while (read != (int) LAST_CHARACTER_IN_A_TRANSMISSION) {
			read = readByteFromInputStream();
			response += byteToAsciiCharacter(read);
		}
		return response;
	}

	private String byteToAsciiCharacter(int r) {
		try {
			return new String(new byte[] { (byte) r }, ASCII_ENCODING);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	private int readByteFromInputStream() {
		try {
			waitForAvailableByteInInputStream();
			int fromImputStream = inputStream.read();
			return fromImputStream;
		} catch (IOException e) {
			throw new Ctx35ReadException(e);
		}
	}

	private void waitForAvailableByteInInputStream() throws IOException {
		int retries = 60;
		while (inputStream.available() == 0 && retries-- > 0) {
			threadSleep(50);
		}
		if (retries < 1) {
			System.out.println("No byte avaiable within timeout.");
			throw new Ctx35ReadException("No bytes avaiable");
		}
	}

	private void threadSleep(int sleepTimeInMillis) {
		try {
			Thread.sleep(sleepTimeInMillis);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	public void destroy() {
		serialPort.close();
	}

	public void init() {
		if (commPortIdentifier.isCurrentlyOwned()) {
			throw new RuntimeException("commport " + commPortIdentifier.getName() + " by " + commPortIdentifier.getCurrentOwner());
		}

		try {
			serialPort = (SerialPort) commPortIdentifier.open(this.getClass().getName(), 2000);
			serialPort.setSerialPortParams(baudRate, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);

			inputStream = serialPort.getInputStream();
			outputStream = serialPort.getOutputStream();

		} catch (PortInUseException e) {
			throw new RuntimeException(e);
		} catch (UnsupportedCommOperationException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		System.out.println("Init completed");
	}

	public void setCommPortIdentifier(CommPortIdentifier commPortIdentifier) {
		this.commPortIdentifier = commPortIdentifier;
	}

}
