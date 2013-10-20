package com.dunnkers.pathmaker.ui.container;

import java.awt.Component;

import com.dunnkers.pathmaker.Configuration;
import com.dunnkers.pathmaker.util.CodeFormat;

public abstract class ContentPaneModel {

	private CodeFormat codeFormat;

	public ContentPaneModel() {
		this.codeFormat = Configuration.INITIAL_CODE_FORMAT;
	}

	public CodeFormat getCodeFormat() {
		return codeFormat;
	}

	public void setCodeFormat(CodeFormat codeFormat) {
		this.codeFormat = codeFormat;
	}

	public abstract Component getComponent();
}