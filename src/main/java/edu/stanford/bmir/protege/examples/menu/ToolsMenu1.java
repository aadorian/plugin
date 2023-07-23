package edu.stanford.bmir.protege.examples.menu;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Set;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import org.protege.editor.owl.model.OWLModelManager;
import org.protege.editor.owl.ui.action.ProtegeOWLAction;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.formats.LatexAxiomsListDocumentFormat;
import org.semanticweb.owlapi.formats.LatexDocumentFormat;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi.io.GZipFileDocumentTarget;
import org.semanticweb.owlapi.io.OWLOntologyDocumentTarget;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLDocumentFormat;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.SetOntologyID;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;


public class ToolsMenu1 extends ProtegeOWLAction {

	public void initialise() throws Exception {
	}

	public void dispose() throws Exception {
	}

	public void actionPerformed(ActionEvent event) {
		StringBuilder message = new StringBuilder("This example menu item is under the Tools menu.\n");
		message.append("The currently selected class is ");
		message.append(getOWLWorkspace().getOWLSelectionModel().getLastSelectedClass());
		message.append(".");
		JOptionPane.showMessageDialog(getOWLWorkspace(), message.toString());
		
		OWLModelManager manager = getOWLModelManager();
		OWLOntology ontology = manager.getActiveOntology();
		
		Set<OWLOntology> activeOntologies = manager.getOntologies();
		for (OWLOntology ont : activeOntologies) {
			JOptionPane.showMessageDialog(getOWLWorkspace(), "Active ontology: " + ont.getOntologyID().getOntologyIRI());
		}
	   	String filePath = "file.owl";
        
        // Create an OWLOntologyManager to manage OWL ontologies

        try {
            // Load the OWL file from the local file system
            File file = new File(filePath);
            // load the file to the actual ontology 
			
            // Do whatever you need to do with the loaded ontology
            System.out.println("Loaded ontology: " + ontology.getOntologyID().getOntologyIRI());
            
        } catch (Exception e) {
            System.err.println("Error loading the OWL file: " + e.getMessage());
            e.printStackTrace();
        }

		try {
			
			ontology.saveOntology(new RDFXMLDocumentFormat(), new FileOutputStream(new File("/Users/user/Desktop/koala.owl")));
			

			//System.out.println("Ontology saved");
				//final String osCommand = "java -jar /Users/user/Desktop/widoco-1.4.19-jar-with-dependencies_JDK-11.jar -ontFile /Users/user/Desktop/koala.owl -getOntologyMetadata -includeImportedOntologies  -displayDirectImportsOnly -uniteSections -outFolder /Users/user/Desktop/doc";

			//executeOSOperation(osCommand);
	 
			// Save the modified ontology to a new file
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(getOWLWorkspace(), "Error: " + e.getMessage());
		}
	
	}
	 
	public static void executeOSOperation(String osCommand) {
        try {
            // Create the ProcessBuilder with the OS command
            ProcessBuilder processBuilder = new ProcessBuilder();
            processBuilder.command("bash", "-c", osCommand);

            // Start the process
            Process process = processBuilder.start();

            // Wait for the process to finish
            int exitCode = process.waitFor();

            if (exitCode == 0) {
                System.out.println("OS operation executed successfully.");
            } else {
                System.err.println("OS operation failed with exit code: " + exitCode);
            }
        } catch (IOException | InterruptedException e) {
           JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());}
		   
        }


		public static OWLOntology createSimpleOntology() throws OWLOntologyCreationException {
			OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
			OWLOntology ontology = manager.createOntology();

			IRI classIRI = IRI.create("http://example.org#Person");
			OWLClass personClass = manager.getOWLDataFactory().getOWLClass(classIRI);

			IRI individualIRI1 = IRI.create("http://example.org#John");
			OWLNamedIndividual johnIndividual = manager.getOWLDataFactory().getOWLNamedIndividual(individualIRI1);

			IRI individualIRI2 = IRI.create("http://example.org#Alice");
			OWLNamedIndividual aliceIndividual = manager.getOWLDataFactory().getOWLNamedIndividual(individualIRI2);

			OWLDeclarationAxiom classDeclaration = manager.getOWLDataFactory().getOWLDeclarationAxiom(personClass);
			OWLDeclarationAxiom johnDeclaration = manager.getOWLDataFactory().getOWLDeclarationAxiom(johnIndividual);
			OWLDeclarationAxiom aliceDeclaration = manager.getOWLDataFactory().getOWLDeclarationAxiom(aliceIndividual);

			OWLClassAssertionAxiom johnIsPerson = manager.getOWLDataFactory().getOWLClassAssertionAxiom(personClass, johnIndividual);
			OWLClassAssertionAxiom aliceIsPerson = manager.getOWLDataFactory().getOWLClassAssertionAxiom(personClass, aliceIndividual);

			manager.addAxiom(ontology, classDeclaration);
			manager.addAxiom(ontology, johnDeclaration);
			manager.addAxiom(ontology, aliceDeclaration);
			manager.addAxiom(ontology, johnIsPerson);
			manager.addAxiom(ontology, aliceIsPerson);

		

			return ontology;
    }

   
    
			



}
