package org.eclipse.dltk.internal.javascript.corext.refactoring.rename;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.dltk.core.IMethod;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.internal.corext.refactoring.rename.RenameModelElementProcessor;
import org.eclipse.dltk.internal.javascript.corext.refactoring.Checks;
import org.eclipse.dltk.internal.javascript.corext.refactoring.RefactoringCoreMessages;
import org.eclipse.dltk.javascript.core.JavaScriptLanguageToolkit;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;

public class RenameFunctionProcessor extends RenameModelElementProcessor {
	public static final String IDENTIFIER = "org.eclipse.dltk.javascript.renameFunctionProcessor"; //$NON-NLS-1$

	public RenameFunctionProcessor(IModelElement function) {
		super(function, JavaScriptLanguageToolkit.getDefault());
	}

	@Override
	public String getIdentifier() {
		return IDENTIFIER;
	}

	public RefactoringStatus checkNewElementName(String newName)
			throws CoreException {
		return Checks.validateIdentifier(newName);
	}

	@Override
	public boolean isApplicable() throws CoreException {
		if (!Checks.isAvailable(fModelElement))
			return false;
		Assert.isTrue(fModelElement instanceof IMethod);
		IMethod method = (IMethod)fModelElement;
		return !"".equals(method.getElementName());
	}

	@Override
	public String getProcessorName() {
		return RefactoringCoreMessages.RenameMethodRefactoring_name;
	}
}
