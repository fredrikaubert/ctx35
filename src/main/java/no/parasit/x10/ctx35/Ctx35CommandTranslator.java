package no.parasit.x10.ctx35;

import java.util.HashMap;
import java.util.Map;


import no.parasit.x10.Command;

public class Ctx35CommandTranslator {

	private Map<Command, String> translationTo = new HashMap<Command, String>();
	private Map<String, Command> translationFrom = new HashMap<String, Command>();
	
	
	public Ctx35CommandTranslator() {
		translationTo.put(Command.on, "ON");
		translationTo.put(Command.off, "OFF");
		translationTo.put(Command.bright, "BGT");
		translationTo.put(Command.dim, "DIM");
		translationTo.put(Command.all_lights_on, "ALN");
		translationTo.put(Command.all_lights_off, "ALF");
		translationTo.put(Command.all_units_off, "AUF");
		
		
		for (Command command : translationTo.keySet()) {
			translationFrom.put(translationTo.get(command), command);
		}
		
	}
	
	public String toCtx35Command(Command command) {
		String ctx35Command = translationTo.get(command);
		if(ctx35Command == null)
		{
			throw new RuntimeException("Cannot translate command:" + command);
		}
		return ctx35Command;
	}
	
	public Command fromCtx35Command(String ctx35Command) {
		return this.translationFrom.get(ctx35Command);
	}
}
