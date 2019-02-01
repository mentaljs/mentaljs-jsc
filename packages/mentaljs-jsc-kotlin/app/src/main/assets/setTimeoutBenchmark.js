var startTime = new Date().getTime();
var previousTimeStamp = 0;
var reps = 0;
var times = {};
var timeoutDelay = 0;
var timeoutFinalStats = {};
var intervalDelay = 0;
var intervalFinalStats = {};
var theInterval;
var theTimeout;
var theBenchmarkTimeout;
var theLimit = 20;


function intervalStyle() {
  var timeStamp = new Date().getTime();
  var elapsed = timeStamp - startTime;
  var diff = timeStamp - previousTimeStamp;

  if (diff < intervalDelay) {
    //throw new Error('Timer fired early.');
    // console.log('Timer fired early - Expected:' + timeoutDelay, 'Actual:'+ diff);
  }

  reps++;

  if (previousTimeStamp === 0) {
    previousTimeStamp = timeStamp;
    return;
  }

  if (!times.hasOwnProperty(diff)) {
    times[diff] = 0;
  }

  times[diff] += 1;

  var output = 'Interval'+intervalDelay+': '+reps+' '+elapsed+' '+Math.round(reps*1000/elapsed)+' '+Math.round(elapsed/reps);

  intervalFinalStats[intervalDelay] = { fin: output, times: times };

  if (reps % 100 === 0) {
    console.log(output);
  }

  previousTimeStamp = timeStamp;
}

function timeoutStyle() {
  var timeStamp = new Date().getTime();
  var elapsed = timeStamp - startTime;
  var diff = timeStamp - previousTimeStamp;

  if (diff < timeoutDelay) {
    //throw new Error('Timer fired early.');
    // console.log('Timer fired early - Expected:' + timeoutDelay + 'Actual:' + diff);
  }

  reps += 1;

  if (previousTimeStamp === 0) {
    previousTimeStamp = timeStamp;
    theTimeout = setTimeout(timeoutStyle, timeoutDelay);
    return;
  }

  if (!times.hasOwnProperty(diff)) {
    times[diff] = 0;
  }

  times[diff] += 1;

  var output = 'Timeout'+timeoutDelay+': '+reps+' '+elapsed+' '+Math.round(reps*1000/elapsed)+' '+Math.round(elapsed/reps);

  timeoutFinalStats[timeoutDelay] = { fin: output, times: times };

  if (reps % 100 === 0) {
    console.log(output);
  }

  previousTimeStamp = timeStamp;

  theTimeout = setTimeout(timeoutStyle, timeoutDelay);
}

function reset() {
  startTime = new Date().getTime();
  previousTimeStamp = 0;
  reps = 0;
  times = {};
}

function theBenchmark() {
  intervalDelay++;
  timeoutDelay++;
  reset();
  theInterval = setInterval(intervalStyle, intervalDelay);
  setTimeout(function() {
    clearInterval(theInterval);
    reset();
    timeoutStyle();
    setTimeout(function() {
      clearTimeout(theTimeout);
      console.log('\r\n\r\n');
      console.log(JSON.stringify(intervalFinalStats, null, '\t'));
      console.log(JSON.stringify(timeoutFinalStats, null, '\t'));
      if (intervalDelay <= theLimit) {
        theBenchmarkTimeout = setTimeout(theBenchmark, 500);
      }
    }, 3000);
  }, 3000);
}

theBenchmark();