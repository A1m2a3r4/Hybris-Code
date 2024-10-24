/*
 * Copyright (c) 2023 SAP SE or an SAP affiliate company. All rights reserved
 */
package com.hybris.training.jalo;

import com.hybris.training.constants.TrainingbackofficeConstants;
import de.hybris.platform.jalo.JaloSession;
import de.hybris.platform.jalo.extension.ExtensionManager;

public class TrainingbackofficeManager extends GeneratedTrainingbackofficeManager
{
	public static final TrainingbackofficeManager getInstance()
	{
		ExtensionManager em = JaloSession.getCurrentSession().getExtensionManager();
		return (TrainingbackofficeManager) em.getExtension(TrainingbackofficeConstants.EXTENSIONNAME);
	}
	
}
