package no.parasit.x10.ctx35;

import java.util.List;

import no.parasit.x10.Command;
import no.parasit.x10.Transmission;
import no.parasit.x10.ctx35.Ctx35QueryResponseParser;

import org.junit.Test;
import static org.junit.Assert.*;

public class CtxQueryResponseParserTest {
	
	private Ctx35QueryResponseParser responseParser = new Ctx35QueryResponseParser();

	@Test
	public void testParseQuery() {
		String response = "$<2800! P01P01 PONPON P02P02 PBGTPBGTPBGTPBGTPBGTPBGTPBGT P03P03 POFFPOFF P04P04 PDIMPDIMPDIMPDIMPDIMPDIM46#";
		responseParser.parseResponse(response);
		
		List<Transmission> transmissions = responseParser.parseResponse(response);
		assertEquals( 4, transmissions.size() );
		Transmission firstTransmission = transmissions.get( 0 );
		assertEquals('P', firstTransmission.getAddressing().getHouseCode().getHouseCode() );
		assertEquals(1, firstTransmission.getAddressing().getUnitCodes()[0].getUnitCode());
		assertEquals(1, firstTransmission.getAddressing().getUnitCodes().length);
	
		assertEquals(Command.on, firstTransmission.getCommand());
		assertEquals(2, firstTransmission.getCommandRepeat());
		
		
	}
	
	@Test(expected = RuntimeException.class)
	public void shouldThrowRuntimeExpectionIfChecksumDoesntMatch() {
		String response = "$<2800! P01P01 PONPON P02P02 PBGTPBGTPBGTPBGTPBGTPBGTPBGT P03P03 POFFPOFF P04P04 PDIMPDIMPDIMPDIMPDIMPDIM47#";
		List<Transmission> transmissions = responseParser.parseResponse(response);
		
		
	}
	
}
