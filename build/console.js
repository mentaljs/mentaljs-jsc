var inspect = (function () {
    function inspect(obj, opts) {
        var ctx = {
            seen: [],
            stylize: stylizeNoColor,
        };
        return formatValue(ctx, obj, opts.depth);
    }
    function stylizeNoColor(str, styleType) {
        return str;
    }
    function arrayToHash(array) {
        var hash = {};
        array.forEach(function (val, idx) {
            hash[val] = true;
        });
        return hash;
    }
    function formatValue(ctx, value, recurseTimes) {
        var primitive = formatPrimitive(ctx, value);
        if (primitive) {
            return primitive;
        }
        var keys = Object.keys(value);
        var visibleKeys = arrayToHash(keys);
        if (isError(value) &&
            (keys.indexOf('message') >= 0 || keys.indexOf('description') >= 0)) {
            return formatError(value);
        }
        if (keys.length === 0) {
            if (isFunction(value)) {
                var name = value.name ? ': ' + value.name : '';
                return ctx.stylize('[Function' + name + ']', 'special');
            }
            if (isRegExp(value)) {
                return ctx.stylize(RegExp.prototype.toString.call(value), 'regexp');
            }
            if (isDate(value)) {
                return ctx.stylize(Date.prototype.toString.call(value), 'date');
            }
            if (isError(value)) {
                return formatError(value);
            }
        }
        var base = '', array = false, braces = ['{', '}'];
        if (isArray(value)) {
            array = true;
            braces = ['[', ']'];
        }
        if (isFunction(value)) {
            var n = value.name ? ': ' + value.name : '';
            base = ' [Function' + n + ']';
        }
        if (isRegExp(value)) {
            base = ' ' + RegExp.prototype.toString.call(value);
        }
        if (isDate(value)) {
            base = ' ' + Date.prototype.toUTCString.call(value);
        }
        if (isError(value)) {
            base = ' ' + formatError(value);
        }
        if (keys.length === 0 && (!array || value.length == 0)) {
            return braces[0] + base + braces[1];
        }
        if (recurseTimes < 0) {
            if (isRegExp(value)) {
                return ctx.stylize(RegExp.prototype.toString.call(value), 'regexp');
            }
            else {
                return ctx.stylize('[Object]', 'special');
            }
        }
        ctx.seen.push(value);
        var output;
        if (array) {
            output = formatArray(ctx, value, recurseTimes, visibleKeys, keys);
        }
        else {
            output = keys.map(function (key) {
                return formatProperty(ctx, value, recurseTimes, visibleKeys, key, array);
            });
        }
        ctx.seen.pop();
        return reduceToSingleString(output, base, braces);
    }
    function formatPrimitive(ctx, value) {
        if (isUndefined(value))
            return ctx.stylize('undefined', 'undefined');
        if (isString(value)) {
            var simple = "'" +
                JSON.stringify(value)
                    .replace(/^"|"$/g, '')
                    .replace(/'/g, "\\'")
                    .replace(/\\"/g, '"') +
                "'";
            return ctx.stylize(simple, 'string');
        }
        if (isNumber(value))
            return ctx.stylize('' + value, 'number');
        if (isBoolean(value))
            return ctx.stylize('' + value, 'boolean');
        if (isNull(value))
            return ctx.stylize('null', 'null');
    }
    function formatError(value) {
        return '[' + Error.prototype.toString.call(value) + ']';
    }
    function formatArray(ctx, value, recurseTimes, visibleKeys, keys) {
        var output = [];
        for (var i = 0, l = value.length; i < l; ++i) {
            if (hasOwnProperty(value, String(i))) {
                output.push(formatProperty(ctx, value, recurseTimes, visibleKeys, String(i), true));
            }
            else {
                output.push('');
            }
        }
        keys.forEach(function (key) {
            if (!key.match(/^\d+$/)) {
                output.push(formatProperty(ctx, value, recurseTimes, visibleKeys, key, true));
            }
        });
        return output;
    }
    function formatProperty(ctx, value, recurseTimes, visibleKeys, key, array) {
        var name, str, desc;
        desc = Object.getOwnPropertyDescriptor(value, key) || { value: value[key] };
        if (desc.get) {
            if (desc.set) {
                str = ctx.stylize('[Getter/Setter]', 'special');
            }
            else {
                str = ctx.stylize('[Getter]', 'special');
            }
        }
        else {
            if (desc.set) {
                str = ctx.stylize('[Setter]', 'special');
            }
        }
        if (!hasOwnProperty(visibleKeys, key)) {
            name = '[' + key + ']';
        }
        if (!str) {
            if (ctx.seen.indexOf(desc.value) < 0) {
                if (isNull(recurseTimes)) {
                    str = formatValue(ctx, desc.value, null);
                }
                else {
                    str = formatValue(ctx, desc.value, recurseTimes - 1);
                }
                if (str.indexOf('\n') > -1) {
                    if (array) {
                        str = str
                            .split('\n')
                            .map(function (line) {
                            return '  ' + line;
                        })
                            .join('\n')
                            .substr(2);
                    }
                    else {
                        str =
                            '\n' +
                                str
                                    .split('\n')
                                    .map(function (line) {
                                    return '   ' + line;
                                })
                                    .join('\n');
                    }
                }
            }
            else {
                str = ctx.stylize('[Circular]', 'special');
            }
        }
        if (isUndefined(name)) {
            if (array && key.match(/^\d+$/)) {
                return str;
            }
            name = JSON.stringify('' + key);
            if (name.match(/^"([a-zA-Z_][a-zA-Z_0-9]*)"$/)) {
                name = name.substr(1, name.length - 2);
                name = ctx.stylize(name, 'name');
            }
            else {
                name = name
                    .replace(/'/g, "\\'")
                    .replace(/\\"/g, '"')
                    .replace(/(^"|"$)/g, "'");
                name = ctx.stylize(name, 'string');
            }
        }
        return name + ': ' + str;
    }
    function reduceToSingleString(output, base, braces) {
        var numLinesEst = 0;
        var length = output.reduce(function (prev, cur) {
            numLinesEst++;
            if (cur.indexOf('\n') >= 0)
                numLinesEst++;
            return prev + cur.replace(/\u001b\[\d\d?m/g, '').length + 1;
        }, 0);
        if (length > 60) {
            return (braces[0] +
                (base === '' ? '' : base + '\n ') +
                ' ' +
                output.join(',\n  ') +
                ' ' +
                braces[1]);
        }
        return braces[0] + base + ' ' + output.join(', ') + ' ' + braces[1];
    }
    function isArray(ar) {
        return Array.isArray(ar);
    }
    function isBoolean(arg) {
        return typeof arg === 'boolean';
    }
    function isNull(arg) {
        return arg === null;
    }
    function isNullOrUndefined(arg) {
        return arg == null;
    }
    function isNumber(arg) {
        return typeof arg === 'number';
    }
    function isString(arg) {
        return typeof arg === 'string';
    }
    function isSymbol(arg) {
        return typeof arg === 'symbol';
    }
    function isUndefined(arg) {
        return arg === void 0;
    }
    function isRegExp(re) {
        return isObject(re) && objectToString(re) === '[object RegExp]';
    }
    function isObject(arg) {
        return typeof arg === 'object' && arg !== null;
    }
    function isDate(d) {
        return isObject(d) && objectToString(d) === '[object Date]';
    }
    function isError(e) {
        return (isObject(e) &&
            (objectToString(e) === '[object Error]' || e instanceof Error));
    }
    function isFunction(arg) {
        return typeof arg === 'function';
    }
    function objectToString(o) {
        return Object.prototype.toString.call(o);
    }
    function hasOwnProperty(obj, prop) {
        return Object.prototype.hasOwnProperty.call(obj, prop);
    }
    return inspect;
})();
function formatMessage(message) {
    var optionalParams = [];
    for (var _i = 1; _i < arguments.length; _i++) {
        optionalParams[_i - 1] = arguments[_i];
    }
    var str;
    if (arguments.length === 1 && typeof arguments[0] === 'string') {
        str = arguments[0];
    }
    else {
        str = Array.prototype.map
            .call(arguments, function (arg) {
            return inspect(arg, { depth: 10 });
        })
            .join(', ');
    }
    return str;
}
var console = {
    log: function (message) {
        var optionalParams = [];
        for (var _i = 1; _i < arguments.length; _i++) {
            optionalParams[_i - 1] = arguments[_i];
        }
        NativeModules.Console.log(formatMessage.apply(void 0, [message].concat(optionalParams)));
    },
    info: function (message) {
        var optionalParams = [];
        for (var _i = 1; _i < arguments.length; _i++) {
            optionalParams[_i - 1] = arguments[_i];
        }
        NativeModules.Console.log(formatMessage.apply(void 0, [message].concat(optionalParams)));
    },
    debug: function (message) {
        var optionalParams = [];
        for (var _i = 1; _i < arguments.length; _i++) {
            optionalParams[_i - 1] = arguments[_i];
        }
        NativeModules.Console.debug(formatMessage.apply(void 0, [message].concat(optionalParams)));
    },
    trace: function (message) {
        var optionalParams = [];
        for (var _i = 1; _i < arguments.length; _i++) {
            optionalParams[_i - 1] = arguments[_i];
        }
        NativeModules.Console.debug(formatMessage.apply(void 0, [message].concat(optionalParams)));
    },
    error: function (message) {
        var optionalParams = [];
        for (var _i = 1; _i < arguments.length; _i++) {
            optionalParams[_i - 1] = arguments[_i];
        }
        NativeModules.Console.error(formatMessage.apply(void 0, [message].concat(optionalParams)));
    },
    warn: function (message) {
        var optionalParams = [];
        for (var _i = 1; _i < arguments.length; _i++) {
            optionalParams[_i - 1] = arguments[_i];
        }
        NativeModules.Console.warn(formatMessage.apply(void 0, [message].concat(optionalParams)));
    }
};
