package com.esri;

import com.esri.arcgis.geodatabase.IGPMessages;
import com.esri.arcgis.geoprocessing.BaseGeoprocessingTool;
import com.esri.arcgis.geoprocessing.GPParameter;
import com.esri.arcgis.geoprocessing.GPString;
import com.esri.arcgis.geoprocessing.GPStringType;
import com.esri.arcgis.geoprocessing.IGPEnvironmentManager;
import com.esri.arcgis.geoprocessing.esriGPParameterDirection;
import com.esri.arcgis.geoprocessing.esriGPParameterType;
import com.esri.arcgis.interop.AutomationException;
import com.esri.arcgis.system.IArray;
import com.esri.arcgis.system.IName;
import com.esri.arcgis.system.ITrackCancel;

import java.io.IOException;

/**
 */
public abstract class AbstractTool extends BaseGeoprocessingTool
{
    @Override
    public IName getFullName() throws IOException, AutomationException
    {
        final DataRushFunctionFactory dataRushFunctionFactory = new DataRushFunctionFactory();
        return (IName) dataRushFunctionFactory.getFunctionName(getName());
    }

    @Override
    public boolean isLicensed() throws IOException, AutomationException
    {
        return true;
    }

    @Override
    public void updateMessages(
            IArray parameters,
            IGPEnvironmentManager environmentManager,
            IGPMessages messages)
    {
    }

    @Override
    public void updateParameters(
            IArray parameters,
            IGPEnvironmentManager environmentManager)
    {
    }

    @Override
    public String getMetadataFile() throws IOException, AutomationException
    {
        return null;
    }

    @Override
    public void execute(
            IArray parameters,
            ITrackCancel trackCancel,
            IGPEnvironmentManager environmentManager,
            IGPMessages messages) throws IOException, AutomationException
    {
        try
        {
            doExecute(parameters, messages, environmentManager);
        }
        catch (Throwable t)
        {
            messages.addMessage(t.getMessage());
            for (final StackTraceElement stackTraceElement : t.getStackTrace())
            {
                messages.addMessage(stackTraceElement.toString());
            }
        }
    }

    protected void addParamString(
            final IArray parameters,
            final String displayName,
            final String name,
            final String value) throws IOException
    {
        final GPParameter parameter = new GPParameter();
        parameter.setDirection(esriGPParameterDirection.esriGPParameterDirectionInput);
        parameter.setDisplayName(displayName);
        parameter.setName(name);
        parameter.setParameterType(esriGPParameterType.esriGPParameterTypeRequired);
        parameter.setDataTypeByRef(new GPStringType());
        final GPString gpString = new GPString();
        gpString.setValue(value);
        parameter.setValueByRef(gpString);
        parameters.add(parameter);
    }

    protected abstract void doExecute(
            IArray parameters,
            IGPMessages messages,
            IGPEnvironmentManager environmentManager) throws Exception;
}
