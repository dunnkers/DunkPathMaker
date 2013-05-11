package com.dunnkers.pathmaker.ui.container;

import com.dunnkers.pathmaker.util.CodeFormat;

public class ContentPaneModel {

	private CodeFormat codeFormat;

	public ContentPaneModel() {
		this.codeFormat = CodeFormat.VINSERT;
	}

	public CodeFormat getCodeFormat() {
		return codeFormat;
	}

	public void setCodeFormat(CodeFormat codeFormat) {
		this.codeFormat = codeFormat;
	}
}