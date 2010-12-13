package no.parasit.x10;

/**
 * Represents an X10 device. E.g. "A1"
 * 
 * @author fredrik
 * 
 */


public class Device {
	HouseCode houseCode;
	UnitCode unitCode;
	public HouseCode getHouseCode()
	{
		return houseCode;
	}
	public UnitCode getUnitCode()
	{
		return unitCode;
	}
	public Device(HouseCode houseCode, UnitCode unitCode)
	{
		super();
		this.houseCode = houseCode;
		this.unitCode = unitCode;
	}
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((houseCode == null) ? 0 : houseCode.hashCode());
		result = prime * result + ((unitCode == null) ? 0 : unitCode.hashCode());
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
		Device other = (Device) obj;
		if (houseCode == null)
		{
			if (other.houseCode != null)
				return false;
		} else if (!houseCode.equals( other.houseCode ))
			return false;
		if (unitCode == null)
		{
			if (other.unitCode != null)
				return false;
		} else if (!unitCode.equals( other.unitCode ))
			return false;
		return true;
	}
	@Override
	public String toString()
	{
		return "Device [" + houseCode + ", " + unitCode + "]";
	}
	
	

}
