package no.parasit.x10;

public class HouseCode {
	private char houseCode;
	
	
	public HouseCode(char houseCode) {
		if (!isHouseCode( houseCode )) {
			throw new IllegalArgumentException("House code must be between A and P. Was:" + houseCode);
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
	
	public static boolean isHouseCode(char houseCodeCandiate) {
		return !(houseCodeCandiate < 'A' || houseCodeCandiate > 'P');
	}
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + houseCode;
		return result;
	}
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HouseCode other = (HouseCode) obj;
		if (houseCode != other.houseCode)
			return false;
		return true;
	}
}
