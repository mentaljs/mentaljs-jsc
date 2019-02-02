import * as React from 'react';

export function createView<T = {}>(name: string) {
    return (props: T & { children?: any }) => {
        return <asyncview asyncViewName={name} {...props}/>
    }
}