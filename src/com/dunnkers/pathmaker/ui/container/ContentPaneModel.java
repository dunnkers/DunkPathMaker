package com.dunnkers.pathmaker.ui.container;

import java.awt.Component;
import java.util.prefs.Preferences;

import com.dunnkers.pathmaker.Configuration;
import com.dunnkers.pathmaker.util.CodeFormat;

public abstract class ContentPaneModel {

	private CodeFormat codeFormat;

	private final Preferences preferences;

	public ContentPaneModel() {
		preferences = Preferences.userNodeForPackage(Configuration.PREFERENCE_PACKAGE);
		{
			/*codeFormat = Configuration.INITIAL_CODE_FORMAT;
			final String codeFormatString = preferences.get(
					Configuration.CODE_FORMAT_KEY,
					Configuration.INITIAL_CODE_FORMAT.name());
			for (final CodeFormat codeFormat : CodeFormat.values()) {
				if (codeFormat.name().equals(codeFormatString)) {
					this.codeFormat = codeFormat;
				}
			}*/
			codeFormat = findEnumObject(CodeFormat.class, preferences.get(
					Configuration.CODE_FORMAT_KEY,
					Configuration.INITIAL_CODE_FORMAT.name()),
					Configuration.INITIAL_CODE_FORMAT);
			System.out.println(codeFormat.name());
		}
	}

	public CodeFormat getCodeFormat() {
		return codeFormat;
	}

	public void setCodeFormat(CodeFormat codeFormat) {
		this.codeFormat = codeFormat;
		preferences.put(Configuration.CODE_FORMAT_KEY, this.codeFormat.name());
	}

	public abstract Component getComponent();
	
	public <E extends Enum<E>, T> E findEnumObject(Class<E> enumClass, String enumName) {
		return findEnumObject(enumClass, enumName, null);
	}
	
	public <E extends Enum<E>, T> E findEnumObject(Class<E> enumClass, String enumName, E defaultEnum) {
		for (final E enumObject : enumClass.getEnumConstants()) {
			if (enumObject.name().equals(enumName)) {
				System.out.println("found dis;"+enumName);
				return enumObject;
			}
		}
		return defaultEnum;
	}
}