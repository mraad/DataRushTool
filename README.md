DataRushTool
============

[DataRush GeoProcessing Tool ArcGIS Extension](http://thunderheadxpler.blogspot.com/2013/04/bigdata-datarush-workflow-in-arcmap.html)

This project depends on the [DataRush Esri](https://github.com/mraad/DataRushEsri) project.

Make to first install in your local maven repo arcobjects.jar from **Your Desktop Folder**\java\lib folder

    $ mvn install:install-file -Dfile=arcobjects.jar -DgroupId=com.esri -DartifactId=arcobjects -Dversion=10.1 -Dpackaging=jar -DgeneratePom=true

# Compiling and packaging

    $ mvn -P cdh4 clean package
    $ rm target/libs/arcobjects-10.1.jar

# Installing the extension in ArcMap

Copy from the **target** folder the file **DataRushTool-1.0-SNAPSHOT.jar** and the folder **libs** into **Your Desktop Folder**\java\lib\ext.  That is what it looks like on my machine:

![Alt JavaLibExt](https://dl.dropbox.com/u/2193160/javaextlib.png)

Check out [this](http://help.arcgis.com/en/arcgisdesktop/10.0/help/index.html#/A_quick_tour_of_managing_tools_and_toolboxes/003q00000001000000/) to see how to add a Toolbox and a Tool to ArcMap.
