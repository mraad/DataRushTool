package com.esri;

import com.esri.arcgis.geodatabase.IEnumGPName;
import com.esri.arcgis.geodatabase.IGPName;
import com.esri.arcgis.geoprocessing.EnumGPName;
import com.esri.arcgis.geoprocessing.GPFunctionName;
import com.esri.arcgis.geoprocessing.IEnumGPEnvironment;
import com.esri.arcgis.geoprocessing.IGPFunction;
import com.esri.arcgis.geoprocessing.IGPFunctionFactory;
import com.esri.arcgis.interop.AutomationException;
import com.esri.arcgis.interop.extn.ArcGISCategories;
import com.esri.arcgis.interop.extn.ArcGISExtension;
import com.esri.arcgis.system.IUID;
import com.esri.arcgis.system.UID;

import java.io.IOException;
import java.util.UUID;

/**
 */
@ArcGISExtension(categories = {ArcGISCategories.GPFunctionFactories})
public class DataRushFunctionFactory implements IGPFunctionFactory
{

    private static final long serialVersionUID = 1L;

    private static final String NAME = "DataRush Tools";

    public IUID getCLSID() throws IOException, AutomationException
    {
        final UID uid = new UID();
        uid.setValue("{" + UUID.nameUUIDFromBytes(this.getClass().getName().getBytes()) + "}");
        return uid;
    }

    public String getName() throws IOException, AutomationException
    {
        return NAME;
    }

    public String getAlias() throws IOException, AutomationException
    {
        return NAME;
    }

    public IGPFunction getFunction(final String s) throws IOException, AutomationException
    {
        if (DataRushTool.NAME.equalsIgnoreCase(s))
        {
            return new DataRushTool();
        }
        return null;
    }

    public IGPName getFunctionName(final String s) throws IOException, AutomationException
    {
        if (DataRushTool.NAME.equalsIgnoreCase(s))
        {
            final GPFunctionName functionName = new GPFunctionName();
            functionName.setCategory(DataRushTool.NAME);
            functionName.setDescription(DataRushTool.NAME);
            functionName.setDisplayName(DataRushTool.NAME);
            functionName.setName(DataRushTool.NAME);
            functionName.setFactoryByRef(this);
            return functionName;
        }
        return null;
    }

    public IEnumGPName getFunctionNames() throws IOException, AutomationException
    {
        final EnumGPName nameArray = new EnumGPName();
        nameArray.add(getFunctionName(DataRushTool.NAME));
        return nameArray;
    }

    public IEnumGPEnvironment getFunctionEnvironments() throws IOException, AutomationException
    {
        return null;
    }
}