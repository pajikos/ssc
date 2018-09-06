# Selenium Screenshot Comparator

The Selenium Screenshot Comparator (SSC) could compare any of two web pages in different browsers and capture their differences not only graphically, but also numerically. Library uses public available tools for its functionality (Selenium, Imagemagick).

The contribution of this library is in a solution that, as the only one from similar tools available on the market, tries to automate the testing process compatibility of appearance of web applications.

## The Library Installation Guide

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Adding the library in to your project

Download or build your own jar file and import into your maven repository:
```
mvn install:install-file -Dfile=C:\path\to\file\selenium-screenshot-comparator-0.1.jar -DgroupId=cz.vse.kit.ssc -DartifactId=selenium-screenshot-comparator -Dversion=0.1 -DpomFile=C:\path\to\file\selenium-screenshot-comparator-0.1.pom -Dpackaging=jar
```
Build folder contains two subfolders: build containing im4java library (im4java web page), because this utility library is not available in any public Maven repository. If you want to add your own im4java jar into your Maven, download build from folder without_im4java. In most cases the build containing im4java will be sufficient.

Add project dependency into project pom.xml file: 
```
<dependency> 
  <groupId>cz.vse.kit.ssc</groupId> 
  <artifactId>selenium-screenshot-comparator</artifactId> 
  <version>0.1-SNAPSHOT</version> <scope>test</scope> 
</dependency>
```
### Installation ImageMagick

For using comparator capabilities, you need to install ImageMagick into your operation system. You can download installation file from the official project page [ImageMagick Binary Releases](http://www.imagemagick.org/script/binary-releases.php). It is available for Unix, Mac OS X, iOS, Windows.

### Adding PATH into your system enviroment
The library need to know, how to run ImageMagick, so you need to set enviroment variable with name IM4JAVA_TOOLPATH with value as path to your ImageMagick installation folder, for example C:\Program Files\ImageMagick-6.8.0-Q16.

If you can not set this variable, you can set the path in source code: 
```
String myPath="C:\\Programs\\ImageMagick"; 
ProcessStarter.setGlobalSearchPath(myPath);

```
## Basic Use of The Library

Library uses Selenium for interacting with web pages. So the you do not have to learn new syntax. All functionality is available through Compatibility Tester class.

### Create a new instance of the main class:
```
CompatibilityTester compatibilityTester = new CompatibilityTester();
```
### Create a new instance of Selenium web browser (Any class, that extends WebDriver):
```
FirefoxDriver driver = new FirefoxDriver();
```
### Open the required page:
```
driver.get("http://www.vse.cz/");
```
### Take a new screenshot and save to a local variable (value "home" is a unique indentifier of screenshot):
```
Screenshot screenshot = compatibilityTester.takeScreenshot("home", driver);
```
### Save variable screenshot into png file:
```
compatibilityTester.saveScreenshotToFile(screenshot, "D:/temp/ssc");
```
## Using Screenshot Repository
You can use The Screenshot Repository capability for automatic saving taked screenshots. At first you need to set the path, where save new screenshots. It exist's two possible ways:

The first way:
```
CompatibilityTester compatibilityTester = new CompatibilityTester("D:/temp/ssc");
```
The second way:
```
compatibilityTester.setScreenshotRepository(new ScreenshotFileRepository("D:/temp/ssc"));
```
### Now you can call simply method takeScreenshotAndSaveToRepo:
```
compatibilityTester.takeScreenshotAndSaveToRepo("home", driver);
```
### After that, you may need to search in saved screenshots. The Screenshot Repository has capabilities for getting required screenshots. The searching is based on a Query By Example principle.
```
ScreenshotRepository repository = compatibilityTester.getScreenshotRepository();
Screenshot queryScreenshot = new Screenshot();
queryScreenshot.setBrowserName("firefox");
queryScreenshot.setId("home");
```
### Getting the last screenshot, that corresponds with query object:
```
Screenshot screenshot = repository.getLastScreenshotByExample(queryScreenshot);
```
### Getting the last two screnshots in timeline:
```
List<Screenshot> lastTwoScreens = repository.getLastTwoScreenshotsByExample(queryScreenshot);
```
### Getting the all screenshots, that meet the request criteria:
```
List listOfScreens = repository.getScreenshotsByExample(queryScreenshot);
```
## Using Screenshot Comparator
This functionality is for graphical compating of two screenshots:
```
driver.get("http://www.vse.cz/");
Screenshot homeScreenshot = compatibilityTester.takeScreenshot("home", driver);
driver.findElement(By.linkText("Profil školy")).click();
Screenshot profilScreenshot = compatibilityTester.takeScreenshot("profil", driver);
Screenshot compareResult = compatibilityTester.compare(homeScreenshot, profilScreenshot);
```
## The Screenshot comparing
You can use another method composeDifference.

## Using Screenshot Similarity
You can compute mathematical difference between two screenshots:
```
float similarity = compatibilityTester.computeSimilarity(homeScreenshot, profilScreenshot);
```
This method returns the value between 0 and 1 (0 means no compliance, 1 means that both images are the same).

## Authors

* **Pavel Sklenar** - *Initial work* - [GIT](https://github.com/pajikos)

## License

This project is licensed under the licence-lgpl-3.0 - see the [licence-lgpl-3.0.txt](licence-lgpl-3.0.txt) file for details

