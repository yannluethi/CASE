package ch.bkw.eai.jira.workflow;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atlassian.jira.issue.MutableIssue;
import com.atlassian.jira.workflow.function.issue.AbstractJiraFunctionProvider;
import com.opensymphony.module.propertyset.PropertySet;
import com.opensymphony.workflow.WorkflowException;

/**
 * This is the post-function class that gets executed at the end of the transition.
 * Any parameters that were saved in your factory class will be available in the transientVars Map.
 */
public class CreateEAModelFunction extends AbstractJiraFunctionProvider
{
    private static final Logger LOG = LoggerFactory.getLogger("atlassian.plugin");
    public static final String FIELD_MESSAGE = "messageField";

    @SuppressWarnings("rawtypes")
	public void execute(Map transientVars, Map args, PropertySet ps) throws WorkflowException
    {		
    	LOG.info("EA Connector started.");
		MutableIssue issue = getIssue(transientVars);
		
		String diagramName = issue.getSummary();
		String epicName = issue.getDescription();
		
		LOG.info("Starting new Thread");
		Runnable modelCreator = new CreateEAModelRunnable(epicName, diagramName);
		new Thread(modelCreator).start();
	}
}