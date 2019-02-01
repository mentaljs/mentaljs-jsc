
let subscriptions = new Map<string, Map<string, ((args?: any) => void)[]>>()

JSModules.EventEmitterJS = {
    postMessage: (name: string, event: string, args?: any) => {
        console.log('post received: ' + name + ': ' + event);
        let s = subscriptions.get(name);
        if (s) {
            console.log('found item');
            let e = s.get(event);
            if (e && e.length > 0) {
                console.log('found item2');
                for (let c of e) {
                    c(args)
                }
            }
        }
    }
}

global.NativeEventEmitter = {
    subscribe: (name: string, event: string, handler: (args?: any) => void) => {
        console.log('register ' + name + ': ' + event);
        let s = subscriptions.get(name)
        if (!s) {
            s = new Map()
            subscriptions.set(name, s)
        }
        let e = s.get(event)
        if (!e) {
            e = []
            s.set(event, e);
        }

        e.push(handler);

        return () => {
            let ind = e.findIndex((c) => c === handler)
            if (ind >= 0) {
                e.splice(ind, 1)
            } else {
                console.warn('NativeEventEmitter: Double unsubscribe detected');
            }
        }
    }
};