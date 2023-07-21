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
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.SetOntologyID;


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
			//ontology = manager.loadOntologyFromOntologyDocument(new File("test.owl"));
			//ontology.saveOntology(new RDFXMLDocumentFormat(), System.out);
			//OutputStream os = new FileOutputStream("test.rdf");
			//ontology.saveOntology(new RDFXMLDocumentFormat(), os);
			//os.close();
			//System.out.println("Ontology saved");
			// Create a new IRI with the desired name
			 ontology.saveOntology(new RDFXMLDocumentFormat(), System.out);
			 OutputStream os = new FileOutputStream("test.rdf");
			 ontology.saveOntology(new GZipFileDocumentTarget(new File("test.rdf.gz")));


			
			//final String osCommand = "java -jar /Users/user/Desktop/widoco-1.4.19-jar-with-dependencies_JDK-11.jar -ontFile /Users/user/Desktop/koala.owl -getOntologyMetadata -includeImportedOntologies  -displayDirectImportsOnly -uniteSections -outFolder /Users/user/Desktop/doc";
				os.close();
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
            e.printStackTrace();
        }
    }
			



}
