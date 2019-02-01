var __handlers = {
    guid: 1,
    pendings: []
};
var setTimeout = function (handler, timeout) {
    var args = [];
    for (var _i = 2; _i < arguments.length; _i++) {
        args[_i - 2] = arguments[_i];
    }
    var id = __handlers.guid++;
    __handlers.pendings[id] = handler;
    NativeModules.Timer.setTimeout(id, timeout || 0);
    return id;
};
var setInterval = function (handler, timeout) {
    var args = [];
    for (var _i = 2; _i < arguments.length; _i++) {
        args[_i - 2] = arguments[_i];
    }
    var id = __handlers.guid++;
    __handlers.pendings[id] = handler;
    NativeModules.Timer.setInterval(id, timeout || 0);
    return id;
};
var clearTimeout = function (handle) {
    if (handle !== undefined && handle !== null) {
        NativeModules.Timer.clearTimeout(handle);
    }
};
var clearInterval = function (handle) {
    if (handle !== undefined && handle !== null) {
        NativeModules.Timer.clearTimeout(handle);
    }
};
JSModules.JSTimer = {
    runCallbacks: function (ids) {
        for (var _i = 0, ids_1 = ids; _i < ids_1.length; _i++) {
            var i = ids_1[_i];
            var p = __handlers.pendings[i];
            p();
        }
    }
};
