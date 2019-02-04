import * as React from 'react';
import { ViewEvents } from './ViewEvents';

// function extractCallbacks(src: any): { props: any, callbacks: any } {
//     let props: any = {};
//     let callbacks: any = {};
//     for (let k in src) {
//         let v = src[k]
//         if (typeof v === 'function') {

//         } else {
//             props[k] = v;
//         }
//     }
// }

export function createView<T extends Object = {}>(name: string) {
    let res = class NativeView extends React.PureComponent<T> {
        static displayName = name;

        registeredCallbacks: { [key: string]: string } = {}

        constructor(props: T) {
            super(props)

            for (let k in props) {
                if (typeof props[k] === 'function') {
                    this.registeredCallbacks[k] = ViewEvents.registerCallback((args) => {
                        if (this.props[k]) {
                            (this.props[k] as any)(args)
                        }
                    });
                }
            }
        }

        componentWillUpdate(nextProps: T) {
            for (let k in nextProps) {
                if (typeof nextProps[k] === 'function') {
                    if (!this.registeredCallbacks[k]) {
                        this.registeredCallbacks[k] = ViewEvents.registerCallback((args) => {
                            if (this.props[k]) {
                                (this.props[k] as any)(args)
                            }
                        })
                    }
                }
            }
        }

        componentWillUnmount() {
            for (let cb in this.registeredCallbacks) {
                ViewEvents.unregisterCallback(cb)
            }
        }

        render() {
            let callbacks: any = {};
            for (let cb in this.registeredCallbacks) {
                callbacks[cb] = this.registeredCallbacks[cb];
            }
            return <asyncview asyncViewName={name} {...this.props} {...callbacks} />
        }
    }
    return res;
    // return (props:T) => {
    //     return <asyncview asyncViewName={name} {...props} />
    // };
}