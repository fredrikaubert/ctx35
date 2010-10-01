package no.parasit.x10;

/**
 * Represents an X10 device. E.g. "A1"
 * 
 * @author fredrik
 * 
 */

@Deprecated
public class Device {
	private char house;
	private int unit;

	public Device(String houseCodeAndUnitCode) {
		setHouseCode(houseCodeAndUnitCode.substring(0, 1).toUpperCase());
		setUnitCode(houseCodeAndUnitCode.substring(1));

	}

	private void setUnitCode(String unitCode) {
		int unit = Integer.parseInt(unitCode);
		if (unit < 1 || unit > 16) {
			throw new IllegalArgumentException("Unit code must be between 1 and 16");
		}
		this.unit = unit;

	}

	private void setHouseCode(String houseCode) {
		char house = houseCode.charAt(0);
		if (house < 'A' || house > 'P') {
			throw new IllegalArgumentException("House code must be between A and P");
		}
		this.house = house;
	}

	public char getHouse() {
		return house;
	}

	public int getUnit() {
		return unit;
	}

}
