package no.parasit.x10.ctx35;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import no.parasit.x10.Addressing;
import no.parasit.x10.Command;
import no.parasit.x10.HouseCode;
import no.parasit.x10.Transmission;
import no.parasit.x10.UnitCode;

import com.sun.xml.internal.rngom.digested.DDataPattern;

public class Ctx35QueryResponseParser
{

	private Ctx35CheckSumCalculator checkSumCalculator = new Ctx35CheckSumCalculator();

	private Ctx35CommandTranslator commandTranslator = new Ctx35CommandTranslator();

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
		String[] dataParts = data.split( " " );
		System.out.println( "dataparts=" + dataParts );

		Addressing addressing = null;
		for (int i = 0; i < dataParts.length; i++)
		{
			String dataPart = dataParts[i];
			if (i % 2 == 0)// addressinformation:
			{
				if (dataPart.length() % 3 != 0)
				{
					// doesnt match 3 letter addressing. This is mysterious
					throw new RuntimeException(
							"Doesn't match addess pattern. dataPart[" + i + "]="
									+ dataPart + " response=" + response );
				}

				Set<String> devices = new HashSet<String>();

				for (int t = 0; t < dataPart.length() / 3; t++)
				{
					String device = dataPart.substring( t * 3, t * 3 + 3 );
					System.out.println( "device" + device );
					devices.add( device );
				}
				Set<String> houseCodes = new HashSet<String>();
				HouseCode houseCode = null;
				Set<UnitCode> unitCodes = new HashSet<UnitCode>();
				for (String string : devices)
				{
					houseCodes.add( string.substring( 0, 1 ) );
					houseCode = new HouseCode( string.charAt( 0 ) );
					unitCodes.add(new UnitCode( string.substring( 2 ) ));
				}
				if( houseCodes.size() != 1) {
					throw new RuntimeException("There can be only one housecode pr transmission. If the current result is legal, we must change implementation. Housecodes.size:" + houseCodes.size());
				}
				
				addressing = new Addressing( houseCode, unitCodes.toArray( new UnitCode[0] ));
				

			} else
			{
				
				// TODO: Bruteforcing is not too elegant:
				Command command = commandTranslator.fromCtx35Command( dataPart.substring( 1, 3 ) );
				int commandRepeat = dataPart.length() / 3 ;
				
				if (command == null)
				{
					command = commandTranslator.fromCtx35Command( dataPart.substring( 1, 4 ) );
					commandRepeat = dataPart.length() / 4 ;
				}

				if (command == null)
				{
					throw new RuntimeException("Cannot extract command from dataPart:" + dataPart + " response=" + response );
				}
				
				transmissions.add( new Transmission( addressing, command, commandRepeat )); //TODO 1 in hardcoded, this is wrong
				addressing = null;
			}
		}

		return transmissions;

	}
}
