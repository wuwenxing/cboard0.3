/**
 * Wait until the test condition is true or a timeout occurs. Useful for waiting
 * on a server response or for a ui change (fadeIn, etc.) to occur.
 *
 * @param testFx javascript condition that evaluates to a boolean,
 * it can be passed in as a string (e.g.: "1 == 1" or "$('#bar').is(':visible')" or
 * as a callback function.
 * @param onReady what to do when testFx condition is fulfilled,
 * it can be passed in as a string (e.g.: "1 == 1" or "$('#bar').is(':visible')" or
 * as a callback function.
 * @param timeOutMillis the max amount of time to wait. If not specified, 3 sec is used.
 */

"use strict";
function waitFor(testFx, onReady, timeOutMillis) {
    var maxtimeOutMillis = timeOutMillis ? timeOutMillis : 3000, //< Default Max Timout is 3s
        start = new Date().getTime(),
        condition = false,
        interval = setInterval(function() {
            if ( (new Date().getTime() - start < maxtimeOutMillis) && !condition ) {
                // If not time-out yet and condition not yet fulfilled
                condition = (typeof(testFx) === "string" ? eval(testFx) : testFx()); //< defensive code
				console.log(condition);
            } else {
				console.log(condition);
                if(!condition) {
                    // If condition still not fulfilled (timeout but condition is 'false')
                    console.log("'waitFor()' timeout");
                    phantom.exit(1);
                } else {
                    // Condition fulfilled (timeout and/or condition is 'true')
                    console.log("'waitFor()' finished in " + (new Date().getTime() - start) + "ms.");
                    typeof(onReady) === "string" ? eval(onReady) : onReady(); //< Do what it's supposed to do once the condition is fulfilled
                    clearInterval(interval); //< Stop this interval
                }
            }
        }, 250); //< repeat check every 250ms
};


var page = require('webpage').create();
var system = require('system');//系统对象
page.viewportSize = {width: 600, height: 400};
var _filePath = "D:/web/accountAnalyze/report";
var _url = system.args[1];// 系统对象第二个参数
var _batchNo = system.args[2];// 系统对象第三个参数
var _fileName = system.args[3];// 系统对象第四个参数
console.log("[PhantomJS] Opening Url:", _url);
page.open(_url, function (status) {
    // Check for page load success
    if (status !== "success") {
        console.log('open page fail!');
    } else {
        console.log("success");
        // Wait for 'signin-dropdown' to be visible
        waitFor(function() {
            // Check in the page if a specific element is now visible
            return page.evaluate(function() {
				return ($("#persistFinish").val() == 'true');
            });
        }, function() {
           console.log("The sign-in dialog should be visible now.");
		   page.render(_filePath + "/" + _batchNo + "/" + _fileName);
           phantom.exit();
        });
    }
});
