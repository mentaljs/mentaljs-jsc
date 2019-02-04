import * as React from 'react';
import { AppRegistry } from "../mentaljs-jsc-view/AppRegistry";
import { createView } from "../mentaljs-jsc-view";
import { processColor } from '../mentaljs-jsc-view/internals/processColor';

// import { NativeEventEmitter } from "../mentaljs-jsc-core/NativeEventEmitter";
// import { getNativeModule } from "../mentaljs-jsc-core/getNativeModule";

// const eventEmitter = new NativeEventEmitter('sandbox');
// const nativeModule = getNativeModule<{ method: () => void }>('SandboxModule');

// eventEmitter.subscribe('sample', (args) => {
//     console.log('Event received: ' + args);
// });

// setInterval(() => { nativeModule.method() }, 1000);

const XViewNative = createView<{ width?: number | null, height?: number | null, backgroundColor?: number | null, marginLeft?: number, opacity?: number, animate?: string, onPress?: () => void }>('XView');

const XView = (props: { width?: number | null, height?: number | null, backgroundColor?: string | null, marginLeft?: number, children?: any, onPress?: () => void, opacity?: number, animate?: string }) => {
    let { backgroundColor, ...other } = props;
    return (<XViewNative {...other} backgroundColor={processColor(backgroundColor)} />)
}

const View = XView;

class RootView extends React.Component<{}, { color: string }> {
    state = {
        color: 'red'
    }
    render() {

        let start = Date.now();

        let res = (<XView>
            <XView width={100} height={100} backgroundColor="green" />
            <XView
                width={this.state.color === 'red' ? 100 : 120}
                height={100}
                backgroundColor="red"
                marginLeft={this.state.color === 'red' ? 0 : 50}
                opacity={this.state.color === 'red' ? 1 : 0.6}
                animate="all"
            />
            <XView width={100} height={100} backgroundColor="blue" onPress={() => this.setState({ color: (this.state.color === 'red' ? 'blue' : 'red') })} animate="all" />


            {this.state.color === 'red' && (<View>
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
                <View width={100} height={100} backgroundColor="red" />
                <View width={100} height={100} backgroundColor="blue" />
            </View>
            )}
        </XView>)

        console.log('UIManager: render in ' + (Date.now() - start) + ' ms');

        return res;
    }
}
// const RootView = () => {
//     let [color, setColor] = React.useState('red')
//     return (

//     )
// }

AppRegistry.registerView('Root', () => RootView);