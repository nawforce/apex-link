// Expose some globals we need
global.__dirname = __dirname;
global.require = require;

// Run check function
let apex_assist = require('./apex-assist-opt.js');
apex_assist.check(process.argv);
