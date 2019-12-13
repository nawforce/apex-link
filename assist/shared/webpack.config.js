module.exports = {
    "entry": {
        "apexassist-fastopt": ["/Users/kevin/Projects/ApexLink/assist/shared/target/scala-2.12/scalajs-bundler/main/apexassist-fastopt-entrypoint.js"]
    },
    "output": {
        "path": "/Users/kevin/Projects/ApexLink/assist/shared/target/scala-2.12/scalajs-bundler/main",
        "filename": "[name]-library.js",
        "library": "ScalaJSBundlerLibrary",
        "libraryTarget": "var"
    },
    "devtool": "source-map",
    "module": {
        "rules": [{
            "test": new RegExp("\\.js$"),
            "enforce": "pre",
            "use": ["source-map-loader"]
        }]
    },
    "target": "node"
}
