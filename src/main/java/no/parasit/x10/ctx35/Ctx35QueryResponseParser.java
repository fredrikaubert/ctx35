package no.parasit.x10.ctx35;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import no.parasit.x10.Addressing;
import no.parasit.x10.Command;
import no.parasit.x10.Device;
import no.parasit.x10.HouseCode;
import no.parasit.x10.Transmission;
import no.parasit.x10.UnitCode;

import com.sun.xml.internal.rngom.digested.DDataPattern;

public class Ctx35QueryResponseParser
{

	private Ctx35CheckSumCalculator checkSumCalculator = new Ctx35CheckSumCalculator();

	private Ctx35CommandTranslator commandTranslator = new Ctx35CommandTranslator();

	private List<ResponseToken> tokens = new LinkedList<ResponseToken>();

	public List<Transmission> parseResponse(String response)
	{
		List<Transmission> transmissions = new ArrayList<Transmission>();

		if (!response.startsWith( "$<2800!" ))
		{
			throw new RuntimeException( "Response doesn't conform to protocol." );
		}
		if (!(response.indexOf( '#' ) == response.length() - 1))
		{
			throw new RuntimeException(
					"# should occure only once, and it should be the last character" );
		}
		String checkSumBase = response.substring( 0, response.length() - 3 );
		System.out.println( checkSumBase );
		String checkSum = response.substring( response.length() - 3,
				response.length() - 1 );
		String calculatedCheckSum = checkSumCalculator.calculate( checkSumBase );
		if (!(calculatedCheckSum.equals( checkSum )))
		{
			throw new RuntimeException( "Checksum does not match. Calculated="
					+ calculatedCheckSum + ", actual=" + checkSum + " response="
					+ response );
		}

		String data = response.substring( "$<2800! ".length(),
				response.length() - 3 );
		String[] stringTokens = data.split( " " );
		
		for (String string : stringTokens)
		{
			tokens.add( new ResponseToken( string ) );
		}
		
		List<Device> devices = new LinkedList<Device>();
		for (ResponseToken token : tokens)
		{
			if(token.isDevice()) {
				devices.add( token.getDevice() );
			}
			if(token.isCommand()) {
				Command command = token.getCommand();
				if(command.isRequiresUnitCode()) {
					List<Device> matchingDevices = removeAllDevicesWithUnitCode(token.getHouseCode(), devices);
					if(matchingDevices.size() == 0) {
						throw new RuntimeException("Command " + command + "(token:" + token.getString() + ") requires addressing, but no matching devices found. Have allready created " + transmissions.size() + " transmissions");
					} 
					UnitCode[] unitCodes = new UnitCode[matchingDevices.size()];
					int i = 0;
					for (Device device : matchingDevices)
					{
						unitCodes[i++] = device.getUnitCode();
					}
					Addressing addressing = new Addressing( matchingDevices.get( 0 ).getHouseCode(), unitCodes );
					Transmission transmission = new Transmission( addressing, command, token.getCommandRepeat() );
					transmissions.add( transmission );
				} else {
					transmissions.add(new Transmission( new Addressing( token.getHouseCode()), command ));
				}
				
			}
		}
		
		if(devices.size() != 0) {
			System.out.println("WARNING: got " + devices.size() + " device leftovers, not assosiated to a command:" + devices);
		}
		return transmissions;

	}

	private List<Device> removeAllDevicesWithUnitCode(HouseCode houseCode, List<Device> devices)
	{
		List<Device> matchingDevices = new LinkedList<Device>();
		for (Device device : devices)
		{
			if(houseCode.equals( device.getHouseCode())) {
				matchingDevices.add(device);
			}
		}
		devices.removeAll( matchingDevices );
		return matchingDevices;
	}
}
