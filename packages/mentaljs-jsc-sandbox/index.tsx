import * as React from 'react';
import { AppRegistry } from "../mentaljs-jsc-view/AppRegistry";
import { createView } from "../mentaljs-jsc-view";

// import { NativeEventEmitter } from "../mentaljs-jsc-core/NativeEventEmitter";
// import { getNativeModule } from "../mentaljs-jsc-core/getNativeModule";

// const eventEmitter = new NativeEventEmitter('sandbox');
// const nativeModule = getNativeModule<{ method: () => void }>('SandboxModule');

// eventEmitter.subscribe('sample', (args) => {
//     console.log('Event received: ' + args);
// });

// setInterval(() => { nativeModule.method() }, 1000);

const XView = createView<{ width?: number, height?: number, backgroundColor?: string }>('XView');

const RootView = () => {
    return (
        <XView>
            <XView width={100} height={100} backgroundColor="red" />
            <XView width={100} height={100} backgroundColor="green" />
            <XView width={100} height={100} backgroundColor="blue" />
        </XView>
    )
}

AppRegistry.registerView('Root', () => RootView);