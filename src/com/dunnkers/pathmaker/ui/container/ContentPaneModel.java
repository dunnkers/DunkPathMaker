package com.dunnkers.pathmaker.ui.container;

import java.awt.Component;
import java.util.prefs.Preferences;

import com.dunnkers.pathmaker.Configuration;
import com.dunnkers.pathmaker.ui.container.ContentPane.TileArrayChangeListener;
import com.dunnkers.pathmaker.util.CodeFormat;
import com.dunnkers.util.Enums;

public abstract class ContentPaneModel {

	private CodeFormat codeFormat;
	private TileArrayChangeListener tileArrayChangeListener;

	private final Preferences preferences;

	public ContentPaneModel() {
		preferences = Preferences.userNodeForPackage(Configuration.PREFERENCE_PACKAGE);
		codeFormat = Enums.findEnumObject(CodeFormat.class, preferences.get(
				Configuration.CODE_FORMAT_KEY,
				Configuration.INITIAL_CODE_FORMAT.name()),
				Configuration.INITIAL_CODE_FORMAT);
	}

	public CodeFormat getCodeFormat() {
		return codeFormat;
	}

	public void setCodeFormat(CodeFormat codeFormat) {
		this.codeFormat = codeFormat;
		preferences.put(Configuration.CODE_FORMAT_KEY, this.codeFormat.name());
	}

	public TileArrayChangeListener getTileArrayChangeListener() {
		return tileArrayChangeListener;
	}

	public void setTileArrayChangeListener(
			TileArrayChangeListener tileArrayChangeListener) {
		this.tileArrayChangeListener = tileArrayChangeListener;
	}

	public abstract Component getComponent();
}