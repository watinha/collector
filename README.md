Collector
=========
[![Build Status](https://api.travis-ci.org/watinha/collector.svg?branch=master)](https://travis-ci.org/watinha/collector)
The Collector is a crawler script developed for collecting data associated to DOM elements in web applications.
Its goal is to collect data of DOM elements as rendered in different browsers and platforms, then build a comparison file in ARFF format.
This ARFF file can be used for classifying DOM elements which present incompatibilities when rendered in a specific browser/platform.

This project was built using the following technologies:
* Selenium WebDriver - for crawling webpages
* Appium - for automatizing the crawling process in mobile browsers
* AShot - for taking full page screenshots on desktop and mobile browsers
* OpenCV - for running screenshot comparison using Chi-squared distance.
* jpHash - for further comparing screenshots.
* jQuery - for identifying the position/size of elements
* useAllFive true-visibility script - for checking elements visibility

Installation
------------
Requirements: OpenCV, Selenium Server and Appium correctly configured for running the desired environments.
Dev Requirements: Maven, for running the application and jUnit with Mockito for running unit tests.

Running the script
------------------
At the time, we have not throughly supported configuration scenarios for the tool.
In order to execute the tool, you need to manually configure the Collector (src/main/java/edu/utfpr/xbi/collector/Collector.java) with an address to a WebDriver server and the Device/Browser which will be used in that server.
The collector already presents a set of possible configurations which can be used, as examples.
We also have implemented several device configurations available in src/main/java/edu/utfpr/xbi/collector/browsers/BrowserConfigurations.java.

Then, you will need to configure the comparisons between browser/devices which you want to conduct in the Merger (src/main/java/edu/utfpr/xbi/merger/Merger).
The file already presents a set of configurations which can be used.

The results of the crawler will be reported in a result folder, which should be created in the path which the project is executed.
