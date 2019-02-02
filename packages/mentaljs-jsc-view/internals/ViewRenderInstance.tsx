import * as React from 'react';
import { AsyncRenderer } from './ReactNativeRenderer';

export class ViewRenderInstance {
    readonly renderer: AsyncRenderer;
    constructor(id: number, Component: React.ComponentType<{}>) {
        this.renderer = new AsyncRenderer((state) => {
            console.log(state);
        }, <Component />);
        console.log(this.renderer.getState());
    }
}