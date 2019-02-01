// declare function clearTimeout(handle?: number): void;
// declare function setInterval(handler: TimerHandler, timeout?: number, ...arguments: any[]): number;

const __handlers = {
    guid: 1,
    pendings: [] as any
};

global.setTimeout = function(handler: Function, timeout?: number, ...args: any[]) {
    let id = __handlers.guid++;
    __handlers.pendings[id] = handler
    NativeModules.Timer.setTimeout(id, timeout || 0)
    return id
}

global.setInterval = function(handler: Function, timeout?: number, ...args: any[]) {
    let id = __handlers.guid++;
    __handlers.pendings[id] = handler
    NativeModules.Timer.setInterval(id, timeout || 0)
    return id
}

global.clearTimeout = function(handle?:number) {
    if (handle !== undefined && handle !== null) {
        NativeModules.Timer.clearTimeout(handle)
    }
}

global.clearInterval = function(handle?:number) {
    if (handle !== undefined && handle !== null) {
        NativeModules.Timer.clearTimeout(handle)
    }
}

JSModules.JSTimer = {
    runCallbacks: function (ids: number[]) {
        for(let i of ids) {
            let p = __handlers.pendings[i]
            p()
        }
    }
}