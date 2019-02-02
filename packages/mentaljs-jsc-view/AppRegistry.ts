import * as React from 'react';
import { NativeEventEmitter } from '../mentaljs-jsc-core/NativeEventEmitter';
import { ViewRenderInstance } from './internals/ViewRenderInstance';



class AppRegistryInstance {
    private map = new Map<string, () => React.ComponentType<{}>>();
    private eventEmitter = new NativeEventEmitter('AppRegistry');
    private instances = new Map<number, ViewRenderInstance>();

    constructor() {
        this.eventEmitter.subscribe('start', (arg) => {
            this.startView(arg.name, arg.id);
        });

        this.eventEmitter.subscribe('stop', (arg) => {
            this.stopView(arg.id);
        });
    }

    private startView(name: string, id: number) {
        console.log('Start view: ' + name + ', ' + id);
        this.instances.set(id, new ViewRenderInstance(id, this.map.get(name)()))
    }

    private stopView(id: number) {
        console.log('Stop view: ' + id);
    }

    registerView(name: string, view: () => React.ComponentType<{}>) {
        this.map.set(name, view);
    }
}

export const AppRegistry = new AppRegistryInstance();