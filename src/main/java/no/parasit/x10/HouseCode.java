package no.parasit.x10;

public class HouseCode {
	private char houseCode;
	public HouseCode(char houseCode) {
		if (houseCode < 'A' || houseCode > 'P') {
			throw new IllegalArgumentException("House code must be between A and P");
		}
		this.houseCode = houseCode;
	}
	public char getHouseCode() {
		return houseCode;
	}
	
	@Override
	public String toString() {
		return "houseCode=" + houseCode;
	}
}
