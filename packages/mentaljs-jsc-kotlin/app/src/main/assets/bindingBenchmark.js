function doBenchmark(name, count, src) {
    var start = Date.now();
    for(var i = 0; i < count; i++) {
        src();
    }
    var d = Date.now() - start;
    console.log(name + ': ' + (Date.now() - start) + ' ms, ' + (count * 1000 / d) + ' op/sec');
}

doBenchmark('Simple', 40000, function () {
    eval('');
    NativeModules.BindingBenchmarking.method()
});

//doBenchmark('One Arg', 40000, function () {
//    eval('');
//    NativeModules.BindingBenchmarking.serializedMethod('!!!!');
//});
//
//doBenchmark('Json Arg', 40000, function () {
//    eval('');
//    NativeModules.BindingBenchmarking.serializedMethod(JSON.stringify({name:'!', t: 2}));
//});
//
//doBenchmark('Args', 40000, function () {
//    eval('');
//    NativeModules.BindingBenchmarking.argsMethod('!!!', 2);
//});
//
//doBenchmark('No reflection', 400000, function () {
//    // eval('');
//    NativeModules.BindingBenchmarking.noReflectionCallback();
//});