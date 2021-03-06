/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.visitdocumentsui;

import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.ConceptClass;
import org.openmrs.ConceptComplex;
import org.openmrs.ConceptDatatype;
import org.openmrs.ConceptDescription;
import org.openmrs.ConceptName;
import org.openmrs.EncounterType;
import org.openmrs.api.ConceptService;
import org.openmrs.api.EncounterService;
import org.openmrs.api.context.Context;
import org.openmrs.module.BaseModuleActivator;
import org.springframework.stereotype.Component;

/**
 * This class contains the logic that is run every time this module is either started or stopped.
 */
@Component(VisitDocumentsConstants.COMPONENT_VDUI_ACTIVATOR)
public class VisitDocumentsActivator extends BaseModuleActivator {
   
   protected Log log = LogFactory.getLog(getClass());
      
   /**
    * @see ModuleActivator${symbol_pound}willRefreshContext()
    */
   public void willRefreshContext() {
      log.info("Refreshing " + VisitDocumentsConstants.MODULE_NAME + " Module");
   }
   
   /**
    * @see ModuleActivator${symbol_pound}contextRefreshed()
    */
   public void contextRefreshed() {
      log.info(VisitDocumentsConstants.MODULE_NAME + " Module refreshed");
   }
   
   /**
    * @see ModuleActivator${symbol_pound}willStart()
    */
   public void willStart() {
      log.info("Starting " + VisitDocumentsConstants.MODULE_NAME + " Module");
   }
   
   /**
    * @see ModuleActivator${symbol_pound}started()
    */
   public void started() {
      
      // Concepts Complex
      {
         final String name = VisitDocumentsConstants.MODULE_SHORT_ID + " PATIENT DOCUMENT";
         final String desc = "Concept complex for 'default patient document' complex obs.";
         final String uuid = VisitDocumentsConstants.CONCEPT_DEFAULT_UUID;
         
         ConceptService conceptService = Context.getConceptService();
         
         if (null == conceptService.getConceptByUuid(uuid)) {
            
            ConceptComplex conceptComplex = new ConceptComplex();
            conceptComplex.setUuid(uuid);
            conceptComplex.setHandler("DefaultDocumentHandler");
            ConceptName conceptName = new ConceptName(name, Locale.ENGLISH);
            conceptComplex.setFullySpecifiedName(conceptName);
            conceptComplex.setPreferredName(conceptName);
            conceptComplex.setConceptClass( conceptService.getConceptClassByUuid(ConceptClass.QUESTION_UUID) );
            conceptComplex.setDatatype( conceptService.getConceptDatatypeByUuid(ConceptDatatype.COMPLEX_UUID) );
            conceptComplex.addDescription(new ConceptDescription(desc, Locale.ENGLISH));
            
            conceptService.saveConcept(conceptComplex);
         }
      }
      {
         final String name = VisitDocumentsConstants.MODULE_SHORT_ID + " PATIENT IMAGE";
         final String desc = "Concept complex for 'patient images with thumbnails' complex obs.";
         final String uuid = VisitDocumentsConstants.CONCEPT_IMAGE_UUID;
         
         ConceptService conceptService = Context.getConceptService();
         
         if (null == conceptService.getConceptByUuid(uuid)) {
            
            ConceptComplex conceptComplex = new ConceptComplex();
            conceptComplex.setUuid(uuid);
            conceptComplex.setHandler("ImageDocumentHandler");
            ConceptName conceptName = new ConceptName(name, Locale.ENGLISH);
            conceptComplex.setFullySpecifiedName(conceptName);
            conceptComplex.setPreferredName(conceptName);
            conceptComplex.setConceptClass( conceptService.getConceptClassByUuid(ConceptClass.QUESTION_UUID) );
            conceptComplex.setDatatype( conceptService.getConceptDatatypeByUuid(ConceptDatatype.COMPLEX_UUID) );
            conceptComplex.addDescription(new ConceptDescription(desc, Locale.ENGLISH));
            
            conceptService.saveConcept(conceptComplex);
         }
      }

      // Encounter Type
      {
         final String name = "Visit Document Upload";
         final String desc = "Encounters used to record visit documents complex obs.";
         final String uuid = VisitDocumentsConstants.ENCOUNTER_TYPE_UUID; // this is also the default GP value set in config.xml
         
         EncounterService es = Context.getEncounterService();
         EncounterType encounterType = es.getEncounterTypeByUuid(uuid);

         if (encounterType == null) {
            encounterType = new EncounterType(name, desc);
            encounterType.setUuid(uuid);
            es.saveEncounterType(encounterType);
         }
      }
      
      log.info(VisitDocumentsConstants.MODULE_NAME + " Module started");
   }
   
   /**
    * @see ModuleActivator${symbol_pound}willStop()
    */
   public void willStop() {
      log.info("Stopping " + VisitDocumentsConstants.MODULE_NAME + " Module");
   }
   
   /**
    * @see ModuleActivator${symbol_pound}stopped()
    */
   public void stopped() {
      log.info(VisitDocumentsConstants.MODULE_NAME + " Module stopped");
   }
      
}