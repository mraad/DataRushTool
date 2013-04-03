package com.esri;

import com.esri.arcgis.geodatabase.IGPMessages;
import com.esri.arcgis.geodatabase.IGPValue;
import com.esri.arcgis.geoprocessing.IGPEnvironmentManager;
import com.esri.arcgis.interop.AutomationException;
import com.esri.arcgis.system.Array;
import com.esri.arcgis.system.IArray;
import com.pervasive.datarush.graphs.EngineConfig;
import com.pervasive.datarush.graphs.LogicalGraph;
import com.pervasive.datarush.json.JSON;
import com.pervasive.datarush.util.FileUtil;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 */
public class DataRushTool extends AbstractTool
{
    public final static String NAME = "DataRushTool";

    @Override
    public String getName() throws IOException, AutomationException
    {
        return NAME;
    }

    @Override
    public String getDisplayName() throws IOException, AutomationException
    {
        return NAME;
    }

    @Override
    public IArray getParameterInfo() throws IOException, AutomationException
    {
        final IArray parameters = new Array();

        addParamString(parameters, "Workflow Path", "in_workflow_path", "C:\\tmp\\workflow.dr");
        addParamString(parameters, "Workflow Properties", "in_workflow_properties", "C:\\tmp\\workflow.properties");
        addParamString(parameters, "Datarush URL", "in_datarush_url", "local");
        addParamString(parameters, "Num Parallelism", "in_num_parallelism", "0");

        return parameters;
    }

    @Override
    protected void doExecute(
            final IArray parameters,
            final IGPMessages messages,
            final IGPEnvironmentManager environmentManager) throws Exception
    {
        final IGPValue workflowPath = gpUtilities.unpackGPValue(parameters.getElement(0));
        final IGPValue workflowProp = gpUtilities.unpackGPValue(parameters.getElement(1));
        final IGPValue datarushURL = gpUtilities.unpackGPValue(parameters.getElement(2));
        final IGPValue numParallelism = gpUtilities.unpackGPValue(parameters.getElement(3));

        final JSON json = new JSON();
        final String text = FileUtil.readFileString(new File(workflowPath.getAsText()), Charset.defaultCharset());
        final LogicalGraph graph = json.parse(text, LogicalGraph.class);
        final Properties properties = new Properties();
        final Reader reader = new FileReader(workflowProp.getAsText());
        try
        {
            properties.load(reader);
        }
        finally
        {
            reader.close();
        }
        final Set<Map.Entry<Object, Object>> entries = properties.entrySet();
        for (final Map.Entry<Object, Object> entry : entries)
        {
            graph.setProperty(entry.getKey().toString(), entry.getValue());
        }
        final String datarushURLAsText = datarushURL.getAsText();
        if ("local".equalsIgnoreCase(datarushURLAsText))
        {
            graph.run();
        }
        else
        {
            graph.run(EngineConfig.engine().
                    parallelism(Integer.parseInt(numParallelism.getAsText())).
                    monitored(false).
                    cluster(datarushURLAsText));
        }
    }
}
