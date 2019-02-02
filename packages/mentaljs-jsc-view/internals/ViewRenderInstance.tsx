import * as React from 'react';
import { AsyncRenderer } from './ReactNativeRenderer';
import { getNativeModule } from '../../mentaljs-jsc-core/getNativeModule';

const native = getNativeModule<{ initView: (id: number, spec: string) => void, updateView: (id: number, spec: string) => void }>('UIManager');

export class ViewRenderInstance {
    readonly renderer: AsyncRenderer;
    constructor(id: number, Component: React.ComponentType<{}>) {
        this.renderer = new AsyncRenderer((state) => {
            native.updateView(id, JSON.stringify(state));
        }, <Component />);
        native.initView(id, JSON.stringify(this.renderer.getState()));
    }
}