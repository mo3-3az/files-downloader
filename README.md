<h3>Agoda coding test - Files Downloader</h3>


Application will be build by maven. https://maven.apache.org/<br/>
Application is based on Java 8.<br/>

<br/>
<h4>Start</h4>
Build the jar by: `mvn package` <br/>
Run the jar by: `java -jar target/filedownloader.jar` <br/>
Optionally: Configure & Run the jar by: `java -jar target/filedownloader.jar config.json`<br/>

<br/>
<h4>Modules</h4>
<ul>
<li>Configurations. Using JSON to describe the configurations.</li>
<li>Url reader. Using URL provided from Java plus custom implementations for SFTP and FTPS.</li>
<li>Stream writer. Using buffered writer/reader.</li>
<li>Files download manger. Using Executor Service to serve a thread pool.</li>
</ul>