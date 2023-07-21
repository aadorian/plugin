package edu.stanford.bmir.protege.examples.menu;

import java.awt.event.ActionEvent;
import java.io.File;
import java.util.Set;

import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JWindow;

import org.apache.http.impl.conn.tsccm.WaitingThread;
import org.protege.editor.owl.ProtegeOWL;
import org.protege.editor.owl.model.OWLModelManager;
import org.protege.editor.owl.ui.action.ProtegeOWLAction;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.AddImport;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.model.RemoveAxiom;

public class ToolsMenu3 extends ProtegeOWLAction {

	public void initialise() throws Exception {
	}

	public void dispose() throws Exception {
	}

	public void actionPerformed(ActionEvent event) {
		StringBuilder message = new StringBuilder(
				"Clear Axioms.\n");
		message.append("The active ontology .\n");
		message.append(getOWLModelManager().getActiveOntology().getClassesInSignature().size());
		message.append(" classes.");
		OWLModelManager manager = getOWLModelManager();
		OWLOntology ontology = manager.getActiveOntology();

		Set<OWLAnnotation> annotations = ontology.getAnnotations();
		message.append(annotations.toString())	;
		//String uri = "http://protege.stanford.edu/ontologies/koala.owl";
    	
		//manager.getOWLOntologyManager().applyChange(new AddImport(ontology, manager.getOWLDataFactory().getOWLImportsDeclaration(IRI.create(uri))));
		
		Set<OWLAxiom> axiomsTBox = ontology.getTBoxAxioms(null);
		
		//Convert the set to a string
		String s = new String();
		for (OWLAxiom axiom : axiomsTBox) {
			s += axiom.toString();
			if (axiom.getAxiomType() == AxiomType.DISJOINT_CLASSES) {
				// empty set symbol
				s += "= \u2205";
			}
			s += "\n";
		}
		
		


		//replace SubClassOf with unicode math symbols
		s=s.replace("SubClassOf", "\u2286");
		//replace DisjointWith with unicode math symbols
		s=s.replace("DisjointWith", "\u2293");
		
		 
		s=s.replace("and", "\u2227");
		//replace or with unicode math symbols
		s=s.replace(" or ", "\u2228");
		//replace some with unicode math symbols
		s=s.replace("some", "\u2203");
		//replace all with unicode math symbols
		s=s.replace("all", "\u2200");

		s= s.replace("(not", "(\u00AC");
		//replace inverse with unicode math symbols
		s = s.replace( "EquivalentTo", "\u2261");

		
		
		//create a new window
		JFrame window = new JFrame();
		//set the title of the window
		window.setTitle("TBox");
		window.setLocationRelativeTo(null);
		//set the size of the window
		window.setSize(500, 500);
		window.setVisible(true);
		JEditorPane pane = new JEditorPane();
		window.getContentPane().add(pane);
		window.getContentPane().add(new JScrollPane(pane));
		pane.setEditable(false);
		pane.setText(s);
		pane.setCaretPosition(0);
		//add a label to the window


		// now set the imported to be the active ontology
	//	JOptionPane.showMessageDialog(getOWLWorkspace(), message.toString());
	//	JFileChooser fileChooser = new JFileChooser();
    //    int result = fileChooser.showOpenDialog(null);
       
		//JOptionPane.showMessageDialog(getOWLWorkspace(), axioms.toString());
        //if (result == JFileChooser.APPROVE_OPTION) {
        //   File selectedFile = fileChooser.getSelectedFile();
        //    String ontologyFile = selectedFile.getAbsolutePath();
		//	manager.getOWLOntologyManager().getOntology(IRI.create("file:" + ontologyFile));
			
        //}	
	}
}
