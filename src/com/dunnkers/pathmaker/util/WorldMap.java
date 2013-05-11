package com.dunnkers.pathmaker.util;

import com.dunnkers.pathmaker.Configuration;
import com.dunnkers.util.resource.ResourcePath;

/**
 * 
 * @author Dunnkers
 */
public enum WorldMap {

	OLD_SCHOOL("Old school (2007)", Configuration.IMAGE_MAP_07, true),
	RECENT("Recent (EOC)", Configuration.IMAGE_MAP_EOC, true);
	
	private final String name;
	private final ResourcePath resourcePath;
	private final boolean enabled;
	
	private WorldMap(final String name, final ResourcePath resourcePath, final boolean enabled) {
		this.name = name;
		this.resourcePath = resourcePath;
		this.enabled = enabled;
	}

	public String getName() {
		return name;
	}

	public ResourcePath getResourcePath() {
		return resourcePath;
	}

	public boolean isEnabled() {
		return enabled;
	}
}
