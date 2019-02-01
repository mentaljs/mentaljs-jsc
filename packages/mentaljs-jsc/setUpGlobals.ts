if (global.GLOBAL === undefined) {
    global.GLOBAL = global;
}

if (global.window === undefined) {
    global.window = global;
}

global.process = global.process || {};
global.process.env = global.process.env || {};