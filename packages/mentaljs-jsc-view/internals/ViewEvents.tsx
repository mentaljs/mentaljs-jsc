import { NativeEventEmitter } from "../../mentaljs-jsc-core/NativeEventEmitter";

class ViewEventsImpl {
    private nextId = 0;
    private callbacks = new Map<string, (args: any) => void>();
    private eventEmitter = new NativeEventEmitter('UIManager');

    constructor() {
        this.eventEmitter.subscribe('event', (e: any) => {
            console.log('UIManager: Received event');
            let key = e.key;
            let args = e.args;
            let cb = this.callbacks.get(key);
            if (cb) {
                cb(args)
            }
        })
    }

    registerCallback = (handler: (args: any) => void) => {
        let key = 'c' + (this.nextId++)
        console.log('UIManager: Register callback');
        this.callbacks.set(key, handler)
        return key
    }

    unregisterCallback = (key: string) => {
        this.callbacks.delete(key);
    }
}

export const ViewEvents = new ViewEventsImpl();