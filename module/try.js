lib = require('./target/scala-2.12/scalajs-bundler/main/apexlink-fastopt');
lib.ServerOps.setJarFile("jars/apexlink-0.5.jar");
lib.ServerOps.getServer().then(server => {
    org = server.getOrg();
    console.log("Org created: " + org);
    console.log("Unmanaged: " + org.unmanagedPackage());
    console.log("Issues: " + org.addPackage("foo", [], []));
}).catch(err => {
    console.log(err);
});

