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
		if ( !isUnitCode( unitCode )) {
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
	
	public static boolean isUnitCode(int unitCodeCandidate) {
		return !(unitCodeCandidate < 1 || unitCodeCandidate > 16);
	}
	
	public static boolean isUnitCode(String unitCodeCandidate) {
		try {
			return isUnitCode( Integer.parseInt( unitCodeCandidate ) );
		} catch( NumberFormatException e ) {
			return false;
		}
	}
	
}
