package no.parasit.x10;

public enum Command {
	on(true), 
	off(true), 
	dim(true), 
	bright(true),
	all_lights_on(false),
	all_lights_off(false),
	all_units_off(false);
	
	private boolean requiresUnitCode;
	
	private Command(boolean requiresUnitCode) {
		this.requiresUnitCode = requiresUnitCode;
	}
	
	public boolean isRequiresUnitCode() {
		return requiresUnitCode;
	}
}
