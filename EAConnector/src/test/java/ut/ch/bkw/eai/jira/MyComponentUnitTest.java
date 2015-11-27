package ut.ch.bkw.eai.jira;

import org.junit.Test;
import ch.bkw.eai.jira.api.MyPluginComponent;
import ch.bkw.eai.jira.impl.MyPluginComponentImpl;

import static org.junit.Assert.assertEquals;

public class MyComponentUnitTest
{
    @Test
    public void testMyName()
    {
        MyPluginComponent component = new MyPluginComponentImpl(null);
        assertEquals("names do not match!", "myComponent",component.getName());
    }
}