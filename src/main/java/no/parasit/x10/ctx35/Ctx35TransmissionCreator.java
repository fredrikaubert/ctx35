package no.parasit.x10.ctx35;

import no.parasit.x10.Addressing;
import no.parasit.x10.Command;
import no.parasit.x10.UnitCode;

public class Ctx35TransmissionCreator {

	private final int maxNumberOfBytesInOneTransmission = 124;
	private Ctx35CheckSumCalculator ctx35CheckSumCalculator = new Ctx35CheckSumCalculator();
	private Ctx35CommandTranslator ctx35CommandTranslator = new Ctx35CommandTranslator();
	

	public String createTransmission(Addressing addressing, Command command, int commandRepeat) {
		if( command.isRequiresUnitCode() && addressing.getUnitCodes().length == 0) {
			throw new IllegalArgumentException("Command '" + command + "' requires alt least one unitcode. Addressing is:" + addressing);
		} 
		
		if( !command.isRequiresUnitCode() && addressing.getUnitCodes().length > 0) {
			throw new IllegalArgumentException("Command '" + command + "' requires no unitcodes. Addressing is:" + addressing);
		}
		
		String house = "" + addressing.getHouseCode().getHouseCode();

		String transmission = "$>28001";
		for (UnitCode unitCode : addressing.getUnitCodes()) {
			transmission += house + extractUnitCodeString(unitCode);
		}
		 transmission += " ";
		for(int i = 0; i < commandRepeat; i++) {
			transmission += house + ctx35CommandTranslator.toCtx35Command(command);
		}
		transmission += ctx35CheckSumCalculator.calculate(transmission);
		transmission += "#";

		if(transmission.length() > maxNumberOfBytesInOneTransmission) {
			throw new RuntimeException("Transmission too long:" + transmission.length() + " CTX35 will only accept 124 bytes.");
		}
		
		return transmission;
	}

	private String extractUnitCodeString(UnitCode unitCode) {
		String unit = Integer.toString(unitCode.getUnitCode());
		if (unit.length() == 1) {
			unit = "0" + unit;
		}
		return unit;
	}
}
