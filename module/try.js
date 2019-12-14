lib = require('./target/scala-2.12/scalajs-bundler/main/apexassist-fastopt');
console.log(lib.ServerOps);
lib.ServerOps.setJarFile("jars/apexlink-0.5.jar");
lib.ServerOps.getServer().then(impl => {
    org = impl.newOrg();
    console.log(org)
}).catch(err => {
    console.log(err.message());
});

