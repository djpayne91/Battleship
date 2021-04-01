# Battleship
Repository for my implementation of Battleship

It uses JavaFX and fxml files for the GUI along with Java for the controllers. <br>

Cross-platform support is still very shaky, but it currently works on Windows 10 with the latest JRE installed.

The easiest way to run it is:
<ol>
<li>Ensure you have the latest version of Java installed.</li>
<li>Download the included MyBattleship.jar file</li>
<li>Open a terminal and navigate to the location of the jar</li>
<li>run the command <code>java -jar MyBattleship.jar</code></li>
</ol>

If the above doesn't work,:
<ol>
<li>Ensure you have the latest version of Java installed.</li>
<li>Download the JavaFXSDK for your platform from <a href="https://gluonhq.com/products/javafx/">here</a></li>
<li>Open a terminal, clone the repository, and navigate to the /out/production/MyBattleship  folder</li>
<li>run the command <code>java --module-path "%PATH_TO_JAVAFX%\lib" --add-modules=javafx.controls,javafx.fxml main/Launcher</code> replacing %PATH_TO_JAVAFX% with the location of the root folder of the SDK</li>
</ol>
