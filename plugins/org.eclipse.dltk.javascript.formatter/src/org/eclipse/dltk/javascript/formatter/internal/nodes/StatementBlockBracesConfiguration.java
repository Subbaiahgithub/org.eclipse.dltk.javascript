package org.eclipse.dltk.javascript.formatter.internal.nodes;

import org.eclipse.dltk.formatter.IFormatterDocument;

public class StatementBlockBracesConfiguration extends BlockBracesConfiguration {

	public StatementBlockBracesConfiguration(IFormatterDocument document) {
		super(document);
	}

	public int insertBeforeOpenBrace() {
		return IBracesConfiguration.LINE_BREAK;
	}

}
