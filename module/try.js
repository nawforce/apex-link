lib = require('./target/scala-2.12/scalajs-bundler/main/apexlink-fastopt');
lib.ServerOps.setJarFile("jars/apexlink-0.5.jar");
lib.ServerOps.getServer().then(impl => {
    org = impl.newOrg();
    console.log("Org created: " + org)
}).catch(err => {
    console.log(err.message());
});

