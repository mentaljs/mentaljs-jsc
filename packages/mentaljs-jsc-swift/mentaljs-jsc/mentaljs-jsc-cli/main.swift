//
//  main.swift
//  mentaljs-jsc-cli
//
//  Created by Steve Kite on 1/30/19.
//

import Foundation
import JavaScriptCore
import MentalJS

@objc protocol ConsoleExport: JSExport {
    func log(_ src: String)
}

@objc class ConsoleModule: NSObject, MentalModule, ConsoleExport {
    
    func register(context: JSContext) {
        context.setObject(self, forKeyedSubscript: "console" as NSCopying & NSObjectProtocol)
    }
    
    func log(_ src: String) {
        print("CONSOLE: " + src)
    }
}

let console = ConsoleModule()

let engine = MentalJSEngine(source: "console.log('Hello')", modules: [console])
