var exec = require('cordova/exec');


( function(){

    var FileAssociation = {
        getAssociatedData : function(arg0, success, error) {
            exec(success, error, "FileAssociation", "getAssociatedData", [arg0]);
        }
    };

    module.exports.fileAssociation = FileAssociation;

    if (!window.plugins) {
        window.plugins = {};
    }

    if (!window.plugins.fileAssociation) {
        window.plugins.fileAssociation = FileAssociation;
    }

    console.log("FileAssociation plugin initialized");

}());

