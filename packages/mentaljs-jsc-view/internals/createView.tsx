export function createView<T = {}>(name: string) {
    return (props: T & { children?: any }) => {
        return <asyncview viewName={name} {...props}/>
    }
}