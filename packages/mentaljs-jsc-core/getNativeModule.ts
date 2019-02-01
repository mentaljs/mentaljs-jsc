export function getNativeModule<T>(name: string): T {
    let res = global.NativeModules[name] as T
    if (!res){
        throw Error('Unable to find native module ' + name)
    }
    return res;
}