package edu.stanford.bmir.protege.examples.menu;

import java.awt.event.ActionEvent;
import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;

import org.protege.editor.owl.model.OWLModelManager;
import org.protege.editor.owl.model.inference.ProtegeOWLReasonerInfo;
import org.protege.editor.owl.ui.action.ProtegeOWLAction;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.RDFXMLOntologyFormat;
import org.semanticweb.owlapi.io.StringDocumentTarget;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.reasoner.structural.StructuralReasonerFactory;
import org.protege.editor.owl.model.inference.ProtegeOWLReasonerInfo;



   


public class ToolsMenu2 extends ProtegeOWLAction {

	private JFrame frame;
 	

    protected void initialiseOWLView(OWLOntology ontology) throws Exception {
        // Create the main window
        frame = new JFrame(ontology.getOntologyID().getOntologyIRI().get().toString());
        // allow several components on frame
		frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
		 // Set the size and close operation
        frame.setSize(800, 600);
		frame.setLocationRelativeTo(this.getOWLWorkspace());
		//set as active
		//set as the first window 
		frame.setAlwaysOnTop(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        // Example: Add a label to the window
        // Create the root node
    
		
		// Create the root node for the JTree
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Axioms");
 	    DefaultMutableTreeNode rootTbox = new DefaultMutableTreeNode("TBox Axioms");
 		
        // Get the TBox axioms
        Set<OWLEquivalentClassesAxiom> equivalentClassesAxioms = ontology.getAxioms(AxiomType.EQUIVALENT_CLASSES).stream().filter(axiom -> axiom.getClassExpressions().size() > 1).collect(Collectors.toSet());
		Set<OWLDisjointClassesAxiom> disjointClassesAxioms = ontology.getAxioms(AxiomType.DISJOINT_CLASSES).stream().filter(axiom -> axiom.getClassExpressions().size() > 1).collect(Collectors.toSet());
		Set<OWLDisjointUnionAxiom> disjointUnionAxioms = ontology.getAxioms(AxiomType.DISJOINT_UNION).stream().filter(axiom -> axiom.getClassExpressions().size() > 1).collect(Collectors.toSet());
		// Get the RBox axioms
		Set<OWLSubObjectPropertyOfAxiom> subObjectPropertyOfAxioms = ontology.getAxioms(AxiomType.SUB_OBJECT_PROPERTY);
		Set<OWLEquivalentObjectPropertiesAxiom> equivalentObjectPropertiesAxioms = ontology.getAxioms(AxiomType.EQUIVALENT_OBJECT_PROPERTIES);
		Set<OWLDisjointObjectPropertiesAxiom> disjointObjectPropertiesAxioms = ontology.getAxioms(AxiomType.DISJOINT_OBJECT_PROPERTIES);
		Set<OWLSubPropertyChainOfAxiom> subPropertyChainOfAxioms = ontology.getAxioms(AxiomType.SUB_PROPERTY_CHAIN_OF);

		// Get the ABox axioms
		Set<OWLClassAssertionAxiom> classAssertionAxioms = ontology.getAxioms(AxiomType.CLASS_ASSERTION);
		Set<OWLObjectPropertyAssertionAxiom> objectPropertyAssertionAxioms = ontology.getAxioms(AxiomType.OBJECT_PROPERTY_ASSERTION);
		Set<OWLDataPropertyAssertionAxiom> dataPropertyAssertionAxioms = ontology.getAxioms(AxiomType.DATA_PROPERTY_ASSERTION);
		Set<OWLNegativeObjectPropertyAssertionAxiom> negativeObjectPropertyAssertionAxioms = ontology.getAxioms(AxiomType.NEGATIVE_OBJECT_PROPERTY_ASSERTION);

		// Get the annotation axioms
		Set<OWLAnnotationAssertionAxiom> annotationAssertionAxioms = ontology.getAxioms(AxiomType.ANNOTATION_ASSERTION);
		Set<OWLSubAnnotationPropertyOfAxiom> subAnnotationPropertyOfAxioms = ontology.getAxioms(AxiomType.SUB_ANNOTATION_PROPERTY_OF);
		Set<OWLAnnotationPropertyDomainAxiom> annotationPropertyDomainAxioms = ontology.getAxioms(AxiomType.ANNOTATION_PROPERTY_DOMAIN);
		Set<OWLAnnotationPropertyRangeAxiom> annotationPropertyRangeAxioms = ontology.getAxioms(AxiomType.ANNOTATION_PROPERTY_RANGE);

		
		// gEt class  axioms 
		Set<OWLDeclarationAxiom> declarationAxioms = ontology.getAxioms(AxiomType.DECLARATION);
		Set<OWLSubClassOfAxiom> subClassOfAxioms = ontology.getAxioms(AxiomType.SUBCLASS_OF);
		Set<OWLHasKeyAxiom> hasKeyAxioms = ontology.getAxioms(AxiomType.HAS_KEY);
		
		// get all individuals
		Set<OWLNamedIndividual> individuals = ontology.getIndividualsInSignature();
		//get all classes
		Set<OWLClass> classes = ontology.getClassesInSignature();

		//Add individuals to the JTree
		DefaultMutableTreeNode individualsNode = new DefaultMutableTreeNode("Individuals");
		for (OWLNamedIndividual individual : individuals) {
			DefaultMutableTreeNode individualNode = new DefaultMutableTreeNode(individual);
			individualsNode.add(individualNode);
		}
		root.add(individualsNode);

		//Add classes to the JTree
		DefaultMutableTreeNode classesNode = new DefaultMutableTreeNode("Classes");
		for (OWLClass classs : classes) {
			DefaultMutableTreeNode classNode = new DefaultMutableTreeNode(classs);
			classesNode.add(classNode);
		}
		root.add(classesNode);

		// Add declaration axioms to the JTree
		DefaultMutableTreeNode declarationNode = new DefaultMutableTreeNode("Declaration Axioms");
		for (OWLDeclarationAxiom axiom : declarationAxioms) {
			DefaultMutableTreeNode axiomNode = new DefaultMutableTreeNode(axiom);
			declarationNode.add(axiomNode);
		}
		rootTbox.add(declarationNode);
		// Add subClassOfAxioms to the JTree
		DefaultMutableTreeNode subClassOfNode = new DefaultMutableTreeNode("Subclass Of Axioms");
		for (OWLSubClassOfAxiom axiom : subClassOfAxioms) {
			DefaultMutableTreeNode axiomNode = new DefaultMutableTreeNode(axiom);
			//substitute the axiom description SubClassOf \u2291
			axiomNode.setUserObject(axiomNode.getUserObject().toString().replace("SubClassOf", "\u2291"));
			//substitute the axiom descriptionn some \u2203
			axiomNode.setUserObject(axiomNode.getUserObject().toString().replace("some", " \u2203 "));
			//substitute the axiom value \u2192
			axiomNode.setUserObject(axiomNode.getUserObject().toString().replace("value", " \u003D "));

			//and ", " \u2227 ");
			axiomNode.setUserObject(axiomNode.getUserObject().toString().replace("and", " \u2227 "));
			
			// or ", " \u2228 ");	
			axiomNode.setUserObject(axiomNode.getUserObject().toString().replace("or", " \u2228 "));
		
			subClassOfNode.add(axiomNode);
		}
		rootTbox.add(subClassOfNode);

		// Add hasKeyAxioms to the JTree
		DefaultMutableTreeNode hasKeyNode = new DefaultMutableTreeNode("Has Key Axioms");
		for (OWLHasKeyAxiom axiom : hasKeyAxioms) {
			DefaultMutableTreeNode axiomNode = new DefaultMutableTreeNode(axiom);
			hasKeyNode.add(axiomNode);
		}
		rootTbox.add(hasKeyNode);

		// Add disjointObjectPropertiesAxioms to the JTree
		DefaultMutableTreeNode disjointObjectPropertiesNode = new DefaultMutableTreeNode("Disjoint Object Properties Axioms");
		for (OWLDisjointObjectPropertiesAxiom axiom : disjointObjectPropertiesAxioms) {
			DefaultMutableTreeNode axiomNode = new DefaultMutableTreeNode(axiom);
			disjointObjectPropertiesNode.add(axiomNode);
		}
		rootTbox.add(disjointObjectPropertiesNode);


		//Add subObjectPropertyOfAxioms to the JTree
		DefaultMutableTreeNode subObjectPropertyOfNode = new DefaultMutableTreeNode("Sub Object Property Of Axioms");
		for (OWLSubObjectPropertyOfAxiom axiom : subObjectPropertyOfAxioms) {

			DefaultMutableTreeNode axiomNode = new DefaultMutableTreeNode(axiom);
			axiomNode.setUserObject(axiomNode.getUserObject().toString().replace("SubPropertyOf:", "\u2291"));
			
			subObjectPropertyOfNode.add(axiomNode);
		}
		rootTbox.add(subObjectPropertyOfNode);
		// Add	subPropertyChainOfAxioms to the JTree
		DefaultMutableTreeNode subPropertyChainOfNode = new DefaultMutableTreeNode("Sub Property Chain Of Axioms");
		for (OWLSubPropertyChainOfAxiom axiom : subPropertyChainOfAxioms) {
			DefaultMutableTreeNode axiomNode = new DefaultMutableTreeNode(axiom);
			axiomNode.setUserObject(axiomNode.getUserObject().toString().replace("SubPropertyOf:", "\u2291"));
			
			subPropertyChainOfNode.add(axiomNode);
		}
		rootTbox.add(subPropertyChainOfNode);
		// Add equivalentObjectPropertiesAxioms to the JTree
		DefaultMutableTreeNode equivalentObjectPropertiesNode = new DefaultMutableTreeNode("Equivalent Object Properties Axioms");
		for (OWLEquivalentObjectPropertiesAxiom axiom : equivalentObjectPropertiesAxioms) {
			DefaultMutableTreeNode axiomNode = new DefaultMutableTreeNode(axiom);
			equivalentObjectPropertiesNode.add(axiomNode);
		}
		rootTbox.add(equivalentObjectPropertiesNode);

		// Detect A subclass of B
		/* *
		DefaultMutableTreeNode subclassOfNode = new DefaultMutableTreeNode("Subclass Of Axioms");
		for (OWLSubClassOfAxiom axiom : ontology.getAxioms(AxiomType.SUBCLASS_OF)) {
			
			DefaultMutableTreeNode axiomNode = new DefaultMutableTreeNode(axiom);
			//substitute the axiom description SubClassOf \u2291
			axiomNode.setUserObject(axiomNode.getUserObject().toString().replace("SubClassOf", "\u2291"));
			//substitute the axiom descriptionn some \u2203
			axiomNode.setUserObject(axiomNode.getUserObject().toString().replace("some", " \u2203 "));
			//substitute the axiom value \u2192
			axiomNode.setUserObject(axiomNode.getUserObject().toString().replace("value", " \u003D "));

			//and ", " \u2227 ");
			axiomNode.setUserObject(axiomNode.getUserObject().toString().replace("and", " \u2227 "));
			
			// or ", " \u2228 ");	
			axiomNode.setUserObject(axiomNode.getUserObject().toString().replace("or", " \u2228 "));
		
			subclassOfNode.add(axiomNode);
		}
		rootTbox.add(subclassOfNode);

		root.add(rootTbox);
		*/
		// Clear anotation axioms in ontology 
		 
		// Add class assertion axioms to the JTree
		DefaultMutableTreeNode classAssertionNode = new DefaultMutableTreeNode("Class Assertion Axioms");
		for (OWLClassAssertionAxiom axiom : classAssertionAxioms) {
			DefaultMutableTreeNode axiomNode = new DefaultMutableTreeNode(axiom);
			//subtitute Type \u2208
			axiomNode.setUserObject(axiomNode.getUserObject().toString().replace("Type", "\u2208"));
			classAssertionNode.add(axiomNode);
		}
		root.add(classAssertionNode);

		// Add object property assertion axioms to the JTree
		DefaultMutableTreeNode objectPropertyAssertionNode = new DefaultMutableTreeNode("Object Property Assertion Axioms");
		for (OWLObjectPropertyAssertionAxiom axiom : objectPropertyAssertionAxioms) {
			DefaultMutableTreeNode axiomNode = new DefaultMutableTreeNode(axiom);
			objectPropertyAssertionNode.add(axiomNode);
		}
		root.add(objectPropertyAssertionNode);
		// Add negative object property assertion axioms to the JTree
		DefaultMutableTreeNode negativeObjectPropertyAssertionNode = new DefaultMutableTreeNode("Negative Object Property Assertion Axioms");
		for (OWLNegativeObjectPropertyAssertionAxiom axiom : negativeObjectPropertyAssertionAxioms) {
			DefaultMutableTreeNode axiomNode = new DefaultMutableTreeNode(axiom);
				//substitute the axiom value \u2192
			axiomNode.setUserObject(axiomNode.getUserObject().toString().replace("value", "\u003D"));

			negativeObjectPropertyAssertionNode.add(axiomNode);
		}
		root.add(negativeObjectPropertyAssertionNode);
		// Add data property assertion axioms to the JTree
		DefaultMutableTreeNode dataPropertyAssertionNode = new DefaultMutableTreeNode("Data Property Assertion Axioms");
		for (OWLDataPropertyAssertionAxiom axiom : dataPropertyAssertionAxioms) {
			DefaultMutableTreeNode axiomNode = new DefaultMutableTreeNode(axiom);
			dataPropertyAssertionNode.add(axiomNode);
		}
		root.add(dataPropertyAssertionNode);
		

        // Add equivalent classes axioms to the JTree
        DefaultMutableTreeNode equivalentClassesNode = new DefaultMutableTreeNode("Equivalent Classes Axioms");
        for (OWLEquivalentClassesAxiom axiom : equivalentClassesAxioms) {
			String dlSyntax = axiom.getClassExpressionsAsList().stream().map(OWLClassExpression::toString).collect(Collectors.joining(" \u2261 "));
			//substitute the word and with \u2227
			dlSyntax = dlSyntax.replace(" and ", " \u2227 ");
			//substitute the word or with \u2228
			dlSyntax = dlSyntax.replace(" or ", " \u2228 ");	
			//("some", " \u2203 "));
			dlSyntax = dlSyntax.replace("some", " \u2203 ");
		
			DefaultMutableTreeNode axiomNode = new DefaultMutableTreeNode(dlSyntax);
			axiomNode.setUserObject(axiomNode.getUserObject().toString().replace("value", "\u003D"));
            equivalentClassesNode.add(axiomNode);
        }
        root.add(equivalentClassesNode);

        // Add disjoint classes axioms to the JTree
        DefaultMutableTreeNode disjointClassesNode = new DefaultMutableTreeNode("Disjoint Classes Axioms");
        for (OWLDisjointClassesAxiom axiom : disjointClassesAxioms) {
			String dlSyntax = axiom.getClassExpressionsAsList().stream().map(OWLClassExpression::toString).collect(Collectors.joining(" \u2293 ")).concat(" \u003D \u2205");
            DefaultMutableTreeNode axiomNode = new DefaultMutableTreeNode(dlSyntax);
            disjointClassesNode.add(axiomNode);
        }
        root.add(disjointClassesNode);

		// Add disjoint union axioms to the JTree
		DefaultMutableTreeNode disjointUnionNode = new DefaultMutableTreeNode("Disjoint Union Axioms");
		for (OWLDisjointUnionAxiom axiom : disjointUnionAxioms) {
			String dlSyntax = axiom.getClassExpressions().stream().map(OWLClassExpression::toString).collect(Collectors.joining(" \u2293 ")).concat(" \u003D ").concat(axiom.getOWLClass().toString());
			DefaultMutableTreeNode axiomNode = new DefaultMutableTreeNode(dlSyntax);
			disjointUnionNode.add(axiomNode);
		}
		root.add(disjointUnionNode);

	
		//Add anotation axioms to the JTree
		DefaultMutableTreeNode anotationNode = new DefaultMutableTreeNode("Anotation Axioms");
		for (OWLAnnotationAssertionAxiom axiom : annotationAssertionAxioms) {
			DefaultMutableTreeNode axiomNode = new DefaultMutableTreeNode(axiom);
			anotationNode.add(axiomNode);
		}
		root.add(anotationNode);

		//Add anotation property domain axioms to the JTree
		DefaultMutableTreeNode anotationPropertyDomainNode = new DefaultMutableTreeNode("Anotation Property Domain Axioms");
		for (OWLAnnotationPropertyDomainAxiom axiom : annotationPropertyDomainAxioms) {
			DefaultMutableTreeNode axiomNode = new DefaultMutableTreeNode(axiom);
			anotationPropertyDomainNode.add(axiomNode);
		}
		root.add(anotationPropertyDomainNode);

		//Add anotation property range axioms to the JTree
		DefaultMutableTreeNode anotationPropertyRangeNode = new DefaultMutableTreeNode("Anotation Property Range Axioms");
		for (OWLAnnotationPropertyRangeAxiom axiom : annotationPropertyRangeAxioms) {
			DefaultMutableTreeNode axiomNode = new DefaultMutableTreeNode(axiom);
			anotationPropertyRangeNode.add(axiomNode);
		}
		root.add(anotationPropertyRangeNode);

		//Add sub anotation property axioms to the JTree
		DefaultMutableTreeNode subAnotationPropertyNode = new DefaultMutableTreeNode("Sub Anotation Property Axioms");
		for (OWLSubAnnotationPropertyOfAxiom axiom : subAnnotationPropertyOfAxioms) {
			DefaultMutableTreeNode axiomNode = new DefaultMutableTreeNode(axiom);
			subAnotationPropertyNode.add(axiomNode);
		}
		root.add(subAnotationPropertyNode);
		
        // Create the tree
        JTree tree = new JTree(root);

		OWLReasonerFactory reasonerFactory = new StructuralReasonerFactory();
        OWLReasoner reasoner = reasonerFactory.createReasoner(ontology);
 // Get the class for which you want to retrieve instances
 		OWLModelManager manager = getOWLModelManager();
		
        //OWLClass owlClass = manager.getOWLDataFactory().getOWLClass(IRI.create("newClass"));
  		//NodeSet<OWLNamedIndividual> instances = reasoner.getInstances(owlClass, false);
		 StringBuilder message = new StringBuilder("\n");

        // Process the instances
        //for (OWLNamedIndividual instance : instances.getFlattened()) {
          //  message.append("Instance: " + instance.toStringID() + "\n");
        //}
        // Create a scroll pane for the tree
        JScrollPane scrollPane = new JScrollPane(tree);
		JLabel label = new JLabel("In ontology engineering and knowledge representation , a TBox (Terminological Box) is a component of the Description Logic (DL) language used in the Semantic Web and OWL (Web Ontology Language). The TBox is responsible for defining the terminology and hierarchical relationships between concepts (classes) in an ontology.");
		JTextArea textArea = new JTextArea(message.toString());
		textArea.setEditable(false);
		//add the text Area in a new tab  of the JTabbedPane
		JTabbedPane tabbedPane = new JTabbedPane();

		tabbedPane.addTab("Manchester Syntax", textArea);
		tabbedPane.setTabPlacement(JTabbedPane.TOP);
		tabbedPane.setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT);
		//add title to the tab
		String manchesterSyntaxList = ontology.getAxioms().stream().map(OWLAxiom::getAxiomWithoutAnnotations).map(axiom -> axiom.toString()).collect(Collectors.joining("\n"));
		
		//convert previous string to a collection 
		Collection<String> manchesterSyntaxCollection = Arrays.asList(manchesterSyntaxList.split("\n"));
		// order the collection alphabetically
		manchesterSyntaxCollection = manchesterSyntaxCollection.stream().sorted().collect(Collectors.toList());
		

		String manchesterSyntax = manchesterSyntaxCollection.toString();
		//remove the brackets
		manchesterSyntax = manchesterSyntax.substring(1, manchesterSyntax.length()-1);
		textArea.setText(manchesterSyntax);


		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		JScrollPane scrollPane2 = new JScrollPane(textArea);
		scrollPane2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	
		
		tabbedPane.addTab("Box",scrollPane);
		tabbedPane.addTab("Manchester Syntax",scrollPane2);
		
		//set the the active tab to be the first one
		//add the label and the tabbedPane to the frame
		tabbedPane.setSelectedIndex(0);
		frame.add(label);
		frame.add(tabbedPane);
        frame.setVisible(true);

    }

    protected void disposeOWLView() {
        // Dispose of the window when the view component is closed
        frame.dispose();
    }
	public void initialise() throws Exception {
	}

	public void dispose() throws Exception {
	}
	public void actionPerformed(ActionEvent event) {
		StringBuilder message = new StringBuilder("\n");

		OWLModelManager manager = getOWLModelManager();
		OWLOntology ontology = manager.getActiveOntology();
		OWLDataFactory factory = manager.getOWLDataFactory();
		//IRI ontologyIRI = factory.getOWLNothing().getIRI();
		
		//list all properties of ontology
		//for (OWLObjectProperty property : ontology.getObjectPropertiesInSignature()) {
		//	message.append(property.getIRI());
		//	message.append("\n");

		//}

		//message.append("Classes:" + ontology.getClassesInSignature().size());
		//message.append("Axioms:" + ontology.getAxiomCount());
		//message.append("IRI:" + ontologyIRI);
			//message.append(getOWLWorkspace().getOWLSelectionModel().getLastSelectedClass());
		//lista de clases

		try {
			initialiseOWLView(ontology);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//message.append(ontology.getClassesInSignature());
		//message.append("The currently data property");
		//message.append(getOWLWorkspace().getOWLSelectionModel().getLastSelectedDataProperty());
		//message.append(ontologyIRI);
		//message.append("Axioms: " +ontology.getAxioms());
		//message.append("OntoID: " + ontology.getOntologyID());
			//message.append("IRI: " + ontology.getOntologyID().getOntologyIRI());
		OWLModelManager modelManager = getOWLModelManager();
		
		//Crea un instancia
		//IRI new_ontologyIRI = IRI.create("newClass");
		//OWLClass newClass = factory.getOWLClass(new_ontologyIRI);
		//OWLDeclarationAxiom declarationAxiom = factory.getOWLDeclarationAxiom(newClass);
        //manager.applyChange(new AddAxiom(ontology, declarationAxiom));
	
		//Set<OWLOntology> activeOntologies = modelManager.getActiveOntologies();
        //message.append("Active ontologies: " + activeOntologies.size());  
		 // Create a reasoner
       
		//JOptionPane.showMessageDialog(getOWLWorkspace(), message.toString(), "Hello", JOptionPane.INFORMATION_MESSAGE);
	}
}
