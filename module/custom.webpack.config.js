
module.exports = require('./scalajs.webpack.config');

// Set target to fix problem not finding 'fs'
module.exports.target = "node";

