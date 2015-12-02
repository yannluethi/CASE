package ch.bkw.eai.jira.workflow;

import java.util.Map;

import com.atlassian.jira.issue.MutableIssue;
import com.atlassian.jira.workflow.function.issue.AbstractJiraFunctionProvider;
import com.opensymphony.module.propertyset.PropertySet;
import com.opensymphony.workflow.WorkflowException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sparx.Collection;
import org.sparx.Diagram;
import org.sparx.DiagramObject;
import org.sparx.Element;
import org.sparx.Package;

/**
 * This is the post-function class that gets executed at the end of the transition.
 * Any parameters that were saved in your factory class will be available in the transientVars Map.
 */
public class CreateEADiagramsFunction extends AbstractJiraFunctionProvider
{
    private static final Logger log = LoggerFactory.getLogger(CreateEADiagramsFunction.class);
    public static final String FIELD_MESSAGE = "messageField";

    public void execute(Map transientVars, Map args, PropertySet ps) throws WorkflowException
    {
    	
    	org.sparx.Repository myrepository = new org.sparx.Repository();
		myrepository.OpenFile("c:/Users/Yann/TestProject.eap");
		
		MutableIssue issue = getIssue(transientVars);
		
		String diagramName = issue.getSummary();
		String epic = "Test (EAI-001)";
		
		copyTemplate(diagramName, myrepository);
		linkDiagrams(diagramName, myrepository);
		addEpic(epic, diagramName, myrepository);
	}
	
	public static void addEpic(String epicName, String name, org.sparx.Repository myrepository){
		Collection<org.sparx.Package> modelsCln = myrepository.GetModels();	//Model Collection
		
		for(short x = 0; x < modelsCln.GetCount(); x++){
			if(modelsCln.GetAt(x).GetName().equals("EAI Integration Model")){
				Collection<org.sparx.Package> eaiCln = modelsCln.GetAt(x).GetPackages(); //EAI Integration Model Collection
				
				for(short y = 0; y < eaiCln.GetCount(); y++){
					if(eaiCln.GetAt(y).GetName().equals("Anwendungsfall")){
						Collection<org.sparx.Package> usecaseViewCln = eaiCln.GetAt(y).GetPackages(); // Use Cases Collection
						int epicID = 0;
						DiagramObject epicLink = null;
						
						for(short z = 0; z < usecaseViewCln.GetCount(); z++){
							if(usecaseViewCln.GetAt(z).GetName().equals("Auftrag")){
								Collection<org.sparx.Package> epicCol = usecaseViewCln.GetAt(z).GetPackages();
								
								for(short s = 0; s < epicCol.GetCount(); s++){
									if(epicCol.GetAt(s).GetName().equals(epicName)){
										epicID = epicCol.GetAt(s).GetElement().GetElementID();
									}
								}	
								
							}
						}
						
						for(short z = 0; z < usecaseViewCln.GetCount(); z++){
							if(usecaseViewCln.GetAt(z).GetName().equals("Anwendungsfall")){
								Collection<org.sparx.Element> usecaseCln = usecaseViewCln.GetAt(z).GetElements();
								
								for(short s = 0; s < usecaseCln.GetCount(); s++){
									if(usecaseCln.GetAt(s).GetName().equals(name)){
										Collection<org.sparx.Diagram> diagrams = usecaseCln.GetAt(s).GetDiagrams();
										
										for(short t = 0; t < diagrams.GetCount(); t++){
											if(diagrams.GetAt(t).GetName().equals(name)){
												Diagram ucDiagram = diagrams.GetAt(t);
												epicLink = ucDiagram.GetDiagramObjects().AddNew("l=30;r=180;t=310;b=380","");
												epicLink.Update();
												epicLink.SetElementID(epicID);
												epicLink.Update();
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}
					
	
	public static void linkDiagrams(String name, org.sparx.Repository myrepository){
			
		Collection<org.sparx.Package> modelsCln = myrepository.GetModels();	//Model Collection
		
		for(short x = 0; x < modelsCln.GetCount(); x++){
			if(modelsCln.GetAt(x).GetName().equals("EAI Integration Model")){
				Collection<org.sparx.Package> eaiCln = modelsCln.GetAt(x).GetPackages(); //EAI Integration Model Collection
				
				for(short y = 0; y < eaiCln.GetCount(); y++){
					if(eaiCln.GetAt(y).GetName().equals("Anwendungsfall")){
						Collection<org.sparx.Package> usecaseViewCln = eaiCln.GetAt(y).GetPackages(); // Use Cases Collection
						int usecaseID = 0;
						int callgraphID = 0;
						DiagramObject ucLink1 = null;
						DiagramObject ucLink2 = null;
						DiagramObject cgLink1 = null;
						DiagramObject cgLink2 = null;
						
						for(short z = 0; z < usecaseViewCln.GetCount(); z++){
							if(usecaseViewCln.GetAt(z).GetName().equals("Anwendungsfall")){
								Collection<org.sparx.Element> usecaseCln = usecaseViewCln.GetAt(z).GetElements();
								
								for(short s = 0; s < usecaseCln.GetCount(); s++){
									if(usecaseCln.GetAt(s).GetName().equals(name)){
										usecaseID = usecaseCln.GetAt(s).GetElementID();
										Collection<org.sparx.Diagram> diagrams = usecaseCln.GetAt(s).GetDiagrams();
										
										for(short t = 0; t < diagrams.GetCount(); t++){
											if(diagrams.GetAt(t).GetName().equals(name)){
												Diagram ucDiagram = diagrams.GetAt(t);
												ucLink1 = ucDiagram.GetDiagramObjects().AddNew("l=250;r=400;t=10;b=80","");
												ucLink1.Update();
												ucLink2 = ucDiagram.GetDiagramObjects().AddNew("l=450;r=600;t=10;b=80","");
												ucLink2.Update();
											}
										}
									}
								}
							}
							if(usecaseViewCln.GetAt(z).GetName().equals("Call Graph")){
								Collection<org.sparx.Element> callgraphCln = usecaseViewCln.GetAt(z).GetElements();
									
									for(short s = 0; s < callgraphCln.GetCount(); s++){
										if(callgraphCln.GetAt(s).GetName().equals(name)){
											callgraphID = callgraphCln.GetAt(s).GetElementID();
											Collection<org.sparx.Diagram> diagrams = callgraphCln.GetAt(s).GetDiagrams();
											
											for(short t = 0; t < diagrams.GetCount(); t++){
												if(diagrams.GetAt(t).GetName().equals(name)){
													Diagram cgDiagram = diagrams.GetAt(t);
													cgLink1 = cgDiagram.GetDiagramObjects().AddNew("l=250;r=400;t=10;b=80","");
													cgLink1.Update();
													cgLink2 = cgDiagram.GetDiagramObjects().AddNew("l=450;r=600;t=10;b=80","");
													cgLink2.Update();
												}
											}
										}
									}
								}
							
								
							}
						
						ucLink1.SetElementID(callgraphID); //link diagrams
						ucLink1.Update();
						ucLink2.SetElementID(usecaseID);
						ucLink2.Update();
						cgLink1.SetElementID(callgraphID);
						cgLink1.Update();
						cgLink2.SetElementID(usecaseID);
						cgLink2.Update();
						}
					}
				}
			}
		}
		
	
	public static void copyTemplate(String name, org.sparx.Repository myrepository){
		
		Collection<org.sparx.Package> modelsCln = myrepository.GetModels();	//Model Collection
		
		for(short x = 0; x < modelsCln.GetCount(); x++){
			if(modelsCln.GetAt(x).GetName().equals("EAI Integration Model")){
				Collection<org.sparx.Package> eaiCln = modelsCln.GetAt(x).GetPackages(); //EAI Integration Model Collection
				
				for(short y = 0; y < eaiCln.GetCount(); y++){
					if(eaiCln.GetAt(y).GetName().equals("Anwendungsfall")){
						Collection<org.sparx.Package> usecaseViewCln = eaiCln.GetAt(y).GetPackages(); // Use Cases Collection
						Package uctemplate = null;
						Package cgtemplate = null;
						int uctemplateID = 0;
						int cgtemplateID = 0;
						
						for(short z = 0; z < usecaseViewCln.GetCount(); z++){
							if(usecaseViewCln.GetAt(z).GetName().equals("Anwendungsfall Template")){
								uctemplate = usecaseViewCln.GetAt(z).Clone(); // Copy Use Case Template
								uctemplateID = uctemplate.GetPackageID();
							}
						}
						
						for(short z = 0; z < usecaseViewCln.GetCount(); z++){
							if(usecaseViewCln.GetAt(z).GetName().equals("Call Graph Template")){
								cgtemplate = usecaseViewCln.GetAt(z).Clone(); // Copy Call Graph Template
								cgtemplateID = cgtemplate.GetPackageID();
							}
						}
						
						for(short z = 0; z < usecaseViewCln.GetCount(); z++){
							if(usecaseViewCln.GetAt(z).GetName().equals("Anwendungsfall")){
								int packageID = usecaseViewCln.GetAt(z).GetPackageID();
								Collection<org.sparx.Element> usecase = uctemplate.GetElements();
								
								for(short s = 0; s < usecase.GetCount(); s++){
									if(usecase.GetAt(s).GetName().equals("IUC_0001: Template")){
										Element usecaseelement = usecase.GetAt(s);
										usecaseelement.SetPackageID(packageID); // Paste Template to Anwendungsfall Package
										usecaseelement.SetName(name);
										usecaseelement.Update();
										Collection<org.sparx.Diagram> ucdiagrams = usecaseelement.GetDiagrams();
										
										for(short i = 0; i < ucdiagrams.GetCount(); i++){
											if(ucdiagrams.GetAt(i).GetName().equals("IUC_0001: Template")){
												Diagram umbenennen = ucdiagrams.GetAt(i);
												umbenennen.SetName(name);
												umbenennen.Update();
											}
										}
									}
								}								
							}
						}
						
						for(short z = 0; z < usecaseViewCln.GetCount(); z++){
							if(usecaseViewCln.GetAt(z).GetName().equals("Call Graph")){
								int packageID = usecaseViewCln.GetAt(z).GetPackageID();
								Collection<org.sparx.Element> callgraph = cgtemplate.GetElements();
								
								for(short s = 0; s < callgraph.GetCount(); s++){
									if(callgraph.GetAt(s).GetName().equals("IUC_0001: Template")){
										Element callgraphelement = callgraph.GetAt(s);
										callgraphelement.SetPackageID(packageID); // Paste Template to Call Graph Package
										callgraphelement.SetName(name);
										callgraphelement.Update();
										Collection<org.sparx.Diagram> cgdiagrams = callgraphelement.GetDiagrams();
										
										for(short i = 0; i < cgdiagrams.GetCount(); i++){
											if(cgdiagrams.GetAt(i).GetName().equals("IUC_0001: Template")){
												Diagram umbenennen = cgdiagrams.GetAt(i);
												umbenennen.SetName(name);
												umbenennen.Update();
											}
										}
									}
								}								
							}
						}
						
						usecaseViewCln.Refresh();
						for(short z = 0; z < usecaseViewCln.GetCount(); z++){
							if(usecaseViewCln.GetAt(z).GetPackageID() == uctemplateID || usecaseViewCln.GetAt(z).GetPackageID() == cgtemplateID){
								usecaseViewCln.Delete(z);
							}
						}						
					}
				}
			}
		}
	

    	
//        MutableIssue issue = getIssue(transientVars);
//        String message = (String)transientVars.get(FIELD_MESSAGE);
//
//        if (null == message) {
//            message = "";
//        }
//
//        issue.setDescription(issue.getDescription() + message);
    }
}