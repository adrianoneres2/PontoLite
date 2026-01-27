package com.octadata.pontolite.util;

import org.springframework.ui.Model;

public final class ModelMessage {

	private ModelMessage() {
	}

	public static void setAttribute(Model model, String code, String message) {
		model.addAttribute("codeMessage", code);
		model.addAttribute("msgDescription", message);
	}
}
