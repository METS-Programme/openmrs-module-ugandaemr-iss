/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.ugandaemr.iss;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.BaseModuleActivator;
import org.openmrs.module.Module;
import org.openmrs.module.ModuleFactory;
import org.openmrs.module.aijar.activator.HtmlFormsInitializer;
import org.openmrs.module.aijar.activator.Initializer;
import org.openmrs.module.dataexchange.DataImporter;

import java.util.ArrayList;
import java.util.List;

/**
 * This class contains the logic that is run every time this module is either started or shutdown
 */
public class UgandaEMRISSClinicActivator extends BaseModuleActivator {
	
	private Log log = LogFactory.getLog(this.getClass());
	
	/**
	 * @see #started()
	 */
	public void started() {
		
		try {
			// run the initializers
			for (Initializer initializer : getInitializers()) {
				initializer.started();
			}
			// install concepts
			log.info("Installing custom concepts for ISS customizations");
			DataImporter dataImporter = Context.getRegisteredComponent("dataImporter", DataImporter.class);
			
			dataImporter.importData("metadata/ISS_Custom_Concepts.xml");
			log.info("ISS Custom Concepts imported");
			log.info("Started UgandaEMR ISS Clinic Module");
		}
		catch (Exception e) {
			Module mod = ModuleFactory.getModuleById("ugandaemr-iss");
			ModuleFactory.stopModule(mod);
			throw new RuntimeException("failed to setup the module ", e);
		}
	}
	
	/**
	 * @see #shutdown()
	 */
	public void shutdown() {
		log.info("Shutdown UgandaEMR ISS Clinic");
	}
	
	private List<Initializer> getInitializers() {
		List<Initializer> l = new ArrayList<Initializer>();
		l.add(new HtmlFormsInitializer(UgandaEMRISSClinicConstants.MODULE_ID));
		return l;
	}
	
}
