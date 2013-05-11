package com.dunnkers.pathmaker.ui;

import com.dunnkers.pathmaker.util.CodeFormat;

public class WindowModel {

	private CodeFormat codeFormat;

	public WindowModel() {
		this.codeFormat = CodeFormat.VINSERT;
	}

	public CodeFormat getCodeFormat() {
		return codeFormat;
	}

	public void setCodeFormat(CodeFormat codeFormat) {
		this.codeFormat = codeFormat;
	}
}