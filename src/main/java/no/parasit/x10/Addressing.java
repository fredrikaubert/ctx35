package no.parasit.x10;

import java.util.ArrayList;
import java.util.List;

public class Addressing {
	private HouseCode houseCode;
	private UnitCode[] unitCodes;
	public Addressing(HouseCode houseCode, UnitCode... unitCodes) {
		this.houseCode = houseCode;
		this.unitCodes = unitCodes;
	}
	
	public Addressing(char houseCode, int... unitCodes) {
		this.houseCode = new HouseCode(houseCode);
		List<UnitCode> unitCodeList = new ArrayList<UnitCode>();
		for (int unitCode : unitCodes) {
			unitCodeList.add(new UnitCode(unitCode));
		}
		this.unitCodes = unitCodeList.toArray(new UnitCode[]{});
	}
	
	
	public HouseCode getHouseCode() {
		return houseCode;
	}
	public UnitCode[] getUnitCodes() {
		return unitCodes;
	}
	
	@Override
	public String toString() {
		String returnValue = "Adressing:" + houseCode + " unitCodes=[";
		for (UnitCode unitCode : unitCodes) {
			returnValue += unitCode.getUnitCode() + ",";
		}
		returnValue += "]";
		return  returnValue;
	}
	
	
}
