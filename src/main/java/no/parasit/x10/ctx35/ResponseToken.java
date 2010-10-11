package no.parasit.x10.ctx35;

import no.parasit.x10.Command;
import no.parasit.x10.Device;
import no.parasit.x10.HouseCode;
import no.parasit.x10.UnitCode;

public class ResponseToken
{
	private Ctx35CommandTranslator commandTranslator = new Ctx35CommandTranslator();
	String string;
	
	public ResponseToken(String string) {
		this.string = string;
	}
	
	public boolean isCommand() {
		return commandTranslator.fromCtx35Command( getNormalizedString().substring( 1 ) ) != null;
	}
	
	
	public Device getDevice() {
		return new Device(new HouseCode( getNormalizedString().charAt( 0 )), new UnitCode( getNormalizedString().substring( 1 ) ));
	}
	
	public Command getCommand() {
		return commandTranslator.fromCtx35Command( getNormalizedString().substring( 1 ) );
	}
	
	private String getNormalizedString() {
		return string.substring( 0, getEndIndexBeforeRepeat() );
	}
	
	private int getEndIndexBeforeRepeat()
	{
		return string.length() / getCommandRepeat();
	}

	public HouseCode getHouseCode() {
		return new HouseCode(getNormalizedString().charAt( 0 ));
	}
	
	public boolean isDevice() {
		if(getNormalizedString().length() != 3) {
			return false;
		}
		if(!HouseCode.isHouseCode( getNormalizedString().charAt( 0 ) )) {
			return false;
		}
		if(!UnitCode.isUnitCode( getNormalizedString().substring( 1 ) )) {
			return false;
		}
		return true;
	}

	public int getCommandRepeat()
	{
		if( string.length() < 5) return 1;
		
		char firstChar = string.charAt( 0 );
		int nextOccurence = string.indexOf( firstChar, 3 );
		if(string.length() % nextOccurence == 0) {
			return string.length() / nextOccurence;
		}
		return -1;
	}

	public String getString()
	{
		return string;
	}
	
	
}
