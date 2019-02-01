export class NativeEventEmitter {
    readonly name: string;

    constructor(name: string) {
        this.name = name;
    }

    subscribe = (event: string, handler: (args?: any) => void) => {
        let res = global.NativeEventEmitter.subscribe(this.name, event, handler)
        return () => {
            res();
        }
    }
}