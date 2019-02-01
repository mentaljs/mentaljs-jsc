import { NativeEventEmitter } from "../mentaljs-jsc-core/NativeEventEmitter";
import { getNativeModule } from "../mentaljs-jsc-core/getNativeModule";

const eventEmitter = new NativeEventEmitter('sandbox');
const nativeModule = getNativeModule<{ method: () => void }>('SandboxModule');

eventEmitter.subscribe('sample', (args) => {
    console.log('Event received: ' + args);
});

setInterval(() => { nativeModule.method() }, 1000);