import * as React from 'react';
import { AsyncRenderer } from './ReactNativeRenderer';
import { getNativeModule } from '../../mentaljs-jsc-core/getNativeModule';

const native = getNativeModule<{ initView: (id: number, spec: string) => void, updateView: (id: number, spec: string) => void }>('UIManager');

export class ViewRenderInstance {
    readonly renderer: AsyncRenderer;
    constructor(id: number, Component: React.ComponentType<{}>) {
        let start = Date.now();
        console.log('UIManager: Start rendering');
        this.renderer = new AsyncRenderer((state) => {
            let start = Date.now();
            console.log('UIManager: Start rendering');
            native.updateView(id, JSON.stringify(state));
            console.log('UIManager: Completed rendering in ' + (Date.now() - start) + ' ms');
        }, <Component />);
        console.log('UIManager: Completed rendering in ' + (Date.now() - start) + ' ms');
        native.initView(id, JSON.stringify(this.renderer.getState()));
        console.log('UIManager: Posted');
    }
}