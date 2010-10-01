package no.parasit.x10;

public class UnitCode {
	private int unitCode;
	
	public UnitCode(String string) {
		setUnitCode(Integer.parseInt( string ));
	}
	
	public UnitCode(int unitCode) {
		setUnitCode( unitCode );
	}
	
	private void setUnitCode(int unitCode) {
		if (unitCode < 1 || unitCode > 16) {
			throw new IllegalArgumentException("Unit code must be between 1 and 16");
		}
		this.unitCode = unitCode;
	}
	
	public int getUnitCode() {
		return unitCode;
	}
	
	@Override
	public String toString() {
		return "unitCode=" + unitCode;
	}
}
