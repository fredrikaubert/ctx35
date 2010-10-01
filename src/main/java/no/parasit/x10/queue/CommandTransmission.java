package no.parasit.x10.queue;

import no.parasit.x10.Addressing;
import no.parasit.x10.Command;

public class CommandTransmission {
	private Addressing addressing;
	private Command command;
	private int commandRepeat = 1;

	public CommandTransmission(Addressing addressing, Command command, int commandRepeat) {
		super();
		this.addressing = addressing;
		this.command = command;
		this.commandRepeat = commandRepeat;
	}
	public Addressing getAddressing() {
		return addressing;
	}
	public Command getCommand() {
		return command;
	}
	public int getCommandRepeat() {
		return commandRepeat;
	}
}
