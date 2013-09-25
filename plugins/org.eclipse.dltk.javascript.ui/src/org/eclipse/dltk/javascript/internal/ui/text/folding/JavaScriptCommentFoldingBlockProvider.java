/*******************************************************************************
 * Copyright (c) 2010 xored software, Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     xored software, Inc. - initial API and Implementation (Alex Panchenko)
 *******************************************************************************/
package org.eclipse.dltk.javascript.internal.ui.text.folding;

import org.eclipse.dltk.javascript.internal.ui.text.JSPartitioningProvider;
import org.eclipse.dltk.javascript.ui.text.IJavaScriptPartitions;
import org.eclipse.dltk.ui.text.folding.IFoldingContent;
import org.eclipse.dltk.ui.text.folding.PartitioningFoldingBlockProvider;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocumentPartitioner;
import org.eclipse.jface.text.ITypedRegion;
import org.eclipse.jface.text.TextUtilities;
import org.eclipse.jface.text.rules.FastPartitioner;

public class JavaScriptCommentFoldingBlockProvider extends
		PartitioningFoldingBlockProvider {

	public JavaScriptCommentFoldingBlockProvider() {
		super(JSPartitioningProvider.getInstance());
	}

	public void computeFoldableBlocks(IFoldingContent content) {
		if (isFoldingComments()) {
			computeBlocksForPartitionType(content,
					IJavaScriptPartitions.JS_SINGLE_LINE_COMMENT,
					JavaScriptFoldingBlockKind.COMMENT, isCollapseComments());
			computeBlocksForPartitionType(content,
					IJavaScriptPartitions.JS_MULTI_LINE_COMMENT,
					JavaScriptFoldingBlockKind.COMMENT, isCollapseComments());
		}
		if (isCollapseHeaderComment()) {
			final String contents = content.getSourceContents();
			if (contents != null && contents.length() >= 2) {
				Document document = new Document(contents);
				final IDocumentPartitioner partitioner = new FastPartitioner(
						JSPartitioningProvider.getInstance()
								.createPartitionScanner(),
						JSPartitioningProvider.getInstance()
								.getPartitionContentTypes());
				partitioner.connect(document);
				document.setDocumentPartitioner(JSPartitioningProvider
						.getInstance().getPartitioning(), partitioner);
				try {
					ITypedRegion region = TextUtilities.getPartition(document,
							JSPartitioningProvider.getInstance()
									.getPartitioning(), 1, true);
					if (region.getType() == IJavaScriptPartitions.JS_MULTI_LINE_COMMENT) {
						int endPos = region.getOffset() + region.getLength();
						String txtAfterComment = document.get(endPos,
								Math.min(20, document.getLength() - endPos));
						int lineDelimiterCount = 0;
						for (int i = 0; i < txtAfterComment.length(); i++) {
							char c = txtAfterComment.charAt(i);
							if (c == 'f') {
								if (txtAfterComment.substring(i).startsWith(
										"function"))
									break;
							} else if (c == 'v') {
								if (txtAfterComment.substring(i).startsWith(
										"var"))
									break;
							} else if (lineDelimiterCount == 1) {
								// if after 1 line delimiter still no function
								// or var is found that it is fine.
								requestor.acceptBlock(region.getOffset(),
										endPos,
										JavaScriptFoldingBlockKind.COMMENT,
										null, true);
							} else if (c == '\n') {
								lineDelimiterCount++;
							} else if (c == '\r') {
								lineDelimiterCount++;
								if (i + 1 < txtAfterComment.length()
										&& txtAfterComment.charAt(i + 1) == '\n')
									i++; // skip \r\n
							}
							if (lineDelimiterCount == 2) {
								requestor.acceptBlock(region.getOffset(),
										endPos,
										JavaScriptFoldingBlockKind.COMMENT,
										null, true);
								break;
							}
						}
					}
				} catch (BadLocationException e) {
				}
			}
		}
		if (isFoldingDocs())
			computeBlocksForPartitionType(content,
					IJavaScriptPartitions.JS_DOC,
					JavaScriptFoldingBlockKind.JSDOC, isCollapseDocs());
	}

}
