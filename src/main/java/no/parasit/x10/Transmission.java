package no.parasit.x10;

public class Transmission
{
	private Addressing addressing;
	private Command command;
	private int commandRepeat = 1;
	
	
	public Transmission(Addressing addressing, Command command)
	{
		super();
		this.addressing = addressing;
		this.command = command;
	}

	
	public Transmission(Addressing addressing, Command command, int commandRepeat)
	{
		super();
		this.addressing = addressing;
		this.command = command;
		this.commandRepeat = commandRepeat;
	}

	public Addressing getAddressing()
	{
		return addressing;
	}

	public Command getCommand()
	{
		return command;
	}

	public int getCommandRepeat()
	{
		return commandRepeat;
	}


	public void setAddressing(Addressing addressing)
	{
		this.addressing = addressing;
	}


	public void setCommand(Command command)
	{
		this.command = command;
	}


	public void setCommandRepeat(int commandRepeat)
	{
		this.commandRepeat = commandRepeat;
	}


	@Override
	public String toString()
	{
		return "Transmission [addressing=" + addressing + ", command=" + command
				+ ", commandRepeat=" + commandRepeat + "]";
	}
	
	
	
	
}

